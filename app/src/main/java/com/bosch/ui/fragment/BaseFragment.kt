package com.bosch.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment: Fragment() {

    abstract fun getLayoutResource(): Int
    abstract fun onCreateView(b: ViewDataBinding, savedInstanceState: Bundle?)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val b: ViewDataBinding = DataBindingUtil.inflate(inflater, getLayoutResource(), container, false)
        onCreateView(b, savedInstanceState)
        return b.root
    }
}