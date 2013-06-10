package com.github.cneftali.spring.batch.ex01;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class HelloTaskletConf implements BatchConfigurer {

	@Autowired
    private JobBuilderFactory jobs;
	
	@Autowired
	private StepBuilderFactory stepBuilders;
	
	@Bean
	public Job job() {
		return jobs.get("HelloJob").start(step()).build();
	}
	
	@Bean
	public Step step() {
		return stepBuilders.get("step").tasklet(helloTasklet()).build();
	}
	
	@Bean
	public Tasklet helloTasklet() {
		return new HelloTasklet();
	}

	@Bean
	public JobRepository getJobRepository() throws Exception {
		final MapJobRepositoryFactoryBean factory = new MapJobRepositoryFactoryBean(getTransactionManager());
		factory.afterPropertiesSet();
		return  (JobRepository) factory.getObject();
	}

	@Bean
	public PlatformTransactionManager getTransactionManager() throws Exception {
		return new ResourcelessTransactionManager();
	}

	@Bean
	public JobLauncher getJobLauncher() throws Exception {
		SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
		jobLauncher.setJobRepository(getJobRepository());
		jobLauncher.afterPropertiesSet();
		return jobLauncher;
	}
}
