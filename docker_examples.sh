# Hello:
docker run hello-world

# List containers:
docker container ls --all

# Pull and run a Dockerized nginx web server that we name, webserver:
docker run --detach --publish 80:80 --name webserver nginx

# Bind mounts:
docker run --rm -v 'C:/Users':/data alpine ls /data
# Bind mounts, --mount syntax:
docker run --rm --mount type=bind,source=/c/Users,destination=/data alpine ls /data


# Useful commands:
#  https://github.com/docker/labs/blob/master/developer-tools/java/chapters/appa-common-commands.adoc