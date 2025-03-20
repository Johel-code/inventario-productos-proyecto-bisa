FROM amazoncorretto:21-alpine-jdk
WORKDIR /app
EXPOSE 8084
ADD ./target/servicio-reporte-0.0.1-SNAPSHOT.jar servicio-reporte.jar

ENTRYPOINT [ "java", "-jar", "servicio-reporte.jar" ]