# h2zero

This is a object relationship mapper for MySQL and Java.

Whilst still a work in progress, database persistence is fully supported and running in production environments.

*See the other markdown (.md) files for more information*

# Background

There are so many object relational mappers (ORMs) out there that do what h2zero does.  It isn't special, it just provides a link between your database and generates your Java code to be able to use it.

Unlike other ORMs, you have full control of the SQL that is generated.  So, if you

 - use MySQL
 - use Java
 - optionally use tomcat (JSPs/Tag Libraries/Servlets)
 
Then this is the ORM for you.
 
Unlike other ORMs, most (not all, but most) of the SQL must be hand-crafted by you, no horrible *best-effort* code  generation, no horrific un-parseable SQL statements in the logs.

You have complete control over the SQL statements that are executed meaning that you can optimise your statements the way you want them, not the way that the generator thinks you should.

Your database, just the way that you designed it.

# Requirements

 - **Java**
 - **MySQL**
 - **c3p0**
 - **Ant**

# Creating a h2zero configuration file


# The CRUD operations (and some more)

## Create 

Normal plain old java objects (POJO).  Just instantiate and insert (or insert silent)

## Read 

Finders, Finders and more Finders

## Update

Updaters

## Delete

Deleters

## and some more

Counters - ever need just a simple count of the data that returns you the number of rows that match a specific sql statement?

This is what these are for:

    "counters": [
      {
      "name": "countAllFlIsActive",
        "selectClause": "select count(*) from blog",
        "whereClause": "where fl_is_active = ?",
        "whereFields": [
          "fl_is_active"
        ]
      }
    ]

This will generate the following files

# The Little Things

  1. No more file changes for generation.  If the h2zero file generation is the same, you won't see any file differences (unless you update to a newer version of h2zero that is) - we don't put dates, change-able comments or anything else in it
  1. We tell you what file was used to generate the code - we use the templar templating language, which is open source and easily modifiable.
  1. Easy editing of the templar files.  If you don't like the way that we generate the file, you are free to modify it any way that suits you.
  1. Exceptions, darn those Exceptions.  Whilst we based h2zero generation on the EJB model (and yes, people still use these things) - we felt that sometimes you just don't need to throw an exception if you already have an expectation of what you want.  The classic example is the login page.
    1. User logs in with email address and password
    1. You try to find the user by email address and password
    1. Couldn't find it - EXCEPTION, EXCEPTION, EXCEPTION
    1. you can just choose to return null (by using the *Silent method signature) - in the above case this means that you couldn't find the user and happily display an error message, rather than littering your code with try/catch/finally code everywhere.
