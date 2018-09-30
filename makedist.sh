#!/bin/sh
(cd lxdprovisioning/build/libs/ && zip -9 ../../../lxdprovisioning.zip lxdprovisioning-all.jar)
(cd lxdprovisioning && zip -9 -r ../lxdprovisioning.zip examples/*)
