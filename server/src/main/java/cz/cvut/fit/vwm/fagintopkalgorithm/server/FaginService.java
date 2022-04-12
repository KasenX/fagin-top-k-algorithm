package cz.cvut.fit.vwm.fagintopkalgorithm.server;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FaginService {

    FaginJpaRepository repository;
    List<Food> food;
    private final Map<String, Food[]> ordered = new HashMap<>();

    public FaginService(FaginJpaRepository repository) {
        this.repository = repository;
        food = repository.findAll();
        if (food.isEmpty())
            throw new RuntimeException("the db is empty");
        ordered.put("energy", food.stream().sorted((Comparator.comparingInt(Food::getEnergy).reversed())).toArray(Food[]::new));
        ordered.put("protein", food.stream().sorted((Comparator.comparingInt(Food::getProtein).reversed())).toArray(Food[]::new));
        ordered.put("carbohydrate", food.stream().sorted((Comparator.comparingInt(Food::getCarbohydrate).reversed())).toArray(Food[]::new));
        ordered.put("fat", food.stream().sorted((Comparator.comparingInt(Food::getFat).reversed())).toArray(Food[]::new));
        normalize();
    }

    public Result getTopK(int k, Function fn, Map<String, Boolean> columns) {
        // Time the Fagin's Top-k
        long startTime = System.nanoTime();
        Pair<Integer, List<Food>> rowsFagin = topFagin(k, fn, columns);
        long endTime = System.nanoTime();
        long durationFagin = endTime - startTime;
        // Time the sequential
        startTime = System.nanoTime();
        Pair<Integer, List<Food>> rowsSequential = topSequential(k, fn, columns);
        endTime = System.nanoTime();
        long durationSequential = endTime - startTime;

        // assert rowsFagin.getSecond().equals(rowsSequential.getSecond()); // Check if the results are equal

        return new Result(k, fn, columns, rowsFagin.getFirst(), rowsFagin.getSecond(), durationFagin, durationSequential);
    }

    private Pair<Integer, List<Food>> topFagin(int k, Function fn, Map<String, Boolean> columns) {
        Map<Food, Integer> selected = new HashMap<>();
        // Total count of attributes needed to be complete
        int columnsCount = (columns.get("energy") ? 1 : 0) + (columns.get("protein") ? 1 : 0)
                + (columns.get("carbohydrate") ? 1 : 0) + (columns.get("fat") ? 1 : 0);

        int rows;          // Count of currently searched rows
        int foodFound = 0; // Count of food found in all requested attributes
        for (rows = 0; foodFound < k; rows++) {
            if (columns.get("energy")) {
                // If already in the map then increment otherwise add to map with 1
                selected.merge(ordered.get("energy")[rows], 1, Integer::sum);
                foodFound += selected.get(ordered.get("energy")[rows]) == columnsCount ? 1 : 0;
            }
            if (columns.get("protein")) {
                selected.merge(ordered.get("protein")[rows], 1, Integer::sum);
                foodFound += selected.get(ordered.get("protein")[rows]) == columnsCount ? 1 : 0;
            }
            if (columns.get("carbohydrate")) {
                selected.merge(ordered.get("carbohydrate")[rows], 1, Integer::sum);
                foodFound += selected.get(ordered.get("carbohydrate")[rows]) == columnsCount ? 1 : 0;
            }
            if (columns.get("fat")) {
                selected.merge(ordered.get("fat")[rows], 1, Integer::sum);
                foodFound += selected.get(ordered.get("fat")[rows]) == columnsCount ? 1 : 0;
            }
        }
        return Pair.of(rows, selected.keySet().stream().sorted((a, b) -> Double.compare(b.applyFunction(fn, columns), a.applyFunction(fn, columns))).limit(k).toList());
    }

    private Pair<Integer, List<Food>> topSequential(int k, Function fn, Map<String, Boolean> columns) {
        return Pair.of(food.size(), food.stream().sorted((a, b) -> Double.compare(b.applyFunction(fn, columns), a.applyFunction(fn, columns))).limit(k).toList());
    }

    private void normalize() {
        int lastIdx = food.size() - 1;
        // Get max and min values for each attribute
        // First max then min
        Pair<Integer, Integer> energy = Pair.of(ordered.get("energy")[0].getEnergy(), ordered.get("energy")[lastIdx].getEnergy());
        Pair<Integer, Integer> protein = Pair.of(ordered.get("protein")[0].getProtein(), ordered.get("protein")[lastIdx].getProtein());
        Pair<Integer, Integer> carbohydrate = Pair.of(ordered.get("carbohydrate")[0].getCarbohydrate(), ordered.get("carbohydrate")[lastIdx].getCarbohydrate());
        Pair<Integer, Integer> fat = Pair.of(ordered.get("fat")[0].getFat(), ordered.get("fat")[lastIdx].getFat());
        for (var f : food) {
            f.setNormEnergy(Util.normalize(f.getEnergy(), energy.getSecond(), energy.getFirst()));
            f.setNormProtein(Util.normalize(f.getProtein(), protein.getSecond(), protein.getFirst()));
            f.setNormCarbohydrate(Util.normalize(f.getCarbohydrate(), carbohydrate.getSecond(), carbohydrate.getFirst()));
            f.setNormFat(Util.normalize(f.getFat(), fat.getSecond(), fat.getFirst()));
        }
    }
}
