package com.example.carworkshopbd.entities_inserting.pair_insert

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.carworkshopbd.R
import com.example.carworkshopbd.databinding.FragmentChoosePairBinding
import com.example.carworkshopbd.db.CarDB
import com.example.carworkshopbd.entities_inserting.values_insert.InsertFragment
import com.example.carworkshopbd.main.MainFragment
import com.example.carworkshopbd.main.Table
import com.example.carworkshopbd.utils.replaceClearedStackTo
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ChoosePairFragment : Fragment(R.layout.fragment_choose_pair) {

    private val binding by viewBinding(FragmentChoosePairBinding::bind)
    private val viewModel by viewModels<ChoosePairFragmentViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ChoosePairFragmentViewModel(
                    CarDB.getDatabase(requireContext()),
                    requireArguments().get(TABLE_KEY) as Table
                ) as T
            }
        }
    }

    private val adapter by lazy {
        ChooserAdapter(
            maxChosen = requireArguments().getInt(PAIRING_NUM_KEY),
            scope = lifecycleScope
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvTables.adapter = adapter

        binding.btnSubmit.setOnClickListener {
            viewModel.insertCorrectItem(
                tempItem = requireArguments().get(TEMP_ENTITY_KEY) as InsertFragment.TempEntity,
                list = adapter.getChosenIds()
            )
            requireActivity().supportFragmentManager.replaceClearedStackTo(MainFragment())
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.dataFlow
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collectLatest {
                    adapter.setData(it)
                }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadCorrectData()
    }

    companion object {
        private const val TABLE_KEY = "table_key"
        private const val PAIRING_NUM_KEY = "pairing_num_key"
        private const val TEMP_ENTITY_KEY = "temp_entity_key"
        fun newInstance(
            table: Table,
            correctPairingNum: Int,
            tempEntity: InsertFragment.TempEntity
        ) = ChoosePairFragment().apply {
            arguments = bundleOf(
                TABLE_KEY to table,
                PAIRING_NUM_KEY to correctPairingNum,
                TEMP_ENTITY_KEY to tempEntity
            )
        }
    }
}