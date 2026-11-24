FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn -B clean package -DskipTests

FROM tomcat:10.1-jdk21-temurin
ENV PORT=10000
RUN rm -rf /usr/local/tomcat/webapps/*
COPY --from=build /app/target/portafolio.war /usr/local/tomcat/webapps/ROOT.war
COPY docker-start.sh /docker-start.sh
RUN chmod +x /docker-start.sh
EXPOSE 10000
CMD ["/docker-start.sh"]
