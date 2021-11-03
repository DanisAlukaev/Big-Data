# Lab Block 3.1. MapReduct with Java
Author: Danis Alukaev \
Email: d.alukaev@innopolis.university \
Group: B19-DS-01 

## Getting started:
Prerequisites:
```
$ vagrant up
$ vagrant ssh server-1

$ start-dfs.sh
$ hdfs dfsadmin -report

$ start-yarn.sh
$ yarn node -list
```
Create `alice.txt` file:
```
$ hdfs dfs -mkdir /user/
$ hdfs dfs -mkdir /user/vagrant/
$ hdfs dfs -put alice.txt /user/vagrant/alice.txt
```
Compile the application:
```
$ javac -cp $(hadoop classpath) WordCount.java
$ jar -cvf WordCount.jar WordCount*.class
```
However, each version is already compiled, see the `Compiled` derictory.

Run jobs:
Move files for necessary version to `/home/vagrant/` directory, and run the following command.
```
$ hadoop jar WordCount.jar WordCount alice.txt lab5
```
Check results of wordcount: 
```
$ hdfs dfs -get lab5 lab5
$ cat lab5/part-r-00000
```

Access web inteface: \
Hadoop: http://10.0.0.11:9870 \
YARN: http://10.0.0.11:8088
