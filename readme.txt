OpenUSS - Open University Support System
=========================================

Building OpenUSS from source code:

Requisites:
------------

+ Maven 2.0.4
  |- set the PATH variable to your local maven installation binary directory, e.g. /maven2/bin
  |- create an eclipse classpath variable called M2_REPO and link it to your local maven repository 
	@see http://maven.apache.org/plugins/maven-eclipse-plugin/overview.html#Maven%202%20repository for details
+ Java SE 5 Update 6 or higher
+ Ant 1.6.5
+ Firebird 1.5.3
 |- Installed empty Firebird Database that is reachable at 
 |
 |- url:	 	jdbc:firebirdsql:localhost:openuss30
 |- user: 		sysdba
 |- password:	masterkey

The database schema will be created automatically by hibernate.

Be sure that the java command at your shell points to Java SE 5. 
Check it by typing java -version into your shell.

Building:
-----------

1. Install missing libraries.
Previously you had to install needed libraries that currently cannot be found in public repositories.
To install these libraries run the install-missing ant script from {openuss}/tools/missing-dependencies} folder.
Due to our own maven 2 repository "http://openussteam.uni-muenster.de/repository" all needed libraries will be
hopefully downloaded automatically.

2. Needed Server
During the build the junit tests will run and these tests need two servers up and running.
1. the firebird database server must be running at localhost and provide a database called openuss30-test
2. an email server must be up and running at localhost and provide a smtp port. Take a look at ./servers/jes-mail/mail.bat.
This will start a small java email server for developing purpose.

3. Building current source folder
mvn install

Troubleshooting:
-----------------
This section was moved to documentation/src/site/fml/troubleshooting.fml
