# lxdprovisioning
An application which uses lxdjavalib to create and provision LXD containers.
lxdprovisioning uses yaml as language.

In the example directory you can find some yaml files which creates different containers. Most containers are not usable out of the box
because mainly the configuration part is not yet finished and must be provided by you.

## Structure of the yaml file
### variables
The variables are read out of the corresponding env file and must be declared here to be able to use it in the whole yaml file.
```
variables:
    certfile:  ${certfile}
    keyfile:   ${keyfile}
    lxdhost:   ${lxdhost:localhost}
    lxdport:   ${lxdport:8443}
    cname:     ${container_name}
    rootpw:    ${rootpw}
    guacamole: ${guacamole}
```
The variables can be accessed by ${variable}.

### lxd
The main configuration how to access LXD. The host and port and of course the certificate and key file
```
lxd:
    certFile: ${certfile}
    keyFile: ${keyfile}
    host: ${lxdhost}
    port: ${lxdport}
```

### container
The description of the container to create. In this example we want to use a debian/stretch container from limuxcontainers.
Wihtin the configuration part, the name of the new container and the profiles must be set.
```
container:
    source:
        protocol: "simplestreams"
        server : "https://images.linuxcontainers.org"
        architecture: "x86_64"
        alias: "debian/stretch"
        type: "image"

    configuration:
        name: ${cname}
        ephemeral: "false"
        profile:
            - "default"
        storage: "default"
```

### provisioning
The main part of the provisioning consists of a list of different tasks. Every tasks is of a specific type.

#### script
The script type.
```
    - script:
        label: "wait for network"
        content: |
            #!/bin/sh
            while  ! systemctl is-active --quiet networking; do
                echo "wait for network being up..."
                sleep 1
            done
            sleep 1
```
The script type consists of a label which will be printed to console before the tasks will be executed. This makes it easier to see, at
which part the provisioning fails - if ever.
The content is the script itself. It will be uploaded as is and then executed within the container.


#### run
The run type can be used to specify a set of commands which shall be executed within the container.
```
    - run:
        label: "install all packages"
        commands: |
            apt-get install -qy build-essential \
                                git wget unzip uuid uuid-dev libarchive13 libarchive-dev
            echo "Hello World. Everything is installed"

```
This result e.g. in executing of
```
apt-get install -qy build-essential git wget unzip uuid uuid-dev libarchive13 libarchive-dev &&  echo "Hello World. Everything is installed"

```

#### createFile
The createFile type can be used to create a file within the container, having the desired uid/gid, permission and content.
```
    - createFile:
        remote: "/etc/systemd/system/vdr.service"
        uid: "0"
        gid: "0"
        permission: "644"
        content: |
            [Unit]
            Description=Start vdr

            [Service]
            Type=simple
            ExecStart=/opt/vdr-2.4.0/vdr
            Restart=on-failure
            RestartSec=10
            TimeoutSec=30

            [Install]
            WantedBy=multi-user.target
```

#### uploadDir
The uploadDir type can be used to copy a local directory into the container. This is mainly used to copy preexisting configuration entries
to the desired place.
```
    - uploadDir:
        local: "vdr-config"
        remote: "/opt/vdr-config"
        uid: "1100"
        gid: "1100"
        permission: "644"
```

#### upload
The upload type can be used to copy a single file into the container. This is mainly used to copy preexisting configuration entries
to the desired place.
```
    - upload:
        local: "vdr-config/setup.conf"
        remote: "/opt/vdr-config/setup.conf"
        uid: "1100"
        gid: "1100"
        permission: "644"
```

#### a common attribute 'container'
All types could also have a container attribute which specifies not to use the current container but an other existing one. Use this
with care.

e.g. the script type.
```
    - script:
        label: "wait for network"
        container: ${guacamole}
        content: |
            #!/bin/sh
            while  ! systemctl is-active --quiet networking; do
                echo "wait for network being up..."
                sleep 1
            done
            sleep 1
```
will execute the script in the container named ${guacamole}.

## Examples
In the examples directory exists some sample yaml files and also the corresponding env file.

### guacamole.yml
Creates a container running with Apache Guacamole (https://guacamole.apache.org/). This container is also used
by all other containers, if configured to predefine a ssh connection for Guacamole.

### binddhcp.yml
Creates a container which runs bind as nameserver and isc-dhcp-server as DHCP server.

### epgd.yml
Creates a container which runs mariadb and the VDR epgd daemon (https://projects.vdr-developer.org/projects/vdr-epg-daemon/wiki).

### jonglisto-ng.yml
Create a container which installs and run jonglisto-ng (https://github.com/Zabrimus/jonglisto-ng).

### minisatip.yml
Creates a container with a running minisatip server (https://github.com/catalinii/minisatip).
The downside is, that all dvb devices must be attached after the container was created.
In the examples directory exists a script which will do this for you: add_dvb_devices.sh.

### oscam.yml
Creates a container running oscam server and a sample client instance.

### vdr_recording_server.yml
Creates a container with VDR 2.4.0 (and all existing patches) and some useful plugins for a server side VDR (http://www.tvdr.de/).

