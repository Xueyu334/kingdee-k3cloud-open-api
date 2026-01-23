package com.rain.lock;

import jakarta.annotation.Nonnull;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
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
    private final AtomicReference<Thread> owner = new AtomicReference<>();

    @Override
    public void lock() {
        Thread currentThread = Thread.currentThread();
        if (owner.compareAndSet(null, currentThread)) {
            return;
        }
        stack.addFirst(currentThread);
        while (true) {
            if (stack.peekFirst() == currentThread && owner.compareAndSet(null, currentThread)) {
                stack.pollFirst();
                return;
            }
            LockSupport.parkNanos(1_000_000);
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        Thread currentThread = Thread.currentThread();
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        if (owner.compareAndSet(null, currentThread)) {
            return;
        }
        stack.addFirst(currentThread);
        while (true) {
            if (Thread.interrupted()) {
                stack.removeFirstOccurrence(currentThread);
                throw new InterruptedException();
            }
            if (stack.peekFirst() == currentThread && owner.compareAndSet(null, currentThread)) {
                stack.pollFirst();
                return;
            }
            LockSupport.parkNanos(1_000_000);
        }
    }

    @Override
    public boolean tryLock() {
        Thread currentThread = Thread.currentThread();
        return owner.compareAndSet(null, currentThread);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        long timeoutNanos = unit.toNanos(time);
        long deadline = System.nanoTime() + timeoutNanos;
        Thread currentThread = Thread.currentThread();
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        if (owner.compareAndSet(null, currentThread)) {
            return true;
        }
        stack.addFirst(currentThread);
        while (true) {
            if (Thread.interrupted()) {
                stack.removeFirstOccurrence(currentThread);
                throw new InterruptedException();
            }
            if (stack.peekFirst() == currentThread && owner.compareAndSet(null, currentThread)) {
                stack.pollFirst();
                return true;
            }
            long remaining = deadline - System.nanoTime();
            if (remaining <= 0) {
                stack.removeFirstOccurrence(currentThread);
                return false;
            }
            LockSupport.parkNanos(Math.min(remaining, 10_000_000L));
        }
    }

    @Override
    public void unlock() {
        Thread currentThread = Thread.currentThread();
        if (!owner.compareAndSet(currentThread, null)) {
            throw new IllegalMonitorStateException("The current thread does not hold the lock");
        }
    }


    @Nonnull
    @Override
    public Condition newCondition() {
        throw new UnsupportedOperationException();
    }
}
