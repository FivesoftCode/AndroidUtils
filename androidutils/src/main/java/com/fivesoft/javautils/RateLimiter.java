package com.fivesoft.javautils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

/**
 * RateLimiter is a class that limits
 * the frequency of task execution.
 */
public final class RateLimiter implements Runnable {

    @Nullable
    private Runnable task;
    private long minIntervalMs;
    private long lastRunTime = 0;
    private boolean isTaskExecutionScheduled = false;
    private final AtomicLong droppedTasks = new AtomicLong(0);
    private final Executor mDelayedExecutor = Executors.newSingleThreadExecutor();
    private final AtomicReference<Thread> mDelayedThread = new AtomicReference<>(null);

    /**
     * Creates a new RateLimiter object.
     * @param task The task to be executed.
     * @param maxFrequency The maximum frequency of task execution in Hz. Must be greater than or equal to 0.
     */
    public RateLimiter(@Nullable Runnable task, float maxFrequency){
        Objects.requireNonNull(task);
        if(task == this){
            throw new IllegalArgumentException("task cannot be this");
        }
        this.task = task;
        setMaxFrequency(maxFrequency);
    }

    /**
     * Creates a new RateLimiter object.
     * @param task The task to be executed.
     * @param minIntervalMs The minimum interval between task executions in milliseconds.
     *                      Negative values will disable the rate limiting.
     */
    public RateLimiter(@Nullable Runnable task,
                       long minIntervalMs){
        if(task == this){
            throw new IllegalArgumentException("task cannot be this");
        }
        this.task = task;
        setMinInterval(minIntervalMs);
    }

    /**
     * Sets the maximum frequency of task execution in Hz.
     * @param hz The maximum frequency in Hz. Negative or zero values will disable the rate limiting.
     */
    public void setMaxFrequency(float hz){
        if(hz <= 0){
            setMinInterval(-1);
            return;
        }
        setMinInterval((long) (1000f / hz));
    }

    /**
     * Sets the minimum interval between task executions in milliseconds.
     * @param ms The minimum interval in milliseconds. Negative values will disable the rate limiting.
     */
    public void setMinInterval(long ms){
        minIntervalMs = ms;
    }

    /**
     * Sets the task to be executed.
     * Changing the task will cancel the current task execution
     * if it is scheduled and is different from the new task.
     * This method will block until the current task is finished. (if it is running)
     * @param task The task to be executed.
     */
    public void setTask(@Nullable Runnable task){
        setTask(task, true);
    }

    /**
     * Sets the task to be executed.
     * @param task The task to be executed.
     * @param interruptCurrentTask If true, the current task will be interrupted immediately.
     */
    public void setTask(@Nullable Runnable task, boolean interruptCurrentTask){
        Objects.requireNonNull(task);
        if(task == this){
            throw new IllegalArgumentException("task cannot be this");
        }
        if(task == this.task){
            return;
        }
        if (interruptCurrentTask) {
            Thread delayedThread = mDelayedThread.get();
            if(delayedThread != null){
                //Cancel the delayed task immediately
                delayedThread.interrupt();
                try {
                    delayedThread.join();
                } catch (InterruptedException e) {
                    //Ignore
                }
            }
        }
        this.task = task;
    }

    /**
     * Returns the max frequency of task execution in Hz or -1 if rate limiting is disabled.
     * @return The max frequency in Hz.
     */
    public float getMaxFrequency(){
        if(minIntervalMs <= 0){
            return -1;
        }
        return 1000f / minIntervalMs;
    }

    /**
     * Returns the minimum interval between task executions in milliseconds or -1 if rate limiting is disabled.
     * @return The minimum interval in milliseconds.
     */
    public long getMinInterval() {
        if(minIntervalMs <= 0){
            return -1;
        }
        return minIntervalMs;
    }

    /**
     * Returns the number of tasks that were dropped because they were scheduled too frequently.
     * @return The number of dropped tasks.
     */
    public long getDroppedTasksCount(){
        return droppedTasks.get();
    }

    /**
     * Resets dropped task counter to 0.
     */
    public void resetDroppedTasksCount(){
        droppedTasks.set(0);
    }

    /**
     * Executes the task if the minimum interval has passed since the last execution.
     * If the interval has not passed, the task will be executed as soon as the interval passes.
     * If the task is already scheduled, it will be dropped.
     */
    @Override
    public void run() {
        synchronized (this) {
            Runnable task = this.task;
            if(task == null){
                //Task is null, do nothing
                return;
            }
            if(minIntervalMs <= 0){
                task.run();
                return;
            }
            if(System.currentTimeMillis() - lastRunTime < minIntervalMs){
                scheduleTaskExecution();
                return;
            }
            lastRunTime = System.currentTimeMillis();
            isTaskExecutionScheduled = false;
            task.run();
        }
    }

    //Internal method to schedule task execution
    private void scheduleTaskExecution(){
        if(isTaskExecutionScheduled){
            droppedTasks.incrementAndGet();
            return;
        }
        isTaskExecutionScheduled = true;
        long delay = lastRunTime + minIntervalMs - System.currentTimeMillis();
        if(delay <= 0){
            run();
        } else {
            mDelayedExecutor.execute(() -> {
                try { Thread.sleep(delay);} catch (InterruptedException ignore) {
                    return;
                }
                synchronized (RateLimiter.this) {
                    if (isTaskExecutionScheduled) {
                        run();
                    } else {
                        droppedTasks.incrementAndGet();
                    }
                }
            });
        }
    }

}
