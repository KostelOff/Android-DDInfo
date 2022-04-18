package com.ntechnology.ddinfo.ui.activities

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.MenuItem
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.ntechnology.ddinfo.R
import com.ntechnology.ddinfo.extensions.moveToMainMenu
import com.ntechnology.ddinfo.extensions.replace
import com.ntechnology.ddinfo.presenters.LauncherPresenter
import com.ntechnology.ddinfo.ui.views.LauncherView
import dagger.android.AndroidInjection
import javax.inject.Inject

class LauncherActivity : MvpAppCompatActivity(), LauncherView {
    @Inject
    @InjectPresenter
    lateinit var presenter: LauncherPresenter

    @ProvidePresenter
    fun providePresenter(): LauncherPresenter = presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)

        supportFragmentManager.addOnBackStackChangedListener {
            val canBack = supportFragmentManager.backStackEntryCount > 0
            supportActionBar?.setDisplayHomeAsUpEnabled(canBack)
            supportActionBar?.setDisplayShowHomeEnabled(canBack)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showMainMenu() {
        moveToMainMenu()
    }

    fun replaceFragment(fragment: Fragment, addToBackStack: Boolean = true) {
        supportFragmentManager.replace(R.id.fragmentContainerView, fragment, addToBackStack)
    }
}