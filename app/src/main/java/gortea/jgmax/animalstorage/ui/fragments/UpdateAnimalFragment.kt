package gortea.jgmax.animalstorage.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import gortea.jgmax.animalstorage.R
import gortea.jgmax.animalstorage.data.model.AnimalItem
import gortea.jgmax.animalstorage.databinding.UpdateAnimalFragmentBinding
import gortea.jgmax.animalstorage.ui.navigation.ActionBarController
import gortea.jgmax.animalstorage.ui.utils.getMessageString
import gortea.jgmax.animalstorage.ui.utils.getString
import gortea.jgmax.animalstorage.ui.view.StateObserver
import gortea.jgmax.animalstorage.ui.viewmodel.AnimalListViewModel
import gortea.jgmax.animalstorage.ui.viewmodel.states.AnimalListState

class UpdateAnimalFragment : Fragment(), StateObserver {
    private var _binding: UpdateAnimalFragmentBinding? = null
    private val binding: UpdateAnimalFragmentBinding
        get() = requireNotNull(_binding)

    private val viewModel: AnimalListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val actionBarController = context as? ActionBarController
        actionBarController?.setActionBarVisibility(true)
        _binding = UpdateAnimalFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            updateBtn.setOnClickListener { onAddClick() }
            if (arguments != null) {
                val item = arguments?.getSerializable(ITEM_KEY) as AnimalItem
                updateBtn.setText(R.string.edit_btn)
                nameEt.setText(item.name)
                ageEt.setText(item.age.toString())
                breedEt.setText(item.breed)
            } else {
                updateBtn.setText(R.string.add_btn)
            }
        }
        observeState()
    }

    private fun onAddClick() {
        binding.let {
            val name = it.nameEt.getString()
            val age = it.ageEt.getString()
            val breed = it.breedEt.getString()
            if (arguments != null) {
                val item = arguments?.getSerializable(ITEM_KEY) as AnimalItem
                viewModel.updateItem(
                    id = item.id,
                    name = name,
                    age = age,
                    breed = breed
                )
            } else {
                viewModel.addNewItem(
                    name = name,
                    age = age,
                    breed = breed
                )
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    // StateObserver Implementation
    override fun observeState() {
        viewModel.getState().observe(viewLifecycleOwner) { state ->
            when (state) {
                is AnimalListState.UpdateError<*> -> showError(state.message)
                is AnimalListState.SuccessInsert -> parentFragmentManager.popBackStack()
                else -> return@observe
            }
        }
    }

    override fun <T> showError(message: T) {
        Toast.makeText(context, message.getMessageString(requireContext()), Toast.LENGTH_SHORT)
            .show()
    }

    companion object {
        private const val ITEM_KEY = "ITEM_KEY"
        fun getInstance(item: AnimalItem): UpdateAnimalFragment {
            val args = Bundle()
            args.putSerializable(ITEM_KEY, item)
            return UpdateAnimalFragment().apply { arguments = args }
        }
    }
}