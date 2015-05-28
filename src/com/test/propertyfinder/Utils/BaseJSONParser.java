package com.test.propertyfinder.Utils;

import org.json.JSONObject;

import org.jsoup.nodes.Element;

public abstract class BaseJSONParser<T> {

	public static final int UNDEFINED = -1;

	public T parse(String input) throws Exception {

		return parseJSON(new JSONObject(input));

	}

	public abstract T parseDocument(Element doc) throws Exception;

	public abstract T parseJSON(JSONObject doc) throws Exception;

}
