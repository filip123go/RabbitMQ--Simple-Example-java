# RabbitMQ-Simple-Example-java
A simple program that pass a value to a queue and returns it through RabbitMQ.

Clone or download and run.

For the program to work, RabbitMQ should be running locally. A docker-compose.yml exists in root folder.
To run the RabbitMQ from Docker , execute the command: docker-compose up.

The program gets a string message in method run , in class Runner , puts it to "spring-boot" queue and then prints it through RabbitMQ.

To see RabbitMQ console, after running RabbitMQ, navigate to http://localhost:15672

Log in credentials :  username: guest , password: guest


Technologies: Spring , RabbitMQ , Docker
