# mfa_test


## Used technology.


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
