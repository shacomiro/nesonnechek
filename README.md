### <center>nesonnechek</center>

<center>txt to epub ebook conversion API</center>

Read this document in: [한국어](./docs/README.KR.md)

## About The Project

This is a REST API that provides the ability to convert user-created txt files to epub files.

## Built With

-   Language
    -   Java 11
-   Framework
    -   Spring Boot 2.7.3
-   Database
    -   MySQL
    -   Redis
-   Library
    -   Lombok 6.5.1
    -   Junit 5.8.2
    -   Spring Data JPA
    -   Spring Rest Docs
    -   [epublib](https://github.com/psiegman/epublib)
-   Infra
    -   AWS S3

## Installation

### Clone the repository

```
git clone https://github.com/shacomiro/nesonnechek.git
```

### Install Gradle libraries

```
gradlew build
```

## Usage

### api-app

```
java -jar ./app-api/build/libs/app-api-X.X.X.jar --spring.config.additional-location=./config/ext-config.yaml --spring.profiles.active={profile-name}
```

[API document](./docs/index.md)

#### txt-epub conversion syntax

```
*BT*book-title    //set 'book-title' to the title of the ebook
*BA*book-author   //set 'book-author' to the author of the ebook
*ST*section-title //set 'section-title' to the title of the section
body-content      //set 'body-content' to a paragraph if there is no syntax
```

### api-batch

```
java -jar ./app-api/build/libs/app-batch-X.X.X.jar --spring.config.additional-location=./config/ext-config.yaml --spring.profiles.active={profile-name} --job.name=deleteExpiredEbookJob time(long)={current-time}
```

> Note: `current-time` is in the format `YYYYMMDDHHmm` and must be numeric.

### External Config

The profiles provided by the application by default are listed below.

-   `prod`: For deployment environments.
-   `dev`: For development environments.
-   `local`: For running without additional environment settings.

Some settings require additional external configuration to protect sensitive information used to access databases and external storage.

Use the `local` profile if you want to run the application in your local environment without any additional configuration. This profile uses H2 Database and [Embedded Redis](https://github.com/ozimov/embedded-redis).

```
ext-config:
  rds:
    secret:
      url: your-database-url
      username: your-database-username
      password: your-database-password
    config:
      time-zone: your-DATABASE-TIMEZONE
      encoding: UTF-8
  redis:
    secret:
      port: your-redis-port
      host: your-redis-host
  aws:
    s3:
      access-key: your-s3-access-key
      secret-key: your-s3-secret-key
      service-endpoint: your-s3-service-endpoint
      region: your-s3-region
      bucket: your-s3-bucket
  jwt:
    secret:
      key: It-is-strongly-recommended-that-you-use-a-string-that-is-at-least-512-bits-(64-characters)-long-for-the-secret-key
    issuer: issuer-name
    valid-milliseconds:
      access: access-milliseconds
      refresh: refresh-milliseconds
  ebook:
    content-directory: content-file-storage-directory-path
    ebook-directory: ebook-file-storage-directory-path
```

## Roadmap

-   [ ] New features
    -   [ ] More ebook syntax elements
    -   [ ] Add an image
    -   [ ] Add API key authentication
    -   [ ] Add the ability to limit the frequency of API calls
-   [ ] Structure improvements
    -   [x] Apply modularization
    -   [ ] Remove core module
-   [ ] Performance improvements
    -   [ ] Optimize file I/O
-   [ ] CI
    -   [x] Create automated build and test workflows
    -   [x] Create an automated release workflow
-   [ ] CD
    -   [ ] Deploying with Docker Compose
    -   [ ] Create an automated deployment workflow
