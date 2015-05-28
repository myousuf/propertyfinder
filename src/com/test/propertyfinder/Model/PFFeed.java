package com.test.propertyfinder.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PFFeed implements Serializable {

	private static final long serialVersionUID = -7957577448455303642L;
	private List<PFProperty> mProperties;
	private String mNextPageURL;
	private String mUserAcquiredFor;

	private boolean mLoadedMore;

	public PFFeed() {
		mProperties = new ArrayList<PFProperty>();
	}

	public PFFeed(List<PFProperty> properties, String nextPageURL,
			String userAcquiredFor) {
		mProperties = properties;
		mNextPageURL = nextPageURL;
		mUserAcquiredFor = userAcquiredFor;
	}

	public void addProperty(PFProperty property) {
		mProperties.add(property);
	}

	public List<PFProperty> getProperties() {
		return mProperties;
	}

	public void addProperties(Collection<PFProperty> properties) {
		mProperties.addAll(properties);
	}

	public String getNextPageURL() {
		return "https://www.propertyfinder.ae/mobileapi"; // mNextPageURL;
	}

	public void setNextPageURL(String mNextPageURL) {
		this.mNextPageURL = mNextPageURL;
	}

	public void appendLoadMoreFeed(PFFeed feed) {
		if (feed == null || feed.getProperties() == null)
			return;

		for (PFProperty candidate : feed.getProperties())
			if (!mProperties.contains(candidate))
				mProperties.add(candidate);
		mNextPageURL = feed.getNextPageURL();
	}

	public boolean isLoadedMore() {
		return mLoadedMore;
	}

	public void setLoadedMore(boolean loadedMore) {
		mLoadedMore = loadedMore;
	}

	public String getUserAcquiredFor() {
		return mUserAcquiredFor;
	}
}