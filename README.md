# What is this Project ?

This is a reference app destinated to start with a new project, it contains all what you need (Authentication JWT, Logging, Tracing, Database configuration and versionning management, mappers, and best practices for a spring application).
What is special for this app is that it supports multi tenant databases in case you need to have a customer per DB, and also the configuration for liquibase to manage the schema for different DBs.

## Run the application
The app is compressed into a FatJar so after you build (```mvn clean install```) you can run it with the ```Java -Jar``` command.
