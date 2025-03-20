FROM amazoncorretto:21-alpine-jdk
WORKDIR /app
EXPOSE 8083
ADD ./target/servicio-lote-0.0.1-SNAPSHOT.jar servicio-lote.jar

ENTRYPOINT [ "java", "-jar", "servicio-lote.jar" ]