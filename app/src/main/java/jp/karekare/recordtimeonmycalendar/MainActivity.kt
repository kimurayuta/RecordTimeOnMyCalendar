package jp.karekare.recordtimeonmycalendar

import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.text.DateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private val keyBeginTime = "KEY_BEGIN_TIME"

    private var beginTime = 0L

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putLong(keyBeginTime, beginTime)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        beginTime = savedInstanceState?.getLong(keyBeginTime) ?: 0L
    }

    override fun onResume() {
        super.onResume()
        switchButtons()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fabRecord.setOnClickListener { _ ->
            beginTime = Calendar.getInstance().timeInMillis
            switchButtons()
        }

        fabPause.setOnClickListener { _ ->
            val endTime = Calendar.getInstance().timeInMillis
            val intent = Intent(Intent.ACTION_INSERT)
            intent.data = CalendarContract.Events.CONTENT_URI
            intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime)
            intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime)
            beginTime = 0L
            startActivity(intent)
        }
    }

    fun switchButtons() {
        if (beginTime == 0L) {
            fabPause.hide()
            fabRecord.show()
            textView.visibility = View.GONE
        } else {
            fabRecord.hide()
            fabPause.show()
            textView.text = DateFormat.getTimeInstance(DateFormat.SHORT).format(Date(beginTime)) + " - "
            textView.visibility = View.VISIBLE
        }
    }
}
