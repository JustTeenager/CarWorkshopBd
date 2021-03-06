package com.example.carworkshopbd.detail

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.carworkshopbd.R
import com.example.carworkshopbd.databinding.FragmentDetailBinding
import com.example.carworkshopbd.db.CarDB
import com.example.carworkshopbd.db.entity.DiscountCard
import com.example.carworkshopbd.db.entity.Manager
import com.example.carworkshopbd.db.entity.Mechanic
import com.example.carworkshopbd.db.entity.Problem
import com.example.carworkshopbd.db.entity.releation.AutoWithProblems
import com.example.carworkshopbd.db.entity.releation.CarWorkshopWithAll
import com.example.carworkshopbd.db.entity.releation.ClientAll
import com.example.carworkshopbd.main.Table
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val binding by viewBinding(FragmentDetailBinding::bind)
    private val viewModel by viewModels<DetailViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return DetailViewModel(
                    arguments?.getParcelable(TABLE_KEY) ?: Table.None,
                    arguments?.getLong(ID_KEY),
                    CarDB.getDatabase(requireContext()),
                ) as T
            }

        }
    }

    private val views by lazy(LazyThreadSafetyMode.NONE) {
        listOf(
            binding.firstTextView,
            binding.secondTextView,
            binding.thirdTextView,
            binding.fourthTextView,
            binding.fifthTextView,
            binding.sixTextView,
            binding.sevenTextView,
        )

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            arguments?.getParcelable<Table>(TABLE_KEY)?.let {
                tvDescription.text = it.value
            }
            lifecycleScope.launch {
                viewModel.detailFlow.flowWithLifecycle(
                    viewLifecycleOwner.lifecycle
                ).collectLatest {
                    val list = when (it) {
                        is AutoWithProblems -> {
                            listOf(
                                "????????: ${it.color}",
                                "????????????: ${it.model}",
                                "??????????: ${it.regNumber}",
                                "???????? ???????????????????? ??????????????: ${it.lastRepair}",
                                "???????????????? ${it.problems?.joinToString(", ") { pr -> pr.description }}",
                            )
                        }
                        is ClientAll -> {
                            listOf(
                                "??????: ${it.client.name}",
                                "?????????? ????????????????: ${it.client.number}",
                                "?????????? ??????????: ${it.discountCard?.number ?: "??????"}",
                                "????????????: ${it.discountCard?.discount ?: "??????"}",
                                "???????????????????? ${it.autoWithProblems}",
                            )
                        }
                        is Mechanic -> {
                            listOf(
                                "??????: ${it.name}",
                                "??????????????: ${it.rating}",
                                "????????: ${it.experience}",
                                "????????????????: ${it.salary}",
                            )
                        }
                        is CarWorkshopWithAll -> {
                            listOf(
                                "??????????: ${it.workshop.address}",
                                "??????????????????: ${it.workshop.income}",
                                "??????????????: ${it.clients.joinToString(", ") { cl -> cl.name }}",
                                "????????????????: ${
                                    it.mechanics.joinToString(", ") { mh ->
                                        mh.name
                                    }
                                }",
                                "?????????????????? ${
                                    it.managers.joinToString(", ") { man ->
                                        man.name
                                    }
                                }",
                            )
                        }
                        is Problem -> {
                            listOf(
                                "????????: ${it.cost}",
                                "????????????????: ${it.description}"
                            )
                        }
                        is DiscountCard -> {
                            listOf(
                                "??????????: ${it.number}",
                                "????????????: ${it.discount}",
                                "????????????: ${it.subtype}"
                            )
                        }
                        is Manager -> {
                            listOf(
                                "??????: ${it.name}",
                                "??????????: ${it.number}",
                                "??????????????????????????: ${it.specialization}",
                                "????????: ${it.experience}",
                                "????????????????: ${it.salary}",
                            )
                        }
                        else -> {
                            emptyList()
                        }
                    }
                    showData(
                        texts = list,
                        view = views.subList(0, list.size)
                    )
                }
            }
        }
    }

    private fun showData(texts: List<String>, view: List<TextView>) {
        texts.forEachIndexed { index, text ->
            view[index].isVisible = true
            view[index].text = text
        }
    }

    companion object {

        private const val TABLE_KEY = "table_key"
        private const val ID_KEY = "id_key"

        fun newInstance(table: Table, id: Long): DetailFragment {
            val args = bundleOf(TABLE_KEY to table, ID_KEY to id)
            val fragment = DetailFragment()
            fragment.arguments = args
            return fragment
        }
    }
}