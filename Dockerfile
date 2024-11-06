# Use an official OpenJDK image as the base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Ajouter le script wait-for-it
COPY wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

# Ajustez le chemin pour correspondre au répertoire cible de votre projet dans Jenkins
COPY target/*.jar app.jar

# Expose port 8089 to the outside world (or adjust as needed)
EXPOSE 8089

# Run the JAR file with wait-for-it.sh to wait for MySQL to be ready
ENTRYPOINT ["/wait-for-it.sh", "mysql:3306", "--", "java", "-jar", "app.jar"]
