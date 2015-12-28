package _;

public class _ {
	
	static public final int TAB = 8;
	
	public static void p(String s) {
		System.out.print(s);
	}
	
	public static void pTab(String s, int n) {
		p(s, n*TAB);
	}
	
	/**
	 * @param s Text.
	 * @param n Number of spaces it will ocupy.
	 */
	public static void p(String s, int n) {
		int m = s.length();
		if ( (n-m) > 0 ) {
			System.out.print(s);
			for (int i = m; i < n; i++) {
				System.out.print(" ");
			}
		}
		else if ( (n-m) == 0 ) {
			System.out.print(s);
		}
		else {
			System.out.print(s.substring(0, n));
		}
	}
	
	public static void pRepeat(String s, int n) {
		for (int i = 0; i < n; i++) {
			System.out.print(s);
		}
	}
	
	public static void pRepeatTab(String s, int n) {
		for (int i = 0; i < n*TAB; i++) {
			System.out.print(s);
		}
	}
	
	public static void pn() {
		System.out.println();
	}
}
