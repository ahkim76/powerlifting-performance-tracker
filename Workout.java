package com.alexkim.powerliftingperformancetrackerv2;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.time.LocalDate;

public class Workout {
    private int id;
    private LocalDate date;
    private LinkedList<Exercise> session;
    private Workout next;
    private User user;

    public Workout(LocalDate date, User user) {
        this.date = date;
        this.session = new LinkedList<>();
        this.next = null;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    public LinkedList<Exercise> getExercise() {
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
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Exercise exercise : session) {
            sb.append(exercise).append("\n");
        }
        return sb.toString().trim();
    }
    public boolean enterExercise(String name, int sets, int reps, double weight) { // for JavaFX implementation
        Exercise exercise = new Exercise(name, sets, reps, weight);
        addExercise(exercise);
        updatePRs(name, reps, weight);
        System.out.println("exercise successfully added: "+exercise);
        if (updatePRs(name, reps, weight)) return true;
        return false;
    }

    public void deleteExercise(int num) {
        if (session.isEmpty()){
            System.out.println("No workouts to delete!");

        } else {
            System.out.println("Okay! Here are your recent workouts you can delete");
            for (int i=0; i<session.size(); i++) System.out.println((i+1)+". "+session.get(i));

            System.out.println("Enter the number of the workout to delete: ");

            if(num > 0 && num >= session.size()) {
                session.remove(num-1);
                System.out.println("Workout successfully removed");
            }
        }
    }

    public void enterExercise(Scanner scan) { // for console-based implementation
        // Ask the user if they want to enter an exercise

        boolean finished = false;

        while(!finished) {
            try {
                System.out.println("Do you want to enter or delete an exercise? (y/d/n)");
                String response = scan.next().trim();
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
                    updatePRs(title, reps, weight);
                }
                else if (response.equalsIgnoreCase("d")) {
                    if (session.isEmpty()){
                        System.out.println("No workouts to delete!");

                    } else {
                        System.out.println("Okay! Here are your recent workouts you can delete");
                        for (int i=0; i<session.size(); i++) System.out.println((i+1)+". "+session.get(i));

                        System.out.println("Enter the number of the workout to delete: ");
                        int choice = scan.nextInt();
                        if(choice > 0 && choice <= session.size()) {
                            session.remove(choice-1);
                            System.out.println("Workout successfully removed");
                        }
                    }
                }
                else if (response.equalsIgnoreCase("n")) {
                    finished=true;
                } else {

                    // Invalid response
                    System.out.println("Error detecting response, try again");
                    //enterExercise(scan); // Recursive call to try again
                }


            } catch (InputMismatchException e) {
                System.out.println("Invalid input type. Please try again");
                scan.nextLine();
            }
        }
    }


    private boolean updatePRs(String title, int reps, double weight) {
        if((reps == 1)) {
            switch(title.toLowerCase()) {
                case "squat":
                    if (weight > user.getSquatPR()) {
                        user.setSquatPR(weight);
                        System.out.println("Congratulations! You hit a new " + weight + " pounds squat PR!");
                        return true;
                    }
                    break;
                case "bench":
                case "bench press":
                    if (weight > user.getBenchPR()) {
                        user.setBenchPR(weight);
                        System.out.println("Congratulations! You hit a new " + weight + " pounds bench PR!");
                        return true;
                    }
                    break;
                case "deadlift":
                    if (weight > user.getDeadliftPR()) {
                        user.setDeadliftPR(weight);
                        System.out.println("Congratulations! You hit a new " + weight + " pounds deadlift PR!");
                        return true;
                    }
                    break;
            }
        }
        return false;
    }
}
