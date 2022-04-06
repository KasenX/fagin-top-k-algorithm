package cz.cvut.fit.vwm.fagintopkalgorithm.server;

import java.util.Collection;

public class Result {

    private final long faginDuration;
    private final long sequentialDuration;
    private final int processedRows;
    private final Collection<Food> topRows;

    public Result(long faginDuration, long sequentialDuration, int processedRows, Collection<Food> topRows) {
        this.faginDuration = faginDuration;
        this.sequentialDuration = sequentialDuration;
        this.processedRows = processedRows;
        this.topRows = topRows;
    }
}
