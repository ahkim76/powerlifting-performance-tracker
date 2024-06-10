package Project;

public class Exercise {
    private String workoutName;
    private int sets;
    private int reps;
    private double weight;

    public Exercise(String workoutName, int sets, int reps, double weight) {
        this.workoutName = workoutName;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
    }

    public String getWorkoutName() {
        return this.workoutName.substring(0,1).toUpperCase()+this.workoutName.substring(1);
    }

    public int getSets() {
        return this.sets;
    }

    public int getReps() {
        return this.reps;
    }

    public double getWeight() {
        return this.weight;
    }

    public String toString() {
        return getWorkoutName() + ": " + getSets() + " sets of " + getReps() + " reps at " + getWeight() + " pounds.";
    }
}
