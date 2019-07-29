
FROM openjdk:8

RUN apt-get install git -y
RUN mkdir -p /opt/gatling/
RUN \
  curl -L -o sbt-1.2.8.deb http://dl.bintray.com/sbt/debian/sbt-1.2.8.deb && \
  dpkg -i sbt-1.2.8.deb && \
  rm sbt-1.2.8.deb && \
  apt-get update && \
  apt-get install sbt && \
  sbt sbtVersion 

ENV URL_GATLING_PROJECT https://github.com/guuhh/gatlingproject.git 

WORKDIR /opt/gatling

CMD git clone $URL_GATLING_PROJECT gatling_project && \
	cd gatling_project && \ 
	sbt gatling:test && \
	cd .. && \
	mv gatling_project gatling_project_$( date +%Y-%m-%d.%H:%M:%S )
