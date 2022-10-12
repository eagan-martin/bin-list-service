# bin-list-service

A repository for sample REST API built with Quarkus Framework.

>Note: This project uses Quarkus, the Supersonic Subatomic Java Framework. If you want to learn more about Quarkus, please visit its website: https://quarkus.io/.

---

## Local Development

### Running the application

You can run your application in dev mode that enables live coding using:

```
$ gradlew quarkusDev
```

### Refreshing dependencies

You can refresh Gradle dependencies using:

```
$ gradlew build --refresh-dependencies
```

### Try It

You can try call the endpoint using:

```
$ curl "http://localhost:8080/v1/bin/verify?cardNo=5443"
```

Note: It is suggested to test this using [Postman](https://www.postman.com/) or any other related REST API tool.

---