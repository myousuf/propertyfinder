package com.test.propertyfinder.Utils;

import android.os.AsyncTask;
import android.util.Log;

//import com.manuelmaly.hn.App;
//import com.manuelmaly.hn.model.HNCommentTreeNode;
//import com.manuelmaly.hn.model.PFFeed;
//import com.manuelmaly.hn.model.HNPostComments;

import com.test.propertyfinder.Model.PFFeed;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.List;

public class FileUtil {

	private static final String LAST_HNFEED_FILENAME = "lastHNFeed";
	private static final String LAST_HNPOSTCOMMENTS_FILENAME_PREFIX = "lastHNPostComments";
	private static final String TAG = "FileUtil";

	public abstract static class GetLastHNFeedTask extends
			AsyncTask<Void, Void, PFFeed> {
		@Override
		protected PFFeed doInBackground(Void... params) {
			return getLastHNFeed();
		}
	}

	/*
	 * Returns null if no last feed was found or could not be parsed.
	 */
	private static PFFeed getLastHNFeed() {
		ObjectInputStream obj = null;
		try {
			obj = new ObjectInputStream(new FileInputStream(
					getLastHNFeedFilePath()));
			Object rawHNFeed = obj.readObject();
			if (rawHNFeed instanceof PFFeed)
				return (PFFeed) rawHNFeed;
		} catch (Exception e) {
			Log.e(TAG, "Could not get last PFFeed from file :(", e);
		} finally {
			if (obj != null) {
				try {
					obj.close();
				} catch (IOException e) {
					Log.e(TAG, "Couldn't close last NH feed file :(", e);
				}
			}
		}
		return null;
	}

	public static void setLastHNFeed(final PFFeed PFFeed) {
		Run.inBackground(new Runnable() {
			public void run() {
				ObjectOutputStream os = null;
				try {
					os = new ObjectOutputStream(new FileOutputStream(
							getLastHNFeedFilePath()));
					os.writeObject(PFFeed);
				} catch (Exception e) {
					Log.e(TAG, "Could not save last PFFeed to file :(", e);
				} finally {
					if (os != null) {
						try {
							os.close();
						} catch (IOException e) {
							Log.e(TAG, "Couldn't close last NH feed file :(", e);
						}
					}
				}
			}
		});
	}

	private static String getLastHNFeedFilePath() {
		File dataDir = App.getInstance().getFilesDir();
		return dataDir.getAbsolutePath() + File.pathSeparator
				+ LAST_HNFEED_FILENAME;
	}

	public static void CopyStream(InputStream is, OutputStream os) {
		final int buffer_size = 1024;
		try {
			byte[] bytes = new byte[buffer_size];
			for (;;) {
				int count = is.read(bytes, 0, buffer_size);
				if (count == -1)
					break;
				os.write(bytes, 0, count);
			}
		} catch (Exception ex) {
		}
	}

}
