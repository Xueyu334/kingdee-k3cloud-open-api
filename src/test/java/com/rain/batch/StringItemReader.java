package com.rain.batch;

import lombok.Setter;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;

import java.util.Optional;

@Setter
public class StringItemReader implements ItemReader<String> {

    private static final int MAX_COUNT = 5;
    private int count = 0;
    private JobParameters jobParameters;

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        this.jobParameters = stepExecution.getJobParameters();
    }

    @Override
    public String read() {
        if (count < MAX_COUNT) {
            count++;
            String parameter = jobParameters.getString("parameter");
            String parm = Optional
                    .ofNullable(parameter).filter(StringUtils::isNotBlank)
                    .orElse("default:");
            return parm + count;
        } else {
            // 返回 null 表示读取完毕
            return null;
        }
    }
}
