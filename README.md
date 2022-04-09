# Packer

Reads an input file containing delivery packages. For
each line in the file the method determines which package is to be
delivered. The basic requirement is to prioritise packages by less
weight and highest cost.

## Requirement

- Java 11 (min)
- Maven 3.6.3 (min)

## How to build

Clean the application and run test cases

```shell
mvn clean install
```


## Publishing library for public usage:

You will need to follow these steps:
1. Generate a GPG key

    ```gpg --send-keys --keyserver keyserver.ubuntu.com```


2. Sign and package your artifact using the gpg
   
    ```mvn package -Dgpg.passphrase=<the-passphrase-used-on-key-creation>```    


2. Upload your artifact to maven repository
    
    ```mvn deploy```
   
    You will need to ensure you have updated your settings file accordingly: ```~/.m2/settings.xml``` 


> Note: A user on sonatype.org and ownership of the groupId is required before pushing to a public repository 
## Using the library

Import the dependency:
```xml
<dependency>
   <groupId>com.mobiquity</groupId>
   <artifactId>packer</artifactId>
   <version>1.0-SNAPSHOT</version>
 </dependency>
```

Import the dependency:
```java
String filePath = "/path/to/source/file";
String results = Packer.pack(filePath);
```

You can refer to test cases within ```src/test/java/com/mobiquity``` for more examples.