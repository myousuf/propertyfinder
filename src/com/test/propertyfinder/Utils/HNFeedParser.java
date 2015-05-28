package com.test.propertyfinder.Utils;

//import com.manuelmaly.hn.App;
//import com.manuelmaly.hn.Settings;
//import com.manuelmaly.hn.model.HNFeed;
//import com.manuelmaly.hn.model.HNPost;
//import com.manuelmaly.hn.util.HNHelper;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.test.propertyfinder.Model.PFFeed;
import com.test.propertyfinder.Model.PFProperty;

import java.util.ArrayList;

public class HNFeedParser extends BaseJSONParser<PFFeed> {

	@Override
	public PFFeed parseDocument(Element doc) throws Exception {

		if (doc == null)
			return new PFFeed();

		String currentUser = Settings.getUserName(App.getInstance());

		ArrayList<PFProperty> posts = new ArrayList<PFProperty>();

		// clumsy, but hopefully stable query - first element retrieved is the
		// top table, we have to skip that:
		Elements tableRows = doc.select("table tr table tr");
		tableRows.remove(0);

		Elements nextPageURLElements = tableRows.select("a:matches(^More$)");

		// In case there are multiple "More" elements, select only the one which
		// is a relative link:
		if (nextPageURLElements.size() > 1) {
			nextPageURLElements = nextPageURLElements.select("a[href^=/]");
		}

		String nextPageURL = null;
		// if (nextPageURLElements.size() > 0)
		// nextPageURL = HNHelper.resolveRelativeHNURL(nextPageURLElements
		// .attr("href"));

		String url = null;
		String title = null;
		String author = null;
		int commentsCount = 0;
		int points = 0;
		String urlDomain = null;
		String postID = null;
		String upvoteURL = null;

		boolean endParsing = false;
		for (int row = 0; row < tableRows.size(); row++) {
			int rowInPost = row % 3;
			Element rowElement = tableRows.get(row);

			switch (rowInPost) {
			case 0:
				Element e1 = rowElement.select("tr > td:eq(2) > a").first();
				if (e1 == null) {
					endParsing = true;
					break;
				}

				// title = e1.text();
				// url = HNHelper.resolveRelativeHNURL(e1.attr("href"));
				// urlDomain = getDomainName(url);
				//
				// Element e4 = rowElement.select("tr > td:eq(1) a").first();
				// if (e4 != null) {
				// upvoteURL = e4.attr("href");
				// if (!upvoteURL.contains("auth=")) // HN changed
				// // authentication
				// upvoteURL = null;
				// //else
				// // upvoteURL = HNHelper.resolveRelativeHNURL(upvoteURL);
				// }
				break;
			case 1:

				// points = getIntValueFollowedBySuffix(
				// rowElement.select("tr > td:eq(1) > span").text(), " p");
				// author = rowElement.select("tr > td:eq(1) > a[href*=user]")
				// .text();
				// Element e2 =
				// rowElement.select("tr > td:eq(1) > a[href*=item]")
				// .last(); // assuming the the last link is the comments
				// // link
				// if (e2 != null) {
				// commentsCount = getIntValueFollowedBySuffix(e2.text(), " c");
				// if (commentsCount == BaseHTMLParser.UNDEFINED
				// && e2.text().contains("discuss"))
				// commentsCount = 0;
				// postID = getStringValuePrefixedByPrefix(e2.attr("href"),
				// "id=");
				// } else
				// commentsCount = BaseHTMLParser.UNDEFINED;

				// posts.add(new PFProperty(url, title, urlDomain, author,
				// postID,
				// commentsCount, points, upvoteURL));

				break;
			default:
				break;
			}

			if (endParsing)
				break;
		}

		return new PFFeed(posts, nextPageURL, Settings.getUserName(App
				.getInstance()));
	}

	@Override
	public PFFeed parseJSON(JSONObject doc) throws Exception {
		// TODO Auto-generated method stub

		String nextPageURL = "";
		ArrayList<PFProperty> posts = new ArrayList<PFProperty>();
		JSONArray jarray = doc.getJSONArray("res");

		for (int i = 0; i < jarray.length(); i++) {

			JSONObject obj = jarray.getJSONObject(i);

			// {"id":2733688,"category_id":1,"title":"Whole Building, - bed",
			// "thumbnail":"http://pfae-a.akamaihd.net/property/2733688-505450.jpg?v=1429109494&w=222&h=176&x=64fcd2",
			// "thumbnail_big":"http://pfae-a.akamaihd.net/property/2733688-505450.jpg?v=1429109494&w=1136&h=640&x=cc503e",
			// "image_count":2,"price":"110,000,000","currency":"AED","featured":"0","location":"Dubai, Al Sufouh","area":"130123",
			// "poa":"0","visited":false,"lat":25.11894,"long":55.18355}

			int category_id = 0, image_count = 0;
			float lat = 0.0f, lang = 0.0f;
			String price = "";
			boolean visited = false;
			String title = null, thumbnail = null, currency = null, featured = null, location = null, area = null, poa = null;

			// if (obj.has(Const.ID)) {
			//
			// }

			if (obj.has(Const.CATEGORY_ID)) {
				category_id = obj.getInt(Const.CATEGORY_ID);
			}

			if (obj.has(Const.TITLE)) {
				title = obj.getString(Const.TITLE);
			}

			if (obj.has(Const.THUMBNAIL)) {
				thumbnail = obj.getString(Const.THUMBNAIL);
			}

			// if (obj.has(Const.THUMBNAIL_BIG)) {
			//
			// }

			if (obj.has(Const.IMAGE_COUNT)) {
				image_count = obj.getInt(Const.IMAGE_COUNT);
			}

			if (obj.has(Const.PRICE)) {
				price = obj.getString(Const.PRICE);
			}

			if (obj.has(Const.CURRENCY)) {
				currency = obj.getString(Const.CURRENCY);
			}

			if (obj.has(Const.FEATURED)) {
				featured = obj.getString(Const.FEATURED);
			}

			if (obj.has(Const.LOCATION)) {
				location = obj.getString(Const.LOCATION);
			}
			if (obj.has(Const.AREA)) {
				area = obj.getString(Const.AREA);
			}

			if (obj.has(Const.POA)) {
				poa = obj.getString(Const.POA);
			}

			if (obj.has(Const.LAT)) {
				lat = obj.getLong(Const.LAT);
			}

			if (obj.has(Const.LONG)) {
				lang = obj.getLong(Const.LONG);
			}

			posts.add(new PFProperty(category_id, title, thumbnail,
					image_count, price, currency, featured, location, area,
					poa, visited, lat, lang));

		}

		System.out.println(jarray.length() + " jsonarray count");

		return new PFFeed(posts, nextPageURL, Settings.getUserName(App
				.getInstance()));
	}

}
