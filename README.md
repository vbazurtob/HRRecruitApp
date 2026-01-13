# HRRecruitApp

A simple Human Resource recruitment application written in Java using Spring 5, PostgreSQL and MaterializeCSS.

## Live demo

You can try the live demo in [this URL](http://hrrecruitapp-vbazurtob.koyeb.app/).

## Download the code

Create a folder for containing the project source code, access the created folder and use git clone with the project url.

```
mkdir hrrecruitapp
cd hrrecruitapp
git clone https://github.com/vbazurtob/HRRecruitApp .
```

## Setup the database

PostgreSQL SQL files can be found in the folder `sql_data`. A database named `hr_recruit_app` is created to hold all db objects (schemas/tables/views). All tables are created under the schema named `vbazurtob_portfolio`.

This is a list of the important SQL script files required to run the application:

* **database.sql** : creates the database that will be used for storing data for the HRRecruitApp application.
* **schema.sql** : creates the schema under which all tables and database objects will be created for this application
* **tables.sql** : creates the tables to store data about jobs, applicants, users, etc.
* **security.sql** : grant the required privileges to the app user role to use the objects in the HRRecruitApp db.
* **initialData.sql** : creates all the minimum required data in the db so the application can work properly. It also create two demo users.
* **testData.sql** : creates extra records for testing


## Building the source code

This project uses two maven profiles: dev and release. By default, it uses the release profile to be used directly in production hosting environments.
In order to use the dev profile, comment the release profile section and uncomment the dev profile inside the POM.xml file.

```
<profiles>
		<profile>
		    <id>dev</id>
		    <properties>
		        <activatedProperties>dev</activatedProperties>
		    </properties>
<!-- 		    <activation> -->
<!-- 		        <activeByDefault>true</activeByDefault> -->
<!-- 		    </activation> -->
		    
		</profile>
		<profile>
		    <id>release</id>
		    <properties>
		        <activatedProperties>release</activatedProperties>
		    </properties>
		   	<activation>
		        <activeByDefault>true</activeByDefault>
		    </activation>
		    
		</profile>
	</profiles>
```

If you are working on dev environment, you can build the project using this command in the project's root folder:

```
mvn spring-boot:run
```

With the latest updates, you will need to configure some environment variables for the database connection
to work properly (see next sections for more details about it).

Alternatively if you are running the project on a IDE such as IntelliJ, you will need to add the
environment variables on your maven run configuration.

In addition, if a .war binary has been built, the application can be executed with this command via console
```
JDBC_DATABASE_URL=jdbc:postgresql://<db-host>:<db-port>/hr_recruit_app DATABASE_USER=hrapp_demo DATABASE_PASSWORD=<replace-with-password> DATABASE_SCHEMA=vbazurtob_portfolio java -jar HRRecruitApp-<current-version>.war`
```


Application will be ready to be accessed in http://localhost:8080/


# Docker image

The project has been updated with a `Dockerfile` to be able to run the application in a containerized environment.
Environment variables are now expected to be passed when running the application via docker to configure the 
database connection:

| Variable name | Description                                                                                                                                                                                                                                                     | Example                                             |
|---------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------|
|`JDBC_DATABASE_URL`| JDBC formatted connection string with the hostname, port, and database name                                                                                                                                                                                     | `jdbc:postgresql://<db-hostname>:<db-port>/<db-name>` |
`DATABASE_USER`| DB username with proper permissions to access the postgresql database                                                                                                                                                                                           | `hrapp_demo` |                                          
`DATABASE_PASSWORD`| DB password to connect to the database| `demo` |                                                
`DATABASE_SCHEMA` | The name of the default schema to be used by the connection. If this is not set correctly, <br/>it could lead to fallback to default "public" schema <br/>in certain hosting sites leading to SQL error not finding tables ("XXXXX relation not found" type of error | `vbazurtob_portfolio`|                                                                            

This an example of a linux command to run the built docker image passing the environmental variables via an env file:

```
docker run -d -p 8080:8080 --env-file config.env <your built docker image>
```

The content of the `config.env` file would be something like this:
```
JDBC_DATABASE_URL=jdbc:postgresql://<docker-host-ip-gateway>:<db-port>/hr_recruit_app
DATABASE_USER=<db-user>
DATABASE_PASSWORD=<db-pwd>
DATABASE_SCHEMA=vbazurtob_portfolio
```

# Troubleshooting the default database schema environmental variable 
The `application.properties` file defines the default db schema to be used during the entire duration of 
the connection to the database via the `spring.datasource.url` property, e.g.:
`spring.datasource.url=${JDBC_DATABASE_URL}?user= ...  &defaultSchema=${DATABASE_SCHEMA}`.

In some hosting environments, the property `defaultSchema` is not recognized and you might need to use 
instead the property `currentSchema`. Just keep that in mind and adjust accordingly if you start seeing
SQL errors complaining about not finding tables or other database objects ("... relation not found" errors).


# CI-CD
In addition, a github actions workflow file has been added in `.github/workflows/main-docker-publish.yml` to
facilitate the re-building of the docker image after every new version is released.

## Credits

Voltaire Bazurto Blacio 2018 - 2026 (All rights reserved)



