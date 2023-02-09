package com.geopokrovskiy.tester;

import com.geopokrovskiy.wordFisher.WordFisher;

import java.io.IOException;

public class WordFisherTester {
	
	public static void main(String[] args) {

		try {
			WordFisher alice = new WordFisher("texts/carroll-alice.txt", "stopwords.txt");
			WordFisher moby = new WordFisher("texts/moby-dick.txt", "stopwords.txt");
			moby.pruneVocabulary();
			alice.pruneVocabulary();
			System.out.println(alice.commonPopularWords(20, moby));
			System.out.println(moby.commonPopularWords(20, alice));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
