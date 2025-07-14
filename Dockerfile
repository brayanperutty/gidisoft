# Etapa 1: Compilar con Maven y Java 21
FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app

# Copiamos solo pom primero para cachear dependencias
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar código fuente
COPY src ./src

# Compilar el proyecto sin tests
RUN mvn clean package -DskipTests

# Etapa 2: Imagen final con Java 21 + LibreOffice
FROM eclipse-temurin:21-jdk AS runtime

# Instalar LibreOffice y fuentes
RUN apt-get update && apt-get install -y \
    libreoffice \
    fonts-dejavu \
    locales \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

# Configurar locale
RUN locale-gen es_CO.UTF-8
ENV LANG es_CO.UTF-8
ENV LANGUAGE es_CO:es
ENV LC_ALL=es_CO.UTF-8

# Directorio de trabajo
WORKDIR /app

# Copiar el .jar generado en la etapa anterior
COPY --from=builder /app/target/*.jar app.jar

# Exponer el puerto de Spring Boot
EXPOSE 8080

# Comando de ejecución
ENTRYPOINT ["java", "-jar", "app.jar"]
