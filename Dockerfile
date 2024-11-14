# Usar a imagem base do OpenJDK
FROM openjdk:23-jdk-slim

# Definir o diretório de trabalho
WORKDIR /app

# Copiar o arquivo pom.xml
COPY pom.xml ./

# Instalar o Maven
RUN apt-get update && \
    apt-get install -y wget && \
    wget https://mirrors.ocf.berkeley.edu/apache/maven/maven-3/3.8.5/binaries/apache-maven-3.8.5-bin.tar.gz && \
    tar -xvzf apache-maven-3.8.5-bin.tar.gz -C /opt && \
    ln -s /opt/apache-maven-3.8.5 /opt/maven && \
    ln -s /opt/maven/bin/mvn /usr/bin/mvn

# Baixar as dependências para o modo offline
RUN mvn dependency:go-offline -B

# Copiar o código fonte
COPY src ./src

# Compilar o projeto
RUN mvn clean install

# Expor a porta (se necessário)
EXPOSE 8080

# Definir o comando para rodar a aplicação
CMD ["mvn", "spring-boot:run"]
