/**
 * simple lock, 模仿乐观锁. 
 * 当lock()被调用时, 会等待unlock()被调用, 然后再继续执行.
 * 该锁实现机制简单, 并不能保证严格意义上的原子执行功能.
 */
// class SimpleLock {
//     constructor() {
//         this.locked = false;
//     }

//     lock() {
//         this.lock = true;
//     }

//     unlock() {
//         this.lock = false;
//     }

//     isLock() {
//         return this.lock;
//     }

//     waitForUnLock() {
//         while (this.isLock()) {
//         }
//     }

// }

class SimpleLock {
  constructor() {
    this._locked = false;        // 是否已加锁
    this._waitQueue = [];        // 等待队列，存储 resolve 和 reject
  }

  /**
   * 获取锁（阻塞直到锁可用）
   */
  async acquire() {
    return new Promise((resolve, reject) => {
      if (!this._locked) {
        this._locked = true;
        resolve();
      } else {
        this._waitQueue.push({ resolve, reject });
      }
    });
  }

  /**
   * 尝试获取锁（非阻塞）
   * @returns {boolean} 是否成功获取锁
   */
  tryAcquire() {
    if (!this._locked) {
      this._locked = true;
      return true;
    }
    return false;
  }

  /**
   * 释放锁（唤醒下一个等待者）
   */
  release() {
    if (this._locked) {
      this._locked = false;

      // 唤醒第一个等待者
      const next = this._waitQueue.shift();
      if (next) {
        this._locked = true;
        next.resolve();
      }
    }
  }

  /**
   * 获取当前是否被锁定
   */
  isLocked() {
    return this._locked;
  }
}