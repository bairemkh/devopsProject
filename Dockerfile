FROM openjdk:11
EXPOSE 8082
COPY tagret/DevOps_Project-1.0.jar DevOps_Project-1.0.jar
ENTRYPOINT["java","-jar","/DevOps_Project-1.0.jar"]