package gortea.jgmax.animalstorage.ui.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import gortea.jgmax.animalstorage.R
import gortea.jgmax.animalstorage.data.model.AnimalItem
import gortea.jgmax.animalstorage.databinding.AnimalListItemBinding
import gortea.jgmax.animalstorage.ui.list.holder.AnimalListViewHolder
import gortea.jgmax.animalstorage.ui.list.holder.implementations.AnimalItemViewHolder
import gortea.jgmax.animalstorage.ui.list.holder.implementations.FooterViewHolder

class AnimalListAdapter(
    private val listener: OnItemClickListener? = null
) : ListAdapter<AnimalItem?, RecyclerView.ViewHolder>(comparator) {

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position) == null) {
            R.layout.animal_list_footer
        } else {
            R.layout.animal_list_item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.animal_list_item -> {
                val binding = AnimalListItemBinding.inflate(inflater, parent, false)
                AnimalItemViewHolder(binding)
            }
            else -> {
                val view = inflater.inflate(R.layout.animal_list_footer, parent, false)
                FooterViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is AnimalListViewHolder) {
            val item = getItem(position) ?: return
            holder.bind(item, listener)
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNullOrEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            if (holder is AnimalListViewHolder) {
                val item = getItem(position) ?: return
                holder.bind(item, payloads)
            }
        }
    }

    interface OnItemClickListener {
        fun onDeleteClick(item: AnimalItem)
        fun onEditClick(item: AnimalItem)
    }

    private companion object {
        @JvmStatic
        private val comparator = object : DiffUtil.ItemCallback<AnimalItem?>() {
            override fun areItemsTheSame(oldItem: AnimalItem, newItem: AnimalItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: AnimalItem, newItem: AnimalItem): Boolean {
                return oldItem == newItem
            }

            override fun getChangePayload(oldItem: AnimalItem, newItem: AnimalItem): Any? {
                return when {
                    oldItem.id == newItem.id && oldItem != newItem -> newItem
                    else -> super.getChangePayload(oldItem, newItem)
                }
            }
        }
    }
}