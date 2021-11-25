package com.example.carworkshopbd.entities_showing

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.SearchView.OnQueryTextListener
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.carworkshopbd.EntitiesAdapter
import com.example.carworkshopbd.R
import com.example.carworkshopbd.auth.AuthFragment
import com.example.carworkshopbd.databinding.FragmentTablesListBinding
import com.example.carworkshopbd.detail.DetailFragment
import com.example.carworkshopbd.entities_inserting.values_insert.InsertFragment
import com.example.carworkshopbd.holders.EntityHolder
import com.example.carworkshopbd.main.Table
import com.example.carworkshopbd.utils.SharedManager
import com.example.carworkshopbd.utils.addTo
import com.example.carworkshopbd.utils.gone
import com.example.carworkshopbd.utils.replaceClearedStackTo
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class EntityShowingFragment : Fragment(R.layout.fragment_tables_list) {

    private val binding by viewBinding(FragmentTablesListBinding::bind)

    private var viewModel: EntityShowingViewModel? = null

    private val sharedManager by lazy { SharedManager(requireContext()) }

    private val adapter by lazy {
        EntitiesAdapter(
            ::onItemClick
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val table = requireArguments().get(TABLE_KEY) as Table

        setupToolbar()

        val search = object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }

        }

        binding.searchView.setOnQueryTextListener(search)
        binding.searchView.imeOptions = EditorInfo.IME_ACTION_DONE

        binding.rvTables.adapter = adapter
        binding.tvDescription.text = table.value

        if (sharedManager.typeUser == SharedManager.MANAGER_TYPE) binding.fabInsert.gone()

        binding.fabInsert.setOnClickListener {
            requireActivity().supportFragmentManager.addTo(InsertFragment.newInstance(table))
        }

        setupTouchHelper()

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel?.dataFlow
                ?.flowWithLifecycle(viewLifecycleOwner.lifecycle)
                ?.collectLatest {
                    adapter.setData(it)
                }
        }
    }

    private fun setupToolbar() {
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
    }

    private fun setupTouchHelper() {
        val helperCallback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if (viewHolder is EntityHolder) {
                    viewModel?.deleteItem(viewHolder.item)
                }
            }
        }

        val helper = ItemTouchHelper(helperCallback)
        helper.attachToRecyclerView(binding.rvTables)
    }

    override fun onResume() {
        super.onResume()
        viewModel?.loadData()
    }

    private fun onItemClick(id: Long) {
        requireActivity().supportFragmentManager.addTo(
            DetailFragment.newInstance(
                requireArguments().getParcelable(TABLE_KEY) ?: Table.None, id
            )
        )
    }

    companion object {
        private const val TABLE_KEY = "table_key"

        fun newInstance(
            vm: EntityShowingViewModel,
            table: Table
        ): EntityShowingFragment = EntityShowingFragment().apply {
            viewModel = vm
            arguments = bundleOf(TABLE_KEY to table)
        }
    }
}