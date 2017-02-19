package pengjunwei.android.algorithms;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pengjunwei.android.algorithms.sort.SortView;

/**
 * Created by WikiPeng on 2017/2/9 16:08.
 */
public class MainActivity extends AppCompatActivity {
    protected List<String> mDataList;
    protected SortView[]   mSortViews;
    protected ViewGroup    mRootLayout;
    public static final int NUM_ARRAY = 29;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        mRootLayout = (ViewGroup) findViewById(R.id.rootLayout);
        mSortViews = new SortView[2];

        //
        DisplayMetrics            metrics      = getResources().getDisplayMetrics();
        int                       height       = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 240, metrics);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);

        int length = mSortViews.length;
        for (int i = 0; i < length; i++) {
            mSortViews[i] = new SortView(this);
            mRootLayout.addView(mSortViews[i], layoutParams);
        }

        mSortViews[0].getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                updateDataList();
            }
        });
    }

    protected void updateDataList() {
        if (mSortViews[0] == null) {
            return;
        }
        int height = mSortViews[0].getHeight() + 1;

        Log.i("log", "height ====>" + height);

        mDataList = new ArrayList<>();
        Random random = new Random(System.currentTimeMillis());
        for (int i = 1; i <= NUM_ARRAY; i++) {
            mDataList.add(String.valueOf(random.nextInt(height)));
        }

//        Collections.shuffle(mDataList);

        for (SortView sortView : mSortViews) {
            sortView.updateDataList(mDataList);
        }
    }
}
