package Project;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.HashMap;

public class User {
    private String name;
    private String userID;
    private double[] sbd;
    private double bodyweight;

    public User() {
        name = null;
        userID = null;
        sbd = null;
    }

    public User(String name, String userID, double[] sbd, double bodyweight) {
        this.name = name;
        this.userID = userID;
        this.sbd = sbd;
        this.bodyweight = bodyweight;
    }
    public String getUserID() {
        return this.userID;
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
            return new User(user, identification, arr, bw);
        } catch (InputMismatchException e) {
            System.out.println("Invalid input type. Please try again");
            scan.next();
            throw e;
        }


    }

    @Override
    public String toString() {
        return "Name: " + name +
                "\nUser ID: " + userID +
                "\nSquat PR: " + sbd[0] + " lbs" +
                "\nBench PR: " + sbd[1] + " lbs" +
                "\nDeadlift PR: " + sbd[2] + " lbs" +
                "\nBodyweight: " + bodyweight + " lbs";
    }
}
