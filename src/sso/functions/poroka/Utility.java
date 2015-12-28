package sso.functions.poroka;

import java.util.Arrays;



public class Utility {

	public static void izpisi(int[][] mat) {
		// izpise matriko
		for ( int i = 0; i < mat.length; i++) {
			for ( int j = 0; j < mat[i].length; j++) {
				System.out.print( mat[i][j] + " " );
			}
			System.out.println();
		}
	}

	public static void izpisiIndeksPlusEna(int[][] mat) {
		// izpise matriko
		for ( int i = 0; i < mat.length; i++) {
			for ( int j = 0; j < mat[i].length; j++) {
				System.out.print( (mat[i][j] + 1) + " " );
			}
			System.out.println();
		}
	}

	public static void izpisiObrnjenoIndeksPlusEna(int[][] mat) {
		// izpise matriko
		for ( int i = mat.length-1; i >= 0; i--) {
			for ( int j = 0; j < mat[i].length; j++) {
				System.out.print( (mat[i][j] + 1) + " " );
			}
			System.out.println();
		}
	}


	public static void izpisiGrupe(int[][][] mat) {
		// izpise matriko
		for ( int k = 0; k < mat.length; k++) {
			for ( int i = 0; i < 2; i++) {
				for ( int j = 0; j < mat[k][i].length; j++) {
					if ( i == 0 )
						System.out.print( (mat[k][i][j] + 1) + " " );
					if ( i == 1 )
						System.out.print( (mat[k][i][j]) + " " );
				}
				System.out.println();
			}

			System.out.println();
		}
	}

	public static void izpisi(double[][] mat) {
		// izpise matriko
		for ( int i = 0; i < mat.length; i++) {
			for ( int j = 0; j < mat[i].length; j++) {
				System.out.print( (float) mat[i][j] + " " );
			}
			System.out.println();
		}
	}

	public static void izpisi(int[][] mat, int to) {
		// izpise matriko
		for ( int i = 0; i < mat.length && i < to; i++) {
			for ( int j = 0; j < mat[i].length; j++) {
				System.out.print( mat[i][j] + " " );
			}
			System.out.println();
		}
	}

	public static void izpisi(int[] arr) {
		// izpise array
		for ( int i = 0; i < arr.length; i++) {
				System.out.print( (arr[i] + 1) + " " );
		}
		System.out.println();
	}

	public static void izpisi(double[] arr) {
		// izpise array
		for ( int i = 0; i < arr.length; i++) {
				System.out.print( arr[i] + " " );
		}
		System.out.println();
	}


	public static void  obrni (int[] a)
	// obrne array intov
	{
		int tmp;
		for (int i = 0; i < a.length/2; i++)
		{
			tmp = a[i];
			a[i] = a[ a.length-1 - i ];
			a[ a.length-1 - i ] = tmp;
		}
	}

	public static boolean vsebuje(int[][] grupa2D, int[] grupa2) {

		int[] grupa1 = 	twoDToOneD( grupa2D );

		for (int i = 0; i < grupa1.length; i++) {
			for (int j = 0; j < grupa2.length; j++) {
				if ( grupa1[i] == grupa2[j] ) {
					return true;
				}
			}
		}

		return false;
	}

	
	
	public static int[] izvrziDuplikate ( int[] a ) {
		// izvrze duplikate

		int[] resitev = new int[a.length];
		int ir = 0;

		Arrays.sort(a);
		int zadjnaVrednost = a[0];

		for (int i = 1; i < a.length; i++) {
			if ( zadjnaVrednost != a[i] ) {
				zadjnaVrednost = a[i];
				resitev[ir++] = a[i];
			}
		}

		return Arrays.copyOf(resitev, ir);

	}

	public static int[] prviBrezDrugih ( int[] prvi, int[] drugi ) {
		// vrne polje prvih elementov brez drugih

		int[] resitev = new int[prvi.length];
		int ir = 0;
		boolean zastavica;

		for (int i = 0; i < prvi.length; i++) {
			// pogledamo ce vsebije
			zastavica = false;
			for (int j = 0; j < drugi.length; j++) {
				if ( (prvi[i] == drugi[j]) ) {
					zastavica = true;
					break;
				}
			}
			// ce drugi ne vsebijejo trenutnega elementa ga prepisemo v resitev
			if ( zastavica == false ) {
				resitev[ir++] = prvi[i];
			}
		}

		resitev = izvrziDuplikate(resitev);

		return Arrays.copyOf(resitev, ir);



	}


	public static int[] twoDToOneD(int[][] posedeni) {
		// dvodimenzionalno polje vrne kot enodimenzionalno

		int lengthOfAll = 0;
		for (int i = 0; (i < posedeni.length) && (posedeni[i] != null); i++) {
			lengthOfAll += posedeni[i].length;
		}

		int[] posedeni2 = new int[lengthOfAll];
		int ip = 0;

		for (int i = 0; (i < posedeni.length) && (posedeni[i] != null); i++) {
			for (int j = 0; j < posedeni[i].length; j++) {
				posedeni2[ip++] = posedeni[i][j];
			}
		}

		return posedeni2;

	}


	public static int[]	concat(int[] A, int[] B) {
	   int[] C= new int[A.length+B.length];
	   System.arraycopy(A, 0, C, 0, A.length);
	   System.arraycopy(B, 0, C, A.length, B.length);

	   return C;
	}


	public static int[][] zamenjaj(int[][] mat, int a, int b, int c, int d) {
		// vrne novo matriko, v kateri sta m[a][b] in m[c][d] zamenjana

		int[][] resitev = multiArrayCopy(mat);

		resitev[a][b] = mat[c][d];
		resitev[c][d] = mat[a][b];

		return resitev;

	}


	public static int[][] premakni(int[][] mat, int a, int b, int c) {
		// vrne novo matriko, v kateri je m[a][b] premaknjen (dodan na zadnje mesto) v m[c][]

		int[][] resitev = multiArrayCopy(mat);


		//1. dodaj ga v resitev[c][]
		resitev[c] = Arrays.copyOf(mat[c], mat[c].length + 1);
		resitev[c][resitev[c].length-1] = mat[a][b];


		//2. izbrisi resitev[a][b]:
		//ce je edini v mat[a]
		if ( resitev[a].length == 1 ) {
			resitev[a] = new int[0];
		}
		//ce je zadnji
		else if ( mat[a].length == b+1 ) {
			resitev[a] = Arrays.copyOf(resitev[a], resitev[a].length - 1);
		}
		//ce je prvi
		else if ( b == 0 ) {
			resitev[a] = Arrays.copyOfRange( resitev[a], 1, resitev[a].length );
		}
		//ce je umes
		else {
			resitev[a] = concat( Arrays.copyOfRange( resitev[a], 0, b ), Arrays.copyOfRange( resitev[a], b+1, resitev[a].length ) );
		}


		return resitev;

	}


	public static int[][] multiArrayCopy(int[][] source) {

		int[][] destination = new int[source.length][];

		for (int a=0;a<source.length;a++) {
			destination[a] = new int[source[a].length];
			System.arraycopy(source[a],0,destination[a],0,source[a].length);
		}

		return destination;

	}
	
	public static int getRandomIntInRange ( int from, int to ) {
		
		int range = Math.abs(from) + Math.abs(to);
		return (int) Math.round( ((Math.random() * (double) range) + (double) from) ); 
				
	}
/*
	public static void izpisiListoNodov ( ArrayList<TreeNode> listaNodov ) {
		
		System.out.println();
		System.out.println();
		System.out.println("DREVESNA STRUKTURA:");
		Collections.sort(listaNodov);
		for ( TreeNode pozicija : listaNodov ) {

			System.out.print("Kazenske tocke: "+pozicija.kazTocke + " ");
			System.out.format ("Povprecje Poskusov: %.1f%n", pozicija.povprecjePoskusov);
			System.out.print(" Najboljsi otrok: " + pozicija.kazenskeTockeNajboljsegaOtroka);
			System.out.print(" Stevilo Poskusov: "+pozicija.stevecPoskusov + " Otroci: ->");
			for ( TreeNode otrok : pozicija.otroci ) {
				System.out.print(otrok.kazTocke + ", ");
			}
			System.out.println();
		}
		System.out.println();
	}
*/
}











