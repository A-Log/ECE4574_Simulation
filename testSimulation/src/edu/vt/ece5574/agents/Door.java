package edu.vt.ece5574.agents;

public class Door extends Item{
	
	
	
	private int a;
	private int b;
	
	public Door(String id, int a, int b){
		 super(id, Item.Obstacle.door);
		 this.setA(a);
		 this.setB(b);
	}

	public int getA() {
		return a;
	}

	public void setA(int a) {
		this.a = a;
	}

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
	}
}
