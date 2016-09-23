package com.lq.ren.many.calendar.view.step19;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Author lqren on 16/9/19.
 */
public class PinnedSectionListView extends ListView {

    public interface PinnedSectionListAdapter extends ListAdapter {
        boolean isItemViewTypePinned(int viewType);
    }

    static class PinnedSection {
        public View view;
        public int postion;
        public long id;
    }
    /** handle touch events */
    private final Rect mTouchRect = new Rect();
    private final PointF mTouchPoint = new PointF();
    private int mTouchSlop;
    private View mTouchTarget;
    private MotionEvent mDownEvent;
    /** drawing shadow under a pinned section */
    private GradientDrawable mShadowDrawable;
    private int mSectionDistanceY;
    private int mShadowHeight;

    OnScrollListener mDelegateOnScrollListener;

    private PinnedSection mRecycleSection;
    private PinnedSection mPinnedSection;
    private int mTranslateY;

    private final OnScrollListener mOnScrollListener = new OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView absListView, int scrollState) {
            if (mDelegateOnScrollListener != null) {
                mDelegateOnScrollListener.onScrollStateChanged(absListView, scrollState);
            }
        }

        @Override
        public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (mDelegateOnScrollListener != null) {
                mDelegateOnScrollListener.onScroll(absListView, firstVisibleItem, visibleItemCount, totalItemCount);
            }

            ListAdapter adapter = getAdapter();
            if (adapter == null || visibleItemCount == 0) return;

            final boolean isFirstVisibleItemSection = isItemViewTypePinned(adapter,
                    adapter.getItemViewType(firstVisibleItem));
            if (isFirstVisibleItemSection) {
                View sectionView = getChildAt(0);
                if (sectionView.getTop() == getPaddingTop()) {
                    destroyPinnedShadow();
                } else {

                }
            }
        }
    };

    private final DataSetObserver mDataSetObserver = new DataSetObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
        }

        @Override
        public void onInvalidated() {
            super.onInvalidated();
        }
    };


    public PinnedSectionListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PinnedSectionListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView() {
        setOnScrollListener(mOnScrollListener);
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        initShadow(true);
    }

    private void initShadow(boolean visible) {
        if (visible) {
            if (mShadowDrawable == null) {
                mShadowDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,
                        new int[] {Color.RED, Color.WHITE, Color.BLUE, Color.YELLOW});
                mShadowHeight = (int) (8 * getResources().getDisplayMetrics().density);
            }
        } else {
            if (mShadowDrawable != null) {
                mShadowDrawable = null;
                mShadowHeight = 0;
            }
        }
    }

    private void setShadowVisible(boolean visible) {
        initShadow(visible);
        if (mPinnedSection != null) {
            View v = mPinnedSection.view;
            invalidate(v.getLeft(), v.getTop(), v.getRight(), v.getBottom() + mShadowHeight);
        }
    }

    private int findCurrentSectionPosition(int fromPosition) {
        ListAdapter adapter = getAdapter();
        if (fromPosition >= adapter.getCount()) return -1;
        return 0;
    }

    private void recreatePinnedShadow() {
        destroyPinnedShadow();
        ListAdapter adapter = getAdapter();
        if (adapter != null && adapter.getCount() > 0) {
            int firstVisiblePosition = getFirstVisiblePosition();
            int sectionPosition = findCurrentSectionPosition(firstVisiblePosition);
            if (sectionPosition == -1) return;

        }
    }

    private void destroyPinnedShadow() {
        if (mPinnedSection != null) {
            mRecycleSection = mPinnedSection;
            mPinnedSection = null;
        }
    }

    public static boolean isItemViewTypePinned(ListAdapter adapter, int viewType) {
        if (adapter instanceof HeaderViewListAdapter) {
            adapter = ((HeaderViewListAdapter) adapter).getWrappedAdapter();
        }
        return ((PinnedSectionListAdapter) adapter).isItemViewTypePinned(viewType);
    }

}
