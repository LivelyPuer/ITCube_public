package com.livlypuer.popava.data;

class AnotherThread extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try{
                Thread.sleep(1000); //Приостанавливает поток на 1 секунду
            } catch(InterruptedException e) {}

        }
    }
}
