package DinnerTime;

/**
 * Created by adam on 4/1/16.
 */

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DinnerTable {
    // The number of philosophers
    private static final int NUM_PHILOS = 5;

    public static void main(String[] args) {

        Lock[] cutlery = new ReentrantLock[NUM_PHILOS];

        for (int i = 0; i < NUM_PHILOS; i++) {
            cutlery[i] = new ReentrantLock();
        }

        Philosopher[] philosophers = new Philosopher[NUM_PHILOS];

        for (int i = 0; i < NUM_PHILOS; i++) {
            philosophers[i] = new Philosopher(i, cutlery[i], cutlery[(i + 1) % NUM_PHILOS]);
            new Thread(philosophers[i]).start();
        }
    }

    static class Philosopher implements Runnable {

        private Random numGenerator = new Random();
        private static final int LOW_TIME = 500, HIGH_TIME = 3000;

        private int id;
        private Lock leftSide;
        private Lock rightChopstick;


        public Philosopher(int id, Lock leftSide, Lock rightChopstick) {
            this.id = id+1;
            this.leftSide = leftSide;
            this.rightChopstick = rightChopstick;
        }

        // Main philosoper algorithm
        public void run() {
            try {
                while (true) {
                    think();
                    pickUpleftSide();
                    pickUpRightChopstick();
                    eat();
                    hey();
                    putDownCutlery();
                }
            } catch (InterruptedException e) {
                System.out.println("Collision :)");
            }
        }

        private void think() throws InterruptedException {
            System.out.println("Philosopher " + id + " is thinking about life");
            Thread.sleep(numGenerator.nextInt(HIGH_TIME - LOW_TIME) + LOW_TIME);
        }

        // Locking Functions
        private void pickUpleftSide() {
            leftSide.lock();
        }

        private void pickUpRightChopstick() {
            rightChopstick.lock();
        }

        // Eating Function
        private void eat() throws InterruptedException {
            System.out.println("Philosopher " + id + " is eating");
            Thread.sleep(numGenerator.nextInt(HIGH_TIME - LOW_TIME) + LOW_TIME);
        }

        // Release The locks on the cutlery
        private void putDownCutlery() {
            leftSide.unlock();
            rightChopstick.unlock();
        }

        // Random fun stuff
        private void hey() {
            System.out.println("Hey Spyros! Awesome course :) Learned a lot!");
        }
    }
}
