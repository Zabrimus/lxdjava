package de.serversenke.lxd.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.serversenke.lxd.client.core.OperationWebsocket;
import de.serversenke.lxd.client.core.model.ContainerSource;
import de.serversenke.lxd.client.core.model.ContainersPost;
import de.serversenke.lxd.client.core.model.StatusCode;
import de.serversenke.lxd.client.listener.ProgressListener;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DebianMgmt extends BaseMgmt {

    /**
     * Constructs a class to manage or create Debian Containers
     *
     * @param host host of the LXD sever
     * @param port port of the LXD server
     * @param containerName the debian container name
     */
    public DebianMgmt(@NonNull String host, @NonNull Integer port, @NonNull String containerName) {
        super(host, port, containerName);
    }

    /**
     * Creates a new Debian container
     *
     * @param release the debian release (e.g. stretch or jessie)
     * @param architecture architecture (e.g. x86_64 or armv7l)
     * @param profiles the profiles to use while creating a container (e.g. default)
     * @param poolName the pool name to use while creating a container (e.g. default)
     * @param listener The listener which get be called periodically to signal the download progress. Can be null.
     * @return the status code
     */
    public StatusCode createContainer(@NonNull String release, @NonNull String architecture, @NonNull List<String> profiles, @NonNull String poolName, ProgressListener listener) {
        ContainerSource source = new ContainerSource();
        source.setType("image");
        source.setProperties(buildMap("os", "debian", "release", release, "architecture", architecture));
        source.setMode("pull");
        source.setAlias("debian/" + release);
        source.setProtocol("simplestreams");
        source.setServer("https://images.linuxcontainers.org");

        ContainersPost post = new ContainersPost();
        post.setName(defaultContainerName);
        post.setArchitecture(architecture);
        post.setEphemeral(false);
        post.setProfiles(profiles.stream().toArray(String[]::new));
        post.setSource(source);

        Map<String, Map<String, String>> devices = new HashMap<>();
        devices.put("root", buildMap("type", "disk", "path", "/", "pool", poolName));

        post.setDevices(devices);

        return createContainer(listener, post);
    }

    public int aptUpdate(OperationWebsocket.Listener listener) {
        Map<String, String> environment = buildMap("DEBIAN_FRONTEND", "noninteractive");
        String[] command = new String[] { "apt", "update" };

        return execCommand(command, environment, listener);
    }

    public int aptUpgrade(OperationWebsocket.Listener listener) {
        Map<String, String> environment = buildMap("DEBIAN_FRONTEND", "noninteractive");
        String[] command = new String[] { "apt", "-y", "upgrade" };

        return execCommand(command, environment, listener);
    }

    public int aptDistUpgrade(OperationWebsocket.Listener listener) {
        Map<String, String> environment = buildMap("DEBIAN_FRONTEND", "noninteractive");
        String[] command = new String[] { "apt", "-y", "dist-upgrade" };

        return execCommand(command, environment, listener);
    }

    public int aptInstall(List<String> packages, OperationWebsocket.Listener listener) {
        Map<String, String> environment = buildMap("DEBIAN_FRONTEND", "noninteractive");

        List<String> command = new ArrayList<>();
        command.addAll(Arrays.asList("apt", "-y", "install"));
        command.addAll(packages);

        return execCommand(command.stream().toArray(String[]::new), environment, listener);
    }

    public int aptClean(OperationWebsocket.Listener listener) {
        Map<String, String> environment = buildMap("DEBIAN_FRONTEND", "noninteractive");

        String[] command = new String[] { "apt-get", "clean" };

        return execCommand(command, environment, listener);
    }

    public int setPassword(String user, String password) {
        return execCommandViaScript("echo \"root:" + password + "\" | chpasswd\n", null, null);
    }

    public int setAllowRootShh(boolean allow) {
        return replaceInRemoteFile("/etc/ssh/sshd_config", "(?m)^(#?.*?PermitRootLogin.*?)$", "PermitRootLogin " + (allow ? "yes" : "no"));
    }

}
