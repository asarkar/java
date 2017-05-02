package org.abhijitsarkar.hystrix;

import com.netflix.hystrix.HystrixCommand;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Abhijit Sarkar
 */
@Slf4j
public class SimpleHystrixCmd extends HystrixCommand<String> {
    private final long sleepMillis;

    public SimpleHystrixCmd(HystrixCommand.Setter setter) {
        this(setter, 0);
    }

    public SimpleHystrixCmd(HystrixCommand.Setter setter, long sleepMillis) {
        super(setter);
        this.sleepMillis = sleepMillis;
    }

    @Override
    protected String run() throws Exception {
        if (sleepMillis > 0) {
            Thread.sleep(sleepMillis);
        }

        String key = getCommandGroup().name() + "-" + getCommandKey().name();
        log.info("{} executing in thread: {}", key, Thread.currentThread().getName());
        return key;
    }
}
