package com.minibank.effect;

import java.util.concurrent.TimeUnit;

public class Effects {

    public static void pause(int seconds) {
        for (int i = 0; i < seconds; i++) {
            try {
                Thread.sleep(1000); //делает паузу на одну секунду
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.print(".");
        }
    }

    public static void pauseSilent(int seconds) {

        for (int i = 0; i < seconds; i++) {

            try {
                TimeUnit.SECONDS.sleep(1); //делает паузу на одну секунду
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            System.out.print(".");
        }
    }

}
