
# ThunderOTP - A passwordless authentication architecture based on One-time codes approach.

<img width="auto" align="left" src="./doc/logo.png" />

Passwordless authentication will be the future of online authentication!

Passwordless authentication has been gaining traction, and the main reason is the lack of security that passwords offer today, as passwords are reused and stolen, more and more frequently. The second reason is that passwords have to be increasingly complex, which degrades the user experience. Security and user experience are among the top priorities of any digital company and usually are in direct conflict. Hence the interest in passwordless authentication, with its promises to offer more security and a better user experience at the same time.

</br>

<p align="center">
  <img src="https://img.shields.io/badge/Kotlin-F58025?&style=for-the-badge&logo=kotlin&logoColor=white" />
  <img src="https://img.shields.io/badge/GraalVM-BD081C?&style=for-the-badge&logo=graalvm&logoColor=white" />
  <img src="https://img.shields.io/badge/Twilio-F22F46?style=for-the-badge&logo=Twilio&logoColor=white" />
  <img src="https://img.shields.io/badge/Firebase-039BE5?style=for-the-badge&logo=Firebase&logoColor=white" />
  <img src="https://img.shields.io/badge/redis-%23DD0031.svg?&style=for-the-badge&logo=redis&logoColor=white" />
  <img src="https://img.shields.io/badge/Docker-2CA5E0?style=for-the-badge&logo=docker&logoColor=white" />
</p>

## What is passwordless authentication?

In online authentication, a passwordless authentication system is any process that authenticates the user without using a password, more specifically is the verification of a user's identity by a method that does not require a password. It is important to make a clear distinction between the different methods used to deliver passwordless authentication. Some are more secure and some provide a better user experience. This architecture has been implemented using the one-time passwords (OTPs) solution. They are best known for multi-factor authentication processes, but one-time passwords or one-time codes can also be used as a standalone authentication method.

## How do OTPs work in passwordless authentication?

One-time passwords (or OTPs) are numeric codes linked to a reference. These codes are sent to the user, so only the server and the user can know this code. When the user enters the code in the platform, they are granted with access and hence they are authenticated.

These codes will be sent to the user's phone via SMS, Push Notification or e-mail.

Furthermore, one-time passwords are always linked to a unique reference, so there aren't any chances that the code is overtaken by different uses. OTPs can be limited in time too, which limits the time of validity of the code.

## Architecture Overview

This architecture can be used as a stand-alone authentication service or as part of a more complex MFA solution. Clients will request a one-time code or password in order to verify their identity, they will indicate the delivery service by which they wish to receive the token (Email, SMS, push notification are the options currently available). The system will generate an OTP token applying rules linked to the specified delivery service, the generated token will persist in the redis cluster with the TTL associated with the type of service and will return to the client a unique operation identifier that can be used for subsequent validation operations , cancel or resend.
The system allows up to a total of 3 resubmissions for an operation identifier, if the client provides an incorrect OTP at the time of validation, it will be eliminated and a new OTP will have to be requested. The system carries out various checks to prevent misuse of the service.

<img width="auto" src="./doc/ThunderOTP.drawio.png" />

## Powered by lightweight cloud-native microservices implemented by Ktor Graalvm native images.

Using GraalVM Native Image technology we can compile the services to native code ahead of time in a way in which the resulting binary does not depend on the JVM for the execution. This executable can be placed as a standalone application in a container and started really, really fast.

<p align="center">
  <img width="350" height="auto" src="./doc/screenshots/ktor_logo.png" />
</p>

* Faster startup time: Building Ahead-of-time compiled microservices that start in milliseconds and deliver peak performance with no warmup.
* Low resource usage: Building Ahead-of-time compiled microservices that use only a fraction of the resources required by the JVM which means they cost less to run and improve utilization.
* Small container image: Trying to compact native executables in lightweight container images for more secure, faster, and efficient deployments.
* Minimize vulnerability: Trying to reduce the attack surface area using Native image by removing all unused classes, method, and fields from your application and libraries while making reverse engineering difficult by converting Java bytecode into native machine code.

<p align="center">
  <img width="250" height="auto" src="./doc/screenshots/graalvm_logo.png" />
</p>

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
| thunderotp:check_docker_task | Check Docker and Docker Compose Task |
| thunderotp:cleaning_environment_task | Cleaning Evironment Task |
| thunderotp:deploy | Deploys Platform Containers and launches all services and daemons needed to properly work |
| thunderotp:login | Authenticating with existing credentials |
| thunderotp:platform:build_hotspot_image | Build Docker Image based on Hotspot JVM |
| thunderotp:platform:build_native_image | Build Docker Image based on Graavlm |
| thunderotp:platform:check_deployment_file | Check Platform Deployment File |
| thunderotp:platform:start | Start Platform Graalvm Containers |
| thunderotp:platform:start_hotspot | Start Platform Hotspot JVM Containers |
| thunderotp:platform:stop | Stop Platform Graalvm Containers |
| thunderotp:platform:stop_hotspot | Stop Platform Hotspot JVM Containers |
| thunderotp:redis:check_deployment_file | Check Redis Cluster Deployment File |
| thunderotp:redis:start | Start and configure Cluster Containers |
| thunderotp:redis:stop | Stop Cluster Containers |
| thunderotp:status | Status Containers |
| thunderotp:undeploy | Undeploy Platform Containers |


To start the platform make sure you have Ruby installed, go to the root directory of the project and run the `rake deploy` task, this task will carry out a series of preliminary checks, discard images and volumes that are no longer necessary and also proceed to download all the images and the initialization of the containers.

### Containers Ports

In this table you can view the ports assigned to each service to access to the Web tools or something else you can use to monitoring the flow.

| Container | Port |
| ------ | ------ |
| Redis Insight | localhost:8001 |
| HAProxy Load Balancer | localhost:9090 |
| HAProxy Stats | localhost:8404 |

## Some screenshots

As follow, I include some images that help us to understand the performance of each part of system

HAProxy offers a website to monitor the operation of the load balancer, we can view statistics and metrics related to requests related to microservices.

<img width="auto" src="./doc/screenshots/hproxy_stats.PNG" />

The Redis Insight tool allows us to visualize the distribution of our Redis cluster configuration, more specifically, we can visualize which are the master and slave nodes, the total memory consumed by each node, the number of keys that each node hosts...

<img width="auto" src="./doc/screenshots/redis_cluster.PNG" />

With the Redis Insight Browser tool we can manage and view the hosted keys and view their content.

<img width="auto" src="./doc/screenshots/redis_cluster_1.PNG" />

OTP token information is stored as JSON templates using the Redis ReJSON module. It used the Jedis library to store and retrieve the models using GSON as a data serialization library, apart from that, it took advantage of the intrinsic mechanism of TTLs that can be applied to the keys, as a way to configure the validity time of the generated codes, as you can see below.

<img width="auto" src="./doc/screenshots/redis_cluster_2.PNG" />

The Redis cluster configuration is made up of 8 Redis nodes, 4 nodes act as master nodes and the other 4 are slave nodes. Apart from that, we have the official Redis Insight container that allows us to operate with the configuration.

<img width="auto" src="./doc/screenshots/redis_cluster_containers.PNG" />

### Microservices deployment based on OpenJDK 11 HotSpot provide by AdoptOpenJDK 

You can execute the task `thunderotp:platform:start_hotspot` in order to deploy a microservice platform based on OpenJDK Hotspot images if you want.

HotSpot is one implementation of JIT technology that starts by running interpreted, and watches the actual performance of the app. Parts of the app are then selected to be fully-compiled as native code and cached, for much faster execution. HotSpot was developed at Sun as a commercial product. After acquiring Sun, Oracle further evolved HotSpot by combining important parts of their competing product, JRockit. HotSpot is now open-sourced through the OpenJDK project, available free-of-charge.

<img width="auto" src="./doc/screenshots/platform_microservices_hotspot_containers.PNG" />

Microservices based on this JDK distribution take more memory than the GraalVM alternatives.

<img width="auto" src="./doc/screenshots/microservice_hotspot_stats.PNG" />

You can execute the task `thunderotp:platform:start` in order to deploy a microservice platform based on GraalVM native images.

Using GraalVM Native Image technology we can compile the services to native code ahead of time in a way in which the resulting binary does not depend on the JVM for the execution. This executable can be placed as a standalone application in a container and started really, really fast.

<img width="auto" src="./doc/screenshots/platform_microservices_native_containers.PNG" />

In this way we will have microservices with a Faster startup time and a Low resource usage as you can see in the picture below.

<img width="auto" src="./doc/screenshots/microservice_native_stats.PNG" />

<img width="auto" src="./doc/screenshots/images_sizes.PNG" />

The platform uses several senders to deliver the OTP codes below you can see examples of deliveries using the SendGrid and Twilio SMS service.

<p align="center">
  <img width="300" src="./doc/screenshots/sender_sms.jpg" />
  <img width="700" src="./doc/screenshots/sender_mail.PNG" />
</p>

## Visitors Count

<img width="auto" src="https://profile-counter.glitch.me/passwordless_authentication_architecture/count.svg" />
