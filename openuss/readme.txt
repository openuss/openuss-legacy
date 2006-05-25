OpenUSS - Open University Support System
=========================================

Building OpenUSS from source code:

Requisites:
------------

Maven 2.0.4 
Java SE 5 Update 6
Ant 1.6.5
Firebird 1.5.3

Installed empty Firebird Database that is reachable with 

url:	 	jdbc:firebirdsql:localhost:openuss30
user: 		sysdba
password:	masterkey

The database schema will be created automatically by hibernate.

Building:
-----------

1. Install missing libraries.
You call install needed libraries that currently cannot find in public repositories.
To install these libraries run the install-missing ant script from {openuss}/tools/missing-dependencies} folder.

2. Cleaning current source folder
mvn clean

3. Building current source folder
mvn install