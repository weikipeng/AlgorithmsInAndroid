package pengjunwei.android.algorithms.sort.method;

import android.util.Log;

import java.util.List;

import pengjunwei.android.algorithms.sort.DataSort;
import pengjunwei.android.algorithms.sort.ISortInterface;
import pengjunwei.android.algorithms.sort.SortInfo;
import pengjunwei.android.algorithms.sort.SortView;

/**
 * Created by WikiPeng on 2017/2/19 15:42.
 */
public class BubbleSort implements ISortInterface {

    protected Object view;

    @Override
    public void sort(Object tView) {
        this.view = tView;
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (view instanceof SortView) {
                    SortView       sortView = (SortView) view;
                    List<DataSort> dataList = sortView.getDataList();
                    SortInfo       sortInfo = sortView.getSortInfo();

                    int        x, y;
                    int        length    = dataList.size();
                    DataSort   valueX, valueY;
                    for (int j = 1; j < length - 1; j++) {
                        for (x = 0; x < length - j; x++) {
                            Log.e("peng", "BubbleSort===> run");
                            sortView.markPosition(x, x + 1);

                            try {
                                final long sleepTime = sortInfo.sleepTime;
                                Thread.sleep(sleepTime);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            valueX = dataList.get(x);
                            valueY = dataList.get(x + 1);
                            if (valueX.value > valueY.value) {
                                dataList.set(x + 1, valueX);
                                dataList.set(x, valueY);

                                sortView.postInvalidate();

                                try {
                                    final long sleepTime = sortInfo.sleepTime;
                                    Thread.sleep(sleepTime);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        sortView.markSortedPosition(length - j);
                    }

                    sortView.endSort();
                }
            }
        }).start();
    }


}
