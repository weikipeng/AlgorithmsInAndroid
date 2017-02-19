package pengjunwei.android.algorithms.sort;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
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
    public static final int SIZE_TEXT_NAME            = 16;
    public static final int SIZE_TEXT_NAME_MARGIN_TOP = 10;

    protected int mNameMarginTop;

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

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

        paint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, SIZE_TEXT_NAME, displayMetrics));
        mNameMarginTop = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, SIZE_TEXT_NAME_MARGIN_TOP, displayMetrics);
        rect = new Rect();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mWidth = getWidth();
        mHeight = getHeight();

        int   textOtherSize = (int) ((paint.descent() + paint.ascent()) / 2);
        float textSize      = paint.getTextSize();

        int rectBottom = (int) (mHeight - textSize - mNameMarginTop - textOtherSize);

        if (mSortInterface != null && !TextUtils.isEmpty(mSortInterface.getSortAlgorithmsName())) {
            int         nameY = (int) (mHeight - textSize / 2 - textOtherSize);
            Paint.Align align = paint.getTextAlign();

            //测试中间线条
//            paint.setColor(Color.RED);
//            canvas.drawText(mSortInterface.getSortAlgorithmsName(), mWidth / 2, nameY, paint);
//            canvas.drawLine(0, mHeight - 1, mWidth, mHeight - 1, paint);
//            canvas.drawLine(0, nameY, mWidth, nameY, paint);
//            canvas.drawLine(mWidth / 2, 0, mWidth / 2, mHeight, paint);

            paint.setTextAlign(Paint.Align.CENTER);
            paint.setColor(Color.BLUE);
            canvas.drawText(mSortInterface.getSortAlgorithmsName(), mWidth / 2, nameY, paint);

            paint.setTextAlign(align);
        }

        if (mDataList != null && mDataList.size() > 0) {
            mItemWith = (mWidth - getPaddingLeft() - getPaddingRight()) / mDataList.size();

            Log.i("SortView", "onDraw====> width:" + mWidth + "  height:" + mHeight + "  mItemHeight:" + mHeight +"\n textOtherSize:"+textOtherSize);

            x = getPaddingLeft();

            for (DataSort dataSort : mDataList) {
                paint.setStyle(Paint.Style.FILL);
                paint.setColor(dataSort.color);
                rect.set(x, rectBottom - dataSort.value, x + mItemWith, rectBottom);
                canvas.drawRect(rect, paint);


                paint.setStyle(Paint.Style.STROKE);
                paint.setColor(dataSort.bordColor);
                rect.set(x, rectBottom - dataSort.value, x + mItemWith, rectBottom);
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

    public void stopAllTask() {
        if (mSortInterface != null) {
            mSortInterface.stop();
        }
    }
}
