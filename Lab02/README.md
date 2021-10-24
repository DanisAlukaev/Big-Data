# Lab Block 1.2. Docker
Author: Danis Alukaev \
Email: d.alukaev@innopolis.university \
Group: B19-DS-01 

## Getting started:
1. Remove VMs with further needed names if any: 
```
$ docker-machine rm myvm1 myvm2
```
2. Create a couple of VMs using docker-machine: 
``` 
$ docker-machine create --driver virtualbox myvm1
$ docker-machine create --driver virtualbox myvm2
```
3. Check available VMs:
```
$ docker-machine ls
```
4. Instruct `myvm1` to become a swarm manager:
```
$ docker-machine ssh myvm1 "docker swarm init --advertise-addr <myvm1 ip>"
```
5. Instruct `myvm2` join new swarm as a worker:
```
$ docker-machine ssh myvm2 "docker swarm join --token <token> <myvm1 ip>:<port>"
```
6. Configure current shell to talk to the Docker daemon on the VM:
```
$ docker-machine env myvm1
$ eval $(docker-machine env myvm1)
$ docker node ls
```
7. Create auxiliary directory on `myvm1`:
```
$ docker-machine ssh myvm1 "mkdir ./data"
```
8. Deploy application on the cluster:
```
$ docker stack deploy -c docker-compose.yml lab02
```
The web page with Redis counter is available on `<myvm1 ip>`, and the visualization on `8080` port.
Stop application using:
```
$ docker stack rm lab02
```

## Structure of repository:
The directory `Application` is a context folder for image building. The image was uploaded to DockerHub, and is [available online here](https://hub.docker.com/repository/docker/aldanis/big-data-lab2).

