package de.serversenke.lxd.client.examples;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import de.serversenke.lxd.client.DebianMgmt;
import de.serversenke.lxd.client.core.ClientKeyStore;
import de.serversenke.lxd.client.core.model.Operation;
import de.serversenke.lxd.client.core.model.StatusCode;
import de.serversenke.lxd.client.listener.DefaultOperationListener;
import de.serversenke.lxd.client.listener.ProgressListener;
import lombok.extern.slf4j.Slf4j;
import okio.ByteString;

/**
 * A example Main class which does the following:
 *
 * - creates a Debian Stretch container
 * - executes apt update, apt dist-upgrade
 * - installs the packages openssh-server, apt-utils, less and nano
 * - change sshd configuration to allow root login via ssh
 * - change/set the root password
 * - restarts sshd
 */
@Slf4j
public class Main {

    public static void main(String[] args) throws Exception {
        ProgressListener listener = new ProgressListener() {
            @Override
            public void updateProgress(String progress) {
                log.info("Progress: " + progress);
            }
        };

        DefaultOperationListener opListener = new DefaultOperationListener() {
            @Override
            public void onMessage(String text) {
                System.out.print(text);
            }

            @Override
            public void onMessage(ByteString bytes) {
                System.out.print(bytes.utf8());
            }
        };

        try {
            // read client key/certificate
            String certificate = new String(Files.readAllBytes(Paths.get(ClassLoader.getSystemResource("lxdclient.crt").toURI())));
            String clientKey = new String(Files.readAllBytes(Paths.get(ClassLoader.getSystemResource("lxdclient.key").toURI())));

            // register client key/certificate
            ClientKeyStore.addClientKey(clientKey, certificate, "");

            DebianMgmt deb = new DebianMgmt("localhost", 8443, "test");

            int cint = 0;
            Operation cop = null;

            // create container
            log.info("Create container...");
            List<String> profiles = Arrays.asList("lanprofile");
            StatusCode cr = deb.createContainer("stretch", "x86_64", profiles, "default", listener);
            if (cr != StatusCode.Success) {
                log.error("Creation of container failed: " + cr);
                System.exit(1);
            }

            log.info("Start container...");
            cop = deb.startContainer();
            if (!cop.getStatus().equals("Success")) {
                log.error("Start of container failed: " + cop);
                System.exit(1);
            }

            // update / dist-upgrade
            log.info("Update...");
            cint = deb.aptUpdate(opListener);
            if (cint != 0) {
                log.error("Update failed: " + cint);
                System.exit(1);
            }

            log.info("Dist-Upgrade...");
            cint = deb.aptDistUpgrade(opListener);
            if (cint != 0) {
                log.error("Dist-Update failed: " + cint);
                System.exit(1);
            }

            // install packages
            log.info("Install packages...");
            List<String> packages = Arrays.asList("openssh-server", "apt-utils", "less", "nano");
            cint = deb.aptInstall(packages, opListener);
            if (cint != 0) {
                log.error("Install failed: " + cint);
                System.exit(1);
            }

            // configure ssh to allow root login
            log.info("set ssh root login...");
            cint = deb.setAllowRootShh(true);
            if (cint != 0) {
                log.error("setting ssh root access failed: " + cint);
                System.exit(1);
            }


            // This is only for demonstration!
            // set root password
            log.info("set root pasword...");
            cint = deb.setPassword("root", "topsecret");
            if (cint != 0) {
                log.error("setting password failed: " + cint);
                System.exit(1);
            }

            // restart sshd
            log.info("restart sshd...");
            cop = deb.restartServiceSystemd("ssh");
            if (!cop.getStatus().equals("Success")) {
                log.error("restart sshd failed: " + cop);
                System.exit(1);
            }

            log.info("The End");
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

        System.exit(1);
    }
}
