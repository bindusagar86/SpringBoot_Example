package com.bbtech.service;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bbtech.model.User;

@Service
public class GitHubLookupService {

	
	private static final Logger log = LoggerFactory.getLogger(GitHubLookupService.class);

	private final RestTemplate restTemplate;
	
	public GitHubLookupService(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate=restTemplateBuilder.build();
	}
	
	@Async
	public CompletableFuture<User> findUser(String user) throws InterruptedException{
		log.info("Lookup user: "+user);
		String url = String.format("https://api.github.com/users/%s", user);
		User results=restTemplate.getForObject(url, User.class);
		Thread.sleep(1000L);
		
		return CompletableFuture.completedFuture(results);
	}
}
