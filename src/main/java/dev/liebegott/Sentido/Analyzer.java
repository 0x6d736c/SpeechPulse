package dev.liebegott.Sentido;

import edu.stanford.nlp.simple.*;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.util.ArrayList;

/**
 * A class to analyze speeches for their sentiments.
 * @author Michael Liebegott
 *
 */
public class Analyzer {
	/*
	 * PRIVATE MEMBER VARIABLES
	 */
	private ArrayList<Integer> sentimentValues;	//Integer equivalent of the SentimentClass enums, normalized.
	private String speech;						//Speech as a String value.
	DescriptiveStatistics statistics;
	
	/*
	 * CONSTRUCTORS
	 */
	/**
	 * Constructs an Analyzer object using an empty speech string.
	 */
	Analyzer() {
		this("");
	}
	
	/**
	 * Constructs an Analyzer object using the provided speech String.
	 * @param speech - the speech String to be analyzed.
	 */
	Analyzer(String speech) {
		this.sentimentValues = new ArrayList<>();
		this.speech = speech;

	}
	
	/*
	 * PRIMARY METHODS
	 */
	/**
	 * Analyzes the speech and pushes the normalized sentiment values to the sentimentValues ArrayList.
	 */
	public ArrayList<Integer> analyze() {
		//Clears the sentimentValues ArrayList to perform a new analysis.
		if (this.sentimentValues.size() > 0) {
			this.sentimentValues = new ArrayList<>();
		}
		
		Document speechDocument = new Document(this.speech);	//Create a CoreNLP Document object with speech as input.
		
		for (Sentence sentence : speechDocument.sentences()) {
			int rawSentimentValue = sentence.sentiment().ordinal();	//Captures the raw, unnormalized sentiment value from sentence.
			this.sentimentValues.add(normalize(rawSentimentValue));	//Normalizes the sentiment value and pushes to ArrayList.
		}
		
		return this.sentimentValues;
	}
	
	/**
	 * Provides summary statistics for the sentiment values array.
	 */
	public DescriptiveStatistics generateStatistics() {
		double[] sentimentValues = new double[this.sentimentValues.size()];
		
		//Casts each value in the sentimentValues array list to a double for processing with StatUtils package.
		for (int i = 0; i < sentimentValues.length; i++) {
			sentimentValues[i] = (double) this.sentimentValues.get(i);
		}
		
		this.statistics = new DescriptiveStatistics(sentimentValues);
		return this.statistics;
	}
	
	
	/**
	 * Returns the sentiment value -2 to provide for true negatives (STRONG negatives = -2; WEAK = -1, NEUTRAL = 0; etc.)
	 * @param sentimentValue - the sentiment value to be normalized.
	 * @return sentimentValue - 2
	 */
	private static int normalize(int sentimentValue) {
		return sentimentValue - 2;
	}

	
	/*
	 * GETTERS AND SETTERS
	 */
	/**
	 * @return the speech
	 */
	public String getSpeech() {
		return speech;
	}

	/**
	 * @param speech the speech to set
	 */
	public void setSpeech(String speech) {
		this.speech = speech;
	}

	/**
	 * @return the sentimentValues
	 */
	public ArrayList<Integer> getSentimentValues() {
		return sentimentValues;
	}
	/**
	 * 
	 * @return the statistics for the analyzed speech
	 */
	public DescriptiveStatistics getStatistics() {
		return this.statistics;
	}
}



