package Project;

import static java.lang.Math.round;

public class OneRepMaxCalculator {
    private double weight;
    private int reps;

    public OneRepMaxCalculator(double weight, int reps) {
        this.weight = weight;
        this.reps = reps;
    }

    public double getWeight() {
        return this.weight;
    }

    public int getReps() {
        return this.reps;
    }

    public double findMax() {
        double bryzyckiMax=0.0;
        double epleyMax=0.0;
        bryzyckiMax = this.weight / (1.0278-0.0278*this.reps); // Brzycki 1RM formula
        epleyMax = this.weight * (1+0.0333*this.reps);
        //System.out.println(bryzyckiMax);
        //System.out.println(epleyMax);
        return Math.round((bryzyckiMax+epleyMax)/2);
    }

    public String toString() {
        return "Your estimated 1RM: " + findMax() + "lbs";
    }
}
