package gortea.jgmax.animalstorage.ui.list.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import gortea.jgmax.animalstorage.data.model.AnimalItem
import gortea.jgmax.animalstorage.ui.list.adapter.AnimalListAdapter

abstract class AnimalListViewHolder(root: View) : RecyclerView.ViewHolder(root) {
    abstract fun bind(item: AnimalItem, listener: AnimalListAdapter.OnItemClickListener?)
    abstract fun bind(item: AnimalItem, payloads: List<Any>)
}