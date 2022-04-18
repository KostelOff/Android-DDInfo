package com.ntechnology.ddinfo.ui.fragments

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.ntechnology.ddinfo.R
import com.ntechnology.ddinfo.extensions.moveToCalculator
import com.ntechnology.ddinfo.extensions.moveToPaymentMenu
import com.ntechnology.ddinfo.extensions.moveToPhysicalMenu
import com.ntechnology.ddinfo.models.CalculatorModel
import com.ntechnology.ddinfo.models.MenuItemModel
import com.ntechnology.ddinfo.presenters.MainMenuPresenter
import com.ntechnology.ddinfo.ui.activities.LauncherActivity
import com.ntechnology.ddinfo.ui.adapters.MainMenuListAdapter
import com.ntechnology.ddinfo.ui.decorators.DividerDecorator
import com.ntechnology.ddinfo.ui.views.MainMenuView
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_main_menu.*
import javax.inject.Inject

class MainMenuFragment : MvpAppCompatFragment(), MainMenuView {
    private val adapter by lazy {
        MainMenuListAdapter().apply {
            clickListener = { menuItem ->
                presenter.onMenuClick(menuItem)
            }
        }
    }

    @Inject
    @InjectPresenter
    lateinit var presenter: MainMenuPresenter

    @ProvidePresenter
    fun providePresenter(): MainMenuPresenter = presenter

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main_menu, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as? AppCompatActivity)?.supportActionBar?.let { actionBar ->
            actionBar.title = resources.getString(R.string.screen_title_main_menu)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerDecorator(context))
    }

    override fun setMenuItems(items: List<MenuItemModel>) {
        adapter.items = items
    }

    override fun showCalculator() {
        (activity as? LauncherActivity)?.moveToCalculator(CalculatorModel.CALCULATOR_ID_DD)
    }

    override fun showBenefitCalculator() {
        (activity as? LauncherActivity)?.moveToCalculator(CalculatorModel.CALCULATOR_ID_BENEFIT)
    }

    override fun showPaymentMenu() {
        (activity as? LauncherActivity)?.moveToPaymentMenu()
    }

    override fun showPhysicalMenu() {
        (activity as? LauncherActivity)?.moveToPhysicalMenu()
    }

    companion object {
        fun newInstance() = MainMenuFragment()
    }
}