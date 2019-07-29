# Gatling project

This a example of a gatling project using the service provided by them at URL: http://computer-database.gatling.io/computers

## Installation

To extend this project Java 8,  Scala and SBT should be already installed in your local environment. We used SDKMan ourselves, 
the installation process can be accessed in https://sdkman.io/i .
To run this project is just a matter of running from your shell the following command

```bash
sbt gatling:test
```
To run using docker all you need is build the provided Dockerfile and run it.
```bash
docker build . -t <name_of_image>
docker run  -v /path/to/host/machine/folder:/opt/gatling <name_of_image>
```
When the docker run ends a folder named /path/to/host/machine/folder/gatling_project_YYYY-mm-dd.HH:MM:SS. 
Its contents are the builded project and test reports.
