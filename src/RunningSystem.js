import { Constants } from './Constants.js';

class RunningSystem {

    constructor(eventBus) {
        this.runningStatus = Constants.RUNNING_STATUS_STOPPED;
        this.currentFloor  = 0;
        this.nextStation   = 0; // 电梯下一站
        this.pm            = null;
        this.eventBus      = eventBus;
        this.renderDone    = false; // 前端渲染是否完成
        this.register();
    }

    register() {
        this.eventBus.on(Constants.ELEVATOR_RENDER_DONE, () => {
            this.renderDone = true; // 前端渲染完成
        }); 
    }

    setPositionManager(pm) {
        this.pm = pm;
    }

    /**
     * 电梯运行获取下一站. 如果电梯此时处于运行状态, 则返回下一站.
     * 这样可以避免再运行过程中, 出现下一站被改变的情况, 从而减少
     * 系统设计的复杂度.
     */
    getCurrentFloor() {
        if (this.runningStatus === Constants.RUNNING_STATUS_RUNNING) {
            return this.nextStation; 
        }
        return this.currentFloor;
    }

    /**
     * 核心运行方法. 负责拉取{@link PositionManager}中的请求, 并控制前端电梯的渲染.
     */
    async doRun() {
        while (true) {
            let next = this.pm.getNext();
            if (next == Constants.NULL) {
                continue;
            }
            // 控制前端运行
            this.eventBus.emit(Constants.ELEVATOR_MOVING_TO_NEXT, next);
            // 等待前端渲染完成
            while (!this.renderDone) {
                await sleep(100);
            }
        }
    }
}