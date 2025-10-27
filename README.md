# Simple Registration Account

Simple web application built with Java EE and Vaadin with features:

1. Registration Account
2. Registration Account Batch
3. Table consist of submited accounts

## Clean and build project

```
mvn clean install
```

## Deploy project to .war

```
mvn wildfly:deploy
```

Create `mockva.war` in `/target` and ready to deploy on `wildfly`.

## Tested on

- Java 17.0.12
- Apache Maven 3.8.1
- Wildfly 24.0.1 Final
