package cz.cvut.fit.vwm.fagintopkalgorithm.server;

public class Util {

    public static double normalize(int val, int min, int max) {
        return (val - min) / (double) (max - min);
    }
}
