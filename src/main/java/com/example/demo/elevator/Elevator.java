package com.example.demo.elevator;

import lombok.Data;

@Data
public class Elevator {

    private final PositionManager pm;
    private final RunningSystem rs;
    private final RequestPool rp;

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
        rs.doRun();
    }

    public Connection connect() {
        return new Connection(this.rp);
    }
}