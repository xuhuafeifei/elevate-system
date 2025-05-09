package com.example.demo.elevator;

import com.example.demo.controller.MyWebSocketHandler;
import com.example.demo.entity.Response;
import com.example.demo.utils.Constants;
import com.example.demo.utils.RandomUtils;

import java.util.Map;

public class FloorClient {

    private final int floor;
    private final int totalFloors;
    private Connection connection;

    public FloorClient(int floor, int totalFloors) {
        this.floor = floor;
        this.totalFloors = totalFloors;
    }

    public void doConnect(Elevator elevator) {
        this.connection = elevator.connect();
    }

    public void doSend() {
        // 产生随机请求两个
        for (int i = 0; i < Constants.RANDOM_REQUEST_COUNT; i++) {
            int destFloor = RandomUtils.nextIntExclude(1, totalFloors, this.floor);
            // 通知前端渲染
            Map<String, Integer> data = Map.of(
                    "floor", this.floor,
                    "destFloor", destFloor
            );
            MyWebSocketHandler.broadcast(Response.toJson(data, Constants.FLOOR_REQUEST_GENERATE));
            this.connection.send(new FloorRequest(destFloor));
        }
    }
}