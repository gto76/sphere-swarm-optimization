package sso.foo;

public class WallsIdentitiCheck {

	public static void main(String[] args) {
		
		int iter = 1000; 
		
		/*
		double wallsPI = 4;
		double zgori = 2;
		double spodi = 3;
		for (int i = 0; i < iter; i++) {
			wallsPI = wallsPI * zgori / spodi;
			if ( (i%2) == 0 ) {
				zgori += 2;
			}
			else {
				spodi += 2;
			}
		}
		System.out.println("Walls PI: \t"+ wallsPI);		
		*/
		
		double gregorysPI = 1;
		for (int i = 1; i < iter; i++) {
			if ( (i%2) == 1 ) {
				gregorysPI -= 1.0/(i*2+1);
			}
			else {
				gregorysPI += 1.0/(i*2+1);
			}
		}
		gregorysPI *= 4;
		
		System.out.println("Gregorys PI: \t"+ gregorysPI + "\tDiff: "
				+ (gregorysPI-Math.PI));

		
		System.out.println("PI proper: \t"+ Math.PI);
		
		
	}
}
