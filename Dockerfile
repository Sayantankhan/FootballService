FROM maven:3.5-jdk-8 AS build  
COPY src /usr/src/app/src  
COPY pom.xml /usr/src/app  
RUN mvn -f /usr/src/app/pom.xml clean package

FROM gcr.io/distroless/java  
COPY --from=build /usr/src/app/target/FootballService-0.0.1-SNAPSHOT.jar /usr/app/FootballService-0.0.1-SNAPSHOT.jar  
EXPOSE 8080
ENV JASYPT_ENCRYPTOR_PASSWORD=
ENTRYPOINT ["java","-jar","/usr/app/FootballService-0.0.1-SNAPSHOT.jar"] 