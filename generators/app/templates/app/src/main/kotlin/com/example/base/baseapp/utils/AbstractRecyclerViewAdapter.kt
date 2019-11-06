package com.example.base.baseapp.utils

import android.support.v7.util.SortedList
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import java.util.ArrayList

import butterknife.ButterKnife
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

abstract class AbstractRecyclerViewAdapter<T, E : AbstractRecyclerViewAdapter.ItemHolder> (tClass: Class<T>, private val additionalElement: ConstantElement = ConstantElement.NONE) : RecyclerView.Adapter<AbstractRecyclerViewAdapter.ItemHolder>() {

    private val onItemClickSubject = PublishSubject.create<T>()
    private val onItemLongClickSubject = PublishSubject.create<T>()
    private val onConstantClickSubject = PublishSubject.create<View>()

    protected val elements: SortedList<T>
    private var originalElements: List<T>? = null

    protected val constantItemLayoutResource: Int
        get() =
            throw UnsupportedOperationException("Implement getConstantItemLayoutResource in your adapter")

    val elementClicks: Observable<T>
        get() = onItemClickSubject.hide()

    val elementLongClicks: Observable<T>
        get() = onItemLongClickSubject.hide()

    val constantElementClicks: Observable<View>
        get() = onConstantClickSubject.hide()

    protected abstract val viewHolderLayoutResource: Int

    enum class ViewType {
        ITEM, CONSTANT_ITEM
    }

    enum class ConstantElement {
        NONE, TOP, BOTTOM
    }

    init {
        elements = SortedList(tClass, object : SortedList.Callback<T>() {

            override fun onInserted(position: Int, count: Int) {
                notifyItemRangeInserted(position, count)
            }

            override fun onRemoved(position: Int, count: Int) {
                notifyItemRangeRemoved(position, count)
            }

            override fun onMoved(fromPosition: Int, toPosition: Int) {
                notifyItemMoved(fromPosition, toPosition)
            }

            override fun compare(o1: T, o2: T): Int {
                return compareElements(o1, o2)
            }

            override fun onChanged(position: Int, count: Int) {
                notifyItemRangeChanged(position, count)
            }

            override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(item1: T, item2: T): Boolean {
                return item1 === item2
            }
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewTypeInt: Int): ItemHolder {
        val viewType = ViewType.values()[viewTypeInt]
        val view: View
        if (viewType == ViewType.ITEM) {
            view = LayoutInflater.from(parent.context)
                    .inflate(viewHolderLayoutResource, parent, false)
            return instantiateViewHolder(view)
        } else {
            view = LayoutInflater.from(parent.context)
                    .inflate(constantItemLayoutResource, parent, false)
            return ItemHolder(view)
        }
    }

    override fun onBindViewHolder(holder: AbstractRecyclerViewAdapter.ItemHolder, position: Int) {
        if (position < elements.size()) {
            val element = elements.get(position)
            holder.itemView.setOnClickListener { _ -> onItemClickSubject.onNext(element) }
            holder.itemView.setOnLongClickListener { _ ->
                onItemLongClickSubject.onNext(element)
                true
            }
            @Suppress("UNCHECKED_CAST")
            onBindViewHolder(holder as E, element)
        } else {
            holder.itemView.setOnClickListener { _ -> onConstantClickSubject.onNext(holder.itemView) }
        }
    }

    override fun getItemViewType(position: Int): Int {
        when (additionalElement) {
            AbstractRecyclerViewAdapter.ConstantElement.TOP -> return if (position == 0) ViewType.CONSTANT_ITEM.ordinal else ViewType.ITEM.ordinal
            AbstractRecyclerViewAdapter.ConstantElement.BOTTOM -> return if (position == elements.size()) ViewType.CONSTANT_ITEM.ordinal else ViewType.ITEM.ordinal
            else -> return ViewType.ITEM.ordinal
        }
    }

    override fun getItemCount(): Int {
        return elements.size() + if (additionalElement != ConstantElement.NONE) 1 else 0
    }

    fun setElements(newElements: List<T>) {
        originalElements = ArrayList(newElements)
        elements.replaceAll(originalElements!!)
    }

    protected fun compareElements(o1: T, o2: T): Int {
        return originalElements!!.indexOf(o1) - originalElements!!.indexOf(o2)
    }

    protected abstract fun instantiateViewHolder(view: View): E

    protected abstract fun onBindViewHolder(holder: E, data: T)

    open class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            ButterKnife.bind(this, itemView)
        }
    }
}
