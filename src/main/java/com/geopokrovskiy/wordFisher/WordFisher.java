package com.geopokrovskiy.wordFisher;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

public class WordFisher {

	// Please note these variables. they are the state of the object.
	public Map<String, Long> vocabulary;
	public List<String> stopwords; // User ArrayList for initialization
	private String inputTextFile;
	private String stopwordsFile;

	public WordFisher(String inputTextFile, String stopwordsFile) throws IOException {
		this.inputTextFile = inputTextFile;
		this.stopwordsFile = stopwordsFile;

		buildVocabulary();
		getStopwords();
	}

	/**
	 * This function is doing something
	 * */
	public void buildVocabulary() throws IOException {
		// TODO: load in each word from inputTextFile into the vocabulary.
		// By the end of this method, vocabulary should map each word to the number of
		// times it occurs in inputTextFile.
		// Therefore, as you iterate over words, increase the value that the word maps
		// to in vocabulary by 1.
		// If it's not in the vocabulary, then add it with an occurrence of 1.
		// Use getStopwords as an example of reading from files.

		List<String> vocabularyList = Files.readAllLines(Path.of(this.inputTextFile));
		this.vocabulary = Arrays.stream(vocabularyList.stream().
						collect(Collectors.joining()).
						toLowerCase().
						replaceAll("[^a-zA-Z0-9 ]", "").
						split("\\s+")).
				collect(Collectors.groupingBy(x -> x, Collectors.counting()));
		this.vocabulary.remove(" ");
	}

	public void getStopwords() throws IOException{
		this.stopwords = Files.readAllLines(Path.of(this.stopwordsFile));
	}

	public int getWordCount() {
		// TODO: Return the total number of words in inputTextFile.
		int wordCount = 0;
		for(var entry : vocabulary.entrySet()){
			wordCount += entry.getValue();
		}
		return wordCount;
	}

	public int getNumUniqueWords() {
		// TODO: Return the number of unique words.
		// This should be the same as the number of keys in vocabulary.
		return this.vocabulary.size();
	}

	public int getFrequency(String word) {
		
		// TODO: Return the number of times word occurs. 
		// (Should be one simple line of code.)
		// Think about what vocabulary stores.
		if(vocabulary.containsKey(word)){
			return Math.toIntExact(vocabulary.get(word));
		}
		return -1;
	}

	public void pruneVocabulary() {
		// TODO: remove stopwords from the vocabulary.
		for(var word : this.stopwords){
			if(this.vocabulary.containsKey(word)){
				this.vocabulary.remove(word);
			}
		}
	}

	public List<String> getTopWords(int n) {
		// TODO: get the top n words.
		return this.vocabulary.entrySet().
				stream().sorted( (x, y) -> -Long.compare(x.getValue(), y.getValue())).
				limit(n).
				map(x -> x.getKey()).
				collect(Collectors.toList());
	}

	/**
	 *
	 * @param n this is
	 * @param other
	 * @return
	 */
	public List<String> commonPopularWords(int n, WordFisher other) {
		// TODO: get the common popular words.
		List<String> thisTop = this.getTopWords(this.vocabulary.size());
		List<String> otherTop = other.getTopWords(other.vocabulary.size());
		thisTop.retainAll(otherTop);
		otherTop.retainAll(thisTop);
		return thisTop.stream().limit(n).toList();
	}
}
