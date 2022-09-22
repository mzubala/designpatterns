package pl.com.bottega.designpatterns.marsrover;

import java.util.Scanner;

public class MarsRoverApp {

    private int positionX, positionY;

    private Direction direction = Direction.NORTH;

    private Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        new MarsRoverApp().run();
    }

    private void run() {
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
                    System.out.println("Sorry, I don't understand");
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
        System.out.println(String.format("Current position: (%d, %d), direction: %s", positionX, positionY, direction.name()));
    }

    private String readCommand() {
        return scanner.nextLine().trim();
    }
}

enum Direction {
    NORTH, EAST, SOUTH, WEST
}

