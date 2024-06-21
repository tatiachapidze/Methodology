
/*
 * File: HangmanLexicon.java
 * -------------------------
 * This file contains a stub implementation of the HangmanLexicon
 * class that you will reimplement for Part III of the assignment.
 */

import java.io.BufferedReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

import acm.program.ConsoleProgram;

import acm.util.*;

public class HangmanLexicon extends ConsoleProgram {
	private ArrayList<String> words = new ArrayList<String>();

	/** Returns the number of words in the lexicon. */
	public int getWordCount() {
		return words.size();
	}

	/** Returns the word at the specified index. */
	public String getWord(int index) {
		return words.get(index);
	}

	/** reads lines from the file and adds them to Arraylist */
	public HangmanLexicon() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("HangmanLexicon.txt"));
			String word = reader.readLine();
			while (word != null) {
				word = reader.readLine();
				words.add(word);
			}
			reader.close();
		} catch (Exception ex) {
			println("Error" + ex);
		}
	}

}
