package sso.foo;

import java.util.ArrayList;

import sso.gauss.GParticle;
import sso.gauss.Point;

public class TestCorrect {

	public static void main(String[] args) {

		ArrayList<Double> ddd = new ArrayList<Double>();
		for (int i = 0; i < 4; i++) {
			ddd.add(0.5);
		}			
		//ddd.add(0.61);
		System.out.println(ddd);
		System.out.println("	...correct:...");
		GParticle.correct(ddd);
		System.out.println(ddd);
		System.out.println("	...correctKocka:...");
		GParticle.correctKocka(ddd);
		System.out.println(ddd);
		
	}
	
}
