package com.github.cneftali.spring.batch.ex03.conf;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.io.ClassPathResource;

import com.github.cneftali.spring.batch.bean.Sample;
import com.github.cneftali.spring.batch.ex03.LogProcessor;

@Configuration
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@Import(BatchInfrastructureConfiguration.class)
public class BatchImporterConfiguration {

	@Autowired
	private DataSource dataSource;
	
	@Bean(name = "SampleJob")
	public Job sampleJob(final JobBuilderFactory jobs,  @Qualifier("step1") final Step s1) {
		return jobs.get("sampleJob").start(s1).build();
	}

	@Bean(name = "step1")
	public Step step1(StepBuilderFactory stepBuilders) {
		return stepBuilders.get("step").<Sample, Sample>chunk(1).reader(reader()).processor(process()).writer(writer()).build();
	}
	
	@Bean(name = "reader")
	public FlatFileItemReader<Sample> reader() {
		FlatFileItemReader<Sample> itemReader = new FlatFileItemReader<Sample>();
		itemReader.setLineMapper(lineMapper());
		itemReader.setResource(new ClassPathResource("import.csv"));
		return itemReader;
	}
	
	@Bean
	public LineMapper<Sample> lineMapper() {
		final DefaultLineMapper<Sample> lineMapper = new DefaultLineMapper<Sample>();
		final DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setNames(new String[]{ "id", "name" });
		final BeanWrapperFieldSetMapper<Sample> fieldSetMapper = new BeanWrapperFieldSetMapper<Sample>();
		fieldSetMapper.setTargetType(Sample.class);
		lineMapper.setLineTokenizer(lineTokenizer);
		lineMapper.setFieldSetMapper(fieldSetMapper);
		return lineMapper;
	}
	
	@Bean(name = "writer")
	public ItemWriter<Sample> writer() {
		final JdbcBatchItemWriter<Sample> itemWriter = new JdbcBatchItemWriter<Sample>();
		itemWriter.setSql("INSERT INTO SAMPLE (SAMPLE_ID, SAMPLE_NAME) VALUES (:id,:name)");
		itemWriter.setDataSource(dataSource);
		itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Sample>());
		return itemWriter;
	}
	
	@Bean(name = "process")
	public ItemProcessor<Sample, Sample> process() {
		return new LogProcessor();
	}
}
