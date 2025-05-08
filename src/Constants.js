export default class Constants {
  // 🏢 电梯相关常量
  static RUNNING_STATUS_STOPPED = 'STOP'; // 电梯停止
  static RUNNING_STATUS_RUNNING = 'RUNNING'; // 电梯运行

  static ELEVATOR_RENDER_DONE   = 'ELEVATOR_RENDER_DONE';

  // 小球颜色
  static RED = 'RED'; // 红色
  static BLUE = 'BLUE'; // 蓝色
  static DARK = 'DARK'; // 黑色

  static NULL = 'NULL'; // 空值

  static FLOOR_HEIGHT = 3; // 每层楼高度（单位：米）
  static ELEVATOR_SPEED = 1.5; // 电梯移动速度（单位：米/秒）
  static DOOR_OPEN_TIME = 2000; // 电梯开门持续时间（毫秒）
  static DOOR_CLOSE_TIME = 1000; // 电梯关门持续时间（毫秒）

  // 🎮 系统行为配置
  static MAX_FLOORS = 10;
  static MIN_FLOORS = 1;
  static DEFAULT_START_FLOOR = 1;

  // 🎨 Three.js 渲染配置
  static CAMERA_DEFAULT_POSITION = { x: 0, y: 10, z: 15 };
  static LIGHT_INTENSITY = 1;
  static AMBIENT_LIGHT_COLOR = 0xffffff;

  // 📡 事件名称（用于 EventBus）
  static EVENT_NEW_REQUEST = 'new-request';
  static EVENT_ELEVATOR_MOVING = 'elevator-moving';
  static EVENT_ELEVATOR_ARRIVED = 'elevator-arrived';
  static EVENT_ELEVATOR_OPEN_DOOR = 'elevator-open-door';
  static EVENT_ELEVATOR_CLOSE_DOOR = 'elevator-close-door';

  // 🧱 工具方法（可选）
  static getFloorPosition(floor) {
    return (floor - 1) * this.FLOOR_HEIGHT;
  }
}