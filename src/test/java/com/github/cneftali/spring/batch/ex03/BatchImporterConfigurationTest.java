package com.github.cneftali.spring.batch.ex03;

import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.github.cneftali.spring.batch.ex03.conf.BatchImporterConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { BatchImporterConfiguration.class }, loader = AnnotationConfigContextLoader.class)
public class BatchImporterConfigurationTest {

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job job;
	
	@Test
	public void testJob() throws Exception {
		JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
		jobParametersBuilder.addDate("startTime", new Date());
		JobExecution jobExecution = jobLauncher.run(job, jobParametersBuilder.toJobParameters());
		Assert.assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
	}
}
