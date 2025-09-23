# Getting Started

http://localhost:8080/swagger-ui/index.html

### Reference Documentation

For further reference, please consider the following sections:

- [Official Gradle documentation](https://docs.gradle.org)
- [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/3.4.4/gradle-plugin)
- [Create an OCI image](https://docs.spring.io/spring-boot/3.4.4/gradle-plugin/packaging-oci-image.html)
- [Spring Data JPA](https://docs.spring.io/spring-boot/3.4.4/reference/data/sql.html#data.sql.jpa-and-spring-data)
- [Rest Repositories](https://docs.spring.io/spring-boot/3.4.4/how-to/data-access.html#howto.data-access.exposing-spring-data-repositories-as-rest)
- [Spring Boot DevTools](https://docs.spring.io/spring-boot/3.4.4/reference/using/devtools.html)
- [Spring Web](https://docs.spring.io/spring-boot/3.4.4/reference/web/servlet.html)

### Guides

The following guides illustrate how to use some features concretely:

- [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
- [Accessing JPA Data with REST](https://spring.io/guides/gs/accessing-data-rest/)
- [Accessing Neo4j Data with REST](https://spring.io/guides/gs/accessing-neo4j-data-rest/)
- [Accessing MongoDB Data with REST](https://spring.io/guides/gs/accessing-mongodb-data-rest/)
- [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
- [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
- [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)

### Additional Links

These additional references should also help you:

- [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)

For spinning up docker container follow the following steps:

1. Start the docker desktop app
2. Go to the file explorer where the project resides and open command prompt from there and use the following command
   docker-compose up --build -d
3. The containers should be started and you should be able to view it in the docker desktop

For spinning down docker container follow the following steps:

1. Go to the file explorer where the project resides and open command prompt from there and use the following command
   docker-compose down

Common Causes and Fixes
✅ 1. Gmail + App Passwords (if 2FA is enabled)

If your Google account has 2-Step Verification (2FA) enabled, your normal password won’t work for SMTP.
You must generate an App Password:

🔧 How to generate an App Password (Gmail):

Go to https://myaccount.google.com/security

Under "Signing in to Google", make sure 2-Step Verification is ON.

Then click App passwords.

Select:

App: Mail

Device: Other (enter something like “Spring Boot”)

Google will give you a 16-character password — use this instead of your Gmail password in application.yaml.

✅ 2. Allow Less Secure Apps (only if 2FA is OFF)

If you do not have 2FA enabled:

Visit: https://www.google.com/settings/security/lesssecureapps

Enable Access for less secure apps.

⚠️ Google is phasing out this option. App Passwords (above) are preferred and more secure.

✅ 3. Check Gmail Activity and Unlock Captcha

Sometimes, Google blocks sign-in attempts from new locations or programs.

Go to: https://accounts.google.com/DisplayUnlockCaptcha

Click Continue to allow access.

Then immediately try sending the email again from your Spring Boot app.

Confirm Access to App Passwords

Try visiting this direct link:

🔗 https://myaccount.google.com/apppasswords

If it redirects you to the home page or says “The setting is not available for your account”, then your account is restricted and you won’t be able to use Gmail SMTP directly.

# Keycloak Setup Flow (with JWT Roles)

1. Create Realm
2. Create Client (app/microservice)
3. Create Roles (Realm Roles or Client Roles)
4. Create User
5. Assign Roles to User
6. Configure Mappers to Include Roles in Token (if needed)
7. Get JWT and Verify Roles

https://www.keycloak.org/docs-api/latest/rest-api/openapi.yaml
https://www.keycloak.org/docs-api/latest/rest-api/index.html
https://editor.swagger.io/?_gl=1*dxmzzv*_gcl_au*OTk5MTk3OTY3LjE3NTg1NDM1MDc.
https://documenter.getpostman.com/view/7294517/SzmfZHnd

https://blogs.learningdevops.com/integrating-keycloak-for-role-management-across-systems-3597f1f3e7c2

https://medium.com/@pavithbuddhima/how-to-add-custom-claims-to-jwt-tokens-from-an-external-source-in-keycloak-52bd1ff596d3
