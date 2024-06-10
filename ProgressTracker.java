package Project;
//import org.jfree.chart.ChartFactory;
import java.util.LinkedList;

public class ProgressTracker {
  //  private Workout lifts;
    private LinkedList<Workout> workouts;
    public ProgressTracker() {
        workouts = new LinkedList<>();
    }

    public void addWorkouts(Workout workout) {
        workouts.add(workout);
    }

    public void viewProgress() {
        for (Workout workout : workouts) {
           workout.displayDetails();
        }
    }
}
