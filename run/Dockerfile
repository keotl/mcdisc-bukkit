FROM openjdk:8

RUN wget https://hub.spigotmc.org/jenkins/job/BuildTools/lastSuccessfulBuild/artifact/target/BuildTools.jar

RUN java -jar BuildTools.jar --rev 1.16.3

COPY ./* ./

ENTRYPOINT java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -jar spigot-1.16.3.jar
