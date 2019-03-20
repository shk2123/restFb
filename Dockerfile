FROM frolvlad/alpine-oraclejdk8
VOLUME /tmp
ADD  build/libs/flightSearch-0.0.1-SNAPSHOT.jar flightSearch-0.0.1-SNAPSHOT.jar
EXPOSE 8081:8081
ENV JAVA_OPTS=""
ENTRYPOINT ["java","-jar","/flightSearch-0.0.1-SNAPSHOT.jar"]