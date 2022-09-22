package pl.com.bottega.designpatterns.marsrover;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class MarsRoverApp {

    private final MarsRover marsRover = new MarsRover();

    private final Scanner scanner;

    private final PrintWriter out;
    private CommandGateway commandGateway;

    MarsRoverApp(InputStream in, OutputStream out, CommandGateway commandGateway) {
        scanner = new Scanner(in);
        this.out = new PrintWriter(out, true);
        this.commandGateway = commandGateway;
    }

    public static void main(String[] args) {
        new MarsRoverApp(
            System.in, System.out,
            new CommandGateway()
        ).run();
    }

    void run() {
        printPosition();
        while (true) {
            String command = readCommand();
            try {
                commandGateway.execute(command, marsRover);
                printPosition();
            } catch (IllegalArgumentException ex) {
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

