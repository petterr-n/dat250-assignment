### REPORT ###
# Docker Image #

The database swap from an embedded database to the PostgreSql database ran through a Docker container:
<img width="1512" alt="Skjermbilde 2024-10-10 kl  10 17 16" src="https://github.com/user-attachments/assets/74b143ae-8fae-4f20-a933-212892459b7b">



CREATING A DOCKER IMAGE:

The Docker container is built with:
docker build -t image .

And ran with:
docker run -d -p 8080:8080 --name myapp-container image

The image file was created following the lecture 14 example linked in the assignment.
There were some technical difficulties with the database swap, where the schema up and down files that were generated, contained duplicates of the CREATE table statements. Which created some headaches when running them.
Aside from this, there were no major difficulties met during this assignment, both with postgres and creating an image. 
