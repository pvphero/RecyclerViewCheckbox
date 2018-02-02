package com.ok.recyclerview.delegate;


import com.ok.recyclerview.ViewHolder;

import java.util.List;

/**
 * Created by ShenZhenWei on 18/1/31.
 */

public interface ItemViewDelegate<T> {
    int getItemViewLayoutId();

    /**
     * 获取到的position和item,是否要显示在当前的ViewType上
     * @param item
     * @param position
     * @return
     */
    boolean isForViewType(T item,int position);

    void onBind(ViewHolder holder, T t, int position, List<Object> payloads);

    void onViewRecyclered(ViewHolder holder);

    boolean onFailedToRecyclerView(ViewHolder holder);

    void onViewAttachedToWindow(ViewHolder holder);

    void onViewDetachedFromWindow(ViewHolder holder);
}
