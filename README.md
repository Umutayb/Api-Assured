# Quickstart Library

### How To Get Started:

First, the library should be exported into an empty automation project. There are two ways of doing this.
### First Way:

[![](https://jitpack.io/v/umutayb/api-assured.svg)](https://jitpack.io/#umutayb/api-assured)

The dependency can be acquired by adding Jitpack repository into the pom.xml, as well as the dependency for the library as:
```xml
<dependencies>
    <!-- Framework -->
        <dependency>
            <groupId>com.github.Umutayb</groupId>
            <artifactId>Api-Assured</artifactId>
            <version>0.1.8</version>
        </dependency>   
</dependencies>
.
.
.
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```
### Second Way:
```shell
mvn clean package -DbuildDirectory=directory/to/project/lib
```
For instance:
```shell
mvn clean package -DbuildDirectory=/Users/Umut/Github/Api-Automation-Sample-Cucumber/lib
```
There, the imported jar file should be added as a dependency in pom.xml file of that project:
```xml
    <!-- Framework -->
        <dependency>
            <groupId>bora</groupId>
            <artifactId>API-Framework-0.1.8.jar</artifactId>
            <version>0.0.2</version>
            <systemPath>${project.basedir}/lib/API-FRAMEWORK-0.1.8.jar</systemPath>
            <scope>system</scope>
            <type>jar</type>
        </dependency>
```

After updating your project, the quickstart library is ready to use.
___
### How To Use:

The quickstart library consists of many utility methods and a ready to use built in Rest Assured support using Retrofit 2 and OkHttp.
The infrastructure allows easy representation of endpoints with Retrofit interfaces.

#### Example execution command:
In order to execute a specific feature file in a specific browser, use:
 ```shell
>mvn clean test -Dcucumber.options="--tags @PetStore"
 ```

#### To create a cucumber project:
Run the following command:
````shell
mvn archetype:generate                      \
"-DarchetypeGroupId=io.cucumber"            \
"-DarchetypeArtifactId=cucumber-archetype"  \
"-DarchetypeVersion=6.10.4"                 \
"-DgroupId=hellocucumber"                   \
"-DartifactId=hellocucumber"                \
"-Dpackage=hellocucumber"                   \
"-Dversion=0.0.1"                           \
"-DinteractiveMode=false"
````
