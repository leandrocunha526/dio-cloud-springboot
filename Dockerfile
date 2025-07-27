# Stage 1: Build da aplicação usando JDK completo
FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

# Copia o código fonte para dentro do container
COPY . .

# Dá permissão para o gradlew (se estiver no projeto)
RUN chmod +x ./gradlew

# Builda o jar executável, ignorando testes para build mais rápido
RUN ./gradlew clean bootJar -x test

# Stage 2: Imagem menor com JRE para rodar a aplicação
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copia o jar gerado no build para a imagem final
COPY --from=build /app/build/libs/*.jar app.jar

# Define variável de ambiente para o profile de produção
ENV SPRING_PROFILES_ACTIVE=prd

# Expor a porta padrão do Spring Boot
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
