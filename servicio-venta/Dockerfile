FROM amazoncorretto:21-alpine-jdk
WORKDIR /app
EXPOSE 8082
ADD ./target/servicio_venta-0.0.1-SNAPSHOT.jar servicio-venta.jar

ENTRYPOINT [ "java", "-jar", "servicio-venta.jar" ]