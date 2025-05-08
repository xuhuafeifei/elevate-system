class EventBus {
  constructor() {
    this.events = {};
  }

  /**
   * 监听事件
   * @param {string} event 事件名称
   * @param {function} callback 回调函数
   * @param {object} context 可选的上下文（this）
   */
  on(event, callback, context) {
    if (!this.events[event]) {
      this.events[event] = [];
    }
    this.events[event].push({ callback, context });
    return this; // 支持链式调用
  }

  /**
   * 一次性监听事件（触发一次后自动解绑）
   * @param {string} event 事件名称
   * @param {function} callback 回调函数
   * @param {object} context 可选的上下文（this）
   */
  once(event, callback, context) {
    const wrapper = (...args) => {
      this.off(event, wrapper);
      callback.apply(context, args);
    };
    this.on(event, wrapper, context);
    return this;
  }

  /**
   * 触发事件
   * @param {string} event 事件名称
   * @param {...any} args 传递给回调的参数
   */
  emit(event, ...args) {
    const eventListeners = this.events[event];
    if (eventListeners) {
      eventListeners.forEach(({ callback, context }) => {
        callback.apply(context, args);
      });
    }
    return this;
  }

  /**
   * 移除事件监听器
   * @param {string} event 事件名称
   * @param {function} callback 要移除的回调函数（可选，若不传则移除所有监听器）
   */
  off(event, callback) {
    const eventListeners = this.events[event];
    if (!eventListeners) return this;

    if (!callback) {
      // 移除所有事件监听器
      delete this.events[event];
    } else {
      // 移除指定回调
      this.events[event] = eventListeners.filter(listener => listener.callback !== callback);
    }
    return this;
  }

  /**
   * 移除所有事件监听器
   */
  clear() {
    this.events = {};
    return this;
  }
}