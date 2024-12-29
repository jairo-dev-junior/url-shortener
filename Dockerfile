FROM amazoncorretto:21-alpine-jdk
COPY . .
RUN ./gradlew build
COPY . build/
CMD ["./gradlew", "run"]
