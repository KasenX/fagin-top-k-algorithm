package cz.cvut.fit.vwm.fagintopkalgorithm.server;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Food {

    @Id
    @GeneratedValue(generator="food_seq")
    @SequenceGenerator(name = "food_seq", sequenceName = "food_seq")
    private Long Id;
    @Column(nullable = false)
    private String Name;
    @Column(nullable = false)
    private int Energy;
    @Column(nullable = false)
    private int Protein;
    @Column(nullable = false)
    private int Carbohydrate;
    @Column(nullable = false)
    private int Fat;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getEnergy() {
        return Energy;
    }

    public void setEnergy(int energy) {
        Energy = energy;
    }

    public int getProtein() {
        return Protein;
    }

    public void setProtein(int protein) {
        Protein = protein;
    }

    public int getCarbohydrate() {
        return Carbohydrate;
    }

    public void setCarbohydrate(int carbohydrate) {
        Carbohydrate = carbohydrate;
    }

    public int getFat() {
        return Fat;
    }

    public void setFat(int fat) {
        Fat = fat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Food food = (Food) o;
        return Id.equals(food.Id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id);
    }
}
