package com.infodeliver.ui;

import java.util.Date;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.infodeliver.utils.NSLog;
import com.infodeliver.utils.NSResourceManager;

/**
 * listview and header
 * 
 * @author yujian
 * 
 */
public class NSScrollOverListView extends ListView implements OnScrollListener {

	private int mLastY;
	private int mBottomPosition;
	private static final String TAG = "NSScrollOverListView";

	private final static int RELEASE_TO_REFRESH = 0;
	private final static int PULL_TO_REFRESH = 1;
	private final static int REFRESHING = 2;
	private final static int DONE = 3;
	private final static int LOADING = 4;
	
	private final static int RATIO = 3;
	
	private LayoutInflater inflater;
	private LinearLayout headView;
	private TextView tipsTextview;
	private TextView lastUpdatedTextView;
	private ImageView arrowImageView;
	private ProgressBar progressBar;
	private RotateAnimation animation;
	private RotateAnimation reverseAnimation;
	private boolean isRecored;
	private int headContentHeight;
	private int startY;
	private int firstItemIndex;
	private int state;
	
	private boolean isBack;
	public boolean showRefresh = true;

	public static boolean canRefleash = false;
	
	private Context mContext;

	public NSScrollOverListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
//		init(context);
		this.mContext = context;
	}

	public NSScrollOverListView(Context context, AttributeSet attrs) {
		super(context, attrs);
//		init(context);
		this.mContext = context;
	}

	public NSScrollOverListView(Context context) {
		super(context);
//		init(context);
		this.mContext = context;
	}

	public void init(String head,int headerType) {
		
		mBottomPosition = 0;
		setCacheColorHint(0);
		inflater = LayoutInflater.from(mContext);

		headView = (LinearLayout) inflater.inflate(NSResourceManager.getResourceID(
				head, "layout"),
				null);
		arrowImageView = (ImageView) headView
				.findViewById(NSResourceManager.getResourceID(
						"head_arrow", "id"));
		arrowImageView.setMinimumWidth(70);
		arrowImageView.setMinimumHeight(50);
		progressBar = (ProgressBar) headView
				.findViewById(NSResourceManager.getResourceID(
						"head_progress", "id"));
		tipsTextview = (TextView) headView.findViewById(NSResourceManager.getResourceID(
				"head_tips", "id"));
		lastUpdatedTextView = (TextView) headView
				.findViewById(NSResourceManager.getResourceID(
						"head_last_updated", "id"));
		
		measureView(headView);

		switch (headerType) {
		case 1:
			headContentHeight = 0;
			break;

		default:
			headContentHeight = headView.getMeasuredHeight();
			break;
		}
		
		headView.setPadding(0, -1 * headContentHeight, 0, 0);
		headView.invalidate();

		addHeaderView(headView, null, false);

		setOnScrollListener(this);

		animation = new RotateAnimation(0, -180,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		animation.setInterpolator(new LinearInterpolator());
		animation.setDuration(250);
		animation.setFillAfter(true);
		

		reverseAnimation = new RotateAnimation(-180, 0,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		
		reverseAnimation.setInterpolator(new LinearInterpolator());
		reverseAnimation.setDuration(200);
		reverseAnimation.setFillAfter(true);
		
		state = DONE;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {

		final int action = ev.getAction();
		final int y = (int) ev.getRawY();
		cancelLongPress();
		switch (action) {
		case MotionEvent.ACTION_DOWN: {
			if (firstItemIndex == 0 && !isRecored) {
				isRecored = true;
				startY = (int) ev.getY();
				NSLog.v(TAG, "down Y‘");
			}

			mLastY = y;
			final boolean isHandled = mOnScrollOverListener.onMotionDown(ev);
			if (isHandled) {
				mLastY = y;
				return isHandled;
			}
			break;
		}

		case MotionEvent.ACTION_MOVE: {
			int tempY = (int) ev.getY();
			if (showRefresh) {
				
				if (!isRecored && firstItemIndex == 0) {
					NSLog.v(TAG, "move Y");
					isRecored = true;
					startY = tempY;
				}
				if (state != REFRESHING && isRecored && state != LOADING) {

					if (state == RELEASE_TO_REFRESH) {

						setSelection(0);

						if (((tempY - startY) / RATIO < headContentHeight)
								&& (tempY - startY) > 0) {
							state = PULL_TO_REFRESH;
							changeHeaderViewByState();

						}
						else if (tempY - startY <= 0) {
							state = DONE;
							changeHeaderViewByState();

							NSLog.v(TAG, "release to refresh - > done");
						}
						else {
						}
					}
					if (state == PULL_TO_REFRESH) {

						setSelection(0);

						if ((tempY - startY) / RATIO >= headContentHeight) {
							state = RELEASE_TO_REFRESH;
							isBack = true;
							changeHeaderViewByState();

							NSLog.v(TAG, "done/pull down -> release to refresh");
						}
						else if (tempY - startY <= 0) {
							state = DONE;
							changeHeaderViewByState();

							NSLog.v(TAG, "done/pull down -> done");
						}
					}

					if (state == DONE) {
						if (tempY - startY > 0) {
							state = PULL_TO_REFRESH;
							changeHeaderViewByState();
						}
					}

					if (state == PULL_TO_REFRESH) {
						
						headView.setPadding(0, -1 * headContentHeight
						+ (tempY - startY) / RATIO, 0, 0);
					}

					if (state == RELEASE_TO_REFRESH) {
						
						headView.setPadding(0, (tempY - startY) / RATIO
						- headContentHeight, 0, 0);
					}

				}
			}

			final int childCount = getChildCount();
			if (childCount == 0)
				return super.onTouchEvent(ev);
			final int itemCount = getAdapter().getCount() - mBottomPosition;
			final int deltaY = y - mLastY;
			final int lastBottom = getChildAt(childCount - 1).getBottom();
			final int end = getHeight() - getPaddingBottom();

			final int firstVisiblePosition = getFirstVisiblePosition();

			final boolean isHandleMotionMove = mOnScrollOverListener
					.onMotionMove(ev, deltaY);

			if (isHandleMotionMove) {
				mLastY = y;
				return true;
			}

			if (firstVisiblePosition + childCount >= itemCount
					&& lastBottom <= end && deltaY < 0) {
				final boolean isHandleOnListViewBottomAndPullDown;
				isHandleOnListViewBottomAndPullDown = mOnScrollOverListener
						.onListViewBottomAndPullUp(deltaY);
				if (isHandleOnListViewBottomAndPullDown) {
					mLastY = y;
					return true;
				}
			}
			break;
		}

		case MotionEvent.ACTION_UP: {
			if (state != REFRESHING && state != LOADING) {
				if (state == DONE) {
				}
				if (state == PULL_TO_REFRESH) {
					state = DONE;
					changeHeaderViewByState();
					NSLog.v(TAG, "pull down -> done");
				}

				if (state == RELEASE_TO_REFRESH) {
					state = REFRESHING;
					changeHeaderViewByState();

					canRefleash = true;

					NSLog.v(TAG, "release to refresh -> done");
				}
			}

			isRecored = false;
			isBack = false;

			final boolean isHandlerMotionUp = mOnScrollOverListener
					.onMotionUp(ev);
			if (isHandlerMotionUp) {
				mLastY = y;
				return true;
			}
			break;
		}
		}
		mLastY = y;
		return super.onTouchEvent(ev);
	}

	private OnScrollOverListener mOnScrollOverListener = new OnScrollOverListener() {

		@Override
		public boolean onListViewTopAndPullDown(int delta) {
			return false;
		}

		@Override
		public boolean onListViewBottomAndPullUp(int delta) {
			return false;
		}

		@Override
		public boolean onMotionDown(MotionEvent ev) {
			return false;
		}

		@Override
		public boolean onMotionMove(MotionEvent ev, int delta) {
			return false;
		}

		@Override
		public boolean onMotionUp(MotionEvent ev) {
			return false;
		}

	};

	public void setTopPosition(int index) {
		if (getAdapter() == null)
			throw new NullPointerException(
					"You must set adapter before setTopPosition!");
		if (index < 0)
			throw new IllegalArgumentException("Top position must > 0");
	}

	public void setBottomPosition(int index) {
		if (getAdapter() == null)
			throw new NullPointerException(
					"You must set adapter before setBottonPosition!");
		if (index < 0)
			throw new IllegalArgumentException("Bottom position must > 0");

		mBottomPosition = index;
	}

	public void setOnScrollOverListener(
			OnScrollOverListener onScrollOverListener) {
		mOnScrollOverListener = onScrollOverListener;
	}

	public interface OnScrollOverListener {
		boolean onListViewTopAndPullDown(int delta);

		boolean onListViewBottomAndPullUp(int delta);

		boolean onMotionDown(MotionEvent ev);

		boolean onMotionMove(MotionEvent ev, int delta);

		boolean onMotionUp(MotionEvent ev);
	}

	
	@Override
	public void onScroll(AbsListView arg0, int firstVisiableItem, int arg2,
			int arg3) {
		firstItemIndex = firstVisiableItem;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {

	}

	private void measureView(View child) {
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
					MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}

	public void onRefreshComplete() {
		state = DONE;
		lastUpdatedTextView.setText("last:" + new Date().toLocaleString());
		changeHeaderViewByState();
	}

	private void changeHeaderViewByState() {
		switch (state) {
		case RELEASE_TO_REFRESH:
			arrowImageView.setVisibility(View.VISIBLE);
			progressBar.setVisibility(View.GONE);
			tipsTextview.setVisibility(View.VISIBLE);
			lastUpdatedTextView.setVisibility(View.VISIBLE);

			arrowImageView.clearAnimation();
			arrowImageView.startAnimation(animation);

			tipsTextview.setText(NSResourceManager
					.getResourceID("comm_head_tips_text_release", "string"));

			NSLog.v(TAG, "release to refresh");
			break;
		case PULL_TO_REFRESH:
			progressBar.setVisibility(View.GONE);
			tipsTextview.setVisibility(View.VISIBLE);
			lastUpdatedTextView.setVisibility(View.VISIBLE);
			arrowImageView.clearAnimation();
			arrowImageView.setVisibility(View.VISIBLE);
			if (isBack) {
				isBack = false;
				arrowImageView.clearAnimation();
				arrowImageView.startAnimation(reverseAnimation);

				tipsTextview.setText(NSResourceManager
						.getResourceID("comm_head_tips_text", "string"));
			} else {
				tipsTextview.setText(NSResourceManager
						.getResourceID("comm_head_tips_text", "string"));
			}
			NSLog.v(TAG, "pull down");
			break;

		case REFRESHING:

			headView.setPadding(0, 0, 0, 0);
			progressBar.setVisibility(View.VISIBLE);
			arrowImageView.clearAnimation();
			arrowImageView.setVisibility(View.GONE);
			tipsTextview.setText(NSResourceManager
					.getResourceID("comm_head_tips_text_doing", "string"));
			lastUpdatedTextView.setVisibility(View.VISIBLE);

			NSLog.v(TAG, "refreshing...");
			break;
		case DONE:
			headView.setPadding(0, -1 * headContentHeight, 0, 0);
			progressBar.setVisibility(View.GONE);
			arrowImageView.clearAnimation();
			tipsTextview.setText(NSResourceManager
					.getResourceID("comm_head_tips_text", "string"));
			lastUpdatedTextView.setVisibility(View.VISIBLE);

			NSLog.v(TAG, "done");
			break;
		}
	}
}
