# Usar una imagen base con JDK y Maven
FROM maven:3.9.9-eclipse-temurin-21

# Establecer el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar los archivos del proyecto al contenedor
COPY . .

# Exponer el puerto que usa Spring Boot
EXPOSE 8080

# Comando para ejecutar la aplicación en modo desarrollo
CMD ["mvn", "spring-boot:run"]
