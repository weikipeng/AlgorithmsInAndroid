package pengjunwei.android.algorithms.sort;

import android.graphics.Color;

/**
 * Created by WikiPeng on 2017/2/19 15:58.
 */
public class DataSort implements Cloneable {
    public int value;
    public int color;
    public int bordColor;
    public int flag;
    public static final int FLAG_NORMAL  = 0;
    public static final int FLAG_SORTING = 1;
    public static final int FLAG_SORTED  = 1 << 2;

    public DataSort(int value) {
        this.value = value;
        color = Color.TRANSPARENT;
        bordColor = Color.BLACK;
    }

    public void markSorting() {
        this.flag = FLAG_SORTING;
    }

    public void markSorted() {
        this.flag = FLAG_SORTED;
    }

    public void markNormal() {
        this.flag = FLAG_NORMAL;
    }

    @Override
    public DataSort clone() throws CloneNotSupportedException {
        return (DataSort) super.clone();
    }
}
