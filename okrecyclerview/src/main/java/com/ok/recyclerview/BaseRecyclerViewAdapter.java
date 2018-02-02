package com.ok.recyclerview;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.ok.recyclerview.click.ItemClickListener;
import com.ok.recyclerview.click.ItemLongClickListener;
import com.ok.recyclerview.click.ViewClickListener;
import com.ok.recyclerview.delegate.AdapterDelegate;
import com.ok.recyclerview.delegate.ItemViewDelegate;
import com.ok.recyclerview.delegate.ItemViewDelegateManager;

import java.util.List;

/**
 * Created by ShenZhenWei on 18/1/31.
 */

public class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<ViewHolder> {

    private static final String TAG = "BaseRecyclerViewAdapter";
    private SparseArray<ViewClickListener> onViewClickListeners;
    private ItemLongClickListener onLongClickListener;
    private ItemClickListener onItemClickListener;
    private List<T> mData;

    private Context context;
    protected ItemViewDelegateManager delegateManager;

    public BaseRecyclerViewAdapter(List<T> mData) {
        this.mData = mData;
        onViewClickListeners=new SparseArray<>();
        delegateManager=new ItemViewDelegateManager();
    }

    public void setData(List<T> mData){
        this.mData=mData;
        notifyDataSetChanged();
    }

    public Context getContext(){
        return context;
    }

    public void setOnViewClickListener(int viewId,ViewClickListener listener){
        ViewClickListener listener_=onViewClickListeners.get(viewId);
        if (listener_==null){
            onViewClickListeners.put(viewId,listener);
        }
    }

    public void setOnLongClickListener(ItemLongClickListener onLongClickListener) {
        this.onLongClickListener = onLongClickListener;
    }

    public void setOnItemClickListener(ItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context=parent.getContext();

        ItemViewDelegate delegateForViewType=delegateManager.getItemViewDelegate(viewType);
        int layoutId=delegateForViewType.getItemViewLayoutId();
        return ViewHolder.getViewHolder(parent,layoutId);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position,List<Object> payloads) {
        Log.d(TAG,"onBindViewHolder2:"+position);
        int newPosition=position;
        T itemModel=getItem(position);
        onBind(holder,itemModel,position,payloads);
        checkClickListener(holder,position);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
    }

    @Override
    public int getItemViewType(int position) {
        Log.d(TAG,"getItemViewType:"+position);
        if (!useItemViewDelegateManager()){
            return super.getItemViewType(position);
        }
        return delegateManager.getItemViewType(mData.get(position),position);
    }

    public BaseRecyclerViewAdapter<T> addItemViewDelegate(AdapterDelegate<T> itemViewDelegate){
        delegateManager.addDelegate(itemViewDelegate);
        return this;
    }

    private boolean useItemViewDelegateManager() {
        return delegateManager.getItemViewDelegateCount()>0;
    }

    private void checkClickListener(ViewHolder holder, final int position) {
        for (int i=0;i<onViewClickListeners.size();i++){
            int id=onViewClickListeners.keyAt(i);
            View view=holder.getView(id);
            if (view==null){
                continue;
            }
            final ViewClickListener listener=onViewClickListeners.get(id);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener!=null){
                        listener.onItemClicked(view,position);
                    }
                }
            });
        }

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                boolean flag=true;
                if (onLongClickListener!=null){
                    flag=onLongClickListener.onItemLongClicked(position,view);
                }
                return flag;
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener !=null){
                    onItemClickListener.onItemClicked(position,view);
                }
            }
        });
    }

    public T getItem(final  int position){
        if (mData==null){
            return null;
        }
        return mData.get(position);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        Log.d(TAG, "onAttachedToRecyclerView: ");
        RecyclerView.LayoutManager manager=recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager){
            GridLayoutManager gridManager= (GridLayoutManager) manager;
            Log.d(TAG,"onAttachedToRecyclerView: "+gridManager.getSpanCount());
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup(){

                @Override
                public int getSpanSize(int position) {
                    Log.d(TAG,"onAttachedToRecyclerView position: "+position);
                    return 1;
                }
            });
        }
    }

    @Override
    public void onViewAttachedToWindow(ViewHolder holder) {
        int position=holder.getLayoutPosition();
        delegateManager.onViewAttachedToWindow(holder);
    }

    @Override
    public int getItemCount() {
        int count =getDataSize();
        return count;
    }

    public void onBind(ViewHolder holder, T itemModel, int position, List<Object> payloads){
        delegateManager.onBind(holder,itemModel,position,payloads);
    }


    public int getDataSize() {
        Log.d(TAG,"getDataSize:"+((mData!=null)?mData.size():0));
        return (mData!=null)?mData.size():0;
    }
}
