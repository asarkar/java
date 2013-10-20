package com.github.mkalin.jwsur2.ch2.jaxrs2.predictions3;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/resourcesP")
public class RestfulPrediction extends Application {
	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> set = new HashSet<Class<?>>();
		set.add(PredictionsRS.class);
		return set;
	}
}
