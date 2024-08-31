package com.rain.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

@Slf4j
public class StringJobExecutionListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("job before==>{}", jobExecution.getJobInstance().getJobName());

    }


    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info("job after==>{}", jobExecution.getJobInstance().getJobName());
    }
}
