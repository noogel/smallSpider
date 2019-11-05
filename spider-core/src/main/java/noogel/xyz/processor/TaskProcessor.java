package noogel.xyz.processor;

public interface TaskProcessor {

    void process() throws InterruptedException;

    void run(String... args) throws InterruptedException;
}
