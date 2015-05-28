package com.test.propertyfinder.Model;

import java.io.Serializable;

public class PFProperty implements Serializable {

	int category_id;
	String title;
	String thumbnail;
	int image_count;
	String price;
	String currency;
	String featured;
	String location;
	String area;
	String poa;
	boolean visited;
	float lat;
	float lang;

	public PFProperty(int category_id, String title, String thumbnail,
			int image_count, String price, String currency, String featured,
			String location, String area, String poa, boolean visited,
			float lat, float lang) {

		this.category_id = category_id;
		this.title = title;
		this.thumbnail = thumbnail;
		this.image_count = image_count;
		this.price = price;
		this.currency = currency;
		this.featured = featured;
		this.location = location;
		this.area = area;
		this.poa = poa;
		this.visited = visited;
		this.lat = lat;
		this.lang = lang;

	}

	public int getCategory_id() {
		return category_id;
	}

	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public int getImage_count() {
		return image_count;
	}

	public void setImage_count(int image_count) {
		this.image_count = image_count;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getFeatured() {
		return featured;
	}

	public void setFeatured(String featured) {
		this.featured = featured;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getPoa() {
		return poa;
	}

	public void setPoa(String poa) {
		this.poa = poa;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public float getLat() {
		return lat;
	}

	public void setLat(float lat) {
		this.lat = lat;
	}

	public float getLang() {
		return lang;
	}

	public void setLang(float lang) {
		this.lang = lang;
	}

	// @Override
	// public int hashCode() {
	// final int prime = 31;
	// int result = 1;
	// result = prime * result + ((mAuthor == null) ? 0 : mAuthor.hashCode());
	// result = prime * result + ((mPostID == null) ? 0 : mPostID.hashCode());
	// result = prime * result + ((mURL == null) ? 0 : mURL.hashCode());
	// return result;
	// }
	//
	// @Override
	// public boolean equals(Object obj) {
	// if (this == obj)
	// return true;
	// if (obj == null)
	// return false;
	// if (getClass() != obj.getClass())
	// return false;
	// HNPost other = (HNPost) obj;
	// if (mAuthor == null) {
	// if (other.mAuthor != null)
	// return false;
	// } else if (!mAuthor.equals(other.mAuthor))
	// return false;
	// if (mPostID == null) {
	// if (other.mPostID != null)
	// return false;
	// } else if (!mPostID.equals(other.mPostID))
	// return false;
	// if (mURL == null) {
	// if (other.mURL != null)
	// return false;
	// } else if (!mURL.equals(other.mURL))
	// return false;
	// return true;
	// }
	//

}
