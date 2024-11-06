# Utilisation de l'image Maven pour la compilation
FROM maven:3.8.5-openjdk-17 AS builder

# Répertoire de travail pour la compilation
WORKDIR /app

# Copier le projet dans le conteneur
COPY . .

# Compiler le projet et ignorer les tests
RUN mvn clean install -DskipTests

# Utiliser une image OpenJDK plus légère pour exécuter l'application
FROM openjdk:17-jdk-slim

# Répertoire de travail pour l'exécution
WORKDIR /app

# Copier le JAR compilé depuis l'étape builder
COPY --from=builder /app/target/*.jar app.jar

# Exposer le port sur lequel l'application écoute
EXPOSE 8089

# Lancer l'application
ENTRYPOINT ["java", "-jar", "app.jar"]
