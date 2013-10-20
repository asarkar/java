package com.github.mkalin.jwsur2.ch2.jaxws.aphorism3;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Adages {
	private static CopyOnWriteArrayList<Adage> adages;
	private static AtomicInteger id;

	static {
		String[] aphorisms = {
				"What can be shown cannot be said.",
				"If a lion could talk, we could not understand him.",
				"Philosophy is a battle against the bewitchment of our intelligence by means of language.",
				"Ambition is the death of thought.",
				"The limits of my language mean the limits of my world." };
		adages = new CopyOnWriteArrayList<Adage>();
		id = new AtomicInteger();
		for (String str : aphorisms)
			add(str);
	}

	public static String toPlain() {
		String retval = "";
		int i = 1;
		for (Adage adage : adages)
			retval += adage.toString() + "\n";
		return retval;
	}

	public static Object[] getListAsArray() {
		// Return a read-only copy of the current list.
		return adages.toArray();
	}

	// Support GET one operation.
	public static Adage find(int id) {
		Adage adage = null;
		for (Adage a : adages) {
			if (a.getId() == id) {
				adage = a;
				break;
			}
		}
		return adage;
	}

	// Support POST operation.
	public static void add(String words) {
		int localId = id.incrementAndGet();
		Adage adage = new Adage();
		adage.setWords(words);
		adage.setId(localId);
		adages.add(adage);
	}

	public static void remove(Adage adage) {
		adages.remove(adage);
	}
}
