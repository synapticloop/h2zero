<a name="documentr_top"></a>

> See [http://synapticloop.github.io/h2zero/](http://synapticloop.github.io/h2zero/) for updated documentation

[![Build Status](https://travis-ci.org/synapticloop/h2zero.svg?branch=master)](https://travis-ci.org/synapticloop/h2zero) [![Download](https://api.bintray.com/packages/synapticloop/maven/h2zero/images/download.svg)](https://bintray.com/synapticloop/maven/h2zero/_latestVersion) [![GitHub Release](https://img.shields.io/github/release/synapticloop/h2zero.svg)](https://github.com/synapticloop/h2zero/releases) [![Gradle Plugin Release](https://img.shields.io/badge/gradle%20plugin-3.1.6-blue.svg)](https://plugins.gradle.org/plugin/synapticloop.h2zero) 

> **This project requires JVM version of at least 1.17**






<a name="documentr_heading_0"></a>

# Table of Contents <sup><sup>[top](#documentr_top)</sup></sup>



 - [Table of Contents](#documentr_heading_0)
 - [h2zero](#documentr_heading_1)
 - [VERSION 3](#documentr_heading_2)
 - [VERSION 2](#documentr_heading_3)
 - [Extensions](#documentr_heading_4)
 - [Background](#documentr_heading_5)
 - [Requirements](#documentr_heading_6)
 - [Creating a h2zero configuration file](#documentr_heading_7)
 - [The CRUD operations (and some more)](#documentr_heading_8)
   - [Create](#documentr_heading_9)
   - [Read](#documentr_heading_10)
   - [Update](#documentr_heading_11)
   - [Delete](#documentr_heading_12)
   - [and some more](#documentr_heading_13)
 - [The Little Things](#documentr_heading_14)
 - [h2Zero generation](#documentr_heading_15)
   - [gradle plugin](#documentr_heading_16)
   - [ant](#documentr_heading_17)
   - [Command line generation](#documentr_heading_18)
 - [Building the Package](#documentr_heading_19)
   - [*NIX/Mac OS X](#documentr_heading_20)
   - [Windows](#documentr_heading_21)
 - [Logging - slf4j](#documentr_heading_22)
   - [Log4j](#documentr_heading_23)
 - [Artefact Publishing - Github](#documentr_heading_28)
 - [All-In-One](#documentr_heading_29)
 - [Artefact Publishing - Bintray](#documentr_heading_30)
   - [maven setup](#documentr_heading_31)
   - [gradle setup](#documentr_heading_32)
 - [Artefact Publishing - gradle plugin portal](#documentr_heading_33)
   - [Dependencies - Gradle](#documentr_heading_34)
   - [Dependencies - Maven](#documentr_heading_35)
   - [Dependencies - Downloads](#documentr_heading_36)
 - [License](#documentr_heading_43)






<a name="documentr_heading_1"></a>

# h2zero <sup><sup>[top](#documentr_top)</sup></sup>



> lightweight ORM generator for mysql/sqlite, java with extensions for taglibs and routemaster




<a name="documentr_heading_2"></a>

# VERSION 3 <sup><sup>[top](#documentr_top)</sup></sup>

    This release is a pared down version of h2zero, with un-finished 
    generators being removed and some placed into extensions

The following generators have been removed from h2zero

 - taglibs (see [https://github.com/synapticloop/h2zero-extension-taglibs](https://github.com/synapticloop/h2zero-extension-taglibs) for more details
 - forms
 - adminpages
 - servlets



<a name="documentr_heading_3"></a>

# VERSION 2 <sup><sup>[top](#documentr_top)</sup></sup>

    This now only supports slf4j logging API and as such the 
    "logging" key has been removed from the "options" JSON object


This is a object relationship mapper for MySQL and Java.

Whilst still a work in progress, database persistence is fully supported and running in production environments.



<a name="documentr_heading_4"></a>

# Extensions <sup><sup>[top](#documentr_top)</sup></sup>

Extensions for h2zero are now supported see [https://github.com/synapticloop/h2zero-extension-routemaster-restful](https://github.com/synapticloop/h2zero-extension-routemaster-restful)



<a name="documentr_heading_5"></a>

# Background <sup><sup>[top](#documentr_top)</sup></sup>

There are so many object relational mappers (ORMs) out there that do what h2zero does.  It isn't special, it just provides a link between your database and generates your Java code to be able to use it.

Unlike other ORMs, you have full control of the SQL that is generated.  So, if you

 - use MySQL
 - use Java
 - optionally use tomcat (JSPs/Tag Libraries/Servlets)
 
Then this is the ORM for you.
 
Unlike other ORMs, most (not all, but most) of the SQL must be hand-crafted by you, no horrible *best-effort* code  generation, no horrific un-parseable SQL statements in the logs.

You have complete control over the SQL statements that are executed meaning that you can optimise your statements the way you want them, not the way that the generator thinks you should.

Your database, just the way that you designed it.



<a name="documentr_heading_6"></a>

# Requirements <sup><sup>[top](#documentr_top)</sup></sup>

 - **Java**
 - **MySQL**
 - **c3p0**
 - **Ant** or **gradle** or **command line usage**



<a name="documentr_heading_7"></a>

# Creating a h2zero configuration file <sup><sup>[top](#documentr_top)</sup></sup>

By default the h2zero file would look like the following:



```
{
	"options": {
		...
	},
	"database": {
		"schema": "...",
		"package": "...",
		"tables": [
			"fields": [
				{...}
			],
			"fieldFinders": [
				{...}
			],
			"finders": [
				{...}
			],
			"deleters": [
				{...}
			],
			"inserters": [
				{...}
			],
			"updaters": [
				{...}
			],
			"questions": [
				{...}
			],
			"counters": [
				{...}
			],
			"constants": [
				{...}
			],
		], 
		"views": [
			{...}
		]
	}
	
}
```




<a name="documentr_heading_8"></a>

# The CRUD operations (and some more) <sup><sup>[top](#documentr_top)</sup></sup>



<a name="documentr_heading_9"></a>

## Create  <sup><sup>[top](#documentr_top)</sup></sup>

Normal plain old java objects (POJO).  Just instantiate and insert (or insert silent)



<a name="documentr_heading_10"></a>

## Read  <sup><sup>[top](#documentr_top)</sup></sup>

Finders, Finders and more Finders



<a name="documentr_heading_11"></a>

## Update <sup><sup>[top](#documentr_top)</sup></sup>

Updaters



<a name="documentr_heading_12"></a>

## Delete <sup><sup>[top](#documentr_top)</sup></sup>

Deleters



<a name="documentr_heading_13"></a>

## and some more <sup><sup>[top](#documentr_top)</sup></sup>

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



<a name="documentr_heading_14"></a>

# The Little Things <sup><sup>[top](#documentr_top)</sup></sup>

  1. No more file changes for generation.  If the h2zero file generation is the same, you won't see any file differences (unless you update to a newer version of h2zero that is) - we don't put dates, change-able comments or anything else in it
  1. We tell you what file was used to generate the code - we use the templar templating language, which is open source and easily modifiable.
  1. Easy editing of the templar files.  If you don't like the way that we generate the file, you are free to modify it any way that suits you.
  1. Exceptions, darn those Exceptions.  Whilst we based h2zero generation on the EJB model (and yes, people still use these things) - we felt that sometimes you just don't need to throw an exception if you already have an expectation of what you want.  The classic example is the login page.
    1. User logs in with email address and password
    1. You try to find the user by email address and password
    1. Couldn't find it - EXCEPTION, EXCEPTION, EXCEPTION
    1. you can just choose to return null (by using the *Silent method signature) - in the above case this means that you couldn't find the user and happily display an error message, rather than littering your code with try/catch/finally code everywhere.





<a name="documentr_heading_15"></a>

# h2Zero generation <sup><sup>[top](#documentr_top)</sup></sup>



<a name="documentr_heading_16"></a>

## gradle plugin <sup><sup>[top](#documentr_top)</sup></sup>

Assuming that you have included the plugin



```
h2zero {
	inFile = 'src/test/resources/sample.h2zero'
	outDir = '.'
	verbose = 'false'
}
```





<a name="documentr_heading_17"></a>

## ant <sup><sup>[top](#documentr_top)</sup></sup>

assuming that you have added the dependency above to the `runtime` configuration



```
task h2zero << {
	ant.taskdef(resource: 'h2zero.properties',
				classpath: configurations.runtime.asPath) {
	}

	ant.h2zero(inFile: 'src/main/resources/sample.h2zero',
				outDir: '.',
				verbose: 'false') {
	}
}
```





<a name="documentr_heading_18"></a>

## Command line generation <sup><sup>[top](#documentr_top)</sup></sup>

try



```
  java -jar h2zero-all.jar
```



which will output:




```
Usage:

Command line usage:
===================

  java -jar h2zero-all.jar <mode> <options>

There are three (3) modes of operation, namely:

  generate  - this will generate the source code from the provided .h2zero file
  revenge   - this will reverse engineer a database to an .h2zero file
  quick     - this will generate a quick .h2zero file and output it to the console


generate options:
-----------------
  -in <arg>   the input file
  -out <arg>  the directory to output the generated files
  -verbose    turn on verbose output


revenge options:
----------------
  -host <arg>      the host of the database
  -database <arg>  the database
  -user <arg>      the user that can connect to the database
  -password <arg>  the password for the user
  -outFile <arg>   the file to write out the .h2zero file


quick options:
--------------
  -schema <arg>          the schema
  -generators <arg,...>  which generators to invoke
  -tables <arg,...>      which tables to generate
  -foreign <arg,...>     which foreign keys between the tables


Gradle build.gradle usage:
==========================

If you are using gradle, you can add this to your build.gradle file

  h2zero {
    inFile = 'src/main/resources/your_file_name_here.h2zero'
    outDir = '.'
    verbose = 'false'
  }


Ant build.xml usage:
====================

If you are using ant, you can add this to your build.xml file

  <path id="classpath-h2zero">
    <fileset dir="lib/runtime">
      <include name="*.jar"/>
    </fileset>
  </path>

  <target name="h2zero-generate" description="h2zero generate">
    <taskdef resource="h2zero.properties" classpathref="classpath-h2zero" />
    <h2zero inFile="src/main/java/your_file_name_here.h2zero" outDir="." verbose="false" />
  </target>

Exiting...
```







<a name="documentr_heading_19"></a>

# Building the Package <sup><sup>[top](#documentr_top)</sup></sup>



<a name="documentr_heading_20"></a>

## *NIX/Mac OS X <sup><sup>[top](#documentr_top)</sup></sup>

From the root of the project, simply run

`./gradlew build`




<a name="documentr_heading_21"></a>

## Windows <sup><sup>[top](#documentr_top)</sup></sup>

`./gradlew.bat build`


This will compile and assemble the artefacts into the `build/libs/` directory.

Note that this may also run tests (if applicable see the Testing notes)



<a name="documentr_heading_22"></a>

# Logging - slf4j <sup><sup>[top](#documentr_top)</sup></sup>

slf4j is the logging framework used for this project.  In order to set up a logging framework with this project, sample configurations are below:



<a name="documentr_heading_23"></a>

## Log4j <sup><sup>[top](#documentr_top)</sup></sup>


You will need to include dependencies for this - note that the versions may need to be updated.

### Maven



```
<dependency>
	<groupId>org.apache.logging.log4j</groupId>
	<artifactId>log4j-slf4j-impl</artifactId>
	<version>2.5</version>
	<scope>runtime</scope>
</dependency>

<dependency>
	<groupId>org.apache.logging.log4j</groupId>
	<artifactId>log4j-core</artifactId>
	<version>2.5</version>
	<scope>runtime</scope>
</dependency>

```



### Gradle &lt; 2.1



```
dependencies {
	...
	runtime(group: 'org.apache.logging.log4j', name: 'log4j-slf4j-impl', version: '2.5', ext: 'jar')
	runtime(group: 'org.apache.logging.log4j', name: 'log4j-core', version: '2.5', ext: 'jar')
	...
}
```


### Gradle &gt;= 2.1



```
dependencies {
	...
	runtime 'org.apache.logging.log4j:log4j-slf4j-impl:2.5'
	runtime 'org.apache.logging.log4j:log4j-core:2.5'
	...
}
```




### Setting up the logging:

A sample `log4j2.xml` is below:



```
<Configuration status="WARN">
	<Appenders>
		<Console name="Console" target="SYSTEM_OUT">
			<PatternLayout pattern="%d{HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n"/>
		</Console>
	</Appenders>
	<Loggers>
		<Root level="trace">
			<AppenderRef ref="Console"/>
		</Root>
	</Loggers>
</Configuration>
```





<a name="documentr_heading_28"></a>

# Artefact Publishing - Github <sup><sup>[top](#documentr_top)</sup></sup>

This project publishes artefacts to [GitHub](https://github.com/)

> Note that the latest version can be found [https://github.com/synapticloop/h2zero/releases](https://github.com/synapticloop/h2zero/releases)

As such, this is not a repository, but a location to download files from.




<a name="documentr_heading_29"></a>

# All-In-One <sup><sup>[top](#documentr_top)</sup></sup>

This project's artefact output is an 'all in one' jar which includes all runtime dependencies.

This should appear in the artefact repository along with the compiled code, as a convention, this is usually appended with an `-all` classifier

For example:

`h2zero-3.1.6.jar -> h2zero-3.1.6-all.jar`





<a name="documentr_heading_30"></a>

# Artefact Publishing - Bintray <sup><sup>[top](#documentr_top)</sup></sup>

This project publishes artefacts to [bintray](https://bintray.com/)

> Note that the latest version can be found [https://bintray.com/synapticloop/maven/h2zero/view](https://bintray.com/synapticloop/maven/h2zero/view)



<a name="documentr_heading_31"></a>

## maven setup <sup><sup>[top](#documentr_top)</sup></sup>

this comes from the jcenter bintray, to set up your repository:



```
<?xml version="1.0" encoding="UTF-8" ?>
<settings xsi:schemaLocation='http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd' xmlns='http://maven.apache.org/SETTINGS/1.0.0' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'>
  <profiles>
    <profile>
      <repositories>
        <repository>
          <snapshots>
            <enabled>false</enabled>
          </snapshots>
          <id>central</id>
          <name>bintray</name>
          <url>http://jcenter.bintray.com</url>
        </repository>
      </repositories>
      <pluginRepositories>
        <pluginRepository>
          <snapshots>
            <enabled>false</enabled>
          </snapshots>
          <id>central</id>
          <name>bintray-plugins</name>
          <url>http://jcenter.bintray.com</url>
        </pluginRepository>
      </pluginRepositories>
      <id>bintray</id>
    </profile>
  </profiles>
  <activeProfiles>
    <activeProfile>bintray</activeProfile>
  </activeProfiles>
</settings>
```





<a name="documentr_heading_32"></a>

## gradle setup <sup><sup>[top](#documentr_top)</sup></sup>

Repository



```
repositories {
	maven {
		url  "http://jcenter.bintray.com" 
	}
}
```



or just



```
repositories {
	jcenter()
}
```





<a name="documentr_heading_33"></a>

# Artefact Publishing - gradle plugin portal <sup><sup>[top](#documentr_top)</sup></sup>

This project publishes artefacts to [the gradle plugin portal](https://plugins.gradle.org/)

> Note that the latest version can be found [https://plugins.gradle.org/plugin/synapticloop.h2zero](https://plugins.gradle.org/plugin/synapticloop.h2zero)



<a name="documentr_heading_34"></a>

## Dependencies - Gradle <sup><sup>[top](#documentr_top)</sup></sup>



```
dependencies {
	runtime(group: 'synapticloop', name: 'h2zero', version: '3.1.6', ext: 'jar')

	compile(group: 'synapticloop', name: 'h2zero', version: '3.1.6', ext: 'jar')
}
```



or, more simply for versions of gradle greater than 2.1



```
dependencies {
	runtime 'synapticloop:h2zero:3.1.6'

	compile 'synapticloop:h2zero:3.1.6'
}
```





<a name="documentr_heading_35"></a>

## Dependencies - Maven <sup><sup>[top](#documentr_top)</sup></sup>



```
<dependency>
	<groupId>synapticloop</groupId>
	<artifactId>h2zero</artifactId>
	<version>3.1.6</version>
	<type>jar</type>
</dependency>
```





<a name="documentr_heading_36"></a>

## Dependencies - Downloads <sup><sup>[top](#documentr_top)</sup></sup>


You will also need to download the following dependencies:



### cobertura dependencies

  - `net.sourceforge.cobertura:cobertura:2.1.1`: (It may be available on one of: [bintray](https://bintray.com/net.sourceforge.cobertura/maven/cobertura/2.1.1/view#files/net.sourceforge.cobertura/cobertura/2.1.1) [mvn central](http://search.maven.org/#artifactdetails|net.sourceforge.cobertura|cobertura|2.1.1|jar))


### compile dependencies

  - `synapticloop:templar:1.4.4`: (It may be available on one of: [bintray](https://bintray.com/synapticloop/maven/templar/1.4.4/view#files/synapticloop/templar/1.4.4) [mvn central](http://search.maven.org/#artifactdetails|synapticloop|templar|1.4.4|jar))
  - `org.json:json:20180130`: (It may be available on one of: [bintray](https://bintray.com/org.json/maven/json/20180130/view#files/org.json/json/20180130) [mvn central](http://search.maven.org/#artifactdetails|org.json|json|20180130|jar))
  - `org.apache.ant:ant:1.10.2`: (It may be available on one of: [bintray](https://bintray.com/org.apache.ant/maven/ant/1.10.2/view#files/org.apache.ant/ant/1.10.2) [mvn central](http://search.maven.org/#artifactdetails|org.apache.ant|ant|1.10.2|jar))
  - `com.mchange:c3p0:0.9.5.2`: (It may be available on one of: [bintray](https://bintray.com/com.mchange/maven/c3p0/0.9.5.2/view#files/com.mchange/c3p0/0.9.5.2) [mvn central](http://search.maven.org/#artifactdetails|com.mchange|c3p0|0.9.5.2|jar))
  - `commons-validator:commons-validator:1.6`: (It may be available on one of: [bintray](https://bintray.com/commons-validator/maven/commons-validator/1.6/view#files/commons-validator/commons-validator/1.6) [mvn central](http://search.maven.org/#artifactdetails|commons-validator|commons-validator|1.6|jar))
  - `org.slf4j:slf4j-api:1.7.25`: (It may be available on one of: [bintray](https://bintray.com/org.slf4j/maven/slf4j-api/1.7.25/view#files/org.slf4j/slf4j-api/1.7.25) [mvn central](http://search.maven.org/#artifactdetails|org.slf4j|slf4j-api|1.7.25|jar))
  - `org.apache.logging.log4j:log4j-slf4j-impl:2.10.0`: (It may be available on one of: [bintray](https://bintray.com/org.apache.logging.log4j/maven/log4j-slf4j-impl/2.10.0/view#files/org.apache.logging.log4j/log4j-slf4j-impl/2.10.0) [mvn central](http://search.maven.org/#artifactdetails|org.apache.logging.log4j|log4j-slf4j-impl|2.10.0|jar))
  - `org.apache.logging.log4j:log4j-core:2.10.0`: (It may be available on one of: [bintray](https://bintray.com/org.apache.logging.log4j/maven/log4j-core/2.10.0/view#files/org.apache.logging.log4j/log4j-core/2.10.0) [mvn central](http://search.maven.org/#artifactdetails|org.apache.logging.log4j|log4j-core|2.10.0|jar))
  - `io.dropwizard.metrics:metrics-core:3.1.2`: (It may be available on one of: [bintray](https://bintray.com/io.dropwizard.metrics/maven/metrics-core/3.1.2/view#files/io.dropwizard.metrics/metrics-core/3.1.2) [mvn central](http://search.maven.org/#artifactdetails|io.dropwizard.metrics|metrics-core|3.1.2|jar))
  - `javax.mail:javax.mail-api:1.6.1`: (It may be available on one of: [bintray](https://bintray.com/javax.mail/maven/javax.mail-api/1.6.1/view#files/javax.mail/javax.mail-api/1.6.1) [mvn central](http://search.maven.org/#artifactdetails|javax.mail|javax.mail-api|1.6.1|jar))
  - `com.github.stefanbirkner:system-rules:1.17.1`: (It may be available on one of: [bintray](https://bintray.com/com.github.stefanbirkner/maven/system-rules/1.17.1/view#files/com.github.stefanbirkner/system-rules/1.17.1) [mvn central](http://search.maven.org/#artifactdetails|com.github.stefanbirkner|system-rules|1.17.1|jar))


### runtime dependencies

  - `synapticloop:templar:1.4.4`: (It may be available on one of: [bintray](https://bintray.com/synapticloop/maven/templar/1.4.4/view#files/synapticloop/templar/1.4.4) [mvn central](http://search.maven.org/#artifactdetails|synapticloop|templar|1.4.4|jar))
  - `org.json:json:20180130`: (It may be available on one of: [bintray](https://bintray.com/org.json/maven/json/20180130/view#files/org.json/json/20180130) [mvn central](http://search.maven.org/#artifactdetails|org.json|json|20180130|jar))
  - `com.mchange:c3p0:0.9.5.2`: (It may be available on one of: [bintray](https://bintray.com/com.mchange/maven/c3p0/0.9.5.2/view#files/com.mchange/c3p0/0.9.5.2) [mvn central](http://search.maven.org/#artifactdetails|com.mchange|c3p0|0.9.5.2|jar))
  - `commons-validator:commons-validator:1.6`: (It may be available on one of: [bintray](https://bintray.com/commons-validator/maven/commons-validator/1.6/view#files/commons-validator/commons-validator/1.6) [mvn central](http://search.maven.org/#artifactdetails|commons-validator|commons-validator|1.6|jar))
  - `org.slf4j:slf4j-api:1.7.25`: (It may be available on one of: [bintray](https://bintray.com/org.slf4j/maven/slf4j-api/1.7.25/view#files/org.slf4j/slf4j-api/1.7.25) [mvn central](http://search.maven.org/#artifactdetails|org.slf4j|slf4j-api|1.7.25|jar))
  - `io.dropwizard.metrics:metrics-core:3.1.2`: (It may be available on one of: [bintray](https://bintray.com/io.dropwizard.metrics/maven/metrics-core/3.1.2/view#files/io.dropwizard.metrics/metrics-core/3.1.2) [mvn central](http://search.maven.org/#artifactdetails|io.dropwizard.metrics|metrics-core|3.1.2|jar))
  - `javax.mail:javax.mail-api:1.6.1`: (It may be available on one of: [bintray](https://bintray.com/javax.mail/maven/javax.mail-api/1.6.1/view#files/javax.mail/javax.mail-api/1.6.1) [mvn central](http://search.maven.org/#artifactdetails|javax.mail|javax.mail-api|1.6.1|jar))


### shadowRuntime dependencies

  - `synapticloop:templar:1.4.4`: (It may be available on one of: [bintray](https://bintray.com/synapticloop/maven/templar/1.4.4/view#files/synapticloop/templar/1.4.4) [mvn central](http://search.maven.org/#artifactdetails|synapticloop|templar|1.4.4|jar))
  - `org.json:json:20180130`: (It may be available on one of: [bintray](https://bintray.com/org.json/maven/json/20180130/view#files/org.json/json/20180130) [mvn central](http://search.maven.org/#artifactdetails|org.json|json|20180130|jar))
  - `org.apache.ant:ant:1.10.2`: (It may be available on one of: [bintray](https://bintray.com/org.apache.ant/maven/ant/1.10.2/view#files/org.apache.ant/ant/1.10.2) [mvn central](http://search.maven.org/#artifactdetails|org.apache.ant|ant|1.10.2|jar))


### testCompile dependencies

  - `junit:junit:4.12`: (It may be available on one of: [bintray](https://bintray.com/junit/maven/junit/4.12/view#files/junit/junit/4.12) [mvn central](http://search.maven.org/#artifactdetails|junit|junit|4.12|jar))
  - `org.mockito:mockito-all:1.10.19`: (It may be available on one of: [bintray](https://bintray.com/org.mockito/maven/mockito-all/1.10.19/view#files/org.mockito/mockito-all/1.10.19) [mvn central](http://search.maven.org/#artifactdetails|org.mockito|mockito-all|1.10.19|jar))
  - `com.github.stefanbirkner:system-rules:1.16.1`: (It may be available on one of: [bintray](https://bintray.com/com.github.stefanbirkner/maven/system-rules/1.16.1/view#files/com.github.stefanbirkner/system-rules/1.16.1) [mvn central](http://search.maven.org/#artifactdetails|com.github.stefanbirkner|system-rules|1.16.1|jar))


### testRuntime dependencies

  - `junit:junit:4.12`: (It may be available on one of: [bintray](https://bintray.com/junit/maven/junit/4.12/view#files/junit/junit/4.12) [mvn central](http://search.maven.org/#artifactdetails|junit|junit|4.12|jar))
  - `org.mockito:mockito-all:1.10.19`: (It may be available on one of: [bintray](https://bintray.com/org.mockito/maven/mockito-all/1.10.19/view#files/org.mockito/mockito-all/1.10.19) [mvn central](http://search.maven.org/#artifactdetails|org.mockito|mockito-all|1.10.19|jar))
  - `com.github.stefanbirkner:system-rules:1.16.1`: (It may be available on one of: [bintray](https://bintray.com/com.github.stefanbirkner/maven/system-rules/1.16.1/view#files/com.github.stefanbirkner/system-rules/1.16.1) [mvn central](http://search.maven.org/#artifactdetails|com.github.stefanbirkner|system-rules|1.16.1|jar))
  - `mysql:mysql-connector-java:6.0.6`: (It may be available on one of: [bintray](https://bintray.com/mysql/maven/mysql-connector-java/6.0.6/view#files/mysql/mysql-connector-java/6.0.6) [mvn central](http://search.maven.org/#artifactdetails|mysql|mysql-connector-java|6.0.6|jar))
  - `org.xerial:sqlite-jdbc:3.21.0.1`: (It may be available on one of: [bintray](https://bintray.com/org.xerial/maven/sqlite-jdbc/3.21.0.1/view#files/org.xerial/sqlite-jdbc/3.21.0.1) [mvn central](http://search.maven.org/#artifactdetails|org.xerial|sqlite-jdbc|3.21.0.1|jar))

**NOTE:** You may need to download any dependencies of the above dependencies in turn (i.e. the transitive dependencies)



<a name="documentr_heading_43"></a>

# License <sup><sup>[top](#documentr_top)</sup></sup>



```
The MIT License (MIT)

Copyright (c) 2018 synapticloop

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```




--

> `This README.md file was hand-crafted with care utilising synapticloop`[`templar`](https://github.com/synapticloop/templar/)`->`[`documentr`](https://github.com/synapticloop/documentr/)

--
