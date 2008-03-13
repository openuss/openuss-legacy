package org.openuss.seminarpool;



/**
 * This class implements the simplex algorithm
 * 
 * @author Tim Majchrzak
 * @version 1.2, 02.2007
 */

public class Simplex {

	static final boolean debugmode = false;
	
    private double[][] matrix;
    private double[] targetvalues;
    private double[] endfunction;
    private double[] endfunction_backup;
    private String[] sideconditionsbiggersmaller;
    private int variables;
    private int sideconditions;
    private int scinserted = 0;
    private boolean efinserted = false;
    private boolean minimize;
    private boolean calculated = false;
    private int slackvariables = 0;
    private int slackvariablesnecessary= 0;
    private int auxiliaryvaribles = 0;
    private int[] basiscolumn;
    private double[] gammavalues;
    private boolean gammacalculated = false;
    private double gammavalue_targetvalue;
    private int pivotrow;
    private int pivotcolumn;
    private double[] fdevidedbyxvalues;
    private boolean fdevidedbyxcalculated = false;
    private int[] gammasequence;
    private int gammasequenceentries;
    private boolean auxiliaryproblem = false;
    private int possiblepivotelements = 0;
    private boolean equalgammavalues;
    private int iterations_auxiliaryproblem = 0;
    private int iterations = 0;

    /**
     * Constructor
     * @param variables
     * @param sideconditions
     * @param minimize
     */
    public Simplex(int variables, int sideconditions, boolean minimize) {
    	super();
    	this.variables = variables;
    	this.sideconditions = sideconditions;
    	this.minimize = minimize;
    	// Die Matrix wird im Zweifelsfall zu groß. Auf jeden Fall wird Platz für Schlupfvariablen und etwaige Hilfsvariable gelassen. Auf keinen Fall darf aber nun matrix[].length verwndet werden
    	matrix = new double[sideconditions][variables + sideconditions + sideconditions];
    	endfunction = new double[variables + sideconditions + sideconditions];
    	endfunction_backup = new double[variables + sideconditions + sideconditions];
    	sideconditionsbiggersmaller = new String[sideconditions];
    	targetvalues = new double[sideconditions];
    	gammavalues = new double[variables + sideconditions + sideconditions];
    	gammasequence = new int[variables + sideconditions + sideconditions];
    	basiscolumn = new int[sideconditions];
    	for (int a = 0; a < sideconditions; a++)
    	{
    		basiscolumn[a] = -1;
    	}
    	fdevidedbyxvalues = new double[sideconditions];
    }
    
    /**
     * Add sidecondition
     * @param sc
     * @param biggersmaller
     * @throws Exception
     */
    public void newSC (double[] sc, String biggersmaller) throws Exception
    {
    	double nbfaktor = 1;
    	if (scinserted == sideconditions)
    	{
    		throw new Exception("Fehler: Alle Nebenbedingungen sind bereits eingefügt.");
    	}
    	if (sc.length != (variables + 1))
    	{
    		throw new Exception("Fehler: Die Nebenbedingung enhält eine falsche Anzahl Variablen.");
    	}
    	if (biggersmaller.equals("<=") || biggersmaller.equals(">="))
    	{
    		slackvariablesnecessary++;
    	} else if (!biggersmaller.equals("=")) {
    		throw new Exception("Fehler: Die Nebenbedingung muss größer gleich, kleiner gleich oeder gleich sein!");
    	}
    	
    	if (biggersmaller.equals(">="))
    	{
    		nbfaktor  = -1;
    	}
    	
    	for (int a = 0; a < sc.length - 1; a++)
    	{
    		matrix[scinserted][a] = sc[a] * nbfaktor;
    	}
    	targetvalues[scinserted] = sc[(sc.length - 1)] * nbfaktor;
    	sideconditionsbiggersmaller[scinserted] = "<=";

    	scinserted++;
    }
    
    /**
     * Add endfunction
     * @param ef
     * @throws Exception
     */
    public void newEF(double[] ef) throws Exception
    {
    	if (efinserted)
    	{
    		throw new Exception("Fehler: Die Zielfunktion ist bereits eingefügt.");
    	}
    	if (ef.length != variables)
    	{
    		throw new Exception("Fehler: Die Zielfunktion enhält eine falsche Anzahl Variablen.");
    	}
    	
    	for (int a = 0; a < ef.length; a++)
    	{
    		endfunction[a] = ef[a];
    	}
    	
    	efinserted = true;
    }
    
    
    /**
     * Get iterations
     * @return
     * @throws Exception
     */
    public int[] getIterations() throws Exception
    {
    	if (!calculated)
    	{
    		throw new Exception("Das Problem wurde noch nicht berechnet!");
    	} else {
    		int[] bothiterations = new int[2];
    		bothiterations[0] = (iterations + 1);
    		bothiterations[1] = (iterations_auxiliaryproblem + 1);
    		return bothiterations;
    	}
    }
    
    /**
     * Get the result
     * @return
     * @throws Exception
     */
    public double[][] getResult() throws Exception
    {
    	if (!calculated)
    	{
    		calculate();
    	}
    	
    	int inserted = 0;
    	double[][] result = new double[sideconditions + 1][2];
    	for (int a = 0; a < sideconditions; a++)
    	{
    		if (a == 0)
    		{
    			result[0][0] = basiscolumn[0];
    			result[0][1] = targetvalues[0];
    			inserted++;
    		} else {
    			int b = 0;
    			while (b < inserted && b < (sideconditions - 1) && basiscolumn[a] > (int) result[b][0])
    			{
    				b++;
    			}
				for (int c = (sideconditions - 1); c > b; c--)
				{
					result[c][0] = result[c - 1][0];
					result[c][1] = result[c - 1][1];
				}
				
    			result[b][0] = basiscolumn[a];
    			result[b][1] = targetvalues[a];
    			inserted++;
    		}
    	}
    	
    	for (int a = 0; a < sideconditions; a++)
    	{
    		result[a][0] += 1.0;
    	}
    	
    	result[sideconditions][0] = 0.0;
    	result[sideconditions][1] = gammavalue_targetvalue;
    	
    	return result;
    }
    
    
    
    /**
     * Starts the algorithm
     */
    private void calculate()
    {
    	int numberofcolumnswithone = 0;
    	if (debugmode) {matrix_out("Tableau vor Einfügen der Schlupfvariablen");}
    	
    	// Schlupfvariablen einfügen, falls nötig
    	if (slackvariablesnecessary > 0)
    	{
    		slackvariables = slackvariablesnecessary;
    		int noslack = 0;
    		for(int a = 0; a < sideconditions; a++)
    		{
    			if (sideconditionsbiggersmaller[a].equals("<="))
    			{
    				matrix[a][variables + a + noslack] = 1.0;
    			//} else if (nebenbedingungen_grkl[a].equals(">=")) {
    			//	matrix[a][variablen + a + keinschlupf] = -1.0;
    			} else {
    				noslack++;
    			}
    		}
    	}
    	
    	// für eine Grundlösung muss eben so viele Spalten mit nur einer 1 geben, wie Nebenbedingungen
    	numberofcolumnswithone = getNumberOfColumnsWithOne();
    	if (debugmode) {matrix_out("Tableau nach Einfügen der Schlufvariablen");}
    	if (numberofcolumnswithone < sideconditions || negativeTargetvalue())
    	{
    		auxiliaryproblem = true;
    		calculateAuxiliaryProblem();
    		auxiliaryproblem = false;
    	} else {
    		if (debugmode) {System.out.println("Die erste Phase kann übersprungen werden, keine Hilfsvariablen benötigt!\n");}
    	}
    	if (debugmode) {matrix_out("Tableau vor Start der zweiten Phase, nach dem Löschen der Hilfsvariablen");}
    	gammacalculated = false;
    	fdevidedbyxcalculated = false;
    	calculateSimplex();
    	// Für den Fall, dass das Tableau schon nach dem ersten Durdchlauf optimal war, müssen nun nochmal die Gammawerte und damit auch der Zielwert berechnet werden
    	calculateGammaValues();
    	if (debugmode) {matrix_out("Tableau am Ende. Zielwert:" + gammavalue_targetvalue);}
    	calculated = true;
    }
    
    /**
     * Die Zahl der Spalten, in denen es einen einzigen Wert von "1.0" gibt.
     * @return
     */
    private int getNumberOfColumnsWithOne()
    {
    	int columnsfound = 0;
    	int ones;
    	int onerow = 0;
    	int[] basiscolumns_temp = new int[sideconditions];
    	boolean everythingelsenull = false;
    	for (int a = 0; a < sideconditions; a++)
    	{
    		basiscolumns_temp[a] = -1;
    	}
    		
    	// Spalten durchlaufen
    	for (int a = 0; a < (variables + sideconditions + sideconditions); a++)
    	{
    		ones = 0;
    		everythingelsenull = true;
    		// Zeilen durchlaufen
    		for (int b = 0; b < sideconditions; b++)
    		{
    			if (matrix[b][a] == 1.0)
    			{
    				ones ++;
    				onerow = b;
    			} else if (matrix[b][a] != 0.0) {
    				everythingelsenull = false;
    			}
    		}
    		if (ones == 1 && everythingelsenull)
    		{
    			if (basiscolumns_temp[onerow] == -1)
    			{
    				columnsfound++;
    			}
    			basiscolumns_temp[onerow] = a;
    			
    		}
    	}
    	return columnsfound; 
    }
    
    /**
     * Hilfsfunktion bei negativen Zielwerten
     * @return
     */
    private boolean negativeTargetvalue()
    {
    	boolean negativevalue = false;
    	for (int a = 0; a < sideconditions; a++)
    	{
    		if (targetvalues[a] < 0)
    		{
    			negativevalue = true;
    		}
    	}
    	return negativevalue;
    }
    
    /**
     * Diese Methode ermittelt, in welchen Zeilen Hilfsvariablen eingefügt werden müssen.
     * @return
     */
    private boolean[] getRowsForAuxiliaryVariables()
    {
    	int ones;
    	int onerow = 0;
    	boolean everythingelsenull = false;
    	boolean[] needsauxiliaryvariables = new boolean[sideconditions];
    	for (int a = 0; a < needsauxiliaryvariables.length; a++)
    	{
    		needsauxiliaryvariables[a] = true; 
    	}
    	
    	// Spalten durchlaufen
    	for (int a = 0; a < (variables + slackvariables); a++)
    	{
    		ones = 0;
    		everythingelsenull = true;
    		// Zeilen durchlaufen
    		for (int b = 0; b < sideconditions; b++)
    		{
    			if (matrix[b][a] == 1.0)
    			{
    				ones++;
    				onerow = b;
    			} else if (matrix[b][a] != 0.0) {
    				everythingelsenull = false;
    			}
    		}
    		if (ones == 1 && everythingelsenull)
    		{
    			needsauxiliaryvariables[onerow] = false;
    		}
    	}
    	return needsauxiliaryvariables; 
    }
    
    /**
     * Basisspalten herausfinden.
     */
    private void identifyBasiscolumns()
    {
    	int ones;
    	int onerow = 0;
    	boolean everythingelsenull = false;
    	
    	// Spalten durchlaufen
    	for (int a = 0; a < (variables + sideconditions + sideconditions); a++)
    	{
    		ones = 0;
    		everythingelsenull = true;
    		// Zeilen durchlaufen
    		for (int b = 0; b < sideconditions; b++)
    		{
    			if (matrix[b][a] == 1.0)
    			{
    				ones ++;
    				onerow = b;
    			} else if (matrix[b][a] != 0.0) {
    				everythingelsenull = false;
    			}
    		}
    		if (ones == 1 && everythingelsenull)
    		{
    			basiscolumn[onerow] = a;
    			//System.out.println("Basisspalte: (" + einszeile + ") " + (a + 1));
    		}
    	}
    }
    
    /**
     * Diese Funktion dient dazu, das Hilfsproblem für die Berechnung aufzubereiten.
     * In diesem Zug werden z.B. Hilfsvariablen eingefügt.
     */
    private void calculateAuxiliaryProblem()
    {
    	// Zielfunktion sichern und überall auf 0 setzen!
    	endfunction_backup = endfunction.clone();
    	for (int a = 0; a < endfunction.length; a++)
    	{
    		endfunction[a] = 0.0;
    	}
    	    	
    	// Hilfsvariable einfügen
    	boolean[] needsauxiliaryvariables = getRowsForAuxiliaryVariables();
    	for (int a = 0; a < needsauxiliaryvariables.length; a++)
    	{
    		if (needsauxiliaryvariables[a])
    		{
    			matrix[a][variables + slackvariables + auxiliaryvaribles] = 1.0;
    			endfunction[variables + slackvariables + auxiliaryvaribles] = 1.0;
    			auxiliaryvaribles++;
    		}
    	}
    	// Wenn es keine Hilfsvariablen gibt, benötigt man die zielfunktionswerte der Schkupfvariablen
    	/* if (hilfsvariablen == 0)
    	{
	    	for (int a = variablen; a < (variablen + schlupfvariablen); a++)
	    	{
	    		zielfunktion[a] = 1.0;
	    	} 
    	} */
    	
    	if (debugmode) {matrix_out("Tableau nach Einfügen der Hilfsvariablen");}
    	calculateSimplex();
    	if (debugmode) {matrix_out("Tableau nach der ersten Phase der Berechnung");}
    	deleteAuxiliaryVariable();
    	endfunction = endfunction_backup.clone();
    }
    
    /**
     * Eine einfache Übung: Hilfsvariablen werden gelöscht, indem sie einfahc ignoriert werden.
     */
    private void deleteAuxiliaryVariable()
    {
    	auxiliaryvaribles = 0;
    }
    
    /**
     * Gamma-Werte berechnen
     */
    private void calculateGammaValues()
    {
		for (int a = 0; a < (variables + slackvariables + auxiliaryvaribles); a++)
		{
			gammavalues[a] = 0;
	    	for (int b = 0; b < sideconditions; b++)
	    	{
    			gammavalues[a] += endfunction[basiscolumn[b]] * matrix[b][a];
    		}
	    	gammavalues[a] -= endfunction[a];
	    	//System.out.println("Gamma " + (a + 1) + ": " + gammawerte[a]);
    	}
		
		gammavalue_targetvalue = 0;
		for (int b = 0; b < sideconditions; b++)
		{
			gammavalue_targetvalue += endfunction[basiscolumn[b]] * targetvalues[b];
		}
		
    	gammacalculated = true;
    }
    
    /**
     * Die jeweilige Pivot-Spalte ermitteln
     * @param choise
     */
    private void getPivotcolumn(int choise)
    {
    	gammasequenceentries = 0 ;
    	for (int a = 0; a < (variables + slackvariables + auxiliaryvaribles); a++)
    	{
    		gammasequence[a] = 0;
    	}
    	
    	pivotcolumn = -1;
    	for (int a = 0; a < (variables + slackvariables + auxiliaryvaribles); a++)
    	{
    		if (minimize || auxiliaryproblem)
    		{
	    		if (gammavalues[a] > 0)
	    		{
					if (a > 0)
					{
						insertGammaTable(a);
						possiblepivotelements++;
					} else {
						gammasequence[0] = a;
						possiblepivotelements = 1;
						equalgammavalues = false;
					}
	    		}
    		} else {
	    		if (gammavalues[a] < 0)
	    		{
					if (a > 0)
					{
						insertGammaTable(a);
						possiblepivotelements++;
					} else {
						gammasequence[0] = a;
						possiblepivotelements = 1;
						equalgammavalues = false;
					}
	    		}
    		}
    	}
    }
    
    /**
     * Auxiliary Function for calculating the gamma values
     * @param gammacolumn
     */
    private void insertGammaTable(int gammacolumn)
    {
    	//if (debugmode) {System.out.println("g: " + gammaspalte + " " +gammawerte[gammaspalte]);}
    	int a = 0;
    	if (minimize || auxiliaryproblem)
    	{
    		// passende Position suchen
    		while (a < (gammavalues.length - 1) && gammavalues[gammacolumn] <= gammavalues[gammasequence[a]] && a <= gammasequenceentries)
    		{
    			//if (debugmode) {System.out.println(a + " " + gammawerte[gammaspalte] + " " + gammawerte[gammareihenfolge[a]]);}
    			a++;
    		}
    	}  else {
    		// passende Position suchen
    		while (a < (gammavalues.length - 1) && gammavalues[gammacolumn] >= gammavalues[gammasequence[a]] && a <= gammasequenceentries)
    		{
    			a++;
    		}
    	}
    	    	
		// alles eins runter verschieben
		for (int b = (gammasequence.length - 1); b > a; b--)
		{
			gammasequence[b] = gammasequence[b - 1];
		}
		// einfügen
		gammasequence[a] = gammacolumn;
		gammasequenceentries++;
		
		if (a >= 1 && gammavalues[gammasequence[a]] == gammavalues[gammasequence[a - 1]])
		{
			equalgammavalues = true;
		} else {
			equalgammavalues = false;
		}
    }
    
    /**
     * calculates the "f diveded by x"-values
     */
    private void calculatefDividedByxValues(int choise)
    {
    	pivotrow = -1;
    	for (int a = 0; a < sideconditions; a++)
    	{
    		if (matrix[a][pivotcolumn] == 0)
    		{
    			fdevidedbyxvalues[a] = 0;
    		} else {
    			fdevidedbyxvalues[a] = targetvalues[a] / matrix[a][pivotcolumn];
    		}
    		
			if (fdevidedbyxvalues[a] == 0.0 && matrix[a][pivotcolumn] == 1.0)
			{
				pivotrow = a;
			}
    		
    		if (pivotcolumn < variables + slackvariables)
    		{
	    		if (fdevidedbyxvalues[a] > 0)
	    		{
	    			if (pivotrow == -1)
	    			{
	    				pivotrow = a;
	    			} else if (fdevidedbyxvalues[a] < fdevidedbyxvalues[pivotrow]) {
	    				pivotrow = a;
	    			}
	    		} else if (fdevidedbyxvalues[a] == 0 && possiblepivotelements == 1) {
	    			if (matrix[a][pivotcolumn] == 1.0)
	    			{
	    				pivotrow = a;
	    			}
	    		}
    		} 
    	}
    	fdevidedbyxcalculated = true;
    	
    	
    	try {
	    	if (equalgammavalues && choise >= 1)
	    	{
	    		if (fdevidedbyxvalues[pivotrow] < targetvalues[gammasequence[choise - 1]] / matrix[gammasequence[choise - 1]][pivotrow])
	             {
	    			pivotcolumn = gammasequence[choise - 1];
	    			if (debugmode) {System.out.println("PivotSpalte verbessert (" + (choise - 1) + ")!");}
	             }
	    		
		    	if (gammavalues[gammasequence[choise - 1]] == gammavalues[gammasequence[choise - 2]])
		    	{
		    		equalgammavalues = true;
		    		calculatefDividedByxValues(choise - 1);
		    	} else {
		    		equalgammavalues = false;
		    	}
	    	}
    	} catch (Exception e) {
	    	e.printStackTrace();	
	    }
    }
    
    /**
     * devides the entire row by a value
     */
    private void devideRow()
    {
    	double faktor = matrix[pivotrow][pivotcolumn];
    	for (int a = 0; a < (variables + slackvariables + auxiliaryvaribles); a++)
    	{
    		matrix[pivotrow][a] /= faktor ;
    	}
    	targetvalues[pivotrow] /= faktor;
    }
    
    /**
     * change specific row
     * @param row
     */
    private void changerow(int row)
    {
    	// für den Faktor ist es wichtig, dass der Pivotwert vorher zu 1.0 geworden ist. Dann ergibt er sich einfach als der Wert, der zu 0.0 werden muss!
    	double faktor = matrix[row][pivotcolumn];
    	for (int a = 0; a < (variables + slackvariables + auxiliaryvaribles); a++)
    	{
    		matrix[row][a] -=  (matrix[pivotrow][a] * faktor);
    	}
    	targetvalues[row] -= (targetvalues[pivotrow] * faktor);
    }
    
    /**
     * calculates the simplex itself
     */
    private void calculateSimplex()
    {
    	identifyBasiscolumns();
    	calculateGammaValues();
    	// solange durchlaufen, bis ein abbruchkriterium erfüllt wurde!
    	while (true)
    	{
    		// Pivot-Spalte bei Phase 2
    		if (!auxiliaryproblem)
    		{
	    		int wahl = 0;
	    		pivotrow = -1;
	    		int maximalwahl = 999999999;
	    		while (pivotrow == -1 && wahl <= gammasequenceentries && wahl < maximalwahl)
	    		{
	    			if (debugmode && wahl != 0)
	    			{
	    				System.out.println("Keine passende Pivotzeile gefunden, Versuche die nächst besser Pivotspalte (" + (wahl + 1) + ". Wahl)...");
	    			}
	    			
			    	if (wahl == 0)
			    	{
			    		getPivotcolumn(wahl);
			    	}
			    	
			    	if (((minimize || auxiliaryproblem) && gammavalues[gammasequence[wahl]] > 0) || (!minimize && !auxiliaryproblem && gammavalues[gammasequence[wahl]] < 0))
			    	{
			    		pivotcolumn = gammasequence[wahl];
			    	} else {
			    		pivotcolumn = -1;
			    	}
			    	
			    	// Falls es keine weitere pivotSpalte mehr gibt, bricht das Verfahren ab.
			    	if (pivotcolumn == -1)
			    	{
			    		if (debugmode) {System.out.println("Kann keine Pivot-Spalte identifizieren: Abbruch.");}
			    		return;
			    	}
			    	if (debugmode) {System.out.println("Pivotspalte: " + (pivotcolumn + 1));}
			    	calculatefDividedByxValues(wahl);
			    	wahl++;
	    		}
    		} else {
    			auxiliaryProblemPivotRowColumn();
    			if (debugmode) {System.out.println("Pivotspalte: " + (pivotcolumn + 1));}
    		}
    		// Keine Pivotspalten mehr übrig?
	    	if (pivotrow == -1)
	    	{
	    		if (debugmode) {System.out.println("Alle passenden Pivot-Spalten wurden überprüft: Abbruch.");}
	    		return;
	    	}
	    	
	    	if (debugmode) {System.out.println("Pivotzeile: " + (pivotrow + 1));}
	    	if (debugmode) {matrix_out("Tableau nach Berechnung von Basisspalten, Gammawerten und f-Werten.");}
	    	
	    	// muss die Zeile geteilt werden?
	    	if (matrix[pivotrow][pivotcolumn] != 1.0)
	    	{
	    		devideRow();
	    		if (debugmode) {matrix_out("Tableau nachdem die Pivot-Wert zu 1.0 verändert wurde.");}
	    	}
	    	
	    	// solche Zeilen verändern, die derzeit noch verhinden, dass die PivotSpalte in der Basis ist
	    	for (int a = 0; a < sideconditions; a++)
	    	{
	    		if (matrix[a][pivotcolumn] != 0.0 && a != pivotrow)
	    		{
	    			if (debugmode) {System.out.println("Zeile " + (a + 1) + " wird verändert.");}
	    			changerow(a);
	    		}
	    	}
	    	
	    	// und nun darf die Spalte auch in die Basis!
	    	basiscolumn[pivotrow] = pivotcolumn;
	    	calculateGammaValues();
	    	if (debugmode) {matrix_out("Tableau nach dem Durchlauf.");}
	    	
	    	// Zuletzt noch die Iterationen zählen
	    	if (auxiliaryproblem)
	    	{
	    		iterations_auxiliaryproblem++;
	    	} else {
	    		iterations++;
	    	}
    	}
    }
    
    private void auxiliaryProblemPivotRowColumn()
    {
    	pivotcolumn = -1;
    	pivotrow = -1;
    	double bisherigerzielwert = 0;
    	for (int a = 0; a < sideconditions; a++)
    	{
    		if ((auxiliaryvaribles == 0 && targetvalues[a] < 0 && targetvalues[a] < bisherigerzielwert) || (auxiliaryvaribles > 0 && targetvalues[a] > 0 && targetvalues[a] > bisherigerzielwert))
    		{
    			for (int b = 0; b < variables; b++)
    			{
    				if ((auxiliaryvaribles == 0 && matrix[a][b] < 0) || (auxiliaryvaribles > 0 && matrix[a][b] > 0))
    				{
    					// Vorsicht! Um endlosschleifen zu vermeiden, dürfen nur solche Elemente zum Pivotelement werden, die nich ohnehin schon eine Basisspalte darstellen
    					// Gleichzeitig darf keine PivotSpalte mehr gefunden werden, wenn bei neinem Hilfsprobkem mit HIlfsvariablen letztere nicht mehr in der Basis sind.
    					boolean inbasis = false;
    					boolean HilfsvariableninBasis = false;
    					for (int c = 0; c < sideconditions; c++)
    					{
    						if (b == basiscolumn[c])
    						{
    							inbasis = true;
    						}
    						if (basiscolumn[c] >= (variables + slackvariables))
    						{
    							HilfsvariableninBasis = true;
    						}
    						
    					}
    					if (!inbasis && (auxiliaryvaribles == 0 || HilfsvariableninBasis))
    					{
	    					pivotcolumn = b;
	    					pivotrow = a;
    					}
    				}
    			}
    		}
    	}
    	return;
    }
    
    // Rounds with three decimal places
    private double round(double value)
    {
    	return (Math.round(value * 1000.) / 1000.);
    }
    
    /**
     * Rounds with many decimal places
     * @param value
     * @return
     */
    private double roundlong(double value)
    {
    	return (Math.round(value * 100000.) / 100000.);
    }
    
    /**
     * Ausgabe zu Debug-Zwecken. Die aktuelle Matrix wird in einer gut lesbaren ASCI-Form in die Konsole geschrieben.
     * @param text
     */
    private void matrix_out(String text)
    {
    	String out = "\n" + text + "\n";
    	int maxa = sideconditions;
    	int maxb = variables + slackvariables + auxiliaryvaribles;
    	
    	out += " \t|";
    	for (int b = 0; b < (maxb); b++)
    	{
    		out += round(endfunction[b]) + "\t";
    	}
    	out += "| \t| \t\n";
    	
    	out += " \t|";
    	for (int b = 0; b < (maxb); b++)
    	{
    		out += (b + 1) + "\t";
    	}
    	out += "| \t| \t\n";
    	
    	out += "----\t|";
    	for (int b = 0; b < maxb; b++)
    	{
    		out += "----\t";
    	}
    	out += "----\t|----\t\n";
    	
    	for (int a = 0; a < maxa; a++)
    	{
    		if (basiscolumn[a] != -1)
    		{
    			out += (basiscolumn[a] + 1) + "\t";
    		} else {
    			out += " \t";
    		}
    		out += "|";
    		for (int b = 0; b < maxb; b++)
    		{
    			out += round(matrix[a][b]) + "\t";				
    		}
    		out += "|" + round(targetvalues[a]) + "\t";
    		out += "|";
    		if (fdevidedbyxcalculated)
    		{
    			out += round(fdevidedbyxvalues[a]);
    		} else {
    			out += " ";
    		}
    		out += "\t";
    		out += "\n";
    	}
    	
    	out += "----\t|";
    	for (int b = 0; b < maxb; b++)
    	{
    		out += "----\t";
    	}
    	out += "----\t|----\t\n";
    	
    	out += " \t|";
    	for (int b = 0; b < maxb; b++)
    	{
    		if (gammacalculated)
    		{
    			out += round(gammavalues[b]) + "\t";
    		} else {
    			out += " \t";
    		}
    	}
    	out += "|";
    	if (gammacalculated)
    	{
    		out += round(gammavalue_targetvalue);
    	} else {
    		out += " ";
    	}
    	out += "\t| \t\n";
    	
    	System.out.println(out);
    }
}