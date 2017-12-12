/**
* @author Sergey Svyazhin
* @version 12.12.2017
* @link https://github.com/ssvyazhin/java1/tree/master/HomeWork6
*/

class HomeWork6 {

    public static void main(String[] args) {
    	Dog dg = new Dog();
    	Cat ct = new Cat();
    	
    	dg.Run(400);
    	dg.Swim(9);
    	dg.Jump(1.5);
    	
    	ct.Run(201);
    	ct.Swim(2);
    	ct.Jump(2);

    }
    
}

abstract class Animal {

	protected double RunDistance;
	protected double SwimDistance;
	protected double JumpHeight;
	
	public void Run(double Distance) {
		System.out.println((RunDistance >= Distance) ? "Run: true" : "Run: false"); 
	}
	
	public void Swim(double Distance){
		System.out.println((RunDistance >= Distance) ? "Swim: true" : "Swim: false"); 
	}
	
	public void Jump(double Height){
		System.out.println((JumpHeight >= Height) ? "Jump: true" : "Jump: false"); 
	}

}

class Dog extends Animal {

	Dog() {
		RunDistance = 500;
		SwimDistance = 10;
		JumpHeight = 0.5;
	}
	
}

class Cat extends Animal {

	Cat() {
		RunDistance = 200;
		JumpHeight = 2;
	}
	
	@Override
	public void Swim(double Distance){
		System.out.println("Swim: false"); 
	}
	
}