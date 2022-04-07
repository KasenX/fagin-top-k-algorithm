package cz.cvut.fit.vwm.fagintopkalgorithm.server;

import javax.persistence.*;
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
