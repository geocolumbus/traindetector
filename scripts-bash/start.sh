#!/usr/bin/env bash
if [ `ps x | grep project1 | grep ".jar" | wc -l` -eq 1 ];
then
   echo "Project1 is already started."
else
   echo "[BUILDING PROJECT]"
   > /var/log/project1.log
   /usr/bin/mvn -f /var/java/project1/pom.xml -l /var/log/project1.log clean package
   echo "[STARTING PROJECT]"
   /usr/bin/java -jar -Xms128m -Xmx256m /var/java/project1/target/project1-0.0.1-SNAPSHOT.jar >> /var/log/project1.log &
   echo "[RUNNING]"
fi