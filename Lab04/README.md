# Lab Block 2.2. Hadoop Cluster
Author: Danis Alukaev \
Email: d.alukaev@innopolis.university \
Group: B19-DS-01 

## Getting started:
Prerequisites:
```
$ vagrant up
$ vagrant ssh server-1
$ start-dfs.sh
$ start-yarn.sh
```
Create `alice.txt` file:
```
$ hdfs dfs -mkdir /user/
$ hdfs dfs -mkdir /user/vagrant/
$ hdfs dfs -put alice.txt /user/vagrant/alice.txt
```
Run jobs:
```
$ hadoop jar hadoop/share/hadoop/mapreduce/hadoop-mapreduce-examples-3.1.1.jar pi 20 10
$ hadoop jar hadoop/share/hadoop/mapreduce/hadoop-mapreduce-examples-3.1.1.jar wordcount alice.txt output
```
Check results of wordcount: 
```
$ hdfs dfs -get output output
$ cat output/part-r-00000
```

Access web inteface: \
Hadoop: 10.0.0.11:9870 \
YARN: 10.0.0.11:8088
