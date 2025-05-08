package com.example.demo.elevator;

import com.example.demo.utils.Constants;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RunningSystem {

    private String runningStatus = Constants.RUNNING_STATUS_STOPPED;
    private int currentFloor = 0;
    private int nextStation = 0; // 电梯下一站
    private PositionManager pm;
    private volatile boolean renderDone = false; // 前端渲染是否完成

    public RunningSystem() {
    }


    public void renderDone() {
        this.renderDone = true; // 前端渲染完成
    }

    public void setPositionManager(PositionManager pm) {
        this.pm = pm;
    }

    /**
     * 核心运行方法。负责拉取{@link PositionManager}中的请求，并控制前端电梯的渲染。
     */
    public void doRun() {
        new Thread(() -> {
            while (true) {
                Integer next = pm.getNext();
                if (Objects.equals(next, Constants.NULL)) {
                    continue;
                }
                nextStation = next;
                runningStatus = Constants.RUNNING_STATUS_RUNNING;

                // 控制前端运行
                // todo: 引入websocket, 通知前端电梯移动到下一站
                // eventBus.emit(Constants.ELEVATOR_MOVING_TO_NEXT, next);

                // 等待前端渲染完成
                while (!renderDone) {
                    sleep(100);
                }

                // TODO: 是否需要考虑停靠时用户进入电梯的动画？
                // 为了简化设计，固定暂停2秒
                sleep(2000);

                // 更新当前位置
                currentFloor = next;
                renderDone = false; // 重置渲染状态
                runningStatus = Constants.RUNNING_STATUS_STOPPED;
            }
        }).start();
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }


    /**
     * 电梯运行获取下一站. 如果电梯此时处于运行状态, 则返回下一站.
     * 这样可以避免再运行过程中, 出现下一站被改变的情况, 从而减少
     * 系统设计的复杂度.
     */
    int getCurrentFloor() {
        if (Objects.equals(this.runningStatus, Constants.RUNNING_STATUS_RUNNING)) {
            return this.nextStation;
        }
        return this.currentFloor;
    }
}