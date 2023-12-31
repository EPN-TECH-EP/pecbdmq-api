#FROM openjdk:11.0-jdk-slim-stretch
FROM openjdk:17
#EXPOSE 9091
#RUN addgroup -group spring && adduser spring -ingroup spring
#USER spring:spring
ARG JAR_FILE=/*.jar
COPY ${JAR_FILE} apis/pecb.jar
ENTRYPOINT ["java","-jar","/apis/pecb.jar"]

