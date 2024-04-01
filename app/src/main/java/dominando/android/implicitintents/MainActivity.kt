package dominando.android.implicitintents

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast

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

    private fun openIntentAtPosition(position: Int) {
        val uri: Uri?
        val intent: Intent?

        when(position) {
            0 -> {
                uri = Uri.parse("https://www.linkedin.com/feed/")
                intent = Intent(Intent.ACTION_VIEW, uri)
                openIntent(intent)
            }
        }
    }

    private fun openIntent(intent: Intent) {
        if(intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
        else {
            Toast.makeText(this, R.string.error_intent, Toast.LENGTH_SHORT).show()
        }
    }

}