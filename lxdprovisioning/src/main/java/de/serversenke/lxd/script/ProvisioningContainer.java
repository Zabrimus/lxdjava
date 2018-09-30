package de.serversenke.lxd.script;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;

import de.serversenke.lxd.client.core.model.LxdFile;
import de.serversenke.lxd.client.core.model.LxdFile.FileType;
import de.serversenke.lxd.client.core.model.LxdFile.WriteType;
import de.serversenke.lxd.script.exception.ProvisioningException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProvisioningContainer extends ScriptBase {

    private Path yamlPath;
    int startTask;
    int endTask;

    protected ProvisioningContainer(Path yamlPath, LinkedHashMap<?, ?> yaml, int startTask, int endTask) {
        super(yaml);
        this.yamlPath = yamlPath;
        this.startTask = startTask;
        this.endTask = endTask;
    }

    public void startProvisioning() {
        registerCertKey();

        List<LinkedHashMap<?, ?>> prov = Utils.get(yaml, "provisioning");

        if (startTask < 0) {
            startTask = 0;
        }

        if (endTask > prov.size()-1) {
            endTask = prov.size()-1;
        }

        // iterate over all provisioning commands
        for (int i = startTask; i <= endTask; ++i) {
            LinkedHashMap<?, ?> task = prov.get(i);

            switch (getCommand(task)) {
            case "script":
                commandScript((LinkedHashMap<?, ?>) task.get("script"));
                break;

            case "run":
                commandRun((LinkedHashMap<?, ?>) task.get("run"));
                break;

            case "upload":
                commandUpload((LinkedHashMap<?, ?>) task.get("upload"));
                break;

            case "uploadDir":
                commandUploadDir((LinkedHashMap<?, ?>) task.get("uploadDir"));
                break;

            case "createFile":
                commandCreateFile((LinkedHashMap<?, ?>) task.get("createFile"));
                break;

            default:
                System.err.println("Unknown command '" + task + "'. Ignore...");
            }
        };
    }

    private String getCommand(LinkedHashMap<?, ?> map) {
        // map must contain only one element
        return (String) map.keySet().stream().findFirst().get();
    }

    private void commandScript(LinkedHashMap<?, ?> script) {
        String label = Utils.getOrDefault(script, "", "label");
        String igne = Utils.getOrDefault(script, "false", "ignore-error");
        String container = Utils.getOrDefault(script, defaultContainerName, "container");
        String scriptc = Utils.get(script, "content");

        System.out.println(">> Execute script " + label);

        int result = uploadAndExecScript(container, scriptc, null, opListener);

        if (result != 0)
            if (!Boolean.parseBoolean(igne)) {
                throw new ProvisioningException("Executing script failed. See Logfiles...");
            } else {
                log.warn("Run '" + label + "' failed but ignored due to configuration");
        }
    }

    private void commandRun(LinkedHashMap<?, ?> run) {
        String label = Utils.getOrDefault(run, "", "label");
        String commands = Utils.get(run, "commands");
        String igne = Utils.getOrDefault(run, "false", "ignore-error");
        String container = Utils.getOrDefault(run, defaultContainerName, "container");

        System.out.println(">> Execute run " + label);

        String cmd = commands.trim();
        cmd = cmd.replaceAll("(?s)\\n\\s+", "\n");
        cmd = cmd.replaceAll("(?s)\\s*\\\\\\s*\\n", " ");
        cmd = cmd.replaceAll("\\n", " && ");

        int result = uploadAndExecScript(container, cmd, null, opListener);

        if (result != 0) {
            if (!Boolean.parseBoolean(igne)) {
                throw new ProvisioningException("Executing script failed. See Logfiles...");
            } else {
                log.warn("Script '" + label + "' failed but ignored due to configuration");
            }
        }
    }

    private void commandUpload(LinkedHashMap<?, ?> upload) {
        String label = Utils.getOrDefault(upload, "", "label");
        String localFilename = Utils.get(upload, "local");
        String remoteFilename = Utils.get(upload, "remote");
        String uid = Utils.getOrDefault(upload, "0", "uid");
        String gid = Utils.getOrDefault(upload, "0", "gid");
        String permission = Utils.getOrDefault(upload, "0700", "permission");
        String container = Utils.getOrDefault(upload, defaultContainerName, "container");

        System.out.println(">> Execute upload " + label);

        uploadFile(container, uid, gid, permission, localFilename, remoteFilename);
    }

    private void commandUploadDir(LinkedHashMap<?, ?> uploadDir) {
        String label = Utils.getOrDefault(uploadDir, "", "label");
        String localDir = Utils.get(uploadDir, "local");
        String remoteDir = Utils.get(uploadDir, "remote");
        String uid = Utils.getOrDefault(uploadDir, "0", "uid");
        String gid = Utils.getOrDefault(uploadDir, "0", "gid");
        String permission = Utils.getOrDefault(uploadDir, "0700", "permission");
        String container = Utils.getOrDefault(uploadDir, defaultContainerName, "container");

        System.out.println(">> Execute uploadDir " + label);

        if (localDir == null) {
            throw new ProvisioningException("uploadDir: local directory is not configured");
        }

        if (remoteDir == null) {
            throw new ProvisioningException("uploadDir: remote directory is not configured");
        }

        if (!remoteDir.startsWith("/")) {
            throw new ProvisioningException("uploadDir: remote directory must be an absolute path");
        }

        final String remote;
        if (!remoteDir.endsWith("/")) {
            remote = remoteDir + "/";
        } else {
            remote = remoteDir;
        }

        final String local;
        if (!localDir.endsWith("/")) {
            local = yamlPath + "/" + localDir + "/";
        } else {
            local = yamlPath + "/" + localDir;
        }

        try {
            Files.find(Paths.get(local), Integer.MAX_VALUE, (filePath, fileAttr) -> fileAttr.isRegularFile()).forEach(s -> {
                String localFile = s.toAbsolutePath().toString();
                String remoteFile = s.toAbsolutePath().toString().replace(local, remote);

                uploadFile(container, uid, gid, fixPermission(permission), localFile, remoteFile);
            });
        } catch (NoSuchFileException nsfe) {
            // empty directory?
            log.warn("UploadDir failed. Is the directory emppty?");
        } catch (Exception e) {
            throw new ProvisioningException("Uploading directory failed", e);
        }
    }

    private void commandCreateFile(LinkedHashMap<?, ?> createFile) {
        String label = Utils.getOrDefault(createFile, "", "label");
        String remoteFilename = Utils.get(createFile, "remote");
        String uid = Utils.getOrDefault(createFile, "0", "uid");
        String gid = Utils.getOrDefault(createFile, "0", "gid");
        String permission = Utils.getOrDefault(createFile, "0700", "permission");
        String content = Utils.get(createFile, "content");
        String container = Utils.getOrDefault(createFile, defaultContainerName, "container");

        System.out.println(">> Execute createFile " + label);

        createParentDir(container, remoteFilename);

        upload(container, content, remoteFilename, Integer.parseInt(uid), Integer.parseInt(gid), fixPermission(permission));
    }

    private void uploadFile(String container, String uid, String gid, String permission, String localFilename, String remoteFilename) {
        LxdFile localFile = LxdFile.builder() //
                .filename(localFilename) //
                .uid(Integer.parseInt(uid)) //
                .gid(Integer.parseInt(gid)) //
                .fileType(FileType.FILE) //
                .writeType(WriteType.OVERWRITE) //
                .mode(fixPermission(permission)) //
                .build();

        try {
            // create directory if necessary
            createParentDir(container, remoteFilename);

            log.info("Upload file {} to {}, permission {}", localFilename, remoteFilename, permission);
            getContainer().uploadFile(defaultContainerName, remoteFilename, localFile);
        } catch (FileNotFoundException e) {
            throw new ProvisioningException("Unable to read local file '" + localFilename + "'. Aborting...");
        }
    }

    private void createParentDir(String container, String remoteFilename) {
        // create directory if necessary
        Path parentDir = Paths.get(remoteFilename).normalize().getParent();
        if (parentDir != null) {
            log.info("Create directory {}", parentDir.toString());
            execCommand(container, new String[] { "mkdir", "-p", parentDir.toString() }, null, opListener);
        }
    }

    private String fixPermission(String permission) {
        final String p;
        if (permission.length() < 4) {
            p = "0000".substring(0, 4-permission.length()) + permission;
        } else if (permission.length() > 4) {
            throw new ProvisioningException("uploadDir: illegal file permission");
        } else {
            p = permission;
        }

        return p;
    }
}
