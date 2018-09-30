package de.serversenke.lxd.script;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;

import de.serversenke.lxd.client.core.model.StatusCode;
import lombok.extern.slf4j.Slf4j;

/*
 Parameter: -c            only create the container
            -p            only do the provisioning without creating the container
            -pa <number>  start provisioning at task <number>
            -pe <number>  end provisioning at task <number>
            -pn <number>  only execute task <number>
            -y <filename> read yaml file <filename>
            -e <filename> use another env file than the default
*/
@Slf4j
public class Main {
    final static class Args {
        @Parameter(names={"--container-only", "-c"}, description = "only create the container")
        boolean containerOnly = false;

        @Parameter(names={"--prov-only", "-p"}, description = "only do the provisioning without creating the container")
        boolean provOnly = false;

        @Parameter(names={"--prov-start", "-pa"}, description = "start provisioning at task <number>")
        Integer provStart =  null;

        @Parameter(names={"--prov-end", "-pe"}, description = "end provisioning at task <number>")
        Integer provEnd = null;

        @Parameter(names={"--prov-number", "-pn"}, description = "only execute task <number>")
        Integer provNumber = null;

        @Parameter(names={"--yaml", "-y"}, required = true, description = "read yaml file <filename>")
        String yamlFile = null;

        @Parameter(names={"--env", "-e"}, description = "use another env file than the default")
        String envFile;

        @Parameter(names={"-h", "-?"}, description = "help")
        boolean help = false;

    }

    public static void main(String argv[]) {
        try {
            Args args = new Args();

            // parse arguments
            JCommander parser = JCommander.newBuilder().addObject(args).build();

            try {
                parser.parse(argv);
            } catch (ParameterException e) {
                System.err.println("Error: " + e.getMessage());
                parser.usage();
                System.exit(1);
            }

            if (args.help) {
                parser.usage();
                System.exit(1);
            }

            // do some more parameter tests
            if (args.provNumber != null && (args.provStart != null || args.provEnd != null || args.containerOnly)) {
                System.err.println("Illegal combination of -pn, -pa, -pe and -c");
                parser.usage();
                System.exit(1);
            }

            if (args.containerOnly && (args.provNumber != null || args.provStart != null || args.provEnd != null || args.provOnly)) {
                System.err.println("Illegal combination of -pn, -pa, -pe, -p and -c");
                parser.usage();
                System.exit(1);
            }

            Path parentDir = Paths.get(args.yamlFile).normalize().getParent().toAbsolutePath();

            LinkedHashMap<Object, Object> yaml = Utils.parseYamlFile(args.yamlFile, args.envFile);

            if (!args.provOnly) {
                // create the container
                CreateContainer cc = new CreateContainer(yaml);
                StatusCode result = cc.createContainer();
                System.out.println("Result: " + result);

                // start the container
                cc.startContainer();

                Thread.sleep(2000);

                System.out.println("----------------------------------------------------------");
                System.out.println("Finished creating container '" + Utils.get(yaml,  "container", "configuration", "name") + "'");
                System.out.println("----------------------------------------------------------");
            }

            if (!args.containerOnly) {
                int startTask;
                int endTask;

                if (args.provStart != null) {
                    startTask = args.provStart;
                } else {
                    startTask = 0;
                }

                if (args.provEnd != null) {
                    endTask = args.provEnd;
                } else {
                    endTask = Integer.MAX_VALUE;
                }

                if (args.provNumber != null) {
                    startTask = args.provNumber;
                    endTask = args.provNumber;
                }

                // Start Provisioning
                ProvisioningContainer pc = new ProvisioningContainer(parentDir, yaml, startTask, endTask);
                pc.startProvisioning();

                System.out.println("----------------------------------------------------------");
                System.out.println("Finished provisioning container '" + Utils.get(yaml,  "container", "configuration", "name") + "'");
                System.out.println("----------------------------------------------------------");
            }
        } catch (Exception e) {
            System.out.println("Error provisioning container...");

            e.printStackTrace();
            System.exit(1);
        }

        System.exit(0);
    }
}
