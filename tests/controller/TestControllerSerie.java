package tests.controller;

import tp01.src.controller.ControllerSerie;

public class TestControllerSerie {
    public static void main(String[] args) {
        try {
            ControllerSerie controller = new ControllerSerie();
            controller.menu(); // Simulate the menu interaction
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
