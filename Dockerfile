FROM openjdk:11-jdk-slim
COPY "target/*.jar" "/wishlist-0.0.1.jar"
CMD [ "-jar", "/wishlist-0.0.1.jar" ]
ENTRYPOINT ["java","-jar","/wishlist-0.0.1.jar" ]