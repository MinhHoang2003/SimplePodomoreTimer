package com.product.hstudio.simplepodomorotimer.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.product.hstudio.simplepodomorotimer.databinding.FragmentHomeBinding
import kotlin.reflect.KClass

typealias Inflater<T> = (inflater: LayoutInflater, container: ViewGroup?, attach: Boolean) -> T

abstract class BaseBindingFragment<VB : ViewBinding>(
    private val inflater: Inflater<VB>,
    @LayoutRes layoutId: Int
) : Fragment() {

    private var _binding: VB? = null
    protected val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = inflater(inflater, container, false)
        return binding?.root
    }
}