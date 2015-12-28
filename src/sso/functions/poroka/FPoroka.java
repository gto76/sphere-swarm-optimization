package sso.functions.poroka;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import _._;

import sso.functions.Function;
import sso.io.Razpon;
import sso.io.WeddingProblem;


public class FPoroka extends Function {

	String fileName;
	WeddingProblem wp;
	
	int[][] najboljsaPoseditev;
	public int najKazen = Integer.MAX_VALUE;
	
	int[][] m; //tabela moznih razporeditev stevila ljudi za mize
	//int im;
	
	public FPoroka(String fileName) {
		super("f1");
		this.fileName = fileName;
		this.wp = parse(fileName);
		vrniMozneKombinacijeMiz(wp.stLjudi);
		//_.p("Mozne Poseditve: ");
		//izpisi(m);
		//_.p("\n\n");
	}

	public int[][] getNajboljsaPoseditev() {
		return najboljsaPoseditev;
	}

	public static void izpisi(int[][] mat) {
		// izpise matriko
		for ( int i = 0; i < mat.length; i++) {
			for ( int j = 0; j < mat[i].length; j++) {
				System.out.print( mat[i][j] + " " );
			}
			System.out.println();
		}
	}

	public class Par implements Comparable{
		public final Double vrednost;
		public final Integer index;
		public Par(Double vrednost, Integer index) {
			super();
			this.vrednost = vrednost;
			this.index = index;
		}
		
		@Override
		public int compareTo(Object o) {
			assert o != null;
			Par p = (Par) o;
			if ( this.vrednost < p.vrednost ) return -1;
			else if ( this.vrednost == p.vrednost ) return 0;
			else return 1;
		}
				
	}
	
	@Override
	public double calculate(ArrayList<Double> X) {
		//int najboljsaResitev = Integer.MAX_VALUE;
		
		int stMiz = wp.mize.length;
		ArrayList<ArrayList<Integer>> pos = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < stMiz; i++) {
			pos.add(new ArrayList<Integer>());
		}
		
		//jih posede
		int i = 0;
		for ( Double x : X ) {
			int indexMize = (int)Math.round( x*(stMiz-1) );
			//pos.set(index, element);
			pos.get(indexMize).add(i++);
		}

		//sconverta v int[][]
		int[][] poseditev = new int[stMiz][];
		i = 0;
		for ( ArrayList<Integer> iii : pos ) {
			int j = 0;
			poseditev[i] = new int[iii.size()];
			for ( int val : iii ) {
				poseditev[i][j++] = val;
			}
			i++;
		}
		
		int resitev = stKazenskihTock(poseditev);
		
		
		//se kazenski pribitek za prenapolnjene mize:
		for (int ii = 0; ii < wp.im; ii++) {
			for (int j = 0; j < m[ii].length ; j++) {
				int noOfPeopleBehindTable = m[ii][j];
				int numOfTooMany = poseditev[ii].length - noOfPeopleBehindTable;
				if(numOfTooMany>0) {
					resitev += 10*numOfTooMany;
				}
			}
		}
		
		if ( resitev < najKazen ) { 
			_.p(resitev+"\n");	
			najKazen = resitev;
			najboljsaPoseditev = poseditev;
		}
		
		double fitnes = (new Integer(resitev)).doubleValue();
		return fitnes;
	}
	
	
	
	
	/*
	 * Dela slabo. fit ~ 300.
	 * to pa zato ker prostor ni lepo zvezen...
	 */
	public double calculateOld(ArrayList<Double> X) {
		
		ArrayList<Par> X2 = new ArrayList<Par>();
		int ii = 0;
		for ( Double x : X ) {
			X2.add(new Par(x,ii++));
		}
		Object[] xxx = X2.toArray();
		Arrays.sort(xxx);
		
		int najboljsaResitev = Integer.MAX_VALUE;
		
		for (int i = 0; i < wp.im; i++) {
			
			int[][] poseditev = new int[13][];
			int stZePosedenih = 0;
			
			for (int j = 0; j < m[i].length ; j++) {
				
				int noOfPeopleBehindTable = m[i][j];
				poseditev[j] = new int[noOfPeopleBehindTable];
				//_.p("\nNo Of p:"+noOfPeopleBehindTable+"");
				for (int k = 0; k < noOfPeopleBehindTable; k++) {
					
					//Double lokacijaModela = (Double) xxx[stZePosedenih++];
					Par par = (Par) xxx[stZePosedenih++];
					int indexModela = par.index;//X.indexOf(lokacijaModela);
					//_.p(" IndexModela:"+indexModela);
					poseditev[j][k] = indexModela;
				}
			}
			
			int tempResitev = stKazenskihTock(poseditev);
			
			if ( tempResitev < najboljsaResitev ) {
				najboljsaResitev = tempResitev;
				najboljsaPoseditev = poseditev;
				//izpisi(poseditev);
			}
		}

		_.p(najboljsaResitev+"\n");		
		double fitnes = (new Integer(najboljsaResitev)).doubleValue();
		return fitnes;
	}


	public ArrayList<Razpon> getRazponi() {
		return Razpon.getRazponi(0, 1, wp.stLjudi);
	}

	/************RACUNANJE KAZ. TOCK*********************/

	private int stKazenskihTock( int[][] moznaResitev ) {
		// vrne stevilo kazaenskih tock za dano kombinacijo
		int vsota = 0;
		int oseba1, oseba2;

		for ( int i = 0; i < moznaResitev.length; i++) {
			if ( (moznaResitev[i] != null) && (moznaResitev[i].length > 1) ) {
				for ( int j = 1; j < moznaResitev[i].length; j++) {
					oseba1 = moznaResitev[i][j];
					for ( int k = j-1; k >= 0; k--) {
						oseba2 = moznaResitev[i][k];
						vsota += ( wp.s[oseba1][oseba2] + wp.s[oseba2][oseba1] );
					}
				}
			}
		}
		return vsota;
	}

	/************RACUNANJE KAZ. TOCK*********************/
	
	
	//##############################################################//
	//## OD TLE NAPREJ SO METODE ZA ISKANJE MOZNIH KOMBINACIJ MIZ ##//
	//##############################################################//

	private int[][] vrniMozneKombinacijeMiz(int ljudi) {

		//Utility.izpisi(mize);
		Arrays.sort(wp.mize);
		//Utility.izpisi(mize);
		obrni(wp.mize);
		System.out.println("mize: " + Arrays.toString(wp.mize));

		m = new int[10000][wp.mize.length];
		int[] comb1 = new int[wp.mize.length];


		// ce je miz enako ali vec kot je ljudi
		if ( wp.mize.length >= ljudi ) {
			int i = 0;
			for ( ; i < ljudi; i++) {
				comb1[i] = 1;
			}
			for ( ; i < wp.mize.length; i++) {
				comb1[i] = 0;
			}
			return m;
		}

		//drugace za vsako mizo posedimo enega cloveka
		for ( int i = 0; i < wp.mize.length; i++) {
			comb1[i] = 1;
		}

		// ostale mize napolnimo po vrsti od najvecje proti najmansi, dokler gre
		int lj = ljudi - wp.mize.length; // odstejemo ze posedene
		for ( int i = 0; (i < wp.mize.length) && (lj > 0); i++) {

			for ( int j = 1; (j < wp.mize[i]) && (lj > 0); j++) {
				comb1[i]++;
				--lj;
				//System.out.println(lj + " " + i);
			}

		}

		//prekopiramo comb1 v prvo mesto od m
		m[0] = Arrays.copyOf(comb1, comb1.length);
		wp.im = 1;

		// zacnemo z premetavanjem
		//for ( int i = comb1.length-1; i >= 0; i--)
		//	premeci(i,Arrays.copyOf(comb1, comb1.length));
		premeci(0,Arrays.copyOf(comb1, comb1.length));

		return m;

	}

	private static void  obrni (int[] a)
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
	

	private void premeci(int iz, int[] tmp) {
		// premesca ljudi od leve proti desni in sproti shranjuje nove kombinacije
		// v tabelo m.

		while ( true ) {

			// poisce prosto mesto
			int nas = najdiProstoMesto(iz, tmp);

			if ( iz == tmp.length-1 )
				return;

			if ( nas != -1 ) {
				tmp[iz]--; //prestavi enega cloveka iz trenutne pozicije na prosto mesto
				tmp[nas]++;
				m[wp.im++] = Arrays.copyOf(tmp, tmp.length); //shrani razporeditev
			}

			//REKURZIVNI KLIC z iz+1 in kopijo trenutnega stanja (isto ponovi za eno mizo desno)
			premeci ( iz+1, Arrays.copyOf(tmp, tmp.length) );

			// ce je za desno mizo isto stevilo ljudi kot za trenutno
			if (tmp[iz] == tmp[iz+1]) {

				// pogleda do katere mize sedi isto ljudi
				int i = iz+1;
				for ( ; ( i < tmp.length - 2 ) && (tmp[i] == tmp[i+1]) ; i++);

				// od te mize zdaj proba enega presest in tako vse do mize desno
				// od trenutne pozicije
				for ( ; (i > iz) && (najdiProstoMesto(i, tmp) != -1) ; i--) {
						int nas2 = najdiProstoMesto(i, tmp);
						tmp[i]--;
						tmp[nas2]++;
				}

				// ce rata vse presest
				if ( tmp[iz] > tmp[iz+1] ) {
					// shrani trenutno razporeditev v tabelo m, ce ni ze shranjena na prejsnjem mestu
					if ( !Arrays.equals( m[wp.im-1], tmp ) ) {
						m[wp.im++] = Arrays.copyOf(tmp, tmp.length);
					}
					nas = 0;
				}

			}

			// ce ne najde prostega mesta se izvajanje funkcije prekine
			if ( nas == -1 ) {
				return;
			}

		}

	}
	
	private int najdiProstoMesto(int iz, int[] tmp) {
		//poisce mizo z prostim mestom

		// isci od trenutne pozicije + 1 do konca polja
		for ( int i = iz+1; i < tmp.length; i++) {
			// ce je trenutna pozicija vecja vsaj za dva od iskane
			// in iskana ni napolnjena do konca
			// in je pozicija levo od iskane vecja od iskane
			// in je trenutna vecja od njene desne,
			// potem vrni iskano
			if ( (tmp[iz] >= tmp[i]+2) && (tmp[i] < wp.mize[i])
					&& (tmp[i-1] > tmp[i]) && (tmp[iz] > tmp [iz+1]) ) {
				return i;
			}
		}
		// ce ne najdes primerne pozicije vrni -1
		return -1;

	}

	
	//##############################################################//
	//##                         PARSE FILE                       ##//
	//##############################################################//


	private static WeddingProblem parse(String fileName) {

	    //File file = new File("C:\\MyFile.txt");
	    File file = new File(fileName);
	    FileInputStream fis = null;
	    BufferedInputStream bis = null;
	    DataInputStream dis = null;
	    String line;

		int stLjudi = 0;
		int[] mizeTemp;
		int[] mize;
		int im = 0;
		int[][] s;
		int is = 0;


		//s = new int[2000][2];
		mizeTemp = new int[100];

	    try {
		      fis = new FileInputStream(file);

		      // Here BufferedInputStream is added for fast reading.
		      bis = new BufferedInputStream(fis);
		      dis = new DataInputStream(bis);

		      // dis.available() returns 0 if the file does not have more lines.


		      // prebere st ljudi
		      if (dis.available() != 0) {
		      		line = dis.readLine();
		      		try {
		      			stLjudi = Integer.valueOf( line ).intValue();
		      			//System.out.println(stLjudi);
		      		}
		      		catch (NumberFormatException e) {
	      				e.printStackTrace();
	    			}

		      }


			s = new int[stLjudi+1][stLjudi+1];

			for ( int i = 1; i <= stLjudi; i++) {
				for ( int j = 1; j <= stLjudi; j++) {
					s[i][j] = 0;
				}
			}


		      // prebere mize
		      if (dis.available() != 0) {

		      		line = dis.readLine();
		        	StringTokenizer st = new StringTokenizer(line);
		      		try {
	     				while (st.hasMoreTokens()) {
	         				mizeTemp[im++] = Integer.valueOf( st.nextToken() ).intValue();
	         				//System.out.print(mize[im-1] + " ");
	     				}
					}
		      		catch (NumberFormatException e) {
	      				e.printStackTrace();
	    			}
		        	catch (NoSuchElementException e) {
	    				e.printStackTrace();
	    			}
	    			//System.out.println();

		      }

		      mize = new int[im];
		      mize = Arrays.copyOf(mizeTemp, im);

		      // prebere sovrastva
		      while (dis.available() != 0) {

		      		line = dis.readLine();
		      		StringTokenizer st = new StringTokenizer(line);
		      		try {
	     				s[Integer.valueOf( st.nextToken() ).intValue() - 1]
	     				 [Integer.valueOf( st.nextToken() ).intValue() - 1] = 1;
	     				//System.out.print(s[is-1][0] + " ");
	     				//System.out.println(s[is-1][1]);
					}
		      		catch (NumberFormatException e) {
	      				e.printStackTrace();
	    			}

		      }

		      // dispose all the resources after using them.
		      fis.close();
		      bis.close();
		      dis.close();


		      return new WeddingProblem(stLjudi, mize, im, s, is);
		      
	    } catch (FileNotFoundException e) {
	      	e.printStackTrace();
	    } catch (IOException e) {
	      	e.printStackTrace();
	    }
	    
	    return null; 
	    
	}

	

    //NEW !!!
    public void najdiLokalniMinNajboljseResitve() {

    	//najdiLokalniMinKlicev++;

        //static int najKazen; // najmanj kazenskih tock dozdej
    	//static int[][] najboljsaPoseditev; // kombinacija z najmanj kazenskimi tockami dozdej

        int minDelovni; // najmanj kazenskih tock dozdej
    	int[][] resitevDelovna; // kombinacija z najmanj kazenskimi tockami dozdej

    	//Shrani vse resitve z eno zamenjavo ljudi, ki dajo boljsi rezultat od najKazen,
    	//in rekurzivno isci naprej...
    	
    	//za hitrejse izvajanje
    	int[] kazTockPoMizah = new int[najboljsaPoseditev.length];
    	for ( int i = 0; i < najboljsaPoseditev.length; i++) {
    		kazTockPoMizah[i] = stKazenskihTock( najboljsaPoseditev[i] );
    	}
    	int kazTockTemp = 0;
    	int kazTockBrezMizeI = 0;
    	int[][] tempRazporeditev;

    	
    	minDelovni = najKazen;
		resitevDelovna = najboljsaPoseditev;

		for ( int i = 0; i < najboljsaPoseditev.length - 1; i++) {
			kazTockBrezMizeI = najKazen - kazTockPoMizah[i];
			for ( int j = 0; j < najboljsaPoseditev[i].length; j++) {
				// gre po vseh elementih matrike in proba najdet uredu zamenjavo na drugih mizah

				for ( int k = i+1; k < najboljsaPoseditev.length; k++) {
					for ( int l = 0; l < najboljsaPoseditev[k].length; l++) {

						//zamenjaj m[i][j] z m[k][l] in preveri ce da boljso resitev in ce jo
						//jo spravi v minDelovni

						kazTockTemp = kazTockBrezMizeI - kazTockPoMizah[k];	
						tempRazporeditev = Utility.zamenjaj( najboljsaPoseditev, i, j, k, l);
						kazTockTemp += stKazenskihTock( tempRazporeditev[i] ) +
										stKazenskihTock( tempRazporeditev[k] );
						
						if ( kazTockTemp < minDelovni ) {

								minDelovni = kazTockTemp;
								resitevDelovna = tempRazporeditev;

						}

					}

					//ce miza za katero ga hocemo posedet se ni polna, ga probamo posedit (brez zamenjave)

					if ( wp.mize[k] > najboljsaPoseditev[k].length ) {

						if ( stKazenskihTock( Utility.premakni( najboljsaPoseditev, i, j, k) ) < minDelovni ) {

								minDelovni = stKazenskihTock( Utility.premakni( najboljsaPoseditev, i, j, k) );
								resitevDelovna = Utility.premakni( najboljsaPoseditev, i, j, k);

						}

					}


				}

			}
		}

		// preveri ce je nasel boljso resitev
		if ( minDelovni < najKazen ) {

			najKazen = minDelovni;
			najboljsaPoseditev = resitevDelovna;

			//najdiLokalniMinRekurzivnihKlicev++;

			najdiLokalniMinNajboljseResitve(); // se rekurzivno se enkrat izvede

		}


	}

    

	public int stKazenskihTock( int[] moznaResitev) {
		// vrne stevilo kazaenskih tock za dano kombinacijo
		// ce imas prvega cloveka oznacenega z 1
		int vsota = 0;
		int oseba1, oseba2;

		if ( moznaResitev.length > 1 ) {

			for ( int j = 1; j < moznaResitev.length; j++) {

				oseba1 = moznaResitev[j];

				for ( int k = j-1; k >= 0; k--) {
					oseba2 = moznaResitev[k];
					vsota += ( wp.s[oseba1][oseba2] + wp.s[oseba2][oseba1] );
				}

			}

		}

		return vsota;

	}

	@Override
	public double getBestPossibleResult() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<Razpon> getDefaultRazponi() {
		// TODO Auto-generated method stub
		return null;
	}

	
}