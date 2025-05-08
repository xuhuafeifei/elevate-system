/**
 * 没想好往RequestPool里放什么，先放个FloorRequest, 免得以后不好拓展
 */
class FloorRequest {
    constructor(floorId) {
        this.floorId = floorId;
    }

    getFloorId() {
        return this.floorId; 
    }
}