# Qcloud2Server

This is the backend server of QCloud 2.0 Quality control for Proteomics on the Cloud.

Clone this repo in your computer in order to run it.

## How to deploy

### Deploy in local server

In order to generate a .jar file or a .war (you should change this in the pom.xml file) go to the folder and type `mvn package`

This will run the tests and if it passes it will generate a .jar file.

Go to the target folder and execute `./java -jar QCloudXXX.jar`

### Deploy test in test server

You need to transpile the frontend as described in the readme of the client repository and copy the files in the **dist** folder of the client into the **resources/static** folder of the server.

If this folder does not exist create it.

Then, as before, you need to execute mvn package and upload the file to the server. Then execute with the desired profile:

`java -jar -Dspring.profiles.active=test QCloud2XXX.jar`

Notice the **-Dspring.profiles.active=test**

There are some profiles defined in the **application.yml** file.

test will execute the server listening at port 8181 and using the qcloud2test database instead of the qcloud2 production database.

### Deploy in production

As before, `mvn package` the upload to the server and run:

`java -jar QCloud2XXX.jar`

By default it will listen to the 8080 port and use the qcloud2 production database.

Remember to transpile and upload the client as is described in the README file of the client repository.

## General guide for developing

Every model has its own folder with its class definition, controller, service and repository. Try to keep it this way.

Try to make a dump of the current production database and use it as your development database in your local machine to assure that any change you make will not broke the database.

### Flyway

This application uses flyway migration system to keep the database integrity.

If you want to alter a current entity or create a new one you should first create the migration for that particular case.

This is a critical step and it is highly recommended to create database backup before do it.










