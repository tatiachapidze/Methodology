import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*
 * File: NameSurferDataBase.java
 * -----------------------------
 * This class keeps track of the complete database of names.
 * The constructor reads in the database from a file, and
 * the only public method makes it possible to look up a
 * name and get back the corresponding NameSurferEntry.
 * Names are matched independent of case, so that "Eric"
 * and "ERIC" are the same names.
 */

public class NameSurferDataBase implements NameSurferConstants {
	Map<String, String> map= new HashMap<String, String>();
/* Constructor: NameSurferDataBase(filename) */
/**
 * Creates a new NameSurferDataBase and initializes it using the
 * data in the specified file.  The constructor throws an error
 * exception if the requested file does not exist or if an error
 * occurs as the file is being read.
 */
	public NameSurferDataBase(String filename) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String line =reader.readLine();
			NameSurferEntry entry;
			while (line != null) {
				entry = new NameSurferEntry(line);
				map.put(entry.getName().toUpperCase(), line);
				line=reader.readLine();
				
			}
			reader.close();
		    } catch (Exception e) {				 
		    	e.printStackTrace();
			}
	}
	
	
/* Method: findEntry(name) */
/**
 * Returns the NameSurferEntry associated with this name, if one
 * exists.  If the name does not appear in the database, this
 * method returns null.
 */
	public NameSurferEntry findEntry(String name) {
		String line="";
		if(map.containsKey(name)) {
			line=map.get(name);
		}else {
			return null;
		}
		NameSurferEntry entry=new NameSurferEntry(line);
		return entry;
	}
}

