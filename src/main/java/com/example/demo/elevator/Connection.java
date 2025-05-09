package com.example.demo.elevator;

/**
 * 连接通道. 模拟redis客户端向redis发送请求.
 * 在电梯调度系统中, 则扮演的是{@link FloorClient}向{@link Elevator}发送楼层请求{@link FloorRequest}
 * 每一个请求都会被放入{@link RequestPool}中, 然后由{@link Scheduler}调度器进行调度.
 * @author feigebuge
 * @email 2508020102@qq.com
 */
public class Connection {

    private final RequestPool rp;

    public Connection(RequestPool rp) {
        this.rp = rp;
    }

    public void send(FloorRequest request) {
        rp.add(request);
    }
}
