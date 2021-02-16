package se.iths.plugin.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import se.iths.plugin.model.Contact;

import java.lang.reflect.Type;
import java.util.List;

public class JsonConverter {

	private final Gson gson;

	public JsonConverter() {
		gson = new GsonBuilder().setPrettyPrinting().create();
	}

	public byte[] convertObjectToJson(Object object) {
		return gson.toJson(object).getBytes();
	}

	public byte[] convertListToJson(List<Contact> list) {

		Type listType = new TypeToken<List<Contact>>() {
		}.getType();

		return gson.toJson(list, listType).getBytes();
	}
}