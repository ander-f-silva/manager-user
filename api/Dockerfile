FROM openjdk:8-alpine

ENV APP_HOME "/opt/app"

ADD api/build/libs/*.jar $APP_HOME/api.jar

CMD	java -Xmx1024M -Xms512M -jar -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=$ENVIRONMENT /opt/app/api.jar