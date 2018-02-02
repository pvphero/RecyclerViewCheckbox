package com.ok.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ShenZhenWei on 18/1/31.
 */

public class ViewHolder extends RecyclerView.ViewHolder{
    private SparseArray<View> mViews;
    private View mConvertView;
    public ViewHolder(View itemView) {
        super(itemView);
        mConvertView=itemView;
        mViews=new SparseArray<>();
    }

    /**
     * 加载layoutId视图,并使用viewHolder保存
     * @param parent
     * @param layoutId
     * @return
     */
    protected static ViewHolder getViewHolder(ViewGroup parent,int layoutId){
        View itemView= LayoutInflater.from(parent.getContext()).inflate(layoutId,parent,false);
        return new ViewHolder(itemView);
    }

    protected static ViewHolder getViewHolder(ViewGroup parent,View view){
        return new ViewHolder(view);
    }

    /**
     * 通过viewId获取控件
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId){
        View view=mViews.get(viewId);
        if (view == null){
            view=mConvertView.findViewById(viewId);
            mViews.put(viewId,view);
        }
        return (T)view;
    }

    public View getConvertView(){
        return mConvertView;
    }
}
