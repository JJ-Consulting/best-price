#!/usr/bin/env bash

JAVA_OPTS="-Djava.net.preferIPv4Stack=true"
JAVA_OPTS="$JAVA_OPTS -Dswarm.datasources.data-sources.bpDS.connection-url=jdbc:postgresql://$DB_HOST:$DB_PORT/$DB"
JAVA_OPTS="$JAVA_OPTS -Dswarm.datasources.data-sources.bpDS.user-name=$DB_USER"
JAVA_OPTS="$JAVA_OPTS -Dswarm.datasources.data-sources.bpDS.password=$DB_PASSWORD"

java $JAVA_OPTS -jar $1