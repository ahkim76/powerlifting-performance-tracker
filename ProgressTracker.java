package com.alexkim.powerliftingperformancetrackerv2;

import java.util.LinkedList;

public class ProgressTracker {
    //  private Workout lifts;
    private LinkedList<Workout> workouts;
    private WilksScoreCalculator calculator;
    private User user;
    public ProgressTracker(User user) {
        workouts = new LinkedList<>();
        calculator = new WilksScoreCalculator();
        this.user = user;
    }

    public void addWorkouts(Workout workout) {
        workouts.add(workout);
    }


    public void viewProgress() {
        for (Workout workout : workouts) {
            workout.displayDetails();
        }
    }
    public void getWilksScore() {
        double score = calculator.calculateWilksScore(user.getBodyweight(), user.getSquatPR()+user.getBenchPR()+user.getDeadliftPR(), user.getGender());
        System.out.println(score);
    }


}
