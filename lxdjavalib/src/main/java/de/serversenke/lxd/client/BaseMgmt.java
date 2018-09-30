package de.serversenke.lxd.client;

import java.io.FileNotFoundException;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.function.Function;

import de.serversenke.lxd.client.core.ClientResponse;
import de.serversenke.lxd.client.core.Containers;
import de.serversenke.lxd.client.core.OperationWebsocket;
import de.serversenke.lxd.client.core.Operations;
import de.serversenke.lxd.client.core.model.BackgroundOperation;
import de.serversenke.lxd.client.core.model.ContainerExecPost;
import de.serversenke.lxd.client.core.model.ContainerStatePut;
import de.serversenke.lxd.client.core.model.ContainersPost;
import de.serversenke.lxd.client.core.model.Empty;
import de.serversenke.lxd.client.core.model.LxdFile;
import de.serversenke.lxd.client.core.model.Operation;
import de.serversenke.lxd.client.core.model.StatusCode;
import de.serversenke.lxd.client.core.model.LxdFile.FileType;
import de.serversenke.lxd.client.core.model.LxdFile.WriteType;
import de.serversenke.lxd.client.exception.ContainerException;
import de.serversenke.lxd.client.listener.ProgressListener;
import lombok.NonNull;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BaseMgmt extends Base {

    /**
     * the LXD server host, including port
     */
    protected String lxdServer;

    /**
     * the current container name
     */
    protected String defaultContainerName;

    /**
     * container
     */
    protected Containers containers;

    /**
     * Constructs a class to manage or create containers
     */
    protected BaseMgmt() {
    }

    /**
     * Constructs a class to manage or create containers
     *
     * @param host host of the LXD sever
     * @param port port of the LXD server
     * @param containerName the container name
     */
    protected BaseMgmt(@NonNull String host, @NonNull Integer port, @NonNull String containerName) {
        this.lxdServer = host + ":" + port;
        this.defaultContainerName = containerName;
    }

    /**
     *
     * @param listener the download progress listener
     * @param op the operation which contains the download progress information
     */
    protected void updateDownloadProgress(ProgressListener listener, @NonNull ClientResponse<Operation> op) {
        Map<String, String> metadata = op.getResponse().getMetadata();

        if (metadata != null) {
            String progress = op.getResponse().getMetadata().get("download_progress");
            if (progress != null) {
                listener.updateProgress(progress);
            } else {
                listener.updateProgress("0");
            }
        } else {
            listener.updateProgress("0");
        }
    }

    /**
     * Creates a new container
     *
     * @param listener the listener for download progress, can be null
     * @param post The container information
     * @return the status code
     */
    protected StatusCode createContainer(ProgressListener listener, @NonNull ContainersPost post) {
        try {
            ClientResponse<BackgroundOperation> imageLoadResult = getContainer().create(post);

            if (imageLoadResult.getStatusCode() == StatusCode.Failure) {
                log.info("container create failed: " + imageLoadResult);
                return imageLoadResult.getStatusCode();
            }

            if (imageLoadResult.getErrorCode() == 404) {
                log.info("Containers.create failed: " + imageLoadResult);
                return StatusCode.Error;
            }

            String operationId = imageLoadResult.getResponse().getId();

            // wait for cmd to finish
            Operations operations = new Operations("https://" + lxdServer);

            boolean isRunning = true;
            String lastCode = null;

            if (listener != null) {
                while (isRunning) {
                    sleep(500);

                    ClientResponse<Operation> op = operations.get(operationId);

                    switch (op.getResponse().getStatus()) {
                    case "Running":
                        break;

                    case "Success":
                        isRunning = false;
                        break;

                    default:
                        isRunning = false;
                    }

                    updateDownloadProgress(listener, op);

                    lastCode = op.getResponse().getStatus();
                    log.debug("operation: " + op);

                    if ("Failure".equals(lastCode)) {
                        throw new ContainerException(op.getResponse().getErrorMessage());
                    }
                }
            } else {
                ClientResponse<Operation> op = operations.wait(operationId);
                lastCode = op.getStatus();
                log.debug("operation: " + op);
            }

            return StatusCode.valueOf(lastCode);
        } catch (Exception e) {
            if (e instanceof ContainerException) {
                throw e;
            }

            log.error("create container failed", e);
            return StatusCode.Error;
        }
    }

    /**
     * Uploads a file into the container and calls consumer with the generated filename
     * The uploaded file will be deleted after execution
     *
     * @param content the file content
     * @return the return code of the command
     */
    public int upload(@NonNull String containerName, @NonNull String content, Function<String, Integer> consumer) {
        // script filename
        String scriptFile = "/tmp/" + System.currentTimeMillis() + "-" + createRandomString(10);

        try {
            upload(content, scriptFile, 0, 0, "700");
            return consumer.apply(scriptFile);
        } finally {
            if (scriptFile != null) {
                // delete file
                getContainer().deleteFile(containerName, scriptFile);
            }
        }
    }

    public int upload(@NonNull String content, Function<String, Integer> consumer) {
        return upload(defaultContainerName, content, consumer);
    }

    /**
     * Uploads a file into the container and calls consumer with the generated filename
     *
     * @param content the file content
     * @param remoteFileName the remoteFileName
     * @param uid of the file owner, defaults to 0
     * @param gid of the file owner, defaults to 0
     * @param filePermission Defaults to 700
     * @param listener the upload progress listener
     * @return the return code of the command
     */
    public int upload(@NonNull String containerName, @NonNull String content, @NonNull String remoteFileName, Integer uid, Integer gid, String filePermission) {
        // transfer script to container
        LxdFile lxdFile = LxdFile.builder() //
                            .uid(uid) //
                            .gid(gid) //
                            .fileType(FileType.FILE) //
                            .writeType(WriteType.OVERWRITE) //
                            .mode(filePermission) //
                            .build();

        ClientResponse<Empty> createResponse = null;
        try {
            createResponse = getContainer().createFile(containerName, remoteFileName, lxdFile, content);
        } catch (FileNotFoundException e) {
            // we do not use local files. This cannot happen.
        }

        return createResponse.getErrorCode();
    }

    public int upload(@NonNull String content, @NonNull String remoteFileName, Integer uid, Integer gid, String filePermission) {
        return upload(defaultContainerName, content, remoteFileName, uid, gid, filePermission);
    }

    /**
     * Executes a command in the container. This can be only a simple command, no pipes or similar. See execCommandInScript.
     * @param command The command including all parameters
     * @param environment The environment variables to use while executing the command
     * @return the return code of the command
     */
    public int execCommand(@NonNull String containerName, @NonNull String[] command, Map<String, String> environment, OperationWebsocket.Listener listener) {
        ContainerExecPost post = new ContainerExecPost();
        post.setCommand(command);
        post.setEnvironment(environment);
        post.setInteractive(false);
        post.setWaitForWebsocket(true);

        OperationWebsocket stdinSocket = null;
        OperationWebsocket stdoutSocket = null;
        OperationWebsocket stderrSocket = null;

        try {
            // execute command
            ClientResponse<BackgroundOperation> cmdResult = getContainer().execCommand(containerName, post);

            if (cmdResult.getErrorCode() != 0) {
                // something is wrong
                throw new ContainerException("execCommand failed: " + cmdResult);
            }

            // connect to websocket to get the output
            String stdinSecret = cmdResult.getResponse().getMetadata().get("fds").get("0");
            String stdoutSecret = cmdResult.getResponse().getMetadata().get("fds").get("1");
            String stderrSecret = cmdResult.getResponse().getMetadata().get("fds").get("2");
            String operationId = cmdResult.getResponse().getId();

            stdinSocket = new OperationWebsocket("wss://" + lxdServer, operationId, stdinSecret, listener);
            stdinSocket.connect();

            stdoutSocket = new OperationWebsocket("wss://" + lxdServer, operationId, stdoutSecret, listener);
            stdoutSocket.connect();

            stderrSocket = new OperationWebsocket("wss://" + lxdServer, operationId, stderrSecret, listener);
            stderrSocket.connect();

            Operations operations = new Operations("https://" + lxdServer);
            ClientResponse<Operation> operationResult = operations.wait(operationId);

            try {
                log.debug("OperationResult: " + operationResult);
                String result = operationResult.getResponse().getMetadata().get("return");
                return Integer.parseInt(result);
            } catch (Exception e) {
                return -1;
            }
        } catch (Exception e) {
            throw new ContainerException("execCommand failed: ", e);
        } finally {
            // close sockets

            if (stdinSocket != null) {
                stdinSocket.close();
            }

            if (stderrSocket != null) {
                stderrSocket.close();
            }

            if (stdoutSocket != null) {
                stdoutSocket.close();
            }
        }
    }

    public int execCommand(@NonNull String[] command, Map<String, String> environment, OperationWebsocket.Listener listener) {
        return execCommand(defaultContainerName, command, environment, listener);
    }

    /**
     * Uploads a script and execute it.
     *
     * @param script The script
     * @param environment The environment variables to use while executing the command
     * @return the return code of the command
     */
    public int uploadAndExecScript(@NonNull String containerName, @NonNull String script, Map<String, String> environment, OperationWebsocket.Listener listener) {
        // script filename
        String scriptFile = "/tmp/" + System.currentTimeMillis() + "-" + createRandomString(10);

        try {
            // transfer script to container
            LxdFile lxdFile = LxdFile.builder() //
                                .uid(0) //
                                .gid(0) //
                                .fileType(FileType.FILE) //
                                .writeType(WriteType.OVERWRITE) //
                                .mode("0700") //
                                .build();

            ClientResponse<Empty> createResponse = null;
            try {
                createResponse = getContainer().createFile(containerName, scriptFile, lxdFile, script);
            } catch (FileNotFoundException e) {
                // we do not use local files. This cannot happen.
            }

            return execCommand(containerName, new String[] { scriptFile } , environment, listener);
        } finally {
            if (scriptFile != null) {
                // delete file
                getContainer().deleteFile(containerName, scriptFile);
            }
        }
    }

    public int uploadAndExecScript(@NonNull String script, Map<String, String> environment, OperationWebsocket.Listener listener) {
        return uploadAndExecScript(defaultContainerName, script, environment, listener);
    }

    /**
     * Executes a command in the container. An temp. script will be created to execute the command.
     * @param command The command including all parameters
     * @param environment The environment variables to use while executing the command
     * @return the return code of the command
     */
    public int execCommandViaScript(@NonNull String command, Map<String, String> environment, OperationWebsocket.Listener listener) {
        // create script
        StringWriter writer = new StringWriter();
        writer.append("#!/bin/sh\n");
        writer.append(command);
        writer.append("\nexit $?\n");

        return uploadAndExecScript(writer.toString(), environment, listener);
    }

    /**
     * Start/Stop/Restart the container
     *
     * @param command can be start, stop or restart
     * @param timeout timeout
     * @param stateful whether to store or restore runtime state before stopping or starting
     * @param force force stop
     * @return the final operation
     * @throws Exception
     */
    protected Operation changeContainerState(@NonNull String containerName, String command, Integer timeout, Boolean stateful, Boolean force) {
        ContainerStatePut newState = new ContainerStatePut();
        newState.setAction(command);

        if (timeout != null) {
            newState.setTimeout(timeout);
        }

        if (stateful != null) {
            newState.setStateful(stateful);
        }

        if (force != null) {
            newState.setForce(force);
        }

        ClientResponse<BackgroundOperation> result = getContainer().changeState(containerName, newState);

        if (result.getErrorCode() == 404) {
            log.error("Container '{}' does not exists", containerName);
            throw new ContainerException(String.format("Container '%s' does not exists", containerName));
        }

        // wait for cmd to finish
        String operationId = result.getResponse().getId();
        Operations operations = new Operations("https://" + lxdServer);
        ClientResponse<Operation> operationResult = operations.wait(operationId);

        return operationResult.getResponse();
    }

    protected Operation changeContainerState(String command, Integer timeout, Boolean stateful, Boolean force) {
        return changeContainerState(defaultContainerName, command, timeout, stateful, force);
    }

    /**
     * Start the container
     * @return
     *
     * @throws Exception
     */
    public Operation startContainer() {
        return changeContainerState("start", null, null, null);
    }

    /**
     * Start the container
     *
     * @param timeout timeout value
     * @param stateful restore runtime state
     * @return
     */
    public Operation startContainer(Integer timeout, Boolean stateful) {
        return changeContainerState("start", timeout, stateful, null);
    }

    /**
     * Stop the container
     * @return
     */
    public Operation stopContainer() {
        return changeContainerState("stop", null, null, null);
    }

    /**
     * Stop the container
     *
     * @param timeout the timeout value
     * @param stateful save the runtime state
     * @param force force stop
     * @return
     */
    public Operation stopContainer(Integer timeout, Boolean stateful, Boolean force) {
        return changeContainerState("stop", timeout, stateful, force);
    }

    /**
     * Alternative method to stop the container via command halt. If stopContainer() do not work as desired try to use this procedure. E.g. for debian container
     * @return
     */
    public int stopContainerHalt() {
        String[] command = new String[] { "halt" };
        return execCommand(command, null, null);
    }

    /**
     * Restart the container
     * @return
     */
    public Operation restartContainer() throws Exception {
        return changeContainerState("restart", null, null, null);
    }

    /**
     * Restart the container
     * @param timeout the timeout value
     * @param stateful store/restore runtime state
     * @param force force stop
     * @return
     */
    public Operation restartContainer(Integer timeout, Boolean stateful, Boolean force) {
        stopContainer(timeout, stateful, force);
        return startContainer(timeout, stateful);
    }

    /**
     * Alternative method to restart the container. If restartContainer() do not work as desired, try to use this procedure. E.g. for Debian containers.
     * @param timeout
     * @param stateful
     * @param force
     * @return
     */
    public Operation restartContainerHalt() {
        stopContainerHalt();
        return startContainer();
    }

    /**
     * Restarts a systemd service
     *
     * @param service the service name
     * @return
     * @throws Exception
     */
    public Operation restartServiceSystemd(@NonNull String containerName, String service) {
        Operations operations = new Operations("https://" + lxdServer);

        String operationId = null;
        ClientResponse<Operation> operationResult = null;

        // execute password script
        ContainerExecPost post = new ContainerExecPost();
        post.setCommand(new String[] {"systemctl", "stop", service });
        post.setInteractive(false);
        post.setWaitForWebsocket(false);

        ClientResponse<BackgroundOperation> cmdResponse = getContainer().execCommand(containerName, post);

        // wait for cmd to finish
        operationId = cmdResponse.getResponse().getId();
        operationResult = operations.wait(operationId);

        post.setCommand(new String[] {"systemctl", "start", service });

        cmdResponse = getContainer().execCommand(containerName, post);

        // wait for cmd to finish
        operationId = cmdResponse.getResponse().getId();
        operationResult = operations.wait(operationId);

        return operationResult.getResponse();
    }

    public Operation restartServiceSystemd(String service) {
        return restartServiceSystemd(defaultContainerName, service);
    }

    /**
     * Replaces a searchPattern with replacePattern in a remote File.
     * searchPattern is evaluated as regular expression
     *
     * @param remoteFile The remote file
     * @param searchPattern the search pattern (regex)
     * @param replacePattern the replace pattern (ordinary string)
     *
     * @return
     */
    public int replaceInRemoteFile(@NonNull String containerName, String remoteFile, String searchPattern, String replacePattern) {
        // get config file
        LxdFile file = getContainer().getFileContent(containerName, remoteFile);

        String content = new String(file.getContent(), StandardCharsets.UTF_8);
        content = content.replaceAll(searchPattern, replacePattern);

        // upload new config
        file.setWriteType(WriteType.OVERWRITE);

        try {
            ClientResponse<Empty> createResponse = getContainer().createFile(containerName, remoteFile, file, content);
            return createResponse.getErrorCode();
        } catch (Exception e) {
            throw new ContainerException("replaceInRemoteFile failed", e);
        }
    }

    public int replaceInRemoteFile(String remoteFile, String searchPattern, String replacePattern) {
        return replaceInRemoteFile(defaultContainerName, remoteFile, searchPattern, replacePattern);
    }

    @Synchronized
    protected Containers getContainer() {
        if (containers == null) {
            containers = new Containers("https://" + lxdServer);
        }

        return containers;
    }
}
