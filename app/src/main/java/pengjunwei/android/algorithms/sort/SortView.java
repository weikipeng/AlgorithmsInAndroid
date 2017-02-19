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

    protected List<String> mDataList;
    protected Paint        paint;
    protected Rect         rect;

    protected int mWidth;
    protected int mHeight;

    protected int x, y;
    protected int mItemWith;

    protected final int COLOR_RECT = Color.BLACK;

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
//        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        rect = new Rect();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mWidth = getWidth();
        mHeight = getHeight();


        paint.setColor(COLOR_RECT);

        if (mDataList != null && mDataList.size() > 0) {
            mItemWith = (mWidth - getPaddingLeft() - getPaddingRight()) / mDataList.size();

            Log.i("SortView", "onDraw====> width:" + mWidth + "  height:" + mHeight + "  mItemHeight:" + mHeight);

            x = getPaddingLeft();

            for (String s : mDataList) {
                rect.set(x, mHeight - Integer.parseInt(s), x + mItemWith, mHeight);
                canvas.drawRect(rect, paint);
                x += mItemWith;
            }
        }
    }

    public void updateDataList(List<String> dataList) {
        this.mDataList = dataList;
        postInvalidate();
    }
}
