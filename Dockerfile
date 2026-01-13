# Simple Tomcat-based image that copies the built WAR into ROOT.war
ARG WAR_FILE=target/app.war
FROM tomcat:9.0-jdk17

# Remove default webapps (optional)
RUN rm -rf /usr/local/tomcat/webapps/*

ARG APP_PATH=/usr/local/tomcat/webapps/HRRecruitApp.war

# Copy the WAR produced by Maven into Tomcat's webapps as ROOT.war
# The WAR_FILE arg is expanded at build time; the workflow passes the actual target/*.war path.
ARG WAR_FILE
COPY ${WAR_FILE} ${APP_PATH}

EXPOSE 8080

CMD ["java", "-jar", "${APP_PATH}"]