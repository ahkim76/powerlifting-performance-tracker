package com.alexkim.powerliftingperformancetrackerv2;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.HashMap;

public class User {
    private String name;
    private String username;
    private String password;
    private double[] sbd;
    private double bodyweight;
    private boolean isMale;

    public User() {
        name = null;
        password = null;
        sbd = null;
    }

    public User(String name, String username, String password, double[] sbd, double bodyweight, boolean isMale) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.sbd = sbd;
        this.bodyweight = bodyweight;
        this.isMale = isMale;
    }
    public String getUsername() {
        return this.username;
    }
    public String getPasword() {
        return this.password;
    }

    public double getBodyweight() {
        return this.bodyweight;
    }

    public double getSquatPR() {
        return sbd[0];
    }

    public void setSquatPR(double pr) {
        sbd[0] = pr;
    }

    public double getBenchPR() {
        return sbd[1];
    }

    public void setBenchPR(double pr) {
        sbd[1] = pr;
    }

    public double getDeadliftPR() {
        return sbd[2];
    }

    public void setDeadliftPR(double pr) {
        sbd[2] = pr;
    }

    public boolean getGender()  {
        return this.isMale;
    }



    public User makeUser() {
        Scanner scan = null;
        try {
            scan = new Scanner(System.in);
            System.out.println("Enter name: ");
            String user = scan.next();
            System.out.println("Enter a username: ");
            String identification = scan.next();
            System.out.println("Enter your squat PR: ");
            double squat = scan.nextDouble();
            System.out.println("Enter your bench PR: ");
            double bench = scan.nextDouble();
            System.out.println("Enter your deadlift PR: ");
            double deadlift = scan.nextDouble();
            double[] arr = {squat, bench, deadlift};
            System.out.println("Enter your bodyweight in lbs: ");
            double bw = scan.nextDouble();
            System.out.println("Enter your biological sex (m/f): ");
            String gender = scan.next();
            String passwordd = "hi";
            if (gender.equalsIgnoreCase("f")) {
                isMale = false;
            }

            return new User(user, identification, passwordd, arr, bw, isMale);
        } catch (InputMismatchException e) {
            System.out.println("Invalid input type. Please try again");
            scan.next();
            throw e;
        }


    }

    @Override
    public String toString() {
        return "Name: " + name +
                "\nUsername: " + username +
                "\nPassword: " + password +
                "\nSquat PR: " + sbd[0] + " lbs" +
                "\nBench PR: " + sbd[1] + " lbs" +
                "\nDeadlift PR: " + sbd[2] + " lbs" +
                "\nBodyweight: " + bodyweight + " lbs" + "\nGender: " + isMale;
    }
}
