package xyz.zimuju.common.basal;

import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/*
 * @description BasalAdapter : 对 Adapter 的封装
 * @author Nathaniel-nathanwriting@126.com
 * @time 16-10-10-下午3:44
 * @version v1.0.0
 */
public abstract class BasalAdapter<T> extends BaseAdapter {
    protected List<T> dataList;

    public List<T> getDataList() {
        if (dataList == null) {
            dataList = new ArrayList<>();
        }
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }
}
