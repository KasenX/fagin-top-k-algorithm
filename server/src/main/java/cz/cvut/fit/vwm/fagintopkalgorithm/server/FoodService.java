package cz.cvut.fit.vwm.fagintopkalgorithm.server;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FoodService {

    private final Map<String, List<Food>> ordered = new HashMap<>();

    public FoodService(FoodJpaRepository repository) {
        List<Food> food = repository.findAll();
        ordered.put("energy", food.stream().sorted((Comparator.comparingInt(Food::getEnergy))).collect(Collectors.toList()));
        ordered.put("protein", food.stream().sorted((Comparator.comparingInt(Food::getProtein))).collect(Collectors.toList()));
        ordered.put("carbohydrate", food.stream().sorted((Comparator.comparingInt(Food::getCarbohydrate))).collect(Collectors.toList()));
        ordered.put("fat", food.stream().sorted((Comparator.comparingInt(Food::getFat))).collect(Collectors.toList()));
        ordered.put("fiber", food.stream().sorted((Comparator.comparingInt(Food::getFiber))).collect(Collectors.toList()));
    }

    public Result getTopK(int k, Map<String, Boolean> columns) {
        // Time the Fagin's Top-k
        long startTime = System.nanoTime();
        Pair<Integer, List<Food>> rowsFagin = topFagin(k, columns);
        long endTime = System.nanoTime();
        long durationFagin = endTime - startTime;
        // Time the sequential
        startTime = System.nanoTime();
        Pair<Integer, List<Food>> rowsSequential = topSequential(k, columns);
        endTime = System.nanoTime();
        long durationSequential = endTime - startTime;

        assert rowsFagin.equals(rowsSequential); // Check if the results are equal

        return new Result(durationFagin, durationSequential, rowsFagin.getFirst(), rowsFagin.getSecond());
    }

    private Pair<Integer, List<Food>> topFagin(int k, Map<String, Boolean> columns) {
        return null;
    }

    private Pair<Integer, List<Food>> topSequential(int k, Map<String, Boolean> columns) {
        return null;
    }
}
