package com.example.demo.elevator;

import com.example.demo.entity.Response;
import com.example.demo.utils.Constants;
import com.example.demo.utils.GsonUtils;
import com.example.demo.utils.RandomUtils;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * 电梯和楼层的组合器， 也就是大楼.
 * 该类封装了电梯和楼层的关系， 并提供了一些方法来操作电梯和楼层
 *
 * @author feigebuge
 * @email 2508020102@qq.com
 */
@Component
@Data
public class Building {

    private final   Elevator ele;
    private final   List<FloorClient> clients;
    private boolean start;

    public Building() {
        this.ele     = new Elevator();
        this.start   = false;
        this.clients = Arrays.asList(
                              new FloorClient(1, Constants.TOTAL_FLOORS),
                              new FloorClient(2, Constants.TOTAL_FLOORS),
                              new FloorClient(3, Constants.TOTAL_FLOORS)
                       );
    }

    /**
     * 接受前端请求
     * @param msg
     */
    public void receiveRequest(String msg) {
        Response response = GsonUtils.fromJson(msg, Response.class);

        if (response.getEvent().equals(Constants.ELEVATOR_RENDER_DONE)) {
            ele.getRs().renderDone();
        } else if (response.getEvent().equals(Constants.START)) {
            this.start = true;
            ele.getRs().doRun();
        }
    }

    /**
     * 将请求池中的请求发送给电梯
     */
    public void flushRequestPool() {
        // system启动后才可以发送请求
        if (this.start) {
            ele.getRp().send();
        }
    }

    /**
     * 模拟楼层产生请求
     */
    public void mockFloorRequest() {
        // system启动后才可以发送请求
        if (this.start) {
            // 随机挑选一楼产生请求
            List<Integer> hash = new ArrayList<>();
            for (int i = 0; i < Constants.RANDOM_REQUEST_FLOOR_COUNT; i++) {
                int destFloor = RandomUtils.nextIntExclude(1, Constants.TOTAL_FLOORS, hash);
                hash.add(destFloor);
                this.clients.get(destFloor - 1).doSend();
            }
        }
    }
}
