package com.example.base.baseapp.utils;

import android.support.annotation.NonNull;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public abstract class AbstractRecyclerViewAdapter<T, E extends AbstractRecyclerViewAdapter.ItemHolder> extends RecyclerView.Adapter<AbstractRecyclerViewAdapter.ItemHolder> {

    private final PublishSubject<T> onItemClickSubject = PublishSubject.create();
    private final PublishSubject<T> onItemLongClickSubject = PublishSubject.create();
    private final PublishSubject<View> onConstantClickSubject = PublishSubject.create();

    protected final SortedList<T> elements;
    private final ConstantElement additionalElement;
    private List<T> originalElements;

    public enum ViewType {
        ITEM, CONSTANT_ITEM
    }

    public enum ConstantElement {
        NONE, TOP, BOTTOM
    }

    public AbstractRecyclerViewAdapter(Class<T> tClass) {
        this(tClass, ConstantElement.NONE);
    }

    public AbstractRecyclerViewAdapter(Class<T> tClass, ConstantElement constantElement) {
        this.additionalElement = constantElement;
        elements = new SortedList<>(tClass, new SortedList.Callback<T>() {

            @Override
            public void onInserted(int position, int count) {
                notifyItemRangeInserted(position, count);
            }

            @Override
            public void onRemoved(int position, int count) {
                notifyItemRangeRemoved(position, count);
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
                notifyItemMoved(fromPosition, toPosition);
            }

            @Override
            public int compare(T o1, T o2) {
                return compareElements(o1, o2);
            }

            @Override
            public void onChanged(int position, int count) {
                notifyItemRangeChanged(position, count);
            }

            @Override
            public boolean areContentsTheSame(T oldItem, T newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areItemsTheSame(T item1, T item2) {
                return item1 == item2;
            }
        });
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewTypeInt) {
        ViewType viewType = ViewType.values()[viewTypeInt];
        View view;
        if (viewType == ViewType.ITEM) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(getViewHolderLayoutResource(), parent, false);
            return instantiateViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(getConstantItemLayoutResource(), parent, false);
            return new ItemHolder(view);
        }
    }

    protected int getConstantItemLayoutResource() {
        throw new UnsupportedOperationException("Implement getConstantItemLayoutResource in your adapter");
    }

    @Override
    public void onBindViewHolder(@NonNull AbstractRecyclerViewAdapter.ItemHolder holder, int position) {
        if (position < elements.size()) {
            T element = elements.get(position);
            holder.itemView.setOnClickListener(v -> onItemClickSubject.onNext(element));
            holder.itemView.setOnLongClickListener(v -> {
                onItemLongClickSubject.onNext(element);
                return true;
            });
            onBindViewHolder((E) holder, element);
        } else {
            holder.itemView.setOnClickListener(v -> onConstantClickSubject.onNext(holder.itemView));
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (additionalElement) {
            case TOP:
                return position == 0 ? ViewType.CONSTANT_ITEM.ordinal() : ViewType.ITEM.ordinal();
            case BOTTOM:
                return position == elements.size() ? ViewType.CONSTANT_ITEM.ordinal() : ViewType.ITEM.ordinal();
            default:
                return ViewType.ITEM.ordinal();
        }
    }

    @Override
    public int getItemCount() {
        return elements.size() + (additionalElement != ConstantElement.NONE ? 1 : 0);
    }

    public void setElements(List<T> newElements) {
        originalElements = new ArrayList<>(newElements);
        elements.replaceAll(originalElements);
    }

    public Observable<T> getElementClicks() {
        return onItemClickSubject.hide();
    }

    public Observable<T> getElementLongClicks() {
        return onItemLongClickSubject.hide();
    }

    public Observable<View> getConstantElementClicks() {
        return onConstantClickSubject.hide();
    }

    protected int compareElements(T o1, T o2) {
        return originalElements.indexOf(o1) - originalElements.indexOf(o2);
    }

    protected abstract E instantiateViewHolder(View view);

    protected abstract int getViewHolderLayoutResource();

    protected abstract void onBindViewHolder(E holder, T t);

    public static class ItemHolder extends RecyclerView.ViewHolder {
        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
