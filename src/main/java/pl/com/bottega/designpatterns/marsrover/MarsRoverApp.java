package pl.com.bottega.designpatterns.marsrover;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class MarsRoverApp {

    private final MarsRover marsRover = new MarsRover();

    private final Scanner scanner;

    private final PrintWriter out;

    MarsRoverApp(InputStream in, OutputStream out) {
        scanner = new Scanner(in);
        this.out = new PrintWriter(out, true);
    }

    public static void main(String[] args) {
        new MarsRoverApp(System.in, System.out).run();
    }

    void run() {
        printPosition();
        while (true) {
            String command = readCommand();
            switch (command) {
                case "m":
                    move();
                    printPosition();
                    break;
                case "rl":
                    rotateLeft();
                printPosition();
                    break;
                case "rr":
                    rotateRight();
                    printPosition();
                    break;
                default:
                out.println("Sorry, I don't understand");
            }
        }
    }

    private void move() {
        marsRover.move();
    }

    private void rotateLeft() {
        marsRover.rotateLeft();
    }

    private void rotateRight() {
        marsRover.rotateRight();
    }

    private void printPosition() {
        var position = marsRover.getPosition();
        var direction = marsRover.getDirection();
        out.println(String.format("Current position: %s, direction: %s", position, direction.name()));
    }

    private String readCommand() {
        return scanner.nextLine().trim();
    }
}

