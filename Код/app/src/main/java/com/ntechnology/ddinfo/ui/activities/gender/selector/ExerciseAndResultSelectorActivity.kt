package com.ntechnology.ddinfo.ui.activities.gender.selector

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import com.ntechnology.ddinfo.R
import com.ntechnology.ddinfo.ui.decorators.DividerDecorator
import kotlinx.android.synthetic.main.fragment_selection.*

class ExerciseAndResultSelectorActivity : AppCompatActivity(), StringRVAdapter.ItemSelectedListener {
    override fun itemSelected(position: Int) {

        val result = Intent()
        result.putExtra(SELECTED, items[position])
        setResult(Activity.RESULT_OK, result)
        finish()
    }

    companion object {
        private const val ITEMS = "ITEMS"
         const val SELECTED = "SELECTED"

        fun start(activity: Activity, requestCode: Int, items: Array<String>) {
            val intent = Intent(activity, ExerciseAndResultSelectorActivity::class.java)
            intent.putExtra(ITEMS, items)
            activity.startActivityForResult(intent, requestCode)
        }
    }


    lateinit var adapter: StringRVAdapter
    lateinit var items: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_selection)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        items = intent.getStringArrayExtra(ITEMS)

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = StringRVAdapter(items, this)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerDecorator(this))

    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}