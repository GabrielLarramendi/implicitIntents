package dominando.android.implicitintents

import android.app.SearchManager
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.provider.Settings
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val listView = ListView(this)
        setContentView(listView)

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            resources.getStringArray(R.array.intent_actions))

        listView.adapter = adapter

        listView.setOnItemClickListener {_, _, position, _ ->
            openIntentAtPosition(position)
        }
    }

    private fun callNumber() {
        val uri = Uri.parse("tel:999090200")
        intent = Intent(Intent.ACTION_CALL, uri)
        openIntent(intent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(grantResults.first() == PackageManager.PERMISSION_GRANTED) {
            callNumber()
        }
    }

    private fun openIntentAtPosition(position: Int) {
        val uri: Uri?
        val intent: Intent?

        when(position) {
            0 -> {
                uri = Uri.parse("https://www.linkedin.com/in/gabriel-larramendi")
                intent = Intent(Intent.ACTION_VIEW, uri)
                openIntent(intent)
            }
            1 -> {
                if(ActivityCompat.checkSelfPermission(this,
                    android.Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                        arrayOf<String>(android.Manifest.permission.CALL_PHONE), 0)
                } else {
                    callNumber()
                }
            }
            2 -> {
                //
            }
            3 -> {
                uri = Uri.parse("sms:Olá, tudo bem?")
                intent = Intent(Intent.ACTION_VIEW, uri).
                        putExtra("sms_body", "Corpo do SMS")
                openIntent(intent)
            }
            4 -> {
                intent = Intent()
                    .setAction(Intent.ACTION_SEND)
                    .putExtra(Intent.EXTRA_TEXT, "Compartilhando via intent")
                    .setType("text/plain")
                openIntent(intent)
            }
            5 -> {
                intent = Intent(AlarmClock.ACTION_SET_TIMER)
                    .putExtra(AlarmClock.EXTRA_LENGTH, (86400 * 2/24))
                    .putExtra(AlarmClock.EXTRA_SKIP_UI, true)

                openIntent(intent)
            }
            6 -> {
                intent = Intent(Intent.ACTION_SEARCH)
                    .putExtra(SearchManager.QUERY, "Gabriel Larramendi")
                openIntent(intent)
            }
            7 -> {
                intent = Intent(Settings.ACTION_SETTINGS)
                openIntent(intent)
            }
        }
    }

    private fun openIntent(intent: Intent) {
        try {
            startActivity(intent)
        }
        catch (e: ActivityNotFoundException) {
        Toast.makeText(this, R.string.error_intent, Toast.LENGTH_SHORT).show()
        }
    }

}