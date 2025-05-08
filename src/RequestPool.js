import { Constants } from "./Constants.js";

export class RequestPool {
    constructor(eventBus) {
        this.lock = new SimpleLock();
        this.pool = new Set();
        this.eventBus = eventBus;
    }

    add(request) {
        this.lock.waitForUnLock();
        this.pool.add(request);
    }

    /**
     * 将request通过EventBus发送
     */
    send() {
        this.lock.lock();
        this.eventBus.emit(Constants.SEND_FLOOR_REQUEST, [...this.pool]);
        this.unlock();
    }
}