package com.ok.recyclerview.delegate;

import com.ok.recyclerview.ViewHolder;

import java.util.List;

/**
 * Created by ShenZhenWei on 18/2/1.
 */

public abstract class AdapterDelegate<T> implements ItemViewDelegate<T> {
    @Override
    public abstract void onBind(ViewHolder holder, T t, int position, List<Object> payloads);

    @Override
    public abstract int getItemViewLayoutId();

    @Override
    public abstract boolean isForViewType(T item, int position);

    @Override
    public void onViewRecyclered(ViewHolder holder) {

    }

    @Override
    public boolean onFailedToRecyclerView(ViewHolder holder) {
        return false;
    }

    @Override
    public void onViewAttachedToWindow(ViewHolder holder) {

    }

    @Override
    public void onViewDetachedFromWindow(ViewHolder holder) {

    }
}
