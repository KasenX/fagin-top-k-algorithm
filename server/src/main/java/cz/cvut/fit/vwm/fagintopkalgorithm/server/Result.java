package cz.cvut.fit.vwm.fagintopkalgorithm.server;

import java.util.Collection;

public class Result {

    final long faginDuration;
    final long sequentialDuration;
    final int processedRows;
    final Collection<Food> topRows;

    public Result(long faginDuration, long sequentialDuration, int processedRows, Collection<Food> topRows) {
        this.faginDuration = faginDuration;
        this.sequentialDuration = sequentialDuration;
        this.processedRows = processedRows;
        this.topRows = topRows;
    }
}
