package com.example.skeleton.ui.fragments.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.skeleton.R
import com.example.skeleton.base.BaseFragment
import com.example.skeleton.databinding.FragmentDashboardBinding

class DashboardFragment : BaseFragment<FragmentDashboardBinding, DashboardViewModel>() {
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = FragmentDashboardBinding.inflate(layoutInflater).root
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        viewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override val viewModel: DashboardViewModel
        get() = ViewModelProvider(this).get(DashboardViewModel::class.java)
    override val layoutId: Int
        get() = R.layout.fragment_dashboard
    /*override val root: View
        get() = FragmentDashboardBinding.inflate(layoutInflater).root*/
}