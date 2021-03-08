package com.kirchhoff.presentation.ui.user

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kirchhoff.presentation.R
import com.kirchhoff.presentation.databinding.FragmentUserBinding
import com.kirchhoff.presentation.extensions.setFormattedText
import com.kirchhoff.presentation.ui.base.BaseFragment
import com.kirchhoff.presentation.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserFragment : BaseFragment(R.layout.fragment_user) {

    private val viewModel: UserFragmentVM by viewModels()
    private val viewBinding by viewBinding(FragmentUserBinding::bind)
    private val args: UserFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.init(args.userFragmentArg)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        subscribeViewModel()
    }

    private fun setupViews() {
        viewBinding.bLogout.setOnClickListener {
            findNavController().navigate(
                UserFragmentDirections.toLoginFragment()
            )
        }
    }

    private fun subscribeViewModel() {
        viewModel.viewState.subscribe { viewState ->
            with(viewBinding) {
                tvFirstName.setFormattedText(R.string.first_name_format, viewState.firstName)
                tvLastName.setFormattedText(R.string.last_name_format, viewState.lastName)
                tvInfo.setFormattedText(R.string.info_format, viewState.info)
                tvEmail.setFormattedText(R.string.email_format, viewState.email)
            }
        }
    }
}
