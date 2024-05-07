package com.product.hstudio.simplepodomorotimer.ui.screen

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.product.hstudio.simplepodomorotimer.R
import com.product.hstudio.simplepodomorotimer.databinding.FragmentHomeBinding
import com.product.hstudio.simplepodomorotimer.ui.base.BaseBindingFragment

class HomeFragment : BaseBindingFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate, R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.run {
            button.setOnClickListener {
                timer.start(100_000)
            }
        }
    }
}

