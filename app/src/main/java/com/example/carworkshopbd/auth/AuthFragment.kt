package com.example.carworkshopbd.auth

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.carworkshopbd.R
import com.example.carworkshopbd.databinding.FragmentAuthBinding
import com.example.carworkshopbd.main.MainFragment
import com.example.carworkshopbd.utils.SharedManager
import com.example.carworkshopbd.utils.replaceTo
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AuthFragment : Fragment(R.layout.fragment_auth) {

    private val viewBinding by viewBinding(FragmentAuthBinding::bind)
    private val viewModel by viewModels<AuthViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return AuthViewModel(SharedManager(requireContext())) as T
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding) {
            authButton.setOnClickListener {
                viewModel.openMainFragment(
                    type.selectedItemPosition,
                    passwordEditText.text.toString()
                )
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.openFlow.flowWithLifecycle(
                viewLifecycleOwner.lifecycle,
                Lifecycle.State.STARTED
            ).collectLatest {
                if (it) {
                    openMainFragment()
                } else {
                    errorPassword()
                }
            }
        }
    }

    private fun openMainFragment() {
        requireActivity().supportFragmentManager.replaceTo(MainFragment())
    }

    private fun errorPassword() {
        Toast.makeText(requireContext(), getString(R.string.error_password), Toast.LENGTH_SHORT)
            .show()
    }

    private companion object
}