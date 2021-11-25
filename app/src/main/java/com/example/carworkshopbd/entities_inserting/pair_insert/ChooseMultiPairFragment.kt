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
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.carworkshopbd.R
import com.example.carworkshopbd.databinding.FragmentMultiplePairChooseBinding
import com.example.carworkshopbd.db.CarDB
import com.example.carworkshopbd.entities_inserting.values_insert.InsertFragment
import com.example.carworkshopbd.main.MainFragment
import com.example.carworkshopbd.utils.replaceClearedStackTo
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ChooseMultiPairFragment : Fragment(R.layout.fragment_multiple_pair_choose) {

    private val binding by viewBinding(FragmentMultiplePairChooseBinding::bind)
    private val viewModel by viewModels<ChooseMultiPairViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ChooseMultiPairViewModel(
                    CarDB.getDatabase(requireContext()),
                ) as T
            }
        }
    }

    private val autoAdapter by lazy {
        ChooserAdapter(
            scope = lifecycleScope
        )
    }

    private val discountAdapter by lazy {
        ChooserAdapter(
            scope = lifecycleScope
        )
    }

    private val workshopAdapter by lazy {
        ChooserAdapter(
            scope = lifecycleScope
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindUI()

        viewLifecycleOwner.lifecycleScope.launch {
            launch {
                viewModel.autoFlow
                    .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                    .collectLatest {
                        autoAdapter.setData(it)
                    }
            }
            launch {
                viewModel.discountFlow
                    .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                    .collectLatest {
                        discountAdapter.setData(it)
                    }
            }
            launch {
                viewModel.workshopFlow
                    .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                    .collectLatest {
                        workshopAdapter.setData(it)
                    }
            }
        }
    }

    private fun bindUI() = with(binding) {
        btnSubmit.setOnClickListener {
            viewModel.insertData(
                tempItem = requireArguments().get(TEMP_ENTITY_KEY) as InsertFragment.TempEntity,
                autoId = autoAdapter.getChosenIds().firstOrNull(),
                discountId = discountAdapter.getChosenIds().firstOrNull(),
                workId = workshopAdapter.getChosenIds().firstOrNull()
            )
            requireActivity().supportFragmentManager.replaceClearedStackTo(MainFragment())
        }

        rvAutoTable.adapter = autoAdapter
        rvAutoTable.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )

        rvDiscountTable.adapter = discountAdapter
        rvDiscountTable.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )

        rvWorkShopTable.adapter = workshopAdapter
        rvWorkShopTable.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadData()
    }

    companion object {
        private const val TEMP_ENTITY_KEY = "temp_entity_key"
        fun newInstance(tempEntity: InsertFragment.TempEntity) = ChooseMultiPairFragment().apply {
            arguments = bundleOf(
                TEMP_ENTITY_KEY to tempEntity
            )
        }
    }
}