FROM java:openjdk-8-jdk

ADD target/best-price-be-swarm.jar /opt/best-price-be-swarm.jar
ADD start.sh /opt/start.sh

EXPOSE 8080

ENTRYPOINT [ "sh", "-c", "/opt/start.sh /opt/best-price-be-swarm.jar"]