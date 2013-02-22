/* Copyright (c) 2013, the original author or authors.

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; version 3 of the License.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, see http://www.gnu.org/licenses. */

package com.github.abhijitsarkar.moviedatabase.api.client.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.github.abhijitsarkar.moviedatabase.api.client.ClientType;
import com.github.abhijitsarkar.moviedatabase.api.client.config.ClientConfig;

/**
 * Creates a concrete API client instance that the client can make API calls on.
 * All concrete client instances are Singletons.
 * 
 * @author Abhijit Sarkar
 * 
 */
public class ClientFactory {
	private static Map<ClientType, Client> clients;
	private static ClientFactory factory;
	private static AnnotationConfigApplicationContext ctx;

	private ClientFactory() {
		ctx = new AnnotationConfigApplicationContext();
		ctx.register(ClientConfig.class);
		ctx.refresh();

		clients = new HashMap<ClientType, Client>();
	}

	/**
	 * 
	 * @return A ClientFactory instance
	 */
	public static final ClientFactory getInstance() {
		if (factory == null)
			factory = new ClientFactory();

		return factory;
	}

	/**
	 * 
	 * @param type
	 *            Client type.
	 * @return A concrete API client instance.
	 */
	public final Client getClient(ClientType type) {
		if (clients.get(type) == null) {
			switch (type) {
			case THE_OMDB_API:
				clients.put(type, ctx.getBean("theOmdbApiClient", Client.class));
				break;
			case DEAN_CLATWORTHY:
				clients.put(type,
						ctx.getBean("deanClatworthyClient", Client.class));
				break;
			}
		}
		return clients.get(type);
	}
}
