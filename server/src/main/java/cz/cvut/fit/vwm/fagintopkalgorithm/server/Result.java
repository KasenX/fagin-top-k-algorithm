package cz.cvut.fit.vwm.fagintopkalgorithm.server;

import java.util.Collection;

public class Result {

    private final long durationFagin;
    private final long durationSequential;
    private final int processedRows;
    private final Collection<Food> topRows;

    public Result(long durationFagin, long durationSequential, int processedRows, Collection<Food> topRows) {
        this.durationFagin = durationFagin;
        this.durationSequential = durationSequential;
        this.processedRows = processedRows;
        this.topRows = topRows;
    }
}
