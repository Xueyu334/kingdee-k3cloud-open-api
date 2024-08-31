package com.rain.batch;

import com.rain.K3CloudOpenApiApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = K3CloudOpenApiApplication.class)
@Slf4j
public class BatchJobRunnerTest {

    @Autowired
    private StringItemReader stringItemReader;

    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private Job job;


    @Test
    void test() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        stringItemReader.setCount(0);
        //作用： 唯一标识作业实例
        //控制作业行为
        //实现作业的重启和恢复
        //调度和作业分隔
        JobParameters jobParameters = new JobParametersBuilder()
                // 使用时间戳来生成唯一参数
                .addLong("time", System.currentTimeMillis())
                .addString("parameter", "a1s")
                .toJobParameters();
        JobExecution execution = jobLauncher.run(job, jobParameters);
        System.out.println("Job Status: " + execution.getStatus());
    }
}
