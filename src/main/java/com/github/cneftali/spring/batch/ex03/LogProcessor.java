package com.github.cneftali.spring.batch.ex03;

import lombok.extern.log4j.Log4j;

import org.springframework.batch.item.ItemProcessor;

import com.github.cneftali.spring.batch.bean.Sample;

@Log4j
public class LogProcessor implements ItemProcessor<Sample, Sample> {
	
	public Sample process(Sample item) throws Exception {
		log.info("process object " + item);
		return item;
	}
}
