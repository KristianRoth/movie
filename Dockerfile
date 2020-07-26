FROM gradle:jdk11   

VOLUME /tmp

WORKDIR /code

# Prepare by downloading dependencies
ADD pom.xml /code/pom.xml  
#RUN ["mvn", "dependency:resolve"]
#RUN ["mvn", "verify"]

# Adding source, compile and package into a fat jar

RUN ["mvn", "clean", "install"]

RUN ["ls", "/code/target"]
RUN ["pwd"]
RUN ["ls", "-ltrh", "/code/target"]

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "/code/target/movie-0.0.1-SNAPSHOT.jar" ]
