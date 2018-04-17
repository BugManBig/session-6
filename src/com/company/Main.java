package com.company;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            new Application().run();
        } else {
            new Application().run(args[0]);
        }
    }
}
