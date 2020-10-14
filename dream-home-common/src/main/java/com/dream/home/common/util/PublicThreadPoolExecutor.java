package com.dream.home.common.util;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.concurrent.ThreadSafe;
import java.util.concurrent.*;

/**
 * 公用线程池
 *
 * @author hhz
 * @date 2020-05-14 15:22:00
 */
@ThreadSafe
public class PublicThreadPoolExecutor {

    private static final Logger logger = LoggerFactory.getLogger(PublicThreadPoolExecutor.class);
    private static final int PROCESSOR = Runtime.getRuntime().availableProcessors();

    /**
     * 线程池
     */
    private static final ExecutorService executorService = new ThreadPoolExecutor(PROCESSOR, PROCESSOR, 3, TimeUnit.SECONDS, new LinkedBlockingDeque<>(),
            new ThreadFactoryBuilder().setNameFormat("PublicThreadPoolExecutor-task-%d").build(), new ThreadPoolExecutor.CallerRunsPolicy());

    static {
        logger.info("static CPU核心数 processor size:{}", PROCESSOR);
    }

    private PublicThreadPoolExecutor() {
    }

    /**
     * 获得公用线程池
     *
     * @return pool线程池
     */
    public static ExecutorService getPool() {
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
    public static void submitRunnable(Runnable runnable) {
        getPool().submit(runnable);
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

    public static void main(String[] args) {
        logger.info("main CPU核心数 processor size:{}", PROCESSOR);
    }

}
