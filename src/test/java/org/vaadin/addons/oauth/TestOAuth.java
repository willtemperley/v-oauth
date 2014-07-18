package org.vaadin.addons.oauth;


import java.io.FileNotFoundException;
import java.io.FileReader;

import org.vaadin.addons.oauth.OAuthCredentials;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;


public class TestOAuth {

	public static void main(String[] args) throws FileNotFoundException {
		
		Gson g = new Gson();
        JsonReader jsonReader = new JsonReader(new FileReader("google.json"));

		OAuthCredentials gsp = g.fromJson(jsonReader, OAuthCredentials.class);

		System.out.println(gsp);

	}
}
