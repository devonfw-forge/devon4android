package com.example.base.baseapp.modules.main

import android.view.View
import android.widget.TextView

import com.example.base.baseapp.R
import com.example.base.baseapp.model.ExampleData
import com.example.base.baseapp.utils.AbstractRecyclerViewAdapter

import javax.inject.Inject

import butterknife.BindView

class MainAdapter @Inject
constructor() : AbstractRecyclerViewAdapter<ExampleData, MainAdapter.ItemHolder>(ExampleData::class.java) {

    override val viewHolderLayoutResource: Int
        get() = R.layout.item_main

    override fun instantiateViewHolder(view: View): MainAdapter.ItemHolder {
        return ItemHolder(view)
    }

    override fun onBindViewHolder(holder: ItemHolder, data: ExampleData) {
        holder.textViewId.text = data.id
        holder.textViewContent.text = data.content
    }

    open class ItemHolder(itemView: View) : AbstractRecyclerViewAdapter.ItemHolder(itemView) {
        @BindView(R.id.text_id)
        lateinit var textViewId: TextView

        @BindView(R.id.text_content)
        lateinit var textViewContent: TextView
    }
}
