#!/bin/sh

if [ -z  "$1" ]; then
   echo "First parameter must be the container name"
fi;


for i in `find /dev/dvb -name "*"`; do
    if [ -c $i ]; then
       name=`echo $i | awk -F "/" '{ print $4 "_" $5 }'`

       lxc config device add $1 $name unix-char source=$i
    fi;
done
