package com.rain.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Slf4j
@Configuration
public class BatchConfig {

    @Bean
    public StringItemReader stringItemReader() {
        return new StringItemReader();
    }

    @Bean
    public StringItemWriter stringItemWriter() {
        return new StringItemWriter();
    }

    @Bean
    public StringItemProcessor stringItemProcessor() {
        return new StringItemProcessor();
    }

    @Bean
    public StringJobExecutionListener stringJobExecutionListener() {
        return new StringJobExecutionListener();
    }


    @Bean
    public Job importStringJob(JobRepository jobRepository,
                               @Qualifier(value = "stringStep") Step step,
                               StringJobExecutionListener stringJobExecutionListener) {
        return new JobBuilder("importStringJob", jobRepository)
                .listener(stringJobExecutionListener)
                .start(step)
                .build();
    }


    @Bean
    public Step stringStep(JobRepository jobRepository,
                           DataSourceTransactionManager transactionManager,
                           StringItemReader reader,
                           StringItemProcessor processor,
                           StringItemWriter writer) {
        return new StepBuilder("step1", jobRepository)
                .<String, String>chunk(3, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}
