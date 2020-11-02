package com.example.skeleton.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.skeleton.R
import com.example.skeleton.base.BaseFragment
import com.example.skeleton.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = FragmentHomeBinding.inflate(layoutInflater).root
        val textView: TextView = root.findViewById(R.id.text_home)
        viewModel.text.observe(viewLifecycleOwner, {
            textView.text = it
        })
        return root
    }

    override val viewModel: HomeViewModel
        get() = ViewModelProvider(this).get(HomeViewModel::class.java) // use factory
    override val layoutId: Int
        get() = R.layout.fragment_home
}