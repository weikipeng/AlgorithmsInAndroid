package pengjunwei.android.algorithms.sort;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.List;

/**
 * Created by WikiPeng on 2017/2/19 14:10.
 */
public class SortView extends View {

    protected List<DataSort> mDataList;
    protected Paint          paint;
    protected Rect           rect;

    protected int mWidth;
    protected int mHeight;

    protected int x, y;
    protected int mItemWith;

    protected final int COLOR_RECT = Color.BLACK;

    protected SortInfo       mSortInfo;
    protected ISortInterface mSortInterface;

    public SortView(Context context) {
        this(context, null);
    }

    public SortView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SortView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SortView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mSortInfo = new SortInfo();
//        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint = new Paint();
        rect = new Rect();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mWidth = getWidth();
        mHeight = getHeight();

        if (mDataList != null && mDataList.size() > 0) {
            mItemWith = (mWidth - getPaddingLeft() - getPaddingRight()) / mDataList.size();

            Log.i("SortView", "onDraw====> width:" + mWidth + "  height:" + mHeight + "  mItemHeight:" + mHeight);

            x = getPaddingLeft();

            for (DataSort dataSort : mDataList) {
                paint.setStyle(Paint.Style.FILL);
                paint.setColor(dataSort.color);
                rect.set(x, mHeight - dataSort.value, x + mItemWith, mHeight);
                canvas.drawRect(rect, paint);


                paint.setStyle(Paint.Style.STROKE);
                paint.setColor(dataSort.bordColor);
                rect.set(x, mHeight - dataSort.value, x + mItemWith, mHeight);
                canvas.drawRect(rect, paint);

                x += mItemWith;
            }
        }
    }

    public void updateDataList(List<DataSort> dataList) {
        this.mDataList = dataList;
        postInvalidate();
    }

    public void startSort() {
        sort();
    }

    protected void sort() {
        if (mSortInterface != null) {
            mSortInterface.sort(this);
        }
    }

    public void setSort(ISortInterface sortInterface) {
        mSortInterface = sortInterface;
    }

    public void endSort() {
        mSortInfo.status = SortInfo.SORT_END;
        mSortInfo.endTs = System.currentTimeMillis();

        for (DataSort dataSort : mDataList) {
            dataSort.markSorted();
            dataSort.color = SortInfo.COLOR_SORTED;
        }

        postInvalidate();
    }

    public void markSortedPosition(int... positions) {
        for (int position : positions) {
            mDataList.get(position).markSorted();
            mDataList.get(position).color = SortInfo.COLOR_SORTED;
        }
        postInvalidate();
    }

    public SortInfo getSortInfo() {
        return mSortInfo;
    }

    public List<DataSort> getDataList() {
        return mDataList;
    }

    public void markPosition(int... positions) {
        mSortInfo.setPosition(positions);
        for (int position : positions) {
            mDataList.get(position).markSorting();
            mDataList.get(position).color = SortInfo.COLOR_ARRAY[position % SortInfo.COLOR_ARRAY.length];
        }

        List<Integer> positionList = mSortInfo.getRemovedPosition();

        for (int position : positionList) {
            if (mDataList.get(position).flag == DataSort.FLAG_SORTED) {
                break;
            }
            mDataList.get(position).markNormal();
            mDataList.get(position).color = Color.TRANSPARENT;
        }

        postInvalidate();
    }
}
