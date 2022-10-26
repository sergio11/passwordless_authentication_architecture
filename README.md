# mfa_test

Passwordless authentication will be the future of online authentication

Passwordless authentication has been gaining traction, and the main reason is the lack of security that passwords offer today, as passwords are reused and stolen, more and more frequently. The second reason is that passwords have to be increasingly complex, which degrades the user experience. Security and user experience are among the top priorities of any digital company and usually are in direct conflict. Hence the interest in passwordless authentication, with its promises to offer more security and a better user experience at the same time.

## What is passwordless authentication?

In online authentication, a passwordless authentication system is any process that authenticates the user without using a password

## Used technology.

* Redis Cluster Architecture ( rejson module enabled).
* HAProxy Load Balancer.
* Ktor Framework.
* Netty Server.
* Graalvm high-performance JDK distribution.
* Twilio Java Helper Library.
* Sendgrid Java Helper Library.
* Firebase Cloud Messaging.
* Jedis ( A redis Java client designed for performance and ease of use ).
* Hoplite ( A boilerplate-free Kotlin config library for loading configuration files as data classes ).

## Running Applications as Docker containers.

### Rake Tasks

The available tasks are detailed below (rake --task)

| Task | Description |
| ------ | ------ |
| mfa:check_docker_task | Check Docker and Docker Compose Task |
| mfa:cleaning_environment_task | Cleaning Evironment Task |
| mfa:deploy | Deploys Platform Containers and launches all services and daemons needed to properly work |
| mfa:login | Authenticating with existing credentials |
| mfa:platform:build_hotspot_image | Build Docker Image based on Hotspot JVM |
| mfa:platform:build_native_image | Build Docker Image based on Graavlm |
| mfa:platform:check_deployment_file | Check Platform Deployment File |
| mfa:platform:start | Start Platform Graalvm Containers |
| mfa:platform:start_hotspot | Start Platform Hotspot JVM Containers |
| mfa:platform:stop | Stop Platform Graalvm Containers |
| mfa:platform:stop_hotspot | Stop Platform Hotspot JVM Containers |
| mfa:redis:check_deployment_file | Check Redis Cluster Deployment File |
| mfa:redis:start | Start and configure Cluster Containers |
| mfa:redis:stop | Stop Cluster Containers |
| mfa:status | Status Containers |
| mfa:undeploy | Undeploy Platform Containers |


To start the platform make sure you have Ruby installed, go to the root directory of the project and run the `rake deploy` task, this task will carry out a series of preliminary checks, discard images and volumes that are no longer necessary and also proceed to download all the images and the initialization of the containers.

### Containers Ports

In this table you can view the ports assigned to each service to access to the Web tools or something else you can use to monitoring the flow.

| Container | Port |
| ------ | ------ |
| Redis Insight | localhost:8001 |
