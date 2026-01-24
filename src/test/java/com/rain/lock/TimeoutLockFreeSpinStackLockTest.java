package com.rain.lock;

import com.rain.common.utils.lock.TimeoutLockFreeSpinStackLock;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 针对TimeoutLockFreeSpinStackLock类的单元测试。
 * 测试类验证了该无锁自旋栈锁在并发场景下的核心行为，包括锁获取、超时控制、中断响应以及解锁权限检查。
 * 测试方法模拟了多线程竞争锁的情况，确保锁的互斥性、超时机制的正确性、中断处理的合规性以及解锁操作的线程安全性。
 * 每个测试方法都通过独立的线程执行锁操作，以验证锁在不同线程间的行为是否符合预期。
 * 测试中使用了ExecutorService来管理异步任务，并通过Future获取异步操作的结果，确保测试的准确性和可靠性。
 * 所有测试均遵循JUnit 5的规范，使用Assertions进行断言验证。
 *
 * @author xueyu
 */
@Slf4j
class TimeoutLockFreeSpinStackLockTest {

    @Test
    void tryLockShouldFailWhenHeldByAnotherThread() throws Exception {
        TimeoutLockFreeSpinStackLock lock = new TimeoutLockFreeSpinStackLock();
        log.info("主线程准备获取锁。");
        lock.lock();
        log.info("主线程已获取锁。");
        try {
            log.info("主线程持有锁，工作线程的 tryLock 应该失败。");
            ExecutorService executor = Executors.newSingleThreadExecutor();
            try {
                log.info("提交工作线程执行 tryLock。");
                Future<Boolean> future = executor.submit(() -> lock.tryLock());
                boolean acquired = future.get(1, TimeUnit.SECONDS);
                log.info("工作线程 tryLock 结果: {}", acquired);
                Assertions.assertFalse(acquired);
            } finally {
                executor.shutdownNow();
            }
        } finally {
            log.info("主线程释放锁。");
            lock.unlock();
        }
    }

    @Test
    void tryLockWithTimeoutShouldReturnFalseWhenTimeoutElapsed() throws Exception {
        TimeoutLockFreeSpinStackLock lock = new TimeoutLockFreeSpinStackLock();
        log.info("主线程准备获取锁。");
        lock.lock();
        log.info("主线程已获取锁。");
        try {
            log.info("主线程持有锁，工作线程的 tryLock(timeout) 应该超时失败。");
            ExecutorService executor = Executors.newSingleThreadExecutor();
            try {
                log.info("提交工作线程执行 tryLock(timeout)。");
                Future<Boolean> future = executor.submit(() -> lock.tryLock(50, TimeUnit.MILLISECONDS));
                boolean acquired = future.get(1, TimeUnit.SECONDS);
                log.info("工作线程 tryLock(timeout) 结果: {}", acquired);
                Assertions.assertFalse(acquired);
            } finally {
                executor.shutdownNow();
            }
        } finally {
            log.info("主线程释放锁。");
            lock.unlock();
        }
    }

    @Test
    void lockInterruptiblyShouldThrowWhenInterrupted() throws Exception {
        TimeoutLockFreeSpinStackLock lock = new TimeoutLockFreeSpinStackLock();
        log.info("主线程准备获取锁。");
        lock.lock();
        log.info("主线程已获取锁。");
        CountDownLatch started = new CountDownLatch(1);
        AtomicReference<Throwable> thrown = new AtomicReference<>();
        Thread waiter = new Thread(() -> {
            started.countDown();
            try {
                log.info("等待线程开始 lockInterruptibly。");
                lock.lockInterruptibly();
            } catch (Throwable ex) {
                log.info("等待线程被中断，异常: {}", ex.toString());
                thrown.set(ex);
            }
        });
        waiter.start();
        started.await(1, TimeUnit.SECONDS);
        log.info("主线程中断等待线程。");
        waiter.interrupt();
        waiter.join(1_000);
        log.info("主线程释放锁。");
        lock.unlock();
        Assertions.assertNotNull(thrown.get());
        Assertions.assertTrue(thrown.get() instanceof InterruptedException);
    }

    @Test
    void unlockShouldThrowWhenNotOwner() throws Exception {
        TimeoutLockFreeSpinStackLock lock = new TimeoutLockFreeSpinStackLock();
        log.info("主线程准备获取锁。");
        lock.lock();
        log.info("主线程已获取锁。");
        ExecutorService executor = Executors.newSingleThreadExecutor();
        try {
            log.info("提交工作线程执行 unlock（非持有者）。");
            Future<?> future = executor.submit(lock::unlock);
            ExecutionException ex = Assertions.assertThrows(ExecutionException.class, () -> future.get(1, TimeUnit.SECONDS));
            log.info("工作线程 unlock 失败，异常: {}", ex.getCause().toString());
            Assertions.assertTrue(ex.getCause() instanceof IllegalMonitorStateException);
        } finally {
            executor.shutdownNow();
            log.info("主线程释放锁。");
            lock.unlock();
        }
    }
}
