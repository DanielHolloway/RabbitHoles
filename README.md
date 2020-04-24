## Clean Architecture Example

### Blog post

https://medium.com/slalom-engineering/clean-architecture-with-java-11-f78bba431041

### Pre-requisite

Java 11

```
> java -version
openjdk version "11" 2018-09-25
OpenJDK Runtime Environment 18.9 (build 11+28)
OpenJDK 64-Bit Server VM 18.9 (build 11+28, mixed mode)
```

### Compile

`./gradlew clean build`

### Run Spring example

`java -jar application/spring-app/build/libs/spring-app-1.0.0.jar`

### Use the webbapps

#### Create Article
```
POST: http://localhost:8080/articles
Body:
{
  "title": "test@test.com",
  "topic": "Doe",  
  "link": "John"
}
```

#### Get all articles
```
GET: http://localhost:8080/articles
```

#### Get one article
```
GET: http://localhost:8080/articles/0675171368e011e882d5acde48001122
```

