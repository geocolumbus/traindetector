#!/bin/sh
if [ `ps x | grep project1 | grep ".jar" | wc -l` -eq 1 ];
then
   kill `ps x | grep project1 | grep jar | gawk '{ print $1 }'`
   echo "[STOPPED]"
else
   echo "Project1 is already stopped."
fi