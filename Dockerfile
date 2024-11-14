# Etapa de build
FROM maven:3.8.5-openjdk-23-slim AS build
WORKDIR /app

# Copiar o arquivo pom.xml e fazer o download das dependências
COPY pom.xml ./
RUN mvn dependency:go-offline -B

# Copiar o código-fonte e compilar o projeto
COPY src ./src
RUN mvn package -DskipTests

# Etapa de runtime
FROM openjdk:23-jdk-slim AS runtime
WORKDIR /app

# Copiar o jar gerado do build
COPY --from=build /app/target/*.jar app.jar

# Definir o comando de execução
ENTRYPOINT ["java", "-jar", "app.jar"]
