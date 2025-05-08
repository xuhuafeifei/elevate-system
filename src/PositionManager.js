class PositionManager {

    constructor(eventBus) {
        this.eventBus = eventBus;
        this.lock = new SimpleLock();
        this.dummyHead = new RedBlueBoll(null, null); // 虚拟头节点，用于简化操作
        this.rs = null;
        register();
    }

    setRunningSystem(runningSystem) {
        this.rs = runningSystem;
    }

    register() {
       this.eventBus.on(Constants.SEND_FLOOR_REQUEST, (floorRequests) => {
            this.lock.acquire(); 
            for (let request of floorRequests) {
               insert(request); 
            }
            this.lock.release();
       });
    }

    /**
     * 插入请求
     * @param {*} request floorRequest对象
     */
    insert(request) {
        let current = this.dummyHead;
        // 计算当前小球的颜色
        let color = caculateColor(request.floorId);
        if (color == Constants.DARK) {
            return;
        }
        let boll = new RedBlueBoll(request.floorId, color);
        // 找到相同颜色的小球(first)
        while (current.next != null && current.next.color != color) {
            current = current.next;
        }

        if (current.next == null) {
           current.next = boll; 
        } else {
            // 此时current.next.color == color, 执行插入排序, 找到合适的位置插入
            while (current.next != null && current.next.floorId < request.floorId) {
                current = current.next;  
            }
            if (current.next == null) {
               current.next = boll; 
            } else if (current.next.floorId > request.floorId) {
                boll.next = current.next;
                current.next = boll;
            }
        }
    }

    caculateColor(floorId) {
        let cfloor  = this.rs.getCurrentFloor();
        if (cfloor < floorId) {
            return Constants.RED; 
        } else if (cfloor > floorId) {
            return Constants.BLUE;
        } else {
            // 如果相等, 则废弃请求
            return Constants.DARK;
        }
    }

    /**
     * 返回电梯运行到的下一个楼层的数字
     * @returns 获取电梯运行到的下一个楼层, 如果没有下一个楼层, 则返回null
     */
    async getNext() {
        await this.lock.acquire();
        /*
         * md, 这里不能阻塞, 否则可能会出现死锁 
         * dummyHead.next == null and this.lock.acquire()
         * 这样insert方法一辈子都无法被执行, 因为他获取不到lock
         */
        try {
            if (this.dummyHead.next == null) {
                return Constants.NULL;
            } else {
                return this.dummyHead.next.position;
            }
        } finally {
            this.lock.release();
        }
    }
}