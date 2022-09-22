package pl.com.bottega.designpatterns.marsrover;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

// TODO remove rover logic from this class, it should only be responsible for collecting user commands and printing current rover state
public class MarsRoverApp {

    private int positionX, positionY;

    private Direction direction = Direction.NORTH;

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
        switch (direction) {
            case NORTH -> positionY++;
            case SOUTH -> positionY--;
            case EAST -> positionX++;
            case WEST -> positionX--;
        }
    }

    private void rotateLeft() {
        switch (direction) {
            case NORTH -> direction = Direction.WEST;
            case SOUTH -> direction = Direction.EAST;
            case EAST -> direction = Direction.NORTH;
            case WEST -> direction = Direction.SOUTH;
        }
    }

    private void rotateRight() {
        switch (direction) {
            case NORTH -> direction = Direction.EAST;
            case SOUTH -> direction = Direction.WEST;
            case EAST -> direction = Direction.SOUTH;
            case WEST -> direction = Direction.NORTH;
        }
    }

    private void printPosition() {
        out.println(String.format("Current position: (%d, %d), direction: %s", positionX, positionY, direction.name()));
    }

    private String readCommand() {
        return scanner.nextLine().trim();
    }
}

