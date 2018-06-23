FROM java:openjdk-8-jdk

ADD target/best-price-fe-swarm.jar /opt/best-price-fe-swarm.jar
#ENV JAVA_OPTS -Djava.net.preferIPv4Stack=true -Dswarm.port.offset=1
ENV JAVA_OPTS -Djava.net.preferIPv4Stack=true
EXPOSE 8080

ENTRYPOINT [ "sh", "-c", "java -jar /opt/best-price-fe-swarm.jar $JAVA_OPTS"]
