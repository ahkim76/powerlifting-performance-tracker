package Project;
import java.util.Scanner;
import java.util.Date;

public class PowerliftingPerformanceTracker {

    public void run() {
        boolean exit = false;
        int yee = 0;
        Scanner scan = new Scanner(System.in);
        String choice;
        Workout work = null;
        //User user = null;
        ProgressTracker progressTrack = new ProgressTracker();

        // Normal implementation, will use dummy values for testing
        //User userOne = new User();
        //user = userOne.makeUser();
        User user = new User("Bob", "BobbyJones", new double[]{325, 230, 425}, 157);
        System.out.println(user);

        while(!exit) {
            if (yee ==0) printMainMenu();
            else printSecondaryMenu();
            yee++;
            choice = scan.next();

            switch(choice) {
                case "1": // ADD USER

                    break;

                case "2": // RECORD LIFTS
                    if(user == null) {
                        System.out.println("Please add your details before recording lifts.");
                        break;
                    }
                    Date date = new Date(2024,5, 7);
                    work = new Workout(date, user);
                    work.enterExercise(scan);
                    work.displayDetails();
                    progressTrack.addWorkouts(work);
                    break;

                case "3": // VIEW PROGRESS
                    if (work != null) { // Check if work is initialized
                        progressTrack.viewProgress();
                    } else {
                        System.out.println("Please record lifts before viewing progress.");
                    }
                    break;

                case "4": // CALCULATE ONE REP MAX
                    //System.out.println("Note - 1RM calculators get more inaccurate the more reps you do.");
                    System.out.println("Enter the weight lifted: ");
                    double max = scan.nextDouble();
                    System.out.println("Enter the amount of reps: ");
                    int reps = scan.nextInt();
                    OneRepMaxCalculator one = new OneRepMaxCalculator(max, reps);
                    System.out.println(one);
                    break;

                case "5": // CHATGPT POWERLIFTING COACH
                    System.out.println("Feature coming soon!");
                    break;

                case "6": // EXIT
                    exit = true;
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

    }
    public void printMainMenu() {
        System.out.println("Welcome to the Powerlifting Performance Tracker!");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("1. Add User");
        System.out.println("2. Record Lifts");
        System.out.println("3. View Progress");
        System.out.println("4. Calculate One Rep Max");
        System.out.println("5. Talk to an AI Powerlifting coach");
        System.out.println("6. Exit");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Enter your choice (1-6): ");
    }

    public void printSecondaryMenu() {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Would you like to do anything else? (1-6): ");
    }

}
