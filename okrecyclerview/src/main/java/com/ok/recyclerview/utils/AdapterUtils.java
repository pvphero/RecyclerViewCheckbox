package com.ok.recyclerview.utils;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.ViewGroup;

/**
 * Created by ShenZhenWei on 18/1/31.
 */

public class AdapterUtils {
    public static void setFull(RecyclerView.ViewHolder holder){
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp!=null && lp instanceof StaggeredGridLayoutManager.LayoutParams){
            StaggeredGridLayoutManager.LayoutParams p= (StaggeredGridLayoutManager.LayoutParams) lp;
            p.setFullSpan(true);
        }
    }
}
