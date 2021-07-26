FROM openjdk:11-jdk-slim
COPY "target/*.jar" "/myspringbootapp.jar"
CMD [ "-jar", "/myspringbootapp.jar" ]
ENTRYPOINT ["java","-jar","/myspringbootapp.jar" ]
