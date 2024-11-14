# Etapa de build - Usar openjdk 23 e Maven
FROM openjdk:23-jdk-slim AS build
WORKDIR /app

# Instalar o Maven (se necessário)
RUN apt-get update && \
    apt-get install -y wget && \
    wget https://downloads.apache.org/maven/maven-3/3.8.5/binaries/apache-maven-3.8.5-bin.tar.gz && \
    tar -xvzf apache-maven-3.8.5-bin.tar.gz -C /opt && \
    ln -s /opt/apache-maven-3.8.5 /opt/maven && \
    ln -s /opt/maven/bin/mvn /usr/bin/mvn

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
