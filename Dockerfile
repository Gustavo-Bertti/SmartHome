# Imagem base para compilação
FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app

# Copia o arquivo pom.xml e baixa as dependências
COPY pom.xml ./
RUN mvn dependency:go-offline -B

# Copia o código da aplicação para o container e compila o projeto
COPY src ./src
RUN mvn package -DskipTests

# Imagem final para executar a aplicação
FROM openjdk:17-jdk-slim AS runtime
WORKDIR /app

# Copia o arquivo JAR gerado pelo Maven no estágio de build
COPY --from=build /app/target/*.jar app.jar

# Comando de inicialização da aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]