package com.test.propertyfinder.Utils;

import android.app.Application;

public class App extends Application {

	private static App mInstance;
	private static String sortingCriteria = "pa";

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;

	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}

	public static App getInstance() {
		return mInstance;
	}

	public static String getSortingCriteria() {
		return sortingCriteria;
	}

	public static void setSortingCriteria(String sortingCriteria) {
		App.sortingCriteria = sortingCriteria;
	}

}
