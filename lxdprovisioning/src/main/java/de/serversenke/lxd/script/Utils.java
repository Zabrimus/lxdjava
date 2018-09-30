package de.serversenke.lxd.script;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import de.serversenke.lxd.script.exception.ConfigurationException;

public class Utils {

    @SuppressWarnings("unchecked")
    public static <T> T get(LinkedHashMap<?,?> values, String...path) {
        LinkedHashMap<?,?> current = values;

        for (int i = 0; i < path.length - 1; ++i) {
            current = (LinkedHashMap<?, ?>) current.get(path[i]);
        }

        return (T) current.get(path[path.length - 1]);
    }

    public static <T> T getOrDefault(LinkedHashMap<?,?> values, T defaultValue, String...path) {
        T v = get(values, path);
        return v == null ? defaultValue : v;
    }

    @SuppressWarnings("unchecked")
    public static LinkedHashMap<Object, Object> parseYamlFile(String filename, String envFile) {
        Pattern pattern = Pattern.compile("\\$\\{(.*?)(:(.*?))?\\}");

        LinkedHashMap<Object, Object> yaml = null;

        // read file
        String yamlContent;
        try {
             yamlContent = new String(Files.readAllBytes(Paths.get(filename)), StandardCharsets.UTF_8);
        } catch (IOException e1) {
            throw new ConfigurationException("Unable to read yaml file '" + filename + "'");
        }

        // read env, if available
        Properties env = new Properties();

        try {
            if (envFile != null) {
                env.load(new FileReader(new File(envFile)));
            } else {
                env.load(new FileReader(new File(filename + ".env")));
            }
        } catch (Exception e) {
            // .env file do not exists -> do nothing
        }

        // first run
        try {
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            yaml = (LinkedHashMap<Object, Object>) mapper.readValue(yamlContent, Object.class);
        } catch (Exception e) {
            throw new ConfigurationException("Unable to parse yaml file", e);
        }

        // itererate over all variables and collect them
        Map<String, String> variables = new HashMap<>();

        LinkedHashMap<Object, Object> v = get(yaml, "variables");
        v.entrySet().stream().forEach(entry -> {
            Matcher m;

            m = pattern.matcher((String)entry.getValue());
            if (m.matches()) {
                // check if env exists is not null
                String ev = env.getProperty(m.group(1));
                if (ev != null && ev.length() > 0) {
                    variables.put((String)entry.getKey(), ev);
                } else {
                   if (m.group(3) != null) {
                       variables.put((String)entry.getKey(), m.group(3));
                   } else {
                       variables.put((String)entry.getKey(), "");
                   }
                }
            } else {
                variables.put((String)entry.getKey(), (String)entry.getValue());
            }
        });

        // replace all variables
        Iterator<String> it = variables.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            yamlContent = yamlContent.replaceAll("\\$\\{" + key + "\\}", variables.get(key));
        }

        // second run
        try {
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            yaml = (LinkedHashMap<Object, Object>) mapper.readValue(yamlContent, Object.class);
        } catch (Exception e) {
            throw new ConfigurationException("Unable to parse yaml file", e);
        }

        return yaml;
    }

}
