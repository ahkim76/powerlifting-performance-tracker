package Project;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Workout {
    private Date date;
    private ArrayList<Exercise> session;
    private Workout next;
    private User user;

    public Workout(Date date, User user) {
        this.date = date;
        this.session = new ArrayList<>();
        this.next = null;
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    public ArrayList<Exercise> getExercise() {
        return session;
    }

    public void addExercise(Exercise exercise) {
        session.add(exercise);
    }

    public Workout getNext() {
        return next;
    }

    public void setNext(Workout next) {
        this.next = next;
    }

    public void displayDetails() {
        if(!session.isEmpty()) {
            System.out.println("Workout Date: "+date);
            System.out.println("Lifts performed:");
            for(Exercise exercise: session) {
                System.out.println("- "+exercise);
            }
        } else {
            System.out.println("No lifts were performed :(");
        }

    }

    public void enterExercise(Scanner scan) {
        // Ask the user if they want to enter an exercise

        boolean finished = false;

        while(!finished) {
            try {
                System.out.println("Do you want to enter an exercise? (y/n)");
                String response = scan.next();
                // Process the response
                if (response.equalsIgnoreCase("y")) {
                    // Prompt the user for exercise details
                    System.out.println("Enter name of lift: ");
                    String title = scan.next();
                    System.out.println("Enter amt. of set(s): ");
                    int sets = scan.nextInt();
                    System.out.println("Enter amt. of rep(s): ");
                    int reps = scan.nextInt();
                    System.out.println("Enter weight used: ");
                    double weight = scan.nextDouble();
                    // Create Exercise object and add it to the session
                    Exercise exercise = new Exercise(title, sets, reps, weight);
                    addExercise(exercise);

                    // if exercise was 1 rep and greater than PRs, then replace PR with the exercise


                    if((reps == 1)) {
                        switch(title.toLowerCase()) {
                            case "squat":
                                if (weight > user.getSquatPR()) {
                                    user.setSquatPR(weight);
                                    System.out.println("Congratulations! You hit a new " + weight + " pounds squat PR!");
                                    break;
                                }
                            case "bench":
                                if (weight > user.getBenchPR()) {
                                    user.setBenchPR(weight);
                                    System.out.println("Congratulations! You hit a new " + weight + " pounds bench PR!");
                                    break;
                                }
                            case "deadlift":
                                if (weight > user.getDeadliftPR()) {
                                    user.setDeadliftPR(weight);
                                    System.out.println("Congratulations! You hit a new " + weight + " pounds deadlift PR!");
                                    break;
                                }
                        }
                    }


                } else if (response.equalsIgnoreCase("n")) {
                    finished=true;
                } else {

                    // Invalid response
                    System.out.println("Error detecting response, try again");
                    enterExercise(scan); // Recursive call to try again
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input type. Please try again");
                scan.next();
            }

        }


    }
}

