package cz.cvut.fit.vwm.fagintopkalgorithm.server;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Map;
import java.util.Objects;

@Entity
public class Food {

    @Id
    @GeneratedValue(generator="food_seq")
    @SequenceGenerator(name = "food_seq", sequenceName = "food_seq")
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private int energy;
    @Column(nullable = false)
    private int protein;
    @Column(nullable = false)
    private int carbohydrate;
    @Column(nullable = false)
    private int fat;

    @Transient
    @JsonIgnore
    private double normEnergy;
    @Transient
    @JsonIgnore
    private double normProtein;
    @Transient
    @JsonIgnore
    private double normCarbohydrate;
    @Transient
    @JsonIgnore
    private double normFat;

    public double applyFunction(Function fn, Map<String, Boolean> columns) {
        double result = 0;
        switch (fn) {
            case MIN -> {
                result = 1;
                if (columns.get("energy"))
                    result = Math.min(result, normEnergy);
                if (columns.get("protein"))
                    result = Math.min(result, normProtein);
                if (columns.get("carbohydrate"))
                    result = Math.min(result, normCarbohydrate);
                if (columns.get("fat"))
                    result = Math.min(result, normFat);
            }
            case MAX -> {
                if (columns.get("energy"))
                    result = Math.max(result, normEnergy);
                if (columns.get("protein"))
                    result = Math.max(result, normProtein);
                if (columns.get("carbohydrate"))
                    result = Math.max(result, normCarbohydrate);
                if (columns.get("fat"))
                    result = Math.max(result, normFat);
            }
            case AVG -> {
                int count = 0;
                if (columns.get("energy")) {
                    result += normEnergy;
                    count++;
                }
                if (columns.get("protein")) {
                    result += normProtein;
                    count++;
                }
                if (columns.get("carbohydrate")) {
                    result += normCarbohydrate;
                    count++;
                }
                if (columns.get("fat")) {
                    result += normEnergy;
                    count++;
                }
                result /= count;
            }
            default -> throw new RuntimeException("Unknown function");
        }
        return result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public int getCarbohydrate() {
        return carbohydrate;
    }

    public void setCarbohydrate(int carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public int getFat() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public double getNormEnergy() {
        return normEnergy;
    }

    public void setNormEnergy(double normEnergy) {
        this.normEnergy = normEnergy;
    }

    public double getNormProtein() {
        return normProtein;
    }

    public void setNormProtein(double normProtein) {
        this.normProtein = normProtein;
    }

    public double getNormCarbohydrate() {
        return normCarbohydrate;
    }

    public void setNormCarbohydrate(double normCarbohydrate) {
        this.normCarbohydrate = normCarbohydrate;
    }

    public double getNormFat() {
        return normFat;
    }

    public void setNormFat(double normFat) {
        this.normFat = normFat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Food food = (Food) o;
        return id.equals(food.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
