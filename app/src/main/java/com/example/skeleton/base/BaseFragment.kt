package com.example.skeleton.base

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding


abstract class BaseFragment<T : ViewBinding?, V : BaseViewModel?>: Fragment() {

    private val mActivity: BaseActivity<T, V>? = null
    private var mRootView: View? = null
    private var mViewDataBinding: T? = null

    /**
     * Override for set binding viewmodel
     *
     * @return viewmodel
     */
    abstract val viewModel: V

/*    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    abstract val binding: T*/

    /**
     * @return layout resource id
     */
    @get:LayoutRes
    abstract val layoutId: Int
/*
    *//**
     * @return layout root
     *//*
    abstract val root: View*/

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /**
         * Still unable to make the fragment binding generic
         * Need to work on it
         * */

        return mViewDataBinding?.root
        /*mViewDataBinding = mViewDataBinding?.i
        mRootView = mViewDataBinding?.root
        mRootView?.setBackgroundColor(Color.BLUE)
        return mRootView*/
    }


/*    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //performViewBinding()
    }

    private fun performViewBinding() {
        mRootView.setContentView(bindingVariable.root)
    }*/

}