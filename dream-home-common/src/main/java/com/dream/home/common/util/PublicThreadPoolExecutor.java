package com.dream.home.common.util;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * 公用线程池
 *
 * @author hhz
 * @date 2020-05-14 15:22:00
 */
public class PublicThreadPoolExecutor {

    /**
     * 线程池
     */
    private static ThreadPoolExecutor executorService = null;

    private PublicThreadPoolExecutor() {
    }

    /**
     * 获得公用线程池
     *
     * @return pool线程池
     */
    public static ThreadPoolExecutor getPool() {
        if (executorService == null) {
            synchronized (PublicThreadPoolExecutor.class) {
                executorService = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), Runtime.getRuntime().availableProcessors(), 1000, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>(),
                        new ThreadFactoryBuilder().setNameFormat("PublicThreadPoolExecutor-task-%d").build(), new ThreadPoolExecutor.CallerRunsPolicy());
                // 核心线程超时回收，设置allowCoreThreadTimeout=true（默认false）时，核心线程会超时关闭
                executorService.allowCoreThreadTimeOut(true);
            }
        }
        return executorService;
    }

    /**
     * 执行Runnable任务
     *
     * @param runnable runnable
     */
    public static void executeRunnable(Runnable runnable) {
        getPool().execute(runnable);
    }

    /**
     * 提交Runnable任务
     *
     * @param runnable runnable
     */
    @SuppressWarnings("rawtypes")
    public static Future submitRunnable(Runnable runnable) {
        return getPool().submit(runnable);
    }

    /**
     * 提交带返回值的Callable<T>任务
     *
     * @param callable callable
     * @return Future<T>
     */
    public static <T> Future<T> submitCallable(Callable<T> callable) {
        return getPool().submit(callable);
    }

}
