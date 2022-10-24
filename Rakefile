namespace :mfa do

	task default: %w[deploy]

	desc "Authenticating with existing credentials"
	task :login do
		puts `docker login 2>&1`
	end


	desc "Cleaning Evironment Task"
	task :cleaning_environment_task do 
		puts "Cleaning Environment"
		puts `docker image prune -af`
		puts `docker volume prune -f 2>&1`
	end

	desc "Status Containers"
	task :status do 
		puts "Show Containers Status"
		puts `docker-compose ps 2>&1`
	end

	desc "Deploys Platform Containers and launches all services and daemons needed to properly work"
	task :deploy => [
		:cleaning_environment_task,
		"platform:start",
		"redis:start",
		:status] do
	    puts "Deploying services..."
	end

	desc "Undeploy Platform Containers"
	task :undeploy => [ :status ] do 
		puts "Undeploy Services"
		puts `docker-compose down -v 2>&1`
	end


	desc "Check Docker and Docker Compose Task"
	task :check_docker_task do
		puts "Check Docker and Docker Compose ..."
		if which('docker') && which('docker-compose')
			show_docker_version
			show_docker_compose_version
		else
			raise "Please check that Docker and Docker Compose are visible and accessible in the PATH"
		end
	end


	## Deploy Platform
	namespace :platform do

		desc "Check Platform Deployment File"
		task :check_deployment_file do
			puts "Check Platform Deployment File ..."
			raise "Deployment file not found, please check availability" unless File.file?("./platform/docker-compose.yml")
			puts "Platform Deployment File OK!"
		end

		desc "Start Platform Graalvm Containers"
		task :start => [ :check_docker_task, :login, :check_deployment_file ] do 
			puts "Start Platform Containers"
			puts `docker-compose -f ./platform/docker-compose.yml up -d`
		end 

		desc "Start Platform Hotspot Containers"
		task :start_hotspot => [ :check_docker_task, :login, :check_deployment_file ] do 
			puts "Start Platform Containers"
			puts `docker-compose -f ./platform/docker-compose-hotspot.yml up -d`
		end 


		desc "Stop Platform Graalvm Containers"
		task :stop => [ :check_docker_task, :login, :check_deployment_file  ] do
			puts "Stop Platform Containers"
			puts `docker-compose -f ./platform/docker-compose.yml stop 2>&1`
		end

		desc "Stop Platform Hotspot Containers"
		task :stop_hotspot => [ :check_docker_task, :login, :check_deployment_file  ] do
			puts "Stop Platform Containers"
			puts `docker-compose -f ./platform/docker-compose-hotspot.yml stop 2>&1`
		end


		desc "Build Docker Image based on Graavlm"
		task :build_native_image => [:check_docker_task, :login] do 
			dockerImageName = "ssanchez11/mfa_service:0.0.1"
			microserviceFolder = "./platform/microservice"
			puts "Build Docker Image based on Graavlm at #{microserviceFolder}"  
			puts `docker build -t #{dockerImageName} #{microserviceFolder}`
			puts `docker images`
			puts "Docker image #{dockerImageName} has been created! trying to upload it!"  
			puts `docker push #{dockerImageName}`
		end

		desc "Build Docker Image based on Hotspot"
		task :build_hotspot_image => [:check_docker_task, :login] do 
			dockerImageName = "ssanchez11/mfa_service_hotspot:0.0.1"
			microserviceFolder = "./platform/microservice"
			dockerFile = "#{microserviceFolder}/Dockerfile_hotspot"
			puts "Build Docker Image based on Hotspot at #{microserviceFolder}"  
			puts `docker build -t #{dockerImageName} -f #{dockerFile} #{microserviceFolder}`
			puts `docker images`
			puts "Docker image #{dockerImageName} has been created! trying to upload it!"  
			puts `docker push #{dockerImageName}`
		end


	end
	
	# Redis Cluster
	namespace :redis do

		desc "Check Redis Cluster Deployment File"
		task :check_deployment_file do
			puts "Check Redis Cluster Deployment File ..."
			raise "Deployment file not found, please check availability" unless File.file?("./redis_cluster/docker-compose.yml")
			puts "Platform Deployment File OK!"
		end

		desc "Start and configure Cluster Containers"
		task :start => [ :check_docker_task, :login, :check_deployment_file ] do 
			puts "Start Cluster Containers"
			puts `docker-compose -f ./redis_cluster/docker-compose.yml up -d`
			puts `docker run -it --rm --network=redis_cluster_redis_cluster_network redislabs/rejson:latest redis-cli --cluster create 192.168.0.30:6379 192.168.0.35:6380 192.168.0.40:6381 192.168.0.45:6382 192.168.0.50:6383 192.168.0.55:6384 192.168.0.60:6385 192.168.0.65:6386 --cluster-replicas 1 --cluster-yes`
		end 

		desc "Stop Cluster Containers"
		task :stop => [ :check_docker_task, :login, :check_deployment_file  ] do
			puts "Stop Cluster Containers"
			puts `docker-compose -f ./redis_cluster/docker-compose.yml stop 2>&1`
		end

	end	

	## Utils Functions

	def show_docker_version
	  puts `docker version 2>&1`
	end

	def show_docker_compose_version
	  puts `docker-compose version 2>&1`
	end

	# Cross-platform way of finding an executable in the $PATH.
	# which('ruby') #=> /usr/bin/ruby
	def which(cmd)
	  exts = ENV['PATHEXT'] ? ENV['PATHEXT'].split(';') : ['']
	  ENV['PATH'].split(File::PATH_SEPARATOR).each do |path|
	    exts.each { |ext|
	      exe = File.join(path, "#{cmd}#{ext}")
	      return exe if File.executable?(exe) && !File.directory?(exe)
	    }
	  end
	  return nil
	end
	
end 


