# Use the openjdk:19 image as the base image
FROM openjdk:19

# Set the working directory
WORKDIR /app

# Copy the application files to the container
COPY . /app

# Set the environment variables
ENV JAVA_OPTS=""

# Run the application
CMD ["java", "-jar", "target/async-task-proc-0.0.1-SNAPSHOT.jar"]
