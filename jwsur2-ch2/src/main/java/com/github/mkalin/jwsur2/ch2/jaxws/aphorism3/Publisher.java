package com.github.mkalin.jwsur2.ch2.jaxws.aphorism3;

import javax.xml.ws.Endpoint;

public class Publisher {
	public static void main(String[] args) {
		int port = 8888;
		String url = "http://localhost:" + port + "/";
		System.out.println("Restfully publishing on port " + port);
		Endpoint.publish(url, new AdagesProvider());
	}
}
