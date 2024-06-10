package Project;
import java.util.Scanner;
import java.util.Date;
public class Main {
    public static void main(String[] args) {
        //System.out.println("Hello world!");

        Exercise sample = new Exercise("bench", 3, 3, 3);
        System.out.println(sample);
        Date now = new Date(2022,5,7);
       // Workout work = new Workout(now);
        //work.addExercise(sample);
        //work.displayDetails();
        PowerliftingPerformanceTracker pl = new PowerliftingPerformanceTracker();
        pl.run();

    }
}