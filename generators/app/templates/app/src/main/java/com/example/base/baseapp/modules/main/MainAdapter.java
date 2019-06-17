package com.example.base.baseapp.modules.main;

import android.view.View;
import android.widget.TextView;

import com.example.base.baseapp.R;
import com.example.base.baseapp.model.ExampleData;
import com.example.base.baseapp.utils.AbstractRecyclerViewAdapter;

import javax.inject.Inject;

import butterknife.BindView;

public class MainAdapter extends AbstractRecyclerViewAdapter<ExampleData, MainAdapter.ItemHolder> {

    @Inject
    public MainAdapter() {
        super(ExampleData.class);
    }

    @Override
    protected MainAdapter.ItemHolder instantiateViewHolder(View view) {
        return new ItemHolder(view);
    }

    @Override
    protected int getViewHolderLayoutResource() {
        return R.layout.item_main;
    }

    @Override
    protected void onBindViewHolder(ItemHolder holder, ExampleData exampleData) {
        holder.textViewId.setText(exampleData.getId());
        holder.textViewContent.setText(exampleData.getContent());
    }

    class ItemHolder extends AbstractRecyclerViewAdapter.ItemHolder {
        @BindView(R.id.text_id)
        TextView textViewId;

        @BindView(R.id.text_content)
        TextView textViewContent;

        public ItemHolder(View itemView) {
            super(itemView);
        }
    }
}
