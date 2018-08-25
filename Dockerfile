FROM openjdk:8-jdk-alpine

VOLUME /tmp

ADD target/accountcontrol-0.0.1-SNAPSHOT.jar app.jar

ENV JAVA_OPTS="-XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap  -XX:MaxRAMFraction=1 -XshowSettings:vm"

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -Xms256m -Xmx256m -Dspring.profiles.active=docker -jar /app.jar"]

