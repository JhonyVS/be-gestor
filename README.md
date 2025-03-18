services:
  backend:
    build:
      context: ./be-gestor
      dockerfile: Dockerfile
    ports:
      - "8080:8080" # Puerto del backend
    volumes:
      - ./be-gestor:/app         # Sincroniza el código fuente local con el contenedor
      - /app/.m2  		 # Para evitar descargas repetidas de dependencias
    command: mvn spring-boot:run # Comando para ejecutar en desarrollo
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://db:5432/be-gestor
      SPRING_DATASOURCE_USERNAME: be-gestor
      SPRING_DATASOURCE_PASSWORD: 12345
    depends_on:
      - db

  frontend:
    build:
      context: ./fe-gestor
      dockerfile: Dockerfile
    ports:
      - "3000:3000" # Puerto del frontend
    volumes:
      - ./fe-gestor:/app        		# Sincroniza el código fuente local con el contenedor
      - node_modules:/app/node_modules       	# Evita conflictos con node_modules
    command: npm run dev         		# Comando para ejecutar el servidor de desarrollo

  db:
    image: postgres:15-alpine
    container_name: be-gestor-db
    environment:
      POSTGRES_DB: be-gestor
      POSTGRES_USER: be-gestor
      POSTGRES_PASSWORD: 12345
    ports:
      - "5433:5432" # Puerto de PostgreSQL
    volumes:
      - db-data:/var/lib/postgresql/data # Volumen para persistencia de datos

volumes:
  db-data:
  node_modules:
