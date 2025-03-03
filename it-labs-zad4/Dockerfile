# Use an official Tomcat image as the base image
FROM tomcat:9-jdk17

# Set the working directory
WORKDIR /usr/local/tomcat/webapps

COPY target/my-app.war /usr/local/tomcat/webapps/ROOT.war

# Expose the necessary port
EXPOSE 8080

# Start Tomcat
CMD ["catalina.sh", "run"]
