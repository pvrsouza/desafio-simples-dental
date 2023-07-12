# Imagem base com Java 17
FROM openjdk:17-jdk-alpine

# Diretório de trabalho
WORKDIR /app

# Copiar o arquivo JAR da aplicação para o contêiner
COPY target/desafio-simples-dental-0.0.1-SNAPSHOT.jar desafio-simples-dental-0.0.1-SNAPSHOT.jar

# Expor a porta da aplicação Spring Boot
EXPOSE 8080

# Comando para executar a aplicação Spring Boot
CMD ["java", "-jar", "desafio-simples-dental-0.0.1-SNAPSHOT.jar"]
