package com.test.propertyfinder.Utils;

import android.util.Log;

import com.test.propertyfinder.Model.*;
import com.test.propertyfinder.Runtime.BaseTask;
import com.test.propertyfinder.Utils.IAPICommand.RequestType;

import java.util.HashMap;

public abstract class HNFeedTaskBase extends BaseTask<PFFeed> {

	static int page = 0;

	public HNFeedTaskBase(String notificationBroadcastIntentID, int taskCode) {
		super(notificationBroadcastIntentID, taskCode);
	}

	@Override
	public CancelableRunnable getTask() {
		return new HNFeedTaskRunnable();
	}

	protected abstract String getFeedURL();

	class HNFeedTaskRunnable extends CancelableRunnable {

		StringDownloadCommand mFeedDownload;

		@Override
		public void run() {

			HashMap<String, String> param = new HashMap<String, String>();
			param.put("page", page + "");
			param.put("order", App.getSortingCriteria());

			mFeedDownload = new StringDownloadCommand(getFeedURL(), param,
					RequestType.GET, false, null, App.getInstance());

			mFeedDownload.run();

			page++;
			System.out.println("page: " + page);

			if (mCancelled)
				mErrorCode = IAPICommand.ERROR_CANCELLED_BY_USER;
			else
				mErrorCode = mFeedDownload.getErrorCode();

			if (!mCancelled && mErrorCode == IAPICommand.ERROR_NONE) {
				HNFeedParser feedParser = new HNFeedParser();
				try {
					mResult = feedParser.parse(mFeedDownload
							.getResponseContent());

					Run.inBackground(new Runnable() {
						public void run() {
							FileUtil.setLastHNFeed(mResult);
						}
					});
				} catch (Exception e) {
					mResult = null;
					ExceptionUtil.sendToGoogleAnalytics(e,
							Const.GAN_ACTION_PARSING);
					Log.e("HNFeedTask", "HNFeed Parser Error :(", e);
				}
			}

			if (mResult == null)
				mResult = new PFFeed();
		}

		@Override
		public void onCancelled() {
			mFeedDownload.cancel();
		}

	}

}
