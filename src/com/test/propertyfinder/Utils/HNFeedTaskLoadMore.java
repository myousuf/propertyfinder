package com.test.propertyfinder.Utils;

import android.app.Activity;
import android.content.Context;

import com.test.propertyfinder.Model.PFFeed;

public class HNFeedTaskLoadMore extends HNFeedTaskBase {

	private PFFeed mFeedToAttachResultsTo;

	private static HNFeedTaskLoadMore instance;
	public static final String BROADCAST_INTENT_ID = "HNFeedLoadMore";

	private static HNFeedTaskLoadMore getInstance(int taskCode) {
		synchronized (HNFeedTaskLoadMore.class) {
			if (instance == null)
				instance = new HNFeedTaskLoadMore(taskCode);
		}
		return instance;
	}

	private HNFeedTaskLoadMore(int taskCode) {
		super(BROADCAST_INTENT_ID, taskCode);
	}

	@Override
	protected String getFeedURL() {
		return mFeedToAttachResultsTo.getNextPageURL();
	}

	public static void start(Activity activity,
			ITaskFinishedHandler<PFFeed> finishedHandler,
			PFFeed feedToAttachResultsTo, int taskCode) {
		HNFeedTaskLoadMore task = getInstance(taskCode);
		task.setOnFinishedHandler(activity, finishedHandler, PFFeed.class);
		task.setFeedToAttachResultsTo(feedToAttachResultsTo);
		if (task.isRunning())
			task.cancel();
		task.startInBackground();
	}

	public static void stopCurrent(Context applicationContext, int taskCode) {
		getInstance(taskCode).cancel();
	}

	public static boolean isRunning(Context applicationContext, int taskCode) {
		return getInstance(taskCode).isRunning();
	}

	public void setFeedToAttachResultsTo(PFFeed feedToAttachResultsTo) {
		mFeedToAttachResultsTo = feedToAttachResultsTo;
	}

}
