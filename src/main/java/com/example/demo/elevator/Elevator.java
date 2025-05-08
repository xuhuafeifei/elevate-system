package com.example.demo.elevator;

public class Elevator {

    private final PositionManager pm;
    private final RunningSystem rs;

    public Elevator() {
        this.pm = new PositionManager();
        var  rp = new RequestPool(pm);
        this.rs = new RunningSystem();
        init();
    }

    private void init() {
        pm.setRunningSystem(rs);
        rs.setPositionManager(pm);
    }

    public void start() {
        rs.doRun();
    }
}