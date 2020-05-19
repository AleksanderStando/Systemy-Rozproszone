#!/bin/bash

rm -rf /home/aleksander/zk-data

mkdir -p /home/aleksander/zk-data/zk1
mkdir -p /home/aleksander/zk-data/zk2
mkdir -p /home/aleksander/zk-data/zk3

echo "1" >> /home/aleksander/zk-data/zk1/myid
echo "2" >> /home/aleksander/zk-data/zk2/myid
echo "3" >> /home/aleksander/zk-data/zk3/myid

gnome-terminal -- ./apache-zookeeper-3.5.7-bin/bin/zkServer.sh start-foreground zoo1.cfg
gnome-terminal -- ./apache-zookeeper-3.5.7-bin/bin/zkServer.sh start-foreground zoo2.cfg
gnome-terminal -- ./apache-zookeeper-3.5.7-bin/bin/zkServer.sh start-foreground zoo3.cfg
