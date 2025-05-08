package com.example.demo.elevator;

import org.springframework.stereotype.Component;

@Component
public class Elevator {
    private RequestPool rp;
    private PositionManager pm;
    private RunningSystem rs;

    public Elevator() {
        this.pm = new PositionManager();
        this.rp = new RequestPool(pm);
        this.rs = new RunningSystem();
        init();
    }

    private void init() {
        pm.setRunningSystem(rs);
        rs.setPositionManager(pm);
    }

    public void start() {
        new Thread(() -> {
            rs.doRun();
        }).start();
    }
}