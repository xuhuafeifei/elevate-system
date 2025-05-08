package com.example.demo.elevator;

import com.example.demo.utils.Constants;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PositionManager {

    static class RedBlueBall {
        String color;
        Integer position;
        RedBlueBall next;

        public RedBlueBall(Integer position, String color) {
            this.position = position;
            this.color = color;
            this.next = null;
        }
    }

    private final Lock lock = new ReentrantLock();
    // 虚拟头节点
    private final RedBlueBall dummyHead;
    private RunningSystem rs;

    public PositionManager() {
        this.dummyHead = new RedBlueBall(null, null);
    }

    public void setRunningSystem(RunningSystem runningSystem) {
        this.rs = runningSystem;
    }

    public void inserts(FloorRequest... requests) {
        for (var request : requests) {
            insert(request);
        }
    }
    /**
     * 插入请求
     * 插入算法 + 颜色判别
     * @param request 楼层请求对象
     */
    public void insert(FloorRequest request) {
        lock.lock();
        try {
            RedBlueBall current = dummyHead;

            int floorId = request.floorId();

            String color = calculateColor(floorId);
            if (Objects.equals(color, Constants.DARK)) {
                return; // 如果是暗色，则废弃该请求
            }

            RedBlueBall ball = new RedBlueBall(floorId, color);

            // 找到相同颜色的小球的第一个位置
            while (current.next != null && !current.next.color.equals(color)) {
                current = current.next;
            }

            if (current.next == null) {
                current.next = ball;
            } else {
                // 插入排序：找到合适的位置插入
                while (current.next != null && current.next.position < floorId) {
                    current = current.next;
                }
                if (current.next == null) {
                    current.next = ball;
                } else if (current.next.position > floorId) {
                    ball.next = current.next;
                    current.next = ball;
                }
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * 计算小球的颜色
     * @param floorId 请求的楼层编号
     * @return 红色、蓝色或暗色（无效）
     */
    private String calculateColor(Integer floorId) {
        int currentFloor = rs.getCurrentFloor();
        if (currentFloor < floorId) {
            return Constants.RED;
        } else if (currentFloor > floorId) {
            return Constants.BLUE;
        } else {
            // 当前楼层等于目标楼层，废弃请求
            return Constants.DARK;
        }
    }

    /**
     * 返回电梯运行到的下一个楼层的数字
     * @return 下一个楼层编号，如果没有则返回 Constants.NULL
     */
    public Integer getNext() {
        lock.lock();
        try {
            /*
             * 注意：这里不能阻塞太久，否则可能导致死锁。
             * dummyHead.next == null 并且 lock 被占用时，
             * insert 方法可能永远无法执行。
             */
            if (dummyHead.next == null) {
                return Constants.NULL;
            } else {
                return dummyHead.next.position;
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * 从请求中提取楼层ID（模拟方法）
     * 实际中根据你的请求结构替换此方法
     */
    private Integer getFloorIdFromRequest(Object request) {
        if (request instanceof FloorRequest) {
            return ((FloorRequest) request).floorId();
        } else if (request instanceof Integer) {
            return (Integer) request;
        }
        return null;
    }

}