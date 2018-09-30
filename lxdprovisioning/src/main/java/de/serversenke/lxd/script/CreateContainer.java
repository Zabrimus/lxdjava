package de.serversenke.lxd.script;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import de.serversenke.lxd.client.core.model.ContainerSource;
import de.serversenke.lxd.client.core.model.ContainersPost;
import de.serversenke.lxd.client.core.model.StatusCode;
import de.serversenke.lxd.script.exception.ConfigurationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CreateContainer extends ScriptBase {

    protected CreateContainer(LinkedHashMap<?, ?> yaml) {
        super(yaml);
    }

    public StatusCode createContainer() {
        registerCertKey();

        return createContainer(listener, createContainerPost());
    }

    private Map<String, Map<String, String>> createDevices() {
        try {
            Map<String, Map<String, String>> dev = new HashMap<>();

            LinkedHashMap<?, ?> devices = Utils.get(yaml, "container", "devices");

            if (devices != null) {
                devices.entrySet().forEach(entry -> {
                    LinkedHashMap<?, ?> devConfig = (LinkedHashMap<?, ?>) entry.getValue();

                    Map<String, String> devConfigMap = new HashMap<>();
                    devConfig.entrySet().forEach(d -> {
                        devConfigMap.put(d.getKey().toString(), d.getValue().toString());
                    });

                    dev.put(entry.getKey().toString(), devConfigMap);
                });
            }

            return dev;
        } catch (Exception e) {
            throw new ConfigurationException("Unable to create device configuration", e);
        }
    }

    protected ContainersPost createContainerPost() {
        try {
            ContainerSource source = new ContainerSource();
            source.setType("image");
            source.setMode("pull");
            source.setAlias(Utils.get(yaml,  "container", "source", "alias"));
            source.setProtocol(Utils.get(yaml,  "container", "source", "protocol"));
            source.setServer(Utils.get(yaml,  "container", "source", "server"));

            ContainersPost post = new ContainersPost();
            post.setName(Utils.get(yaml,  "container", "configuration", "name"));
            post.setArchitecture(Utils.get(yaml,  "container", "source", "architecture"));
            post.setEphemeral(Boolean.valueOf(Utils.get(yaml,  "container", "configuration", "ephemeral")));

            LinkedHashMap<?, ?>  config = Utils.get(yaml, "container", "configuration", "config");

            if ((config != null) && (config.size() > 0)) {
                Map<String, String> configMap = new HashMap<>();
                config.entrySet().forEach(entry -> {
                    configMap.put(entry.getKey().toString(), entry.getValue().toString());
                });

                post.setConfig(configMap);
            }

            List<String> profiles = Utils.get(yaml, "container", "configuration", "profile");
            post.setProfiles(profiles.stream().toArray(String[]::new));
            post.setSource(source);

            Map<String, String> storageMap = new HashMap<>();
            storageMap.put("type", "disk");
            storageMap.put("path", "/");
            storageMap.put("pool", Utils.get(yaml, "container", "configuration", "storage"));

            Map<String, Map<String, String>> devices = createDevices();
            devices.put("root", storageMap);

            post.setDevices(devices);

            return post;
        } catch (Exception e) {
            throw new ConfigurationException("Unable to create ContainerPost", e);
        }
    }
}
