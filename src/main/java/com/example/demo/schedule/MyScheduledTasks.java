package com.example.demo.schedule;

import com.example.demo.elevator.Building;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 调度器, 执行定时调度任务
 */
@Component
public class MyScheduledTasks {

    @Autowired
    private Building building;

    // 每隔 10 秒执行一次
    @Scheduled(fixedRate = 10000)
    public void runEvery10Seconds() {
        // 调度执行mock请求
        building.mockFloorRequest();
    }

    @Scheduled(fixedRate = 5000)
    public void runEvery5Seconds() {
        // 调度执行flush请求
        building.flushRequestPool();
    }
}