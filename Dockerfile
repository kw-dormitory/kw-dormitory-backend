FROM openjdk:18.0.2-jdk

COPY build/libs/*.jar app.jar
COPY firebase-kw-dormitory.json firebase-kw-dormitory.json

CMD ["java", "-jar", "app.jar"]