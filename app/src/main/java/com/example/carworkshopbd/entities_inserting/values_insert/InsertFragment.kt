package com.example.carworkshopbd.entities_inserting.values_insert

import android.os.Bundle
import android.os.Parcelable
import android.text.InputType
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.carworkshopbd.R
import com.example.carworkshopbd.ShowableInList
import com.example.carworkshopbd.databinding.FragmentInsertBinding
import com.example.carworkshopbd.db.CarDB
import com.example.carworkshopbd.db.entity.*
import com.example.carworkshopbd.entities_inserting.pair_insert.ChooseMultiPairFragment
import com.example.carworkshopbd.entities_inserting.pair_insert.ChoosePairFragment
import com.example.carworkshopbd.main.Table
import com.example.carworkshopbd.utils.addTo
import com.example.carworkshopbd.utils.gone
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

class InsertFragment : Fragment(R.layout.fragment_insert) {

    private val viewModel by viewModels<InsertViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return InsertViewModel(
                    CarDB.getDatabase(requireContext()),
                    requireArguments().get(TABLE_KEY) as Table
                ) as T
            }
        }
    }

    private val viewBinding by viewBinding(FragmentInsertBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareUI(requireArguments().get(TABLE_KEY) as Table)
    }

    private fun prepareUI(table: Table) = with(viewBinding) {

        btnSubmit.setOnClickListener {
            if (isItemValidated()) {
                goToNextCreatingStep(requireArguments().get(TABLE_KEY) as Table)
            } else Toast.makeText(
                requireContext(),
                R.string.invalid_item,
                Toast.LENGTH_SHORT
            ).show()
        }

        when (table) {
            Table.Automobile -> {
                prepareForAutomobileTable()
            }
            Table.CarWorkshop -> {
                prepareForCarWorkshopTable()
            }
            Table.Client -> {
                prepareForClientTable()
            }
            Table.DiscountCard -> {
                prepareForDiscountCardTable()
            }
            Table.Manager -> {
                prepareForManagerTable()
            }
            Table.Mechanic -> {
                prepareForMechanicTable()
            }
            Table.Problem -> {
                prepareForProblemsTable()
            }
            Table.None -> {
                etFirst.gone()
                etSecond.gone()
                etThird.gone()
                etFourth.gone()
                etFifth.gone()
                etSixth.gone()
            }
        }
    }

    private fun goToNextCreatingStep(table: Table) {
        with(viewBinding) {
            val tempEntity = TempEntity(
                etFirst.text.toString(),
                etSecond.text.toString(),
                etThird.text.toString(),
                etFourth.text.toString(),
                etFifth.text.toString()
            )
            when {
                isSinglePairingNeeded(table) -> {
                    lifecycleScope.launch {
                        requireActivity().supportFragmentManager.addTo(
                            ChoosePairFragment.newInstance(
                                requireArguments().get(TABLE_KEY) as Table,
                                viewModel.getCorrectPairingNum(),
                                tempEntity
                            )
                        )
                    }
                }
                isMultiplePairingNeeded(table) -> {
                    requireActivity().supportFragmentManager.addTo(
                        ChooseMultiPairFragment.newInstance(tempEntity)
                    )
                }
                else -> {
                    viewModel.insert(createRightItem(table))
                    requireActivity().onBackPressed()
                }
            }
        }
    }

    private fun isItemValidated(): Boolean = with(viewBinding) {
        !(
                (etFirst.text.isNullOrBlank() && etFirst.isVisible) ||
                        (etSecond.text.isNullOrBlank() && etSecond.isVisible) ||
                        (etThird.text.isNullOrBlank() && etThird.isVisible) ||
                        (etFourth.text.isNullOrBlank() && etFourth.isVisible) ||
                        (etFifth.text.isNullOrBlank() && etFifth.isVisible) ||
                        (etSixth.text.isNullOrBlank() && etSixth.isVisible)
                )
    }

    private fun isSinglePairingNeeded(table: Table): Boolean =
        (
                table == Table.Automobile ||
                        table == Table.Mechanic ||
                        table == Table.Manager
                )

    private fun isMultiplePairingNeeded(table: Table): Boolean = table == Table.Client

    private fun createRightItem(table: Table): ShowableInList? = with(viewBinding) {
        return TempEntity(
            etFirst.text.toString(),
            etSecond.text.toString(),
            etThird.text.toString(),
            etFourth.text.toString(),
            etFifth.text.toString()
        ).getCorrectItemFromTable(table)
    }

    private fun FragmentInsertBinding.prepareForProblemsTable() {
        tvDescription.text = "Новая проблема"
        etFirst.hint = "Описание"

        etSecond.hint = "Цена"
        etSecond.inputType = InputType.TYPE_CLASS_NUMBER

        etThird.gone()
        etFourth.gone()
        etFifth.gone()
        etSixth.gone()
    }

    private fun FragmentInsertBinding.prepareForMechanicTable() {
        tvDescription.text = "Новый механик"

        etFirst.hint = "ФИО"
        etSecond.hint = "Опыт работы"

        etThird.hint = "Рейтинг"
        etThird.inputType = InputType.TYPE_CLASS_NUMBER

        etFourth.hint = "Заработная плата"
        etFourth.inputType = InputType.TYPE_CLASS_NUMBER

        etFifth.gone()
        etSixth.gone()
    }

    private fun FragmentInsertBinding.prepareForManagerTable() {
        tvDescription.text = "Новый менеджер"

        etFirst.hint = "ФИО"
        etSecond.hint = "Опыт работы"

        etThird.hint = "Заработная плата"
        etThird.inputType = InputType.TYPE_CLASS_NUMBER

        etFourth.hint = "Специализация"

        etFifth.hint = "Номер"
        etFifth.inputType = InputType.TYPE_CLASS_PHONE

        etSixth.gone()
    }

    private fun FragmentInsertBinding.prepareForDiscountCardTable() {
        tvDescription.text = "Новая скидочная карта"

        etFirst.hint = "Номер карты"
        etFirst.inputType = InputType.TYPE_CLASS_NUMBER

        etSecond.hint = "Скидка"
        etSecond.inputType = InputType.TYPE_CLASS_NUMBER

        etThird.gone()
        etFourth.gone()
        etFifth.gone()
        etSixth.gone()
    }

    private fun FragmentInsertBinding.prepareForClientTable() {
        tvDescription.text = "Новый клиент"

        etFirst.hint = "ФИО"

        etSecond.hint = "Номер"
        etSecond.inputType = InputType.TYPE_CLASS_PHONE

        etThird.gone()
        etFourth.gone()
        etFifth.gone()
        etSixth.gone()
    }

    private fun FragmentInsertBinding.prepareForCarWorkshopTable() {
        tvDescription.text = "Новая мастерская"

        etFirst.hint = "Адрес"

        etSecond.hint = "Доход"
        etSecond.inputType = InputType.TYPE_CLASS_NUMBER

        etThird.gone()
        etFourth.gone()
        etFifth.gone()
        etSixth.gone()
    }

    private fun FragmentInsertBinding.prepareForAutomobileTable() {
        tvDescription.text = "Новый автомобиль"

        etFirst.hint = "Модель"
        etSecond.hint = "Номер машины"
        etThird.hint = "Цвет"
        etFourth.hint = "Дата последнего ремонта"

        etFifth.gone()
        etSixth.gone()
    }


    @Parcelize
    class TempEntity(
        val firstString: String,
        val secondString: String,
        val thirdString: String,
        val fourthString: String,
        val fifthString: String,
    ) : Parcelable {
        fun getCorrectItemFromTable(table: Table): ShowableInList? {
            return when (table) {
                Table.Automobile -> {
                    Automobile.newInstance(
                        firstString,
                        secondString,
                        thirdString,
                        fourthString
                    )
                }
                Table.Manager -> Manager.newInstance(
                    firstString,
                    secondString,
                    thirdString,
                    fourthString,
                    fifthString
                )
                Table.Mechanic -> Mechanic.newInstance(
                    firstString,
                    secondString,
                    thirdString,
                    fourthString
                )
                Table.Client -> Client.newInstance(
                    firstString,
                    secondString
                )
                Table.DiscountCard -> DiscountCard.newInstance(
                    firstString,
                    secondString,
                )
                Table.CarWorkshop -> CarWorkshop.newInstance(
                    firstString,
                    secondString
                )
                Table.Problem -> Problem.newInstance(
                    firstString,
                    secondString
                )
                Table.None -> null
            }
        }
    }

    companion object {
        private const val TABLE_KEY = "table_key"
        fun newInstance(table: Table) = InsertFragment().apply {
            arguments = bundleOf(TABLE_KEY to table)
        }
    }
}