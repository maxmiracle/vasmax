FROM alpine/java:17.0.12-jre
RUN apk update && apk add bash
RUN apk --no-cache add curl
WORKDIR /app
COPY build/libs/vasmax*.jar app.jar
RUN mkdir -p /etc/maximserver
RUN chmod a+rwx -R /etc/maximserver
COPY maximserver/domainKey.pem /etc/maximserver/domainKey.pem
COPY maximserver/accountKey.pem /etc/maximserver/accountKey.pem
ENTRYPOINT ["java","-jar","app.jar"]
EXPOSE 80 443