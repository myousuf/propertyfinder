package com.test.propertyfinder.Utils;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class JSONResponseHandler implements ResponseHandler<String> {

	private IAPICommand<String> mCommand;

	public JSONResponseHandler(IAPICommand<String> command, HttpClient client) {
		mCommand = command;
	}

	@Override
	public String handleResponse(HttpResponse response)
			throws ClientProtocolException, IOException {

		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		response.getEntity().writeTo(out);
		final StatusLine statusLine = response.getStatusLine();
		final String responseString = out.toString();
		out.close();
		int statusCode = statusLine.getStatusCode();

		mCommand.responseHandlingFinished(responseString, statusCode);
		return null;
	}

}
