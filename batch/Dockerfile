FROM java:openjdk-8-jre

ENV APP_HOME "/opt/app"

ADD batch/build/libs/*.jar $APP_HOME/batch.jar

CMD	java -Xmx1024M -Xms512M -Denv=local -jar /opt/app/batch.jar $FILE_CSV $LIST_ONE $LIST_TWO