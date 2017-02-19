package pengjunwei.android.algorithms;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.Collections;
import java.util.List;

import pengjunwei.android.algorithms.sort.SortView;

/**
 * Created by WikiPeng on 2017/2/9 16:08.
 */
public class MainActivity extends AppCompatActivity {
    protected List<String> mDataList;
    protected SortView mSortView1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        mSortView1 = (SortView) findViewById(R.id.sortView1);

        for(int i =1;i<=100;i++) {
            mDataList.add(String.valueOf(i));
        }

        Collections.shuffle(mDataList);

        mSortView1.updateDataList(mDataList);
    }
}
