package com.github.mkalin.jwsur2.ch2.adages;

import java.util.Random;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.namespace.QName;

import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/")
public class Adages {
	// Add APHORISMS to taste...
	public static final String[] APHORISMS = {
			"What can be shown cannot be said.",
			"If a lion could talk, we could not understand him.",
			"Philosophy is a battle against the bewitchment of our intelligence by means of language.",
			"Ambition is the death of thought.",
			"The limits of my language mean the limits of my world." };

	public Adages() {
	}

	@GET
	@Produces({ MediaType.APPLICATION_XML })
	// could use "application/xml" instead
	public JAXBElement<Adage> getXml() {
		return toXml(createAdage());
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/json")
	public String getJson() {
		return toJson(createAdage());
	}

	@GET
	@Produces({ MediaType.TEXT_PLAIN })
	@Path("/plain")
	public String getPlain() {
		return createAdage().toString();
	}

	// Create an Adage and set the words property, which
	// likewise sets the wordCount property. The adage is
	// randomly selected from the array, APHORISMS.
	private Adage createAdage() {
		Adage adage = new Adage();
		adage.setWords(APHORISMS[new Random().nextInt(APHORISMS.length)]);
		return adage;
	}

	// Java Adage --> XML document
	@XmlElementDecl(namespace = "http://aphorism.adage", name = "adage")
	private JAXBElement<Adage> toXml(Adage adage) {
		return new JAXBElement<Adage>(new QName("adage"), Adage.class, adage);
	}

	// Java Adage --> JSON document
	// Jersey provides automatic conversion to JSON using the Jackson
	// libraries. In this example, the conversion is done manually
	// with the Jackon libraries just to indicate how straightforward it is.
	private String toJson(Adage adage) {
		String json = "If you see this, there's a problem.";
		try {
			json = new ObjectMapper().writeValueAsString(adage);
		} catch (Exception e) {
		}
		return json;
	}
}
