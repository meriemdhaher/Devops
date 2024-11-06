FROM openjdk:17-alpine
RUN adduser -D myuser

USER myuser
EXPOSE 8182
ADD ./target/*.jar gestion-station-ski-1.0-SNAPSHOT.jar
ENTRYPOINT [ "java", "-jar", "./gestion-station-ski-1.0-SNAPSHOT.jar" ]