package gortea.jgmax.animalstorage.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import gortea.jgmax.animalstorage.AnimalListApp
import gortea.jgmax.animalstorage.data.model.AnimalItem
import gortea.jgmax.animalstorage.databinding.AnimalListFragmentBinding
import gortea.jgmax.animalstorage.ui.list.adapter.AnimalListAdapter
import gortea.jgmax.animalstorage.ui.list.decorators.HorizontalItemDecorator
import gortea.jgmax.animalstorage.ui.list.decorators.VerticalItemDecorator
import gortea.jgmax.animalstorage.ui.navigation.FragmentNavigator
import gortea.jgmax.animalstorage.ui.utils.*
import gortea.jgmax.animalstorage.ui.view.AnimalListView
import gortea.jgmax.animalstorage.ui.view.StateObserver
import gortea.jgmax.animalstorage.ui.viewmodel.AnimalListViewModel
import gortea.jgmax.animalstorage.ui.viewmodel.states.AnimalListState

class AnimalListFragment : Fragment(), AnimalListView, StateObserver, AnimalListAdapter.OnItemClickListener {
    private var _binding: AnimalListFragmentBinding? = null
    private val binding: AnimalListFragmentBinding
        get() = requireNotNull(_binding)

    private val adapter: AnimalListAdapter = AnimalListAdapter(this)
    private val viewModel: AnimalListViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val application = activity?.application as? AnimalListApp ?: return
        viewModel.attachDao(application.animalListDao)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AnimalListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun setupRecyclerView() {
        binding.animalListRv.let {
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = adapter
            it.addItemDecoration(
                HorizontalItemDecorator(
                    leftDivider = ITEM_LEFT_MARGIN_DP.toPx(requireContext()),
                    rightDivider = ITEM_RIGHT_MARGIN_DP.toPx(requireContext())
                )
            )
            it.addItemDecoration(
                VerticalItemDecorator(
                    topDivider = ITEM_TOP_MARGIN_DP.toPx(requireContext()),
                    mediumDivider = ITEM_MEDIUM_VERTICAL_MARGIN_DP.toPx(requireContext()),
                    bottomDivider = ITEM_BOTTOM_MARGIN_DP.toPx(requireContext())
                )
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()
        observeState()
        viewModel.fetchItems()
        binding.addCatBtn.setOnClickListener { onAddClick() }
    }

    private fun onAddClick() {
        openFragment(UpdateAnimalFragment())
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    // StateObserver implementation
    override fun observeState() {
        viewModel.getState().observe(viewLifecycleOwner) { state ->
            when (state) {
                is AnimalListState.Update -> adapter.submitList(state.newList)
                is AnimalListState.UpdateError<*> -> showError(state.message)
                else -> return@observe
            }
        }
    }

    override fun <T> showError(message: T) {
        Toast.makeText(context, message.getMessageString(requireContext()), Toast.LENGTH_SHORT)
            .show()
    }

    // AnimalListView implementation
    override fun openFragment(fragment: Fragment) {
        (activity as? FragmentNavigator)?.openFragment(fragment, addToBackStack = true)
    }

    // OnItemClickListener implementation
    override fun onDeleteClick(item: AnimalItem) {
        viewModel.deleteItem(item)
    }

    override fun onEditClick(item: AnimalItem) {
        val fragment = UpdateAnimalFragment.getInstance(item)
        openFragment(fragment)
    }
}