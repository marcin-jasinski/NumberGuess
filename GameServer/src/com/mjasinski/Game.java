package com.mjasinski;

import java.util.ArrayList;
import java.util.Random;

public class Game extends Thread {

    private int drawnNumber;
    private ArrayList<Integer> divisors;
    public int guessedDivisorsCounter;

    public Game(){
        Random random = new Random();

        while(true){
            drawnNumber = random.nextInt(101);
            if(!isPrime(drawnNumber)) {
                break;
            }
        }

        divisors = new ArrayList<>();
        guessedDivisorsCounter = 0;

        for(int i = 1; i < drawnNumber; i++){
            if(drawnNumber % i == 0){
                divisors.add(i);
            }
        }
    }

    boolean isPrime(int n) {
        if (n%2==0) return false;
        for(int i=3;i*i<=n;i+=2) {
            if(n%i==0)
                return false;
        }
        return true;
    }

    public int getDrawnNumber() {
        return drawnNumber;
    }

    public ArrayList<Integer> getDivisors() {
        return divisors;
    }
}
