FROM amazoncorretto:21-alpine-jdk
WORKDIR /app
EXPOSE 8081
ADD ./target/servicio-producto-0.0.1-SNAPSHOT.jar servicio-producto.jar

ENTRYPOINT [ "java", "-jar", "servicio-producto.jar" ]