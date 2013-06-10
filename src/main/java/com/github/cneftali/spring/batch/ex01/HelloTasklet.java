package com.github.cneftali.spring.batch.ex01;

import static org.springframework.batch.repeat.RepeatStatus.FINISHED;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class HelloTasklet implements Tasklet {

	public RepeatStatus execute(final StepContribution arg0, final ChunkContext arg1) throws Exception {
		System.out.println("Beer or not Beer. What is the Question");
		return FINISHED;
	}

}
