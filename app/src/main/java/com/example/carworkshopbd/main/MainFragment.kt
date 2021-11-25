package com.example.carworkshopbd.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.carworkshopbd.R
import com.example.carworkshopbd.TablesAdapter
import com.example.carworkshopbd.auth.AuthFragment
import com.example.carworkshopbd.databinding.FragmentTablesListBinding
import com.example.carworkshopbd.db.CarDB
import com.example.carworkshopbd.entities_showing.*
import com.example.carworkshopbd.utils.SharedManager
import com.example.carworkshopbd.utils.addTo
import com.example.carworkshopbd.utils.gone
import com.example.carworkshopbd.utils.replaceClearedStackTo
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.launch

@Suppress("UNCHECKED_CAST")
class MainFragment : Fragment(R.layout.fragment_tables_list) {

    private val binding by viewBinding(FragmentTablesListBinding::bind)
    private val viewModel by viewModels<MainViewModel>()

    private val sharedManager by lazy { SharedManager(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fabInsert.gone()
        binding.searchView.gone()

        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_exit_account -> {
                    sharedManager.isAuth = false
                    requireActivity().supportFragmentManager.replaceClearedStackTo(AuthFragment())
                    true
                }
                else -> {
                    false
                }
            }
        }

        binding.rvTables.layoutManager = LinearLayoutManager(context)
        binding.rvTables.adapter = TablesAdapter {
            viewModel.navigateTo(it)
        }.apply {
            setData(viewModel.getCorrectData(sharedManager.typeUser))
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.navigatorFlow
                .flowWithLifecycle(lifecycle)
                .drop(1)
                .collectLatest {
                    requireActivity()
                        .supportFragmentManager
                        .addTo(getFragmentByTable(it))
                }
        }
    }

    private fun getFragmentByTable(table: Table): Fragment {
        val db = CarDB.getDatabase(requireContext())
        return when (table) {
            Table.Automobile -> {
                EntityShowingFragment.newInstance(
                    viewModels<EntityShowingViewModel> {
                        createFactory(AutomobileViewModel(db))
                    }.value,
                    table
                )
            }
            Table.Client -> {
                EntityShowingFragment.newInstance(
                    viewModels<ClientViewModel> {
                        createFactory(ClientViewModel(db))
                    }.value,
                    table
                )
            }
            Table.Manager -> {
                EntityShowingFragment.newInstance(
                    viewModels<ManagerViewModel> {
                        createFactory(ManagerViewModel(db))
                    }.value,
                    table
                )
            }
            Table.CarWorkshop -> {
                EntityShowingFragment.newInstance(
                    viewModels<CarWorkshopViewModel> {
                        createFactory(CarWorkshopViewModel(db))
                    }.value,
                    table
                )
            }
            Table.DiscountCard -> {
                EntityShowingFragment.newInstance(
                    viewModels<DiscountCardViewModel> {
                        createFactory(DiscountCardViewModel(db))
                    }.value,
                    table
                )
            }
            Table.Mechanic -> {
                EntityShowingFragment.newInstance(
                    viewModels<MechanicViewModel> {
                        createFactory(MechanicViewModel(db))
                    }.value,
                    table
                )
            }
            Table.Problem -> {
                EntityShowingFragment.newInstance(
                    viewModels<ProblemsViewModel> {
                        createFactory(ProblemsViewModel(db))
                    }.value,
                    table
                )
            }
            Table.None -> {
                MainFragment()
            }
        }
    }

    private fun createFactory(model: EntityShowingViewModel): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            override fun <K : ViewModel> create(modelClass: Class<K>): K {
                return model as K
            }
        }
    }
}