package com.kw.kwdn.config;

import com.kw.kwdn.domain.firebase.service.FirebaseService;
import com.kw.kwdn.domain.firebase.service.enums.TopicType;
import com.kw.kwdn.domain.notification.tasklet.CommonAndRegularNoticeTasklet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final JobLauncher jobLauncher;
    private final FirebaseService firebaseService;
    private final CommonAndRegularNoticeTasklet commonAndRegularNoticeTasklet;

    @Scheduled(cron = "0 30 0 * * *")
    public void curfew() {
        JobParameters jobParameters = new JobParameters(
                Map.of("date", new JobParameter(LocalDateTime.now().toString())));

        try {
            jobLauncher.run(curfewJob(), jobParameters);
        } catch (JobExecutionAlreadyRunningException e) {
            log.warn("BatchConfig curfew : 기존에 이미 실행되고 있는 job이 있습니다.");
        } catch (JobRestartException e) {
            log.warn("BatchConfig curfew : job이 재실행될 때 예외가 발생하였습니다.");
        } catch (JobInstanceAlreadyCompleteException e) {
            log.warn("BatchConfig curfew : job이 이미 실행된 적이 있어 예외가 발생하였습니다.");
        } catch (JobParametersInvalidException e) {
            log.warn("BatchConfig curfew : job의 파라미터가 유효하지 않습니다.");
        }
    }

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    public void notice() {
        JobParameters jobParameters = new JobParameters(
                Map.of("date", new JobParameter(LocalDateTime.now().toString())));
        try {
            jobLauncher.run(noticeJob(), jobParameters);
        } catch (JobExecutionAlreadyRunningException e) {
            log.warn("BatchConfig notice : 기존에 이미 실행되고 있는 job이 있습니다.");
        } catch (JobRestartException e) {
            log.warn("BatchConfig notice : job이 재실행될 때 예외가 발생하였습니다.");
        } catch (JobInstanceAlreadyCompleteException e) {
            log.warn("BatchConfig notice : job이 이미 실행된 적이 있어 예외가 발생하였습니다.");
        } catch (JobParametersInvalidException e) {
            log.warn("BatchConfig notice : job의 파라미터가 유효하지 않습니다.");
        }
    }

    @Bean
    public Job curfewJob() {
        return jobBuilderFactory.get("curfew job")
                .start(curfewStep())
                .build();
    }

    @Bean
    public Step curfewStep() {
        return stepBuilderFactory.get("curfew step")
                .tasklet((contribution, chunkContext) -> {
                    String title = "통금시간까지 얼마남지 않았습니다.";
                    String body = "(01:00 A.M. 까지 30분 남았습니다.)";
                    firebaseService.sendAlarm(title, body, TopicType.CURFEW);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step noticeStep() {
        return stepBuilderFactory.get("regular | common notice step")
                .tasklet(commonAndRegularNoticeTasklet)
                .build();
    }

    @Bean
    public Job noticeJob() {
        return jobBuilderFactory.get("regular | common notice job")
                .start(noticeStep())
                .build();
    }
}