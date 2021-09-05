package gortea.jgmax.animalstorage.ui.list.holder.implementations

import gortea.jgmax.animalstorage.data.model.AnimalItem
import gortea.jgmax.animalstorage.databinding.AnimalListItemBinding
import gortea.jgmax.animalstorage.ui.list.adapter.AnimalListAdapter
import gortea.jgmax.animalstorage.ui.list.holder.AnimalListViewHolder

class AnimalItemViewHolder(private val binding: AnimalListItemBinding) :
    AnimalListViewHolder(binding.root) {
    override fun bind(item: AnimalItem, listener: AnimalListAdapter.OnItemClickListener?) {
        binding.apply {
            nameTv.text = item.name
            ageTv.text = item.age.toString()
            breedTv.text = item.breed
            deleteBtn.setOnClickListener { listener?.onDeleteClick(item) }
            editBtn.setOnClickListener { listener?.onEditClick(item) }
        }
    }
}