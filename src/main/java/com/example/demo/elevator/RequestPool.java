package com.example.demo.elevator;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RequestPool {

    private final Lock lock = new ReentrantLock();
    private final Set<FloorRequest> pool = Collections.synchronizedSet(new HashSet<>());
    private final PositionManager pm;

    public RequestPool(PositionManager pm) {
        this.pm = pm;
    }

    /**
     * 向请求池中添加一个请求
     * @param request 楼层请求对象
     */
    public void add(FloorRequest request) {
        lock.lock();
        try {
            pool.add(request);
        } finally {
            lock.unlock();
        }
    }

    public void send() {
        lock.lock();
        try {
            // 通知pm处理请求
            pm.inserts(new ArrayList<>(pool).toArray(new FloorRequest[pool.size()]));

            // 发送后清空池子
            pool.clear();
        } finally {
            lock.unlock();
        }
    }
}