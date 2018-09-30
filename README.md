# lxdjava
A java library implementing the rest API for LXD. Currently the API version of LXD 3.0 is available.

## Prepare LXD to allow a new client certificate
```
lxc config set core.https_address [::]:8443
lxc config set core.https_allowed_origin "*"
lxc config set core.https_allowed_methods "GET, POST, PUT, DELETE, OPTIONS"
lxc config set core.https_allowed_headers "Content-Type"
```

## Create a new certificate and client key
```
openssl req -x509 -newkey rsa:4096 -sha256 -nodes -keyout lxdclient.key -out lxdclient.crt -subj "/CN=lxd.home" -days 3650
```

## Optional: create a certificate for a webbrowser (currently not used)
```
openssl pkcs12 -keypbe PBE-SHA1-3DES -certpbe PBE-SHA1-3DES -export -in lxdclient.crt -inkey lxdclient.key -out lxdclient.pfx -name "LXD Browser Certificate"
```

## Register the certificate in LXD
```
lxc config trust add lxdclient.crt
```

## Install key/cert
Copy lxdclient.key and lxdclient.crt into a subdirectory of your choice. But be aware of the directory rights.
A location is e.g. /root/.lxd

## Use certificate/key in the java api
Once before using the API the client key/certifcate have to be registered:
```
   // register certificate/key
   Path certificatePath = FileSystems.getDefault().getPath(certificateFilename, new String[0]);
   Path keyPath = FileSystems.getDefault().getPath(keyFilename, new String[0]);

   String certificate = null;
   String clientKey = null;
   try {
       certificate = new String(Files.readAllBytes(certificatePath));
       clientKey = new String(Files.readAllBytes(keyPath));
   } catch (IOException e) {
       System.err.println("Unable to read certificate or client key file");
       System.exit(2);
   }

   ClientKeyStore.addClientKey(clientKey, certificate, "");
```

# Build all
The build will be started with
```
./gradlew build
```

The created files can be then found in
```
lxdjavalib/build/libs/lxdjavalib.jar
lxdprovisioning/build/libs/lxdprovisioning-all.jar
```

# lxdprovisioning
The lxdprovisioning-all.jar is a runnable jar which contains all necessary classes and can be started with
```
java -jar lxdprovisioning-all.jar <options>

Usage: <main class> [options]
  Options:
    --container-only, -c
      only create the container
      Default: false
    --env, -e
      use another env file than the default
    --prov-end, -pe
      end provisioning at task <number>
    --prov-number, -pn
      only execute task <number>
    --prov-only, -p
      only do the provisioning without creating the container
      Default: false
    --prov-start, -pa
      start provisioning at task <number>
  * --yaml, -y
      read yaml file <filename>
    -h, -?
      help
      Default: false

```
