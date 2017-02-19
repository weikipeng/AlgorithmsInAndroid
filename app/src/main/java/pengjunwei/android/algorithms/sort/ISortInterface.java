package pengjunwei.android.algorithms.sort;

/**
 * Created by WikiPeng on 2017/2/19 15:19.
 */
public interface ISortInterface {
    void sort(Object view);

    /**
     * 获取算法的名字
     */
    String getSortAlgorithmsName();

    /**停止*/
    void stop();
}
