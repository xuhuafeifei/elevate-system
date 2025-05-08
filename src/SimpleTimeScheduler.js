class SimpleScheduler {
  constructor() {
    this.tasks = [];
    this.timers = new Map(); // 存储任务ID → timer
    this.taskId = 0;
  }

  // 添加任务
  addTask(fn, interval = 1000, options = { autoStart: true }) {
    const taskId = ++this.taskId;
    const task = {
      id: taskId,
      fn,
      interval,
      running: false,
      timer: null,
    };

    this.tasks.push(task);

    if (options.autoStart) {
      this.start(taskId);
    }

    return taskId;
  }

  // 启动任务
  start(taskId) {
    const task = this.tasks.find(t => t.id === taskId);
    if (!task || task.running) return;

    task.running = true;
    task.timer = setInterval(() => {
      task.fn();
    }, task.interval);

    this.timers.set(taskId, task.timer);
  }

  // 暂停任务
  pause(taskId) {
    const task = this.tasks.find(t => t.id === taskId);
    if (!task || !task.running) return;

    clearInterval(task.timer);
    task.running = false;
  }

  // 停止任务
  stop(taskId) {
    this.pause(taskId);
    const index = this.tasks.findIndex(t => t.id === taskId);
    if (index > -1) {
      this.tasks.splice(index, 1);
    }
  }

  // 修改任务间隔时间
  updateInterval(taskId, newInterval) {
    this.pause(taskId);
    const task = this.tasks.find(t => t.id === taskId);
    if (task) {
      task.interval = newInterval;
      this.start(taskId);
    }
  }

  // 获取所有任务
  getTasks() {
    return this.tasks.map(t => ({
      id: t.id,
      running: t.running,
      interval: t.interval
    }));
  }

  // 清除所有任务
  clearAll() {
    this.tasks.forEach(t => this.stop(t.id));
    this.tasks = [];
  }
}