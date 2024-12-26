# Use MySQL 8.0 image
FROM mysql:8.0

# Set environment variables
ENV MYSQL_ROOT_PASSWORD=nekipass123
ENV MYSQL_DATABASE=company

# Copy the schema file to the Docker image
COPY init.sql /docker-entrypoint-initdb.d/

# Expose MySQL port
EXPOSE 3306
