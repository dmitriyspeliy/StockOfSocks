FROM openjdk:11.0.11-jre-slim
ADD /target/StockOfSocks-0.0.1-SNAPSHOT.jar StockOfSocks-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","StockOfSocks-0.0.1-SNAPSHOT.jar"]
