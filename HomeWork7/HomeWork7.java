/**
* @author Sergey Svyazhin
* @version 16.12.2017
* @link https://github.com/ssvyazhin/java1/tree/master/HomeWork7
*/

import java.util.ArrayList;

class HomeWork7 {

    public static void main(String[] args) {
    	ArrayList <Cat> cats = new ArrayList<Cat>(); 
    	
    	cats.add(new Cat("Barsik", 5));
    	cats.add(new Cat("Misha", 15));
    	  	
        Plate plate = new Plate(10);
        
        System.out.println("First state:");
        for(Cat cat : cats) {
            cat.info();            
        }
        plate.info();
        System.out.println();
        
        System.out.println("Second state: feed cats");
        for(Cat cat : cats) {
        	cat.eat(plate);
            cat.info();            
        }
        plate.info();
        System.out.println();

        System.out.println("Third state: increase food + feed cats");
        plate.addFood(100);
        plate.info();
        for(Cat cat : cats) {
        	cat.eat(plate);
            cat.info();            
        }
        plate.info();
        System.out.println();

    }
    
}

class Cat {
    private String name;
    private int appetite;
    private boolean isFull;
    
    public Cat(String name, int appetite) {
        this.name = name;
        this.appetite = appetite;
        this.isFull = false;
    }

    public void eat(Plate p) {
        isFull = p.decreaseFood(appetite);
    }
    
    public void info() {
    	System.out.println("cat: " + name + " is full: " + isFull);
    }
}

class Plate {
    private int food;
    
    public Plate(int food) {
        this.food = food;
    }
    
    public boolean decreaseFood(int n) {
        if (n>food)
            return false;
        else {
            food -= n;
            return true;
        }
    }
    
    public void addFood(int n) {
        food +=n;
    }

    public void info() {
        System.out.println("plate: " + food);
    }
}