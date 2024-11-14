# Etapa de build - Usar openjdk 23 e Maven
FROM openjdk:23-jdk-slim AS build
WORKDIR /app

ENV PATH="/opt/maven/bin:${PATH}"

# Copiar pom.xml e dependências
COPY pom.xml ./
RUN mvn dependency:go-offline -B

# Copiar o código fonte e compilar o projeto
COPY src ./src
RUN mvn package -DskipTests

# Etapa de runtime - Usar OpenJDK 23 para executar a aplicação
FROM openjdk:23-jdk-slim AS runtime
WORKDIR /app

# Copiar o JAR gerado da etapa de build
COPY --from=build /app/target/*.jar app.jar

# Definir o comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
