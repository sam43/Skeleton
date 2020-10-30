package com.example.skeleton.base

import android.annotation.TargetApi
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.example.skeleton.databinding.ActivityMainBinding
import com.example.skeleton.utils.NetworkUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

abstract class BaseActivity<T : ViewBinding?, V : BaseViewModel?>: AppCompatActivity() {

    var viewDataBinding: T? = null
        private set

    var mViewModel: V? = null

/*    *//**
     * Override for set binding variable
     *
     * @return variable id
     *//*
    abstract val bindingVariable: ViewBinding*/

    /**
     * @return layout resource id
     */
    @get:LayoutRes
    abstract val layoutId: Int


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performViewBinding()
    }

    private fun performViewBinding() {
        viewDataBinding = ActivityMainBinding.inflate(layoutInflater) as T
        setContentView(viewDataBinding?.root)
    }

/*    private fun performDataBinding() {
        //viewDataBinding = DataBindingUtil.setContentView(this, layoutId)
        viewDataBinding = viewDataBinding.inflate()
        setContentView(viewDataBinding)
        viewDataBinding.setVariable(bindingVariable, mViewModel)
        viewDataBinding.executePendingBindings()
    }*/

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(newBase)
        //add newbase with custom font for each activities
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun hasPermission(permission: String?): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission!!) == PackageManager.PERMISSION_GRANTED
    }

    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }


    fun isNetworkConnected(): Boolean {
        return NetworkUtils.isNetworkConnected(applicationContext);
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermissionsSafely(permissions: Array<String?>?, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions!!, requestCode)
        }
    }

    /*    public void openActivityOnTokenExpire() {
        startActivity(LoginActivity.newIntent(this));
        finish();
    }*/
}