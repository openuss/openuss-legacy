OpenUSS - Open University Support System
=========================================

How to Build
============
1. Download following libs:
- javax.transaction:jta:jar:1.0.1B from: http://www.java2s.com/Code/Jar/j/Downloadjta101bjar.htm
- jaybird:jaybird-full:jar:2.1.1 from: http://www.java2s.com/Code/Jar/j/Downloadjaybirdfull211jar.htm

2. Change from Jaybird to PostgreSQL
- Run the database with Docker: https://hub.docker.com/_/postgres
- Command to run Docker image PostgreSQL
-- docker run -p 5432:5432 --name postgresdb -e POSTGRES_PASSWORD=password -d postgres
- Create Databases: 
-- openuss30
-- openuss30test

In the Docker container (CLI):
psql -U postgres
CREATE DATABASE openuss30;
CREATE DATABASE openuss30test;
