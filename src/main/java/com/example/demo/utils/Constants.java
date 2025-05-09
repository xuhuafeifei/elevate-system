package com.example.demo.utils;

public interface Constants {
    String RED = "red";
    String BLUE = "blue";
    String DARK = "dark";
    Integer NULL = -10000;

    /*------ Running Status ------*/
    String RUNNING_STATUS_RUNNING = "RUNNING_STATUS_RUNNING";
    String RUNNING_STATUS_STOPPED = "RUNNING_STATUS_STOPPED";

    /*------ back to front ------*/
    String ELEVATOR_MOVING_TO_NEXT = "ELEVATOR_MOVING_TO_NEXT";

    /*------ front to back ------*/
    String ELEVATOR_RENDER_DONE = "ELEVATOR_RENDER_DONE";
    // 系统是否启动
    String START = "START";
    // 后端模拟产生FloorRequest
    String FLOOR_REQUEST_GENERATE = "FLOOR_REQUEST_GENERATE";


    // 每个floor每次调度时最多产生2个随机请求
    int RANDOM_REQUEST_COUNT = 2;
    // 总共有3个楼层
    int TOTAL_FLOORS = 3;
    // 随机挑选1楼产生request请求
    int RANDOM_REQUEST_FLOOR_COUNT = 1;
}
