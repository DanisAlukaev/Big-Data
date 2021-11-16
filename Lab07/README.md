# Lab Block 4.1. MLlib
Author: Danis Alukaev \
Email: d.alukaev@innopolis.university \
Group: B19-DS-01 

## Getting started:
For faster Scala prototyping, you can use Jupyter kernel:
```
$ docker run --dns=8.8.8.8 --rm -p 0.0.0.0:8888:8888 -p 0.0.0.0:4040:4040 -e JUPYTER_ENABLE_LAB=yes -v /home/<user>:/home/jovyan/work jupyter/all-spark-notebook start-notebook.sh 
```
The [Jupyter notebook](notebooks/Pipeline.ipynb) contains the developed pipeline. For the sake of demonstration, there were printed parameters of best model selected using the Grid search, and also the accuracy of this model. 

The csv file with training data should be placed inside the hadoop cluster, use the following command
```
hdfs dfs -put twitter_sentiment_data.csv /user/vagrant/twitter_sentiment_data.csv
```

The folder [Source](Source/) contains Scala file with the pipeline as well as compiled `jar`. To run the pipeline on distributed cluster use command 
```
$ spark-submit --master yarn --deploy-mode client spark-twitter_2.12-1.0.jar twitter_sentiment_data.csv
```
