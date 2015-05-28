package com.test.propertyfinder.Runtime;

import com.test.propertyfinder.Model.PFFeed;
import com.test.propertyfinder.Utils.HNFeedTaskBase;
import com.test.propertyfinder.Utils.ITaskFinishedHandler;

import android.app.Activity;
import android.content.Context;

public class PFFeedTaskLoadMore extends HNFeedTaskBase {

	private PFFeed mFeedToAttachResultsTo;

	private static PFFeedTaskLoadMore instance;
	public static final String BROADCAST_INTENT_ID = "PFFeedLoadMore";

	private static PFFeedTaskLoadMore getInstance(int taskCode) {
		synchronized (PFFeedTaskLoadMore.class) {
			if (instance == null)
				instance = new PFFeedTaskLoadMore(taskCode);
		}
		return instance;
	}

	private PFFeedTaskLoadMore(int taskCode) {
		super(BROADCAST_INTENT_ID, taskCode);
	}

	@Override
	protected String getFeedURL() {
		return mFeedToAttachResultsTo.getNextPageURL();
	}

	public static void start(Activity activity,
			ITaskFinishedHandler<PFFeed> finishedHandler,
			PFFeed feedToAttachResultsTo, int taskCode) {
		PFFeedTaskLoadMore task = getInstance(taskCode);
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
