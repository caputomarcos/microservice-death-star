package com.deathstar.planetcommand;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableEurekaClient
@SpringBootApplication
public class Application {

	private static ApplicationContext context;

	public static void main(String... args) {
		setContext(SpringApplication.run(Application.class, args));
	}

	public static ApplicationContext getContext() {
		return context;
	}

	public static void setContext(ApplicationContext context) {
		Application.context = context;
	}
}

@RestController
class ServiceInstanceRestController {

	@Autowired
	private DiscoveryClient discoveryClient;

	@Value("${spring.application.name}")
	private String appName;

	@RequestMapping("/instances")
	public List<ServiceInstance> serviceInstancesByApplicationName() {
		return this.discoveryClient.getInstances(appName);
	}
}

@RefreshScope
@RestController
class MessageRestController {

	@Value("${message}")
	private String message;

	@RequestMapping("/message")
	String getMessage() {
		return this.message;
	}
}
