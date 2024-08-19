package com.rain.lock;

import jakarta.annotation.Nonnull;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

/**
 * 栈锁
 *
 * @author xueyu
 */
public class TimeoutLockFreeSpinStackLock implements Lock {
    /**
     * 无界并发队列 栈
     */
    private final ConcurrentLinkedDeque<Thread> stack = new ConcurrentLinkedDeque<>();

    @Override
    public void lock() {
        Thread currentThread = Thread.currentThread();
        // 当前线程入栈
        stack.addFirst(currentThread);
        // 自旋等待，直到当前线程成为栈顶元素
        while (stack.peekFirst() != currentThread) {
            // 自旋，忙等待
            // 让出 CPU，减少忙等待的 CPU 资源浪费
            Thread.yield();
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        Thread currentThread = Thread.currentThread();
        // 当前线程入栈
        stack.addFirst(currentThread);
        // 自旋等待，直到当前线程成为栈顶元素或被中断
        while (stack.peekFirst() != currentThread) {
            if (Thread.currentThread().isInterrupted()) {
                // 中断时移除线程
                stack.removeFirstOccurrence(currentThread);
                throw new InterruptedException();
            }
            // 自旋，忙等待
            Thread.yield(); // 让出 CPU
        }
    }

    @Override
    public boolean tryLock() {
        Thread currentThread = Thread.currentThread();
        // 使用 compareAndSet 来尝试获取锁
        if (stack.peekFirst() == null) {
            stack.addFirst(currentThread);
            return true;
        }
        // 如果栈顶是当前线程，则表示当前线程已经持有锁
        return stack.peekFirst() == currentThread;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        long timeoutMillis = unit.toMillis(time);
        long startTime = System.currentTimeMillis();
        Thread currentThread = Thread.currentThread();
        stack.addFirst(currentThread);  // 当前线程入栈
        while (stack.peekFirst() != currentThread) {
            // 检查超时
            if (System.currentTimeMillis() - startTime > timeoutMillis) {
                stack.removeFirstOccurrence(currentThread); // 超时移除线程
                return false;
            }
            // 自旋等待，减少忙等待的 CPU 资源浪费
            LockSupport.parkNanos(10_000_000); // 10ms
        }
        return true;
    }

    @Override
    public void unlock() {
        Thread currentThread = Thread.currentThread();
        if (stack.peekFirst() == currentThread) {
            stack.pollFirst(); // 弹出栈顶元素（当前线程）
        } else {
            throw new IllegalMonitorStateException("The current thread does not hold the lock");
        }
    }


    @Nonnull
    @Override
    public Condition newCondition() {
        throw new UnsupportedOperationException();
    }
}
