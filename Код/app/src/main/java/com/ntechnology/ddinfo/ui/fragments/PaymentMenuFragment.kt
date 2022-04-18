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
import com.ntechnology.ddinfo.models.PaymentMenuItemModel
import com.ntechnology.ddinfo.presenters.PaymentMenuPresenter
import com.ntechnology.ddinfo.ui.activities.LauncherActivity
import com.ntechnology.ddinfo.ui.adapters.PaymentMenuListAdapter
import com.ntechnology.ddinfo.ui.decorators.DividerDecorator
import com.ntechnology.ddinfo.ui.views.PaymentMenuView
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_payments.*
import javax.inject.Inject


class PaymentMenuFragment : MvpAppCompatFragment(), PaymentMenuView {
    private val adapter by lazy {
        PaymentMenuListAdapter().apply {
            clickListener = { menuItem ->
                presenter.onMenuClick(menuItem)
            }
        }
    }

    @Inject
    @InjectPresenter
    lateinit var presenter: PaymentMenuPresenter

    @ProvidePresenter
    fun providePresenter(): PaymentMenuPresenter = presenter

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_payments, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as? AppCompatActivity)?.supportActionBar?.let { actionBar ->
            actionBar.title = resources.getString(R.string.screen_title_payment_menu)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerDecorator(context))
    }

    override fun setMenuItems(items: List<PaymentMenuItemModel>) {
        adapter.items = items
    }

    override fun showCalculator(paymentMenuItem: PaymentMenuItemModel) {
        (activity as? LauncherActivity)?.moveToCalculator(paymentMenuItem.calculatorType)
    }

    companion object {
        fun newInstance() = PaymentMenuFragment()
    }
}