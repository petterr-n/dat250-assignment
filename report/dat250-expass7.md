### REPORT ###
# Docker Image #

The database swap from an embedded database to the PostgreSql database ran through a Docker container:

The Docker container is built with:
docker build -t image .

And ran with:
docker run -d -p 8080:8080 --name myapp-container image
