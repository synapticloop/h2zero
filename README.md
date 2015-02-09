# h2zero

This is a object relationship mapper for MySQL and Java.

Whilst still a work in progress, database persistence is fully supported and running in production environments.

# Background

There are so many object relational mappers (ORMs) out there that do what h2zero does.  It isn't special, it just provides a link between your database and generates your Java code to be able to use it.

Unlike other ORMs, you have full control of the SQL that is generated,

So, if you

 - use MySQL
 - use Java
 - optionally use tomcat
 
Then this is the ORM for you.
 
Unlike other ORMs, most (not all, but most) of the SQL must be hand-crafted by you, no horrible *best-effort* code generation, no horrific un-parseable SQL statements in the logs.

Your database, just the way that you designed it.

# Requirements

 - **Java**
 - **MySQL**
 - **c3p0**
 - **Ant**

# Creating a h2zero configuration file

