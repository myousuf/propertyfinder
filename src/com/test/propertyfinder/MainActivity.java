package com.test.propertyfinder;

import java.util.ArrayList;

import com.test.propertyfinder.Model.PFFeed;
import com.test.propertyfinder.Model.PFProperty;
import com.test.propertyfinder.Utils.App;
import com.test.propertyfinder.Utils.Const;
import com.test.propertyfinder.Utils.ITaskFinishedHandler;
import com.test.propertyfinder.Utils.ImageLoader;
import com.test.propertyfinder.Runtime.PFFeedTaskLoadMore;
import com.test.propertyfinder.Utils.HNFeedTaskLoadMore;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements
		ITaskFinishedHandler<PFFeed> {

	ListView mPropertiesList;
	boolean mShouldShowRefreshing = true;
	// LayoutInflater mInflater;
	PFFeed mFeed;

	PostsAdapter mPostsListAdapter;

	private static final int TASKCODE_LOAD_FEED = 10;
	private static final int TASKCODE_LOAD_MORE_PROPERTIES = 20;

	private Parcelable mListState = null;
	private static final String LIST_STATE = "listState";
	private ImageLoader imageLoader;
	LayoutInflater mInflater;
	TextView tvSortingInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		mPropertiesList = (ListView) findViewById(R.id.main_list);
		mFeed = new PFFeed(new ArrayList<PFProperty>(), null, "");
		mPostsListAdapter = new PostsAdapter();
		mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		imageLoader = new ImageLoader(getApplicationContext());
		tvSortingInfo = (TextView) findViewById(R.id.tvSortingInfo);

		mPropertiesList.setAdapter(mPostsListAdapter);

		if (mListState != null) {
			mPropertiesList.onRestoreInstanceState(mListState);
		}
		mListState = null;

		startFeedLoading();
		setSortingText("Price Asc");

	}

	private void setSortingText(String text) {
		tvSortingInfo.setText("Current sorting order: " + text);
	}

	private void startFeedLoading() {

		mShouldShowRefreshing = true;
		com.test.propertyfinder.Utils.HNFeedTaskMainFeed.startOrReattach(this,
				this, TASKCODE_LOAD_FEED);
		supportInvalidateOptionsMenu();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {

		MenuItem item = menu.findItem(R.id.menu_refresh);
		//
		if (!mShouldShowRefreshing) {
			MenuItemCompat.setActionView(item, null);
		} else {

			View v = mInflater.inflate(R.layout.refresh_icon, null);
			MenuItemCompat.setActionView(item, v);
		}

		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_price:

			if (App.getSortingCriteria().equals(Const.SORTING_PRICE_ASEC)) {
				App.setSortingCriteria(Const.SORTING_PRICE_DESC);
				setSortingText("Price Desc");
			} else {
				App.setSortingCriteria(Const.SORTING_PRICE_ASEC);
				setSortingText("Price Asc");
			}

			startFeedLoading(); // sort by price
			return true;
		case R.id.menu_bedroom:

			if (App.getSortingCriteria().equals(Const.SORTING_BED_ASEC)) {
				App.setSortingCriteria(Const.SORTING_BED_DESC);
				setSortingText("Bedroom Asc");
			} else {
				App.setSortingCriteria(Const.SORTING_BED_ASEC);
				setSortingText("Bedroom Desc");
			}

			startFeedLoading(); // sort by bedroom
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		// restore vertical scrolling position if applicable

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	class PostsAdapter extends BaseAdapter {

		private static final int VIEWTYPE_PROPERTY = 0;
		private static final int VIEWTYPE_LOADMORE = 1;

		@Override
		public int getCount() {
			int posts = mFeed.getProperties().size();
			if (posts == 0) {
				return 0;
			} else {
				return posts + (mFeed.isLoadedMore() ? 0 : 1);
			}
		}

		@Override
		public PFProperty getItem(int position) {
			if (getItemViewType(position) == VIEWTYPE_PROPERTY) {
				return mFeed.getProperties().get(position);
			} else {
				return null;
			}
		}

		@Override
		public long getItemId(int position) {
			// Item ID not needed here:
			return 0;
		}

		@Override
		public int getItemViewType(int position) {
			if (position < mFeed.getProperties().size()) {
				return VIEWTYPE_PROPERTY;
			} else {
				return VIEWTYPE_LOADMORE;
			}
		}

		@Override
		public int getViewTypeCount() {
			return 2;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {

			switch (getItemViewType(position)) {
			case VIEWTYPE_PROPERTY:
				if (convertView == null) {

					convertView = mInflater.inflate(R.layout.row_properties,
							parent, false);

					PropertyViewHolder holder = new PropertyViewHolder();

					holder.imgProperty = (ImageView) convertView
							.findViewById(R.id.imgThumbnail);
					holder.imgProperty.setScaleType(ScaleType.FIT_XY);

					holder.imgCountView = (TextView) convertView
							.findViewById(R.id.tvPicCount);
					holder.priceView = (TextView) convertView
							.findViewById(R.id.tvprice);
					holder.descView = (TextView) convertView
							.findViewById(R.id.tvdesc);
					holder.citiesView = (TextView) convertView
							.findViewById(R.id.tvaddress);
					convertView.setMinimumHeight(20);
					convertView.setTag(holder);
				}

				final PFProperty item = getItem(position);

				PropertyViewHolder holder = (PropertyViewHolder) convertView
						.getTag();

				holder.imgCountView.setText(item.getImage_count() + "");
				holder.priceView.setText(item.getPrice() + " "
						+ item.getCurrency());
				holder.descView.setText(item.getTitle());
				holder.citiesView.setText(item.getLocation());

				imageLoader.DisplayImage(item.getThumbnail(),
						holder.imgProperty);

				break;

			case VIEWTYPE_LOADMORE:
				// I don't use the preloaded convertView here because it's
				// only one cell
				// LayoutInflater mInflater = (LayoutInflater)
				// getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = mInflater.inflate(R.layout.loadingtext, parent,
						false);

				final TextView textView = (TextView) convertView
						.findViewById(R.id.tvloading);

				// https://www.propertyfinder.ae/mobileapi?page=[page
				// number]&order=[pd|pd|ba|bd]
				// page: integer, starting with 0 for the first page

				HNFeedTaskLoadMore.start(MainActivity.this, MainActivity.this,
						mFeed, TASKCODE_LOAD_MORE_PROPERTIES);

				mShouldShowRefreshing = true;
				supportInvalidateOptionsMenu();

				// if (PFFeedTaskLoadMore.isRunning(MainActivity.this,
				// TASKCODE_LOAD_MORE_PROPERTIES)) {
				//
				// textView.setVisibility(View.VISIBLE);
				//
				// } else {
				// textView.setVisibility(View.INVISIBLE);
				// }

				textView.setVisibility(View.VISIBLE);

				break;
			default:
				break;
			}

			return convertView;
		}

	}

	static class PropertyViewHolder {

		ImageView imgProperty;
		TextView imgCountView;
		TextView priceView;
		TextView descView;
		TextView citiesView;

	}

	@Override
	public void onTaskFinished(
			int taskCode,
			com.test.propertyfinder.Utils.ITaskFinishedHandler.TaskResultCode code,
			PFFeed result, Object tag) {
		// TODO Auto-generated method stub

		if (taskCode == TASKCODE_LOAD_FEED) {
			if (code.equals(TaskResultCode.Success)
					&& mPostsListAdapter != null) {
				showFeed(result);
			} else if (!code.equals(TaskResultCode.Success)) {

				// Toast.makeText(this,
				// getString(R.string.error_unable_to_retrieve_feed),
				// Toast.LENGTH_SHORT).show();
			}

			mShouldShowRefreshing = false;
			supportInvalidateOptionsMenu();

		} else if (taskCode == TASKCODE_LOAD_MORE_PROPERTIES) {
			if (!code.equals(TaskResultCode.Success) || result == null
					|| result.getProperties() == null
					|| result.getProperties().size() == 0) {

				// getString(R.string.error_unable_to_load_more)
				Toast.makeText(this, "Unable to load more", Toast.LENGTH_SHORT)
						.show();
				mFeed.setLoadedMore(true); // reached the end.
			}

			mFeed.appendLoadMoreFeed(result);
			mPostsListAdapter.notifyDataSetChanged();
			mShouldShowRefreshing = false;
			supportInvalidateOptionsMenu();
		}

	}

	private void showFeed(PFFeed feed) {
		mFeed = feed;
		mPostsListAdapter.notifyDataSetChanged();
	}

	@Override
	protected void onRestoreInstanceState(Bundle state) {
		super.onRestoreInstanceState(state);
		mListState = state.getParcelable(LIST_STATE);
	}

	@Override
	protected void onSaveInstanceState(Bundle state) {
		super.onSaveInstanceState(state);
		mListState = mPropertiesList.onSaveInstanceState();
		state.putParcelable(LIST_STATE, mListState);
	}

}
