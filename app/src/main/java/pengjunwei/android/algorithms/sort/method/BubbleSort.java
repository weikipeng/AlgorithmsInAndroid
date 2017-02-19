package pengjunwei.android.algorithms.sort.method;

import java.util.List;

import pengjunwei.android.algorithms.sort.DataSort;
import pengjunwei.android.algorithms.sort.ISortInterface;
import pengjunwei.android.algorithms.sort.SortInfo;
import pengjunwei.android.algorithms.sort.SortView;

/**
 * Created by WikiPeng on 2017/2/19 15:42.
 */
public class BubbleSort implements ISortInterface {

    @Override
    public void sort(Object view) {
        if (view instanceof SortView) {
            SortView       sortView = (SortView) view;
            SortInfo       sortInfo = sortView.getSortInfo();
            List<DataSort> dataList = sortView.getDataList();

            int      x, y;
            int      length = dataList.size();
            DataSort valueX, valueY;
            for (x = 0; x < length - 2; x++) {

                sortInfo.setPosition(x, x + 1);
                sortView.postInvalidate();

                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                valueX = dataList.get(x);
                valueY = dataList.get(x + 1);
                if (valueX.value > valueY.value) {
                    dataList.set(x + 1, valueX);
                    dataList.set(x, valueY);

                    sortView.postInvalidate();
                }
            }
        }
    }
}
