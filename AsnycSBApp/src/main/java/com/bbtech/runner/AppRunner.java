package com.bbtech.runner;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.bbtech.model.User;
import com.bbtech.service.GitHubLookupService;

@Component
public class AppRunner implements CommandLineRunner {
	
	
	private static final Logger log = LoggerFactory.getLogger(AppRunner.class);

	private final GitHubLookupService gitHubLookupService;
	
	public AppRunner(GitHubLookupService gitHubLookupService) {
		this.gitHubLookupService=gitHubLookupService;
	}

	@Override
	public void run(String... args) throws Exception {
		//start the clock
		long start=System.currentTimeMillis();
		
		//kick off multiple, asynchronous look up
		CompletableFuture<User> page1=gitHubLookupService.findUser("PivotalSoftware");
		CompletableFuture<User> page2=gitHubLookupService.findUser("CloudFoundry");
		CompletableFuture<User> page3=gitHubLookupService.findUser("Spring-Projects");
		CompletableFuture<User> page4=gitHubLookupService.findUser("bindusagar86");
		
		//wait until they are done
		CompletableFuture.allOf(page1,page2,page3,page4).join();
		
		//print results
		log.info("Elapsed time: "+(System.currentTimeMillis()-start)/1000L + "s");
		log.info("--> "+page1.get());
		log.info("--> "+page2.get());
		log.info("--> "+page3.get());
		log.info("--> "+page4.get());
	}

}
