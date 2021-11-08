# Lab Block 3.2. MapReduct with Scala
Author: Danis Alukaev \
Email: d.alukaev@innopolis.university \
Group: B19-DS-01 

## Getting started:
The repository contains several directories, each containing information about the respective step. Accordingly, the `Part 3` is the final version. Inside this folder you can see the source code, compiled version, and logs/output after the successful execution of `.jar` file. 


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
$ cd /src/main/scala
$ sbt package
```
However, each version is already compiled, see the `Compiled` directory.

Run jobs:
Move files for necessary version to `/home/vagrant/` directory, and run the following command.
```
$ spark-submit --master yarn --class WordCount spark-wordcount_2.12-1.0.jar  alice.txt lab06-wordcount
```
Check results of wordcount: 
```
$ hdfs dfs -get lab06-wordcount lab06-wordcount
$ cat lab06-wordcount/part-r-00000
```
The output is available in `output` directory.

