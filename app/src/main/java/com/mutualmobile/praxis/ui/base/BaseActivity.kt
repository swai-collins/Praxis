package com.mutualmobile.praxis.ui.base

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.LayoutRes
import com.mutualmobile.praxis.BR
import com.mutualmobile.praxis.ui.base.navigator.Navigator
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity<B : ViewDataBinding, VM : ViewModel> : DaggerAppCompatActivity() {
  protected lateinit var binding: B
  lateinit var viewModel: VM

  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory

  @Inject
  lateinit var navigator: Navigator

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    // Inject dependencies
    AndroidInjection.inject(this)
    // Bind the view and bind the viewModel to layout
    bindContentView(layoutId())
  }

  fun bindContentView(layoutId: Int) {
    binding = DataBindingUtil.setContentView(this, layoutId)
    viewModel = ViewModelProviders.of(this, viewModelFactory).get(getViewModelClass())
    binding.setVariable(BR.viewModel, viewModel)
  }

  abstract fun getViewModelClass(): Class<VM>

  @LayoutRes protected abstract fun layoutId(): Int

}