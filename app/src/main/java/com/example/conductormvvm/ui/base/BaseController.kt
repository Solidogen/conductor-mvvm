package com.example.conductormvvm.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.ControllerChangeHandler
import com.bluelinelabs.conductor.ControllerChangeType
import com.bluelinelabs.conductor.archlifecycle.LifecycleController
import com.example.conductormvvm.ui.features.main.GlobalUiManager
import com.example.conductormvvm.ui.features.main.MainActivity
import leakcanary.AppWatcher
import org.koin.core.component.KoinComponent

abstract class BaseController(@LayoutRes private val layoutRes: Int, args: Bundle? = null) :
    LifecycleController(args),
    KoinComponent, ViewModelStoreOwner {

    private var shouldBeGarbageCollected = false
    private val viewModelStore = ViewModelStore()

    val globalUiManager: GlobalUiManager?
        get() = (activity as? MainActivity)?.globalUiManager

    init {
        addLifecycleListener(object : Controller.LifecycleListener() {
            override fun postCreateView(controller: Controller, view: View) {
                super.postCreateView(controller, view)
                onViewCreated()
            }
        })
    }

    protected open fun onViewCreated() = Unit

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup,
        savedViewState: Bundle?
    ): View = inflater.inflate(layoutRes, container, false)

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        viewModelStore.clear()
        if (shouldBeGarbageCollected) {
            observeMemoryLeaks()
        }
    }

    @CallSuper
    override fun onChangeEnded(
        changeHandler: ControllerChangeHandler,
        changeType: ControllerChangeType
    ) {
        super.onChangeEnded(changeHandler, changeType)
        shouldBeGarbageCollected = !changeType.isEnter
        if (isDestroyed) {
            observeMemoryLeaks()
        }
    }

    final override fun getViewModelStore(): ViewModelStore {
        return viewModelStore
    }

    /**
     * LeakCanary doesn't know how to track Controller lifecycle, so we need to specify it manually
     * */
    private fun observeMemoryLeaks() {
        AppWatcher.objectWatcher.watch(this, "This controller is destroyed")
    }
}