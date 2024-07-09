package com.alexkim.powerliftingperformancetrackerv2;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.InputMismatchException;

public class MeetPrepTool {
    private LocalDate meetDay, today;
    private long daysUntilMeet, weeksUntilMeet;
    private Period timeUntilMeet;
    private User user;
    private double currentSquat, currentBench, currentDeadlift, bodyweight;
    private boolean gender;
    private Scanner scan;

    public MeetPrepTool(User user, LocalDate date) {

        this.user = user;
        this.today = LocalDate.now();
        this.meetDay = date; // dummy date for now. will ask in JavaFX GUI
        this.timeUntilMeet = Period.between(this.today, this.meetDay);
        this.daysUntilMeet = ChronoUnit.DAYS.between(this.today, this.meetDay);
        this.weeksUntilMeet = ChronoUnit.WEEKS.between(this.today, this.meetDay);
        this.currentSquat = user.getSquatPR();
        this.currentBench = user.getBenchPR();
        this.currentDeadlift = user.getDeadliftPR();
        this.bodyweight = user.getBodyweight();
        this.gender = user.getGender(); // true if male, false if female
        this.scan = new Scanner(System.in);
    }

    public LocalDate getMeetDate() {
        return this.meetDay;
    }

    public long getWeeksUntilMeet() {
        return this.weeksUntilMeet;
    }

    public void prepareForMeet() {
        Scanner scan = new Scanner(System.in);
        double newSquat = getNewPR("squat", currentSquat);
        double newBench = getNewPR("bench", currentBench);
        double newDeadlift = getNewPR("deadlift", currentDeadlift);
        printProgress("Squat", currentSquat, newSquat);
        printProgress("Bench", currentBench, newBench);
        printProgress("Deadlift", currentDeadlift, newDeadlift);
        double oldTotal = currentSquat + currentBench + currentDeadlift;
        double newTotal = newSquat + newBench + newDeadlift;
        System.out.println("Your projected total is: "+newTotal);
        double difference = newTotal-oldTotal;
        System.out.println("That is a " + difference +" pound difference.");

        System.out.println("You have " + this.daysUntilMeet + " days until your competition.");

        int weeks = (int) this.weeksUntilMeet;
        System.out.println(subOneMonthPrep(weeks));
        System.out.println(regularMeetPrep(weeks));
    }

    private String subOneMonthPrep(int weeks) { // hard coding for when limited time to prep
        String plan = "";
        if (weeks == 1) return "Week 1: Practice some heavy singles and rest until your meet.";
         else if (weeks == 2) {
            plan += "Week 1: Low volume, very high intensity (90-100% of 1RM). Competition lifts only.\n";
            plan += "Week 2: Couple sets of heavy singles. Then rest until meet.";
           return plan;
        } else if (weeks == 3) {
            plan += "Week 1: Moderate volume, high intensity (85-95% of 1RM). Couple accessories.\n";
            plan += "Week 2: Low volume, very high intensity (90-100% of 1RM). Competition lifts only.\n";
            plan += "Week 3: Couple sets of heavy singles. Then rest until meet.\n";
            return plan;
        } else if (weeks == 4) {
            plan += "Week 1: Moderate volume, high intensity (85-95% of 1RM). Couple accessories.\n";
            plan += "Week 2: Same as last week. Moderate volume, high intensity.\n";
            plan += "Week 3: Low volume, very high intensity (90-100% of 1RM). Competition lifts only.\n";
            plan += "Week 4: Couple sets of heavy singles. Then rest until meet.\n";
            return plan;
        } else {
             return weeks + "";
        }
    }

    private String regularMeetPrep(int weeks) {
        String plan = "";
        if (weeks <= 4) return null;
        int hypertrophyStage = (int) (weeks * 0.5);
        int strengthStage = (int) (weeks * 0.3);
        int peakingStage = (int) (weeks * 0.2);
        int totalCalculatedWeeks = hypertrophyStage + strengthStage + peakingStage;
        int adjustment = weeks - totalCalculatedWeeks;
        while (adjustment != 0) {
            hypertrophyStage++;
            adjustment--;
        }

        int currentWeek = 0;
        while (currentWeek != weeks) {
            if (currentWeek < hypertrophyStage) {
                currentWeek++;
                plan += "Week " + currentWeek + ". HYPERTROPHY\n";
            } else if (currentWeek <= hypertrophyStage + strengthStage) {
                currentWeek++;
                plan += "Week " + currentWeek + ". STRENGTH\n";
            } else if (currentWeek <= totalCalculatedWeeks) {
                currentWeek++;
               plan += "Week " + currentWeek + ". PEAKING\n";
            }
        }
        return plan;

    }

    public String prepareForMeet(int newSquat, int newBench, int newDeadlift) {
        String everything = "";
        String squatProgress = printProgress("Squat", currentSquat, newSquat)+"\n";
        String benchProgress = printProgress("Bench", currentBench, newBench)+"\n";
        String deadliftProgress = printProgress("Deadlift", currentDeadlift, newDeadlift)+"\n";
        double oldTotal = currentSquat + currentBench + currentDeadlift;
        double newTotal = newSquat + newBench + newDeadlift;
        System.out.println("Your projected total is: "+newTotal);
        String totalStr = "Your projected total is: "+newTotal+"\n";
        double difference = newTotal-oldTotal;
        String differenceStr = "That is a " + difference +" pound difference.\n" + "You have " + this.daysUntilMeet + " days until your competition.\n";
        System.out.println("That is a " + differenceStr +" pound difference.");
        System.out.println("You have " + this.daysUntilMeet + " days until your competition.");
        int weeks = (int) (ChronoUnit.WEEKS.between(this.today, this.meetDay));
        System.out.println(subOneMonthPrep(weeks));
        System.out.println(regularMeetPrep(weeks));
        String prep = "";
        if (weeks <= 4) {
            prep += subOneMonthPrep(weeks);
        } else if (weeks > 4) prep += regularMeetPrep(weeks);
        everything += squatProgress + benchProgress + deadliftProgress + totalStr + differenceStr + prep;
        return everything;
    }

    private String printProgress(String liftType, double currentPR, double newPR) {
        double progress = newPR - currentPR;
        String progressStr = String.format("Your progress in %s: Current PR = %.2f, Target PR = %.2f, Progress = %.2f pounds\n", liftType, currentPR, newPR, progress);
        return progressStr;
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
