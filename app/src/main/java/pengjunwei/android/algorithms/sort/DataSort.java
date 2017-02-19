package pengjunwei.android.algorithms.sort;

import android.graphics.Color;

/**
 * Created by WikiPeng on 2017/2/19 15:58.
 */
public class DataSort {
    public int     value;
    public int     color;
    public int     bordColor;
    public boolean isMark;

    public DataSort(int value) {
        this.value = value;
        color = Color.TRANSPARENT;
        bordColor = Color.BLACK;
    }

    public void mark(boolean flag) {
        this.isMark = flag;
    }
}
