package com.infodeliver.ui;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.infodeliver.ui.NSScrollOverListView.OnScrollOverListener;
import com.infodeliver.utils.NSLog;
import com.infodeliver.utils.NSResourceManager;

/**
 * for listview
 * 
 * @author yujian
 * 
 */
public class NSPullDownView extends LinearLayout implements
		OnScrollOverListener {

	private static final String TAG = "NSPullDownView";
	private static final int START_PULL_DEVIATION = 10;
	private static final int WHAT_DID_MORE = 5;
	private static final int WHAT_DID_REFRESH = 3;
	private RelativeLayout mFooterView;
	private TextView mFooterTextView;
	private ProgressBar mFooterLoadingView;
	private NSScrollOverListView mListView;
	private NSOnPullDownListener mOnPullDownListener;
	private float mMotionDownLastY;
	private boolean mIsFetchMoreing;
	private boolean mIsPullUpDone;
	private boolean mEnableAutoFetchMore;
	private Context mContext;

	public NSPullDownView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
//		initHeaderViewAndFooterViewAndListView(context);
	}

	public NSPullDownView(Context context) {
		super(context);
		mContext = context;
//		initHeaderViewAndFooterViewAndListView(context);
	}

	public void notifyDidMore() {
		mUIHandler.sendEmptyMessage(WHAT_DID_MORE);
	}

	public void RefreshComplete() {
		mUIHandler.sendEmptyMessage(WHAT_DID_REFRESH);
	}

	public void setOnPullDownListener(NSOnPullDownListener listener) {
		mOnPullDownListener = listener;
	}

	public ListView getListView() {
		return mListView;
	}

	public void enableAutoFetchMore(boolean enable, int index) {
		if (enable) {
			mListView.setBottomPosition(index);
			mFooterLoadingView.setVisibility(View.VISIBLE);
		} else {
			mFooterTextView.setText(NSResourceManager.getResourceID(
					"comm_footer_text_more_auto_close", "string"));
			mFooterLoadingView.setVisibility(View.GONE);
		}
		mEnableAutoFetchMore = enable;
	}

	public void initHeaderViewAndFooterViewAndListView(String head, String footer) {
		initHeaderViewAndFooterViewAndListView(head, footer, 0);
	}
	
	public void initHeaderViewAndFooterViewAndListView(String head, String footer, int headerType) {
		setOrientation(LinearLayout.VERTICAL);

		mFooterView = (RelativeLayout) LayoutInflater.from(mContext)
				.inflate(
						NSResourceManager.getResourceID(footer,
								"layout"), null);

		mFooterTextView = (TextView) mFooterView.findViewById(NSResourceManager
				.getResourceID("comm_footer_text", "id"));
		mFooterLoadingView = (ProgressBar) mFooterView
				.findViewById(NSResourceManager.getResourceID(
						"comm_footer_loading", "id"));
		mFooterView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!mIsFetchMoreing) {

					mIsFetchMoreing = true;
					mFooterTextView.setText(NSResourceManager.getResourceID(
							"comm_footer_text_more_doing", "string"));
					mFooterLoadingView.setVisibility(View.VISIBLE);
					mOnPullDownListener.onMore();
				}
			}
		});

		mListView = new NSScrollOverListView(mContext);
		mListView.init(head , headerType);
		mListView.setOnScrollOverListener(this);
		mListView.setCacheColorHint(0);
		addView(mListView, LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);

		mOnPullDownListener = new NSOnPullDownListener() {
			@Override
			public void onRefresh() {
			}

			@Override
			public void onMore() {
			}
		};

		mListView.addFooterView(mFooterView);

	}

	private Handler mUIHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case WHAT_DID_REFRESH: {
				mListView.onRefreshComplete();
				return;
			}

			case WHAT_DID_MORE: {
				mIsFetchMoreing = false;
				mFooterTextView.setText(NSResourceManager.getResourceID(
						"comm_footer_text", "string"));

				mFooterLoadingView.setVisibility(View.GONE);
			}
			}
		}
	};

	private boolean isFillScreenItem() {
		final int firstVisiblePosition = mListView.getFirstVisiblePosition();
		final int lastVisiblePostion = mListView.getLastVisiblePosition()
				- mListView.getFooterViewsCount();
		final int visibleItemCount = lastVisiblePostion - firstVisiblePosition
				+ 1;
		final int totalItemCount = mListView.getCount()
				- mListView.getFooterViewsCount();

		if (visibleItemCount < totalItemCount)
			return true;
		return false;
	}

	@Override
	public boolean onListViewTopAndPullDown(int delta) {
		return true;
	}

	@Override
	public boolean onListViewBottomAndPullUp(int delta) {
		if (!mEnableAutoFetchMore || mIsFetchMoreing)
			return false;
		if (isFillScreenItem()) {
			mIsFetchMoreing = true;
			mFooterTextView.setText(NSResourceManager.getResourceID(
					"comm_footer_text_more_doing", "string"));
			mFooterLoadingView.setVisibility(View.VISIBLE);
			mOnPullDownListener.onMore();
			return true;
		}
		return false;
	}

	@Override
	public boolean onMotionDown(MotionEvent ev) {
		NSLog.d(TAG,"onMotionDown>>>");
		mIsPullUpDone = false;
		mMotionDownLastY = ev.getRawY();

		return false;
	}

	@Override
	public boolean onMotionMove(MotionEvent ev, int delta) {
		NSLog.d(TAG,"onMotionMove>>>");
		if (mIsPullUpDone)
			return true;

		final int absMotionY = (int) Math.abs(ev.getRawY() - mMotionDownLastY);
		if (absMotionY < START_PULL_DEVIATION)
			return true;

		return false;
	}

	@Override
	public boolean onMotionUp(MotionEvent ev) {
		NSLog.d("TAG,onMotionUp>>>");
		if (NSScrollOverListView.canRefleash) {
			NSScrollOverListView.canRefleash = false;
			mOnPullDownListener.onRefresh();
		}
		return false;
	}

	public void setHideHeader() {
		mListView.showRefresh = false;
	}

	public void setShowHeader() {
		mListView.showRefresh = true;
	}

	public void setHideFooter() {
		mFooterView.setVisibility(View.GONE);
		mFooterTextView.setVisibility(View.GONE);
		mFooterLoadingView.setVisibility(View.GONE);
		enableAutoFetchMore(false, 1);
	}

	public void setShowFooter() {
		mFooterView.setVisibility(View.VISIBLE);
		mFooterTextView.setVisibility(View.VISIBLE);
		mFooterLoadingView.setVisibility(View.VISIBLE);
		enableAutoFetchMore(true, 1);
	}

}
