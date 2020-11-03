package com.example.skeleton.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.skeleton.R
import com.example.skeleton.base.BaseFragment
import com.example.skeleton.databinding.CharactersFragmentBinding
import com.example.skeleton.databinding.FragmentHomeBinding
import com.example.skeleton.ui.fragments.characters.CharactersViewModel
import com.example.skeleton.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    private var binding: FragmentHomeBinding by autoCleared()
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.text.observe(viewLifecycleOwner, {
            binding.textHome.text = it // used view binding
        })
    }

    override val viewModel: HomeViewModel
        get() = homeViewModel
    override val layoutId: Int
        get() = R.layout.fragment_home
}