package pengjunwei.android.algorithms.sort;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WikiPeng on 2017/2/19 15:24.
 */
public class SortInfo {
    public static final int SORT_INIT  = 0;
    public static final int SORT_START = 1;
    public static final int SORT_ING   = 1 << 1;
    public static final int SORT_END   = 1 << 2;
    public static final int SORT_PAUSE = 1 << 3;

    public static final int COLOR_ARRAY[] = {
            Color.RED,
            Color.BLUE,
//            Color.MAGENTA
    };

    public static final int COLOR_SORTED = Color.MAGENTA;

    /**
     * 当前排序状态
     */
    public int status;

    /**
     * 开始时间
     */
    public long startTs;

    /**
     * 结束时间
     */
    public long endTs;

    public List<Integer> positionList;
    public List<Integer> removedList;
    private long sleepTime = 500;

    public SortInfo() {
        positionList = new ArrayList<>();
        removedList = new ArrayList<>();
    }

    public boolean isSorting() {
        return status == SORT_START;
    }

    public void setPosition(int... positions) {
        if (positions == null) {
            return;
        }
        removedList.clear();
        removedList.addAll(positionList);

        positionList.clear();
        for (int position : positions) {
            positionList.add(position);
            if (removedList.contains(position)) {
                removedList.remove(removedList.indexOf(position));
            }
        }
    }

    public List<Integer> getRemovedPosition() {
        return removedList;
    }

    public boolean isSorted() {
        return status == SORT_END;
    }

    public long getSleepTime(){
        return sleepTime;
    }

    public void faster(){
        sleepTime = sleepTime / 2;
        if (sleepTime < 1) {
            sleepTime = 1;
        }
    }

    public void slower(){
        sleepTime = sleepTime * 2;
        if (sleepTime > 10000) {
            sleepTime = 10000;
        }
    }
}
