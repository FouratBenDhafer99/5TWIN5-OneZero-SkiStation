FROM openjdk:8
EXPOSE 8083
ADD ./target/5TWIN5-OneZero-SkiStation.jar 5TWIN5-OneZero-SkiStation.jar
ENTRYPOINT ["java","-jar","/5TWIN5-OneZero-SkiStation.jar"]