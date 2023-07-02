package com.example.project;

import com.example.project.Model.JenkinsJobBuild;
import com.example.project.Service.JenkinsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.time.LocalDateTime;
import java.util.List;


@SpringBootApplication
public class ProjectApplication {

    @Autowired
    private JenkinsService jenkinsService;

    public static void main(String[] args) throws JsonProcessingException {
        ConfigurableApplicationContext context = SpringApplication.run(ProjectApplication.class, args);


        ProjectApplication application = context.getBean(ProjectApplication.class);
        application.run();
    }

    public void run() throws JsonProcessingException {

        JenkinsJobBuild response = jenkinsService.getLatestJobBuild();
        System.out.println("\n");
        System.out.println("Job name: " + response.getJobName());
        System.out.println("Build number: " + response.getBuildNumber());
        System.out.println("dateTime: " + response.getdateTime());
        System.out.println("jobDuration: " + response.getjobDuration() + " milliseconds");
        System.out.println("jobStatus: " + response.getJobStatus());


        // Define the start and end time for the time range
        LocalDateTime startTime = LocalDateTime.now().minusHours(1); // Example: 1 hour ago
        LocalDateTime endTime = LocalDateTime.now(); // Example: current time

        // Get job builds within the specified time range
        List<JenkinsJobBuild> jobBuildsInRange = jenkinsService.getJobBuildsByTimeRange(startTime, endTime);
        System.out.println("-----------------------------------------");
        System.out.println("Job Builds last hour:");
        for (JenkinsJobBuild jobBuild : jobBuildsInRange) {
            System.out.println("Job name: " + jobBuild.getJobName());
            System.out.println("Build number: " + jobBuild.getBuildNumber());
            System.out.println("DateTime: " + jobBuild.getdateTime());
            System.out.println("Job duration: " + jobBuild.getjobDuration() + " milliseconds");
            System.out.println("-----------------------------------------");

        }
    }
}
