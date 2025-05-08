import { Constants } from './Constants.js';
import { RequestPool } from './RequestPool.js';
import { PositionManager } from './PositionManager.js';
import { RunningSystem } from './RunningSystem.js';

class Elevator {
    constructor(eventBus) {
        this.eventBus = eventBus;
        this.rp       = new RequestPool(eventBus);
        this.pm       = new PositionManager(eventBus);
        this.rs       = new RunningSystem(eventBus);

        init();
    }

    init() {
       this.pm.setRunningSystem(this.rs);
       this.rs.setPositionManager(this.pm);
    }

    start() {
        // 用nodejs开启一个全新的线程
        this.rs.doRun();
    }

}