package se.iths.plugin.dao;

import com.google.gson.Gson;

public class JsonConverter {

	private Gson gson;

	public JsonConverter() {
		gson = new Gson();
	}

	public byte[] convertToJson(Object object){
		return gson.toJson(object).getBytes();
	}
}