/*
 * File: NameSurferEntry.java
 * --------------------------
 * This class represents a single entry in the database.  Each
 * NameSurferEntry contains a name and a list giving the popularity
 * of that name for each decade stretching back to 1900.
 */

import acm.util.*;
import java.util.*;

public class NameSurferEntry implements NameSurferConstants {
	private String Name;
	private ArrayList<Integer> Rank= new ArrayList<Integer>();

/* Constructor: NameSurferEntry(line) */
/**
 * Creates a new NameSurferEntry from a data line as it appears
 * in the data file.  Each line begins with the name, which is
 * followed by integers giving the rank of that name for each
 * decade.
 */
	public NameSurferEntry(String line) {
		int nextRank=0;
		StringTokenizer token=new StringTokenizer(line);
		Name=token.nextToken();
		while(token.hasMoreTokens()) {
			nextRank=Integer.parseInt(token.nextToken());
			Rank.add(nextRank);
			
		}
		
	}

/* Method: getName() */
/**
 * Returns the name associated with this entry.
 */
	public String getName() {
		
		return Name;
	}

/* Method: getRank(decade) */
/**
 * Returns the rank associated with an entry for a particular
 * decade.  The decade value is an integer indicating how many
 * decades have passed since the first year in the database,
 * which is given by the constant START_DECADE.  If a name does
 * not appear in a decade, the rank value is 0.
 */
	public int getRank(int decade) {
		
		return Rank.get(decade);
	}

/* Method: toString() */
/**
 * Returns a string that makes it easy to see the value of a
 * NameSurferEntry.
 */
	public String toString() {
		String resultLine=Name+" ["+Rank.get(0);
		for(int i=1; i<NDECADES; i++) {
			resultLine+=" "+Rank.get(i);
		}
		resultLine+= "]";
		return resultLine;
	}
}

