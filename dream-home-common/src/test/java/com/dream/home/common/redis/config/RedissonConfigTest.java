package com.dream.home.common.redis.config;

import com.dream.home.common.util.DateUtil;
import com.dream.home.common.util.PublicThreadPoolExecutor;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@SpringBootTest
@Component
@Slf4j
public class RedissonConfigTest {

    /**
     * 创建监听器线程池
     */
    private static final ListeningExecutorService LISTENING_EXECUTOR_SERVICE = MoreExecutors.listeningDecorator(PublicThreadPoolExecutor.getPool());
    @Autowired
    private RedissonClient redissonClient;

    /**
     * redis设置值
     */
    @Test
    public void set() {
        // 设置字符串
        RBucket<String> keyObj = redissonClient.getBucket("hhz");
        keyObj.set("haohaozhi222");
    }

    @Test
    public void testLockTest() {
        testLock("key1");
    }

    /**
     * 被调用方：测试redis分布式锁
     *
     * @param key key
     * @return true/false
     */
    @Test
    public boolean testLock(String key) {
        Assert.notNull(key, "key不可为空！");
        long startTime = System.currentTimeMillis();
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            // 1.获得锁对象实例，公平
            RLock lock = redissonClient.getFairLock(key);
            boolean locked = lock.isLocked();
            log.info("locked:" + locked);
//        int holdCount = lock.getHoldCount();
//        log.info("holdCount:" + holdCount);
//        boolean heldByCurrentThread = lock.isHeldByCurrentThread();
//        log.info("heldByCurrentThread:" + heldByCurrentThread);
//        if (locked) {
//            log.info("redissonClient未获得锁");
//            return;
//        }
//        // 2.获取分布式锁
//        lock.lock(30, TimeUnit.SECONDS);
            // 尝试获得锁
            try {
                boolean tryLockFlag = lock.tryLock(3, 30, TimeUnit.SECONDS);
                if (!tryLockFlag) {
                    log.info("redissonClient未获得锁");
                    return false;
                }
            } catch (InterruptedException e) {
                log.error("redissonClient尝试获得锁出错:{}", e.getMessage(), e);
                return false;
            }

            try {
                // do sth.
                // 设置字符串
                RBucket<String> keyObj = redissonClient.getBucket("hhz");
                String oldValue = keyObj.get();
                String setValue = "haohaozhi" + "(" + key + ") at " + DateUtil.formatDateTime(new Date());
                log.info("setValue:" + setValue);
                keyObj.set(setValue);
                String newValue = keyObj.get();
                log.info("redissonClient获得锁，值设置成功，oldValue:{},newValue:{}", oldValue, newValue);
//            lock.wait(1000L, 10);
//            Thread.sleep(1000L, 10);
//        } catch (InterruptedException e) {
//            log.error(e.getMessage(), e);
                return true;
            } finally {
                log.info("redissonClient操作完成，但不自动释放锁");
//            log.info("redissonClient自动释放锁");
//            lock.unlock(); // 3.释放锁
//            lock.notifyAll();
            }
        } finally {
            long endTime = System.currentTimeMillis();
            log.info("final --> threadId:{},startTime:{},endTime:{},continue:{}", Thread.currentThread().getId(), startTime, endTime, endTime - startTime);
        }
    }

    /**
     * 调用方：线程池测试redis分布式锁
     */
    @Test
    public void testLockByMultiThread() {
        long startTime = System.currentTimeMillis();
        try {
            List<Future<Boolean>> booleanFutureList = new ArrayList<>();
            for (int i = 1; i <= 100; i++) {
                Future<Boolean> booleanFuture = PublicThreadPoolExecutor.submitCallable(
                        () -> testLock("key1")
                );
                booleanFutureList.add(booleanFuture);
            }

            List<Boolean> booleanList = new ArrayList<>();
            for (int i = 0; i < booleanFutureList.size(); i++) {
                try {
                    Boolean aBoolean = booleanFutureList.get(i).get(30, TimeUnit.SECONDS);
                    booleanList.add(aBoolean);
                } catch (TimeoutException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
            log.info("booleanList:{}", booleanList);
            JSONArray jsonArray = new JSONArray(booleanList);
            log.info("booleanList-jsonArray:{}", jsonArray);
        } finally {
            long endTime = System.currentTimeMillis();
            log.info("final --> threadId:{},startTime:{},endTime:{},continue:{}", Thread.currentThread().getId(), startTime, endTime, endTime - startTime);
        }
    }

    /**
     * 并行测试分布式锁，并获取返回结果
     */
    @Test
    public void testLockByParallel() {
        // 监听器集合
        List<ListenableFuture<Boolean>> listenableFutureList = new ArrayList<>();
        long startTime = System.currentTimeMillis();
        try {
            for (int i = 1; i <= 100; i++) {
                ListenableFuture<Boolean> booleanListenableFuture = LISTENING_EXECUTOR_SERVICE.submit(() -> testLock("key1"));
                listenableFutureList.add(booleanListenableFuture);
            }
            @SuppressWarnings("UnstableApiUsage")
            ListenableFuture<List<Boolean>> listListenableFuture = Futures.allAsList(listenableFutureList);
            List<Boolean> booleanList = null;
            try {
                booleanList = listListenableFuture.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            log.info("booleanList:{}", booleanList);
            JSONArray jsonArray = new JSONArray(booleanList);
            log.info("booleanList-jsonArray:{}", jsonArray);
        } finally {
            long endTime = System.currentTimeMillis();
            log.info("final --> startTime:{},endTime:{},continue:{}", startTime, endTime, endTime - startTime);
        }
    }

    @Test
    public boolean test1000Time() {
        long startTime = System.currentTimeMillis();
        try {
            Thread.sleep(1000);
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        } finally {
            long endTime = System.currentTimeMillis();
            log.info("final-test1000Time --> threadId:{},startTime:{},endTime:{},continue:{}", Thread.currentThread().getId(), startTime, endTime, endTime - startTime);
        }
    }

    @Test
    public boolean test2000Time() {
        long startTime = System.currentTimeMillis();
        try {
            Thread.sleep(2000);
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        } finally {
            long endTime = System.currentTimeMillis();
            log.info("final-test2000Time --> threadId:{},startTime:{},endTime:{},continue:{}", Thread.currentThread().getId(), startTime, endTime, endTime - startTime);
        }
    }

    @Test
    public boolean test3000Time() {
        long startTime = System.currentTimeMillis();
        try {
            Thread.sleep(3000);
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        } finally {
            long endTime = System.currentTimeMillis();
            log.info("final-test3000Time --> threadId:{},startTime:{},endTime:{},continue:{}", Thread.currentThread().getId(), startTime, endTime, endTime - startTime);
        }
    }

    @Test
    public boolean test5000Time() {
        long startTime = System.currentTimeMillis();
        try {
            Thread.sleep(5000);
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        } finally {
            long endTime = System.currentTimeMillis();
            log.info("final-test5000Time --> threadId:{},startTime:{},endTime:{},continue:{}", Thread.currentThread().getId(), startTime, endTime, endTime - startTime);
        }
    }

    @Test
    public void testTimeMethod() {
        long startTime = System.currentTimeMillis();
        try {
            List<Future<Boolean>> booleanFutureList = new ArrayList<>();

            Future<Boolean> booleanFuture5 = PublicThreadPoolExecutor.submitCallable(
                    this::test5000Time
            );
            booleanFutureList.add(booleanFuture5);

            Future<Boolean> booleanFuture3 = PublicThreadPoolExecutor.submitCallable(
                    this::test3000Time
            );
            booleanFutureList.add(booleanFuture3);

            Future<Boolean> booleanFuture2 = PublicThreadPoolExecutor.submitCallable(
                    this::test2000Time
            );
            booleanFutureList.add(booleanFuture2);

            Future<Boolean> booleanFuture1 = PublicThreadPoolExecutor.submitCallable(
                    this::test1000Time
            );
            booleanFutureList.add(booleanFuture1);

            List<Boolean> booleanList = new ArrayList<>();
            for (Future<Boolean> booleanFuture : booleanFutureList) {
                try {
                    Boolean aBoolean = booleanFuture.get(30, TimeUnit.SECONDS);
                    booleanList.add(aBoolean);
                } catch (TimeoutException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
            log.info("booleanList:{}", booleanList);
            JSONArray jsonArray = new JSONArray(booleanList);
            log.info("booleanList-jsonArray:{}", jsonArray);

        } finally {
            long endTime = System.currentTimeMillis();
            log.info("testTimeMethod-final --> threadId:{},startTime:{},endTime:{},continue:{}", Thread.currentThread().getId(), startTime, endTime, endTime - startTime);
        }

    }


}