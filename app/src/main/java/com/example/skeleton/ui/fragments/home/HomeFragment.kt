package com.example.skeleton.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.example.skeleton.R
import com.example.skeleton.base.BaseFragment
import com.example.skeleton.base.BaseViewModel
import com.example.skeleton.databinding.ActivityMainBinding
import com.example.skeleton.databinding.FragmentDashboardBinding
import com.example.skeleton.databinding.FragmentHomeBinding

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
    /*override val root: View
        get() = FragmentHomeBinding.inflate(layoutInflater).root*/
}