# HRRecruitApp

A simple Human Resource recruitment application written in Java using Spring 5, PostgreSQL and MaterializeCSS.

## Live demo

You can try the live demo in [this URL](https://hrrecruitapp.herokuapp.com/).

## Download the code

Create a folder for containing the project source code, access the created folder and use git clone with the project url.

```
mkdir hrrecruitapp
cd hrrecruitapp
git clone https://github.com/vbazurtob/HRRecruitApp .
```

## Setup the database

PostgreSQL SQL files can be found in the folder sql_data. A database named HRRecruitApp is created to hold all tables. Tables are created under the schema named "HRRecruitApp"  (remember that
postgres is case sensitive so you need the use the double quotes to reference the schema name).

This is a list of the important SQL script files required to run the application:

* **schema.sql** : creates the database, schema, tables and the app user role that will be used for connecting from the app to the db for dev environment.
* **security.sql** : grant the required privileges to the app user role to use the objects in the HRRecruitApp db.
* **initialData.sql** : creates all the minimum required data in the db so the application can work properly. It also create two demo users.


## Building the source code

This project uses two maven profiles: dev and release. By default, it uses the release profile to be used directly in Heroku.
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

Application will be  ready to access in http://localhost:8080/


## Credits

Voltaire Bazurto Blacio 2018 (All rights reserved)



