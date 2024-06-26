package com.alexkim.powerliftingperformancetrackerv2;
import java.util.Date;
import java.util.Scanner;
import java.util.InputMismatchException;
public class MeetPrepTool {
    private Date meetDay;
    private User user;
    private double currentSquat, currentBench, currentDeadlift, bodyweight;
    private boolean gender;
    private Scanner scan;

    public MeetPrepTool(User user) {
        this.user = user;
        this.meetDay = new Date(2024,10, 7); // dummy date for now. will ask in JavaFX GUI
        this.currentSquat = user.getSquatPR();
        this.currentBench = user.getBenchPR();
        this.currentDeadlift = user.getDeadliftPR();
        this.bodyweight = user.getBodyweight();
        this.gender = user.getGender(); // true if male, false if female
        this.scan = new Scanner(System.in);
    }

    public void prepareForMeet() {
        Scanner scan = new Scanner(System.in);
        double newSquat = getNewPR("squat", currentSquat);
        double newBench = getNewPR("bench", currentBench);
        double newDeadlift = getNewPR("deadlift", currentDeadlift);


    }

    private double getNewPR(String liftType, double currentPR) {

        double newPR = 0.0;
        boolean isValid = false;
        while (!isValid) {
            try {
                System.out.println("What " + liftType + " PR do you want to hit?");
                newPR = scan.nextDouble();
                if (newPR <= currentPR) {
                    System.out.println("You're not going lower than before. Enter again: ");
                } else {
                    isValid = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a numeric value.");
                scan.next(); // this consumes the incorrect input
            }
        }
        return newPR;
    }


}
