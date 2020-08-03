package hw_3_5;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class Main {
    public static final int CARS_COUNT = 4;
    static CountDownLatch startLine = new CountDownLatch(CARS_COUNT);
    static CyclicBarrier roadStage = new CyclicBarrier(CARS_COUNT);
    static Semaphore tunnel = new Semaphore(CARS_COUNT/2,true);
    static CountDownLatch finishLine = new CountDownLatch(CARS_COUNT);

    public static void main(String[] args) {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5);
        Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10), cyclicBarrier);
        }
        for (int i = 0; i < cars.length; i++) {
            new Thread(cars[i]).start();
        }

        while (startLine.getCount() > 0){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            cyclicBarrier.await();
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
            cyclicBarrier.await();
            cyclicBarrier.await();
        } catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
    }
}
