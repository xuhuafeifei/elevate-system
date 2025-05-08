package com.example.demo.elevator;

import org.springframework.stereotype.Service;

public class FloorClient {
    private final int floorId;

    public FloorClient(int floorId) {
        this.floorId = floorId;
    }

    public int getFloorId() {
        return floorId;
    }
}