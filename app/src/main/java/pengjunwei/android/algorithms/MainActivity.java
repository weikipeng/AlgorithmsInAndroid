package pengjunwei.android.algorithms;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pengjunwei.android.algorithms.sort.DataSort;
import pengjunwei.android.algorithms.sort.SortView;
import pengjunwei.android.algorithms.sort.method.BubbleSort;

/**
 * Created by WikiPeng on 2017/2/9 16:08.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    protected List<DataSort> mDataList;
    protected SortView[]     mSortViews;
    protected ViewGroup      mRootLayout;
    protected Button         mBtnStart;
    public static final int NUM_ARRAY = 29;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    protected void onDestroy() {
        for (SortView sortView : mSortViews) {
            sortView.stopAllTask();
        }
        super.onDestroy();
    }

    private void init() {
        mBtnStart = (Button) findViewById(R.id.buttonStart);
        mBtnStart.setOnClickListener(this);
        findViewById(R.id.buttonFaster).setOnClickListener(this);
        findViewById(R.id.buttonSlower).setOnClickListener(this);


        mRootLayout = (ViewGroup) findViewById(R.id.rootLayout);
        mSortViews = new SortView[2];

        int    length      = mSortViews.length;
        String packageName = getPackageName();
        for (int i = 0; i < length; i++) {
            mSortViews[i] = (SortView) findViewById(getResources().getIdentifier("sortView" + (i + 1), "id", packageName));
        }

        //初始化
        mSortViews[0].setSort(new BubbleSort());

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
            mDataList.add(new DataSort(random.nextInt(height)));
        }

        for (SortView sortView : mSortViews) {
            //创建List的拷贝，而不是引用的拷贝
            List<DataSort> tList = getCopyList(mDataList);
            sortView.updateDataList(tList);
        }

        mSortViews[0].markPosition(0, 1);
        mSortViews[1].postInvalidateDelayed(2000);
    }

    private List<DataSort> getCopyList(List<DataSort> list) {
        List<DataSort> result = new ArrayList<>();
        for (DataSort item : list) {
            try {
                result.add(item.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonStart:
                if (mSortViews[0].getSortInfo().isSorted()) {
                    updateDataList();
                }
                mSortViews[0].startSort();
                break;
            case R.id.buttonFaster:
                mSortViews[0].getSortInfo().faster();
                break;
            case R.id.buttonSlower:
                mSortViews[0].getSortInfo().slower();
                break;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.i("peng", "onConfigurationChanged ===> " + newConfig.orientation);
    }
}
