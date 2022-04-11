package cz.cvut.fit.vwm.fagintopkalgorithm.server;

import java.util.Collection;

public class Result {

    private final int k;
    private final int processedRows;
    private final Function function;
    private final Collection<Food> topRows;
    private final long durationFagin;
    private final long durationSequential;

    public Result(int k, int processedRows, Function function, Collection<Food> topRows, long durationFagin, long durationSequential) {
        this.k = k;
        this.processedRows = processedRows;
        this.function = function;
        this.topRows = topRows;
        this.durationFagin = durationFagin;
        this.durationSequential = durationSequential;
    }
}
