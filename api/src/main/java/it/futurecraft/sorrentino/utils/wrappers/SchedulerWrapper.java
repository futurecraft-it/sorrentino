package it.futurecraft.sorrentino.utils.wrappers;

public interface SchedulerWrapper extends Wrapper {
    void async(Runnable task);

    void sync(Runnable task);

    void run(Runnable task);
}
