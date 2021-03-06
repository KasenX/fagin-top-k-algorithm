package cz.cvut.fit.vwm.fagintopkalgorithm.server;

import java.util.Collection;
import java.util.Map;

public class Result {

    // Entry
    private final int k;
    private final Function function;
    private final Map<String, Boolean> parameters;
    // Result
    private final int processedRows;
    private final Collection<Food> topRows;
    private final long durationFagin;
    private final long durationSequential;

    public Result(int k, Function function, Map<String, Boolean> parameters, int processedRows, Collection<Food> topRows, long durationFagin, long durationSequential) {
        this.k = k;
        this.function = function;
        this.parameters = parameters;
        this.processedRows = processedRows;
        this.topRows = topRows;
        this.durationFagin = durationFagin;
        this.durationSequential = durationSequential;
    }

    public int getK() {
        return k;
    }

    public Function getFunction() {
        return function;
    }

    public Map<String, Boolean> getParameters() {
        return parameters;
    }

    public int getProcessedRows() {
        return processedRows;
    }

    public Collection<Food> getTopRows() {
        return topRows;
    }

    public long getDurationFagin() {
        return durationFagin;
    }

    public long getDurationSequential() {
        return durationSequential;
    }
}
