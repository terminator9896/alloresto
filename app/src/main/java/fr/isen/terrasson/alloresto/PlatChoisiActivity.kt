package fr.isen.terrasson.alloresto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import fr.isen.terrasson.alloresto.databinding.ActivityPlatChoisiBinding
import fr.isen.terrasson.alloresto.databinding.LyoCellsBinding

class PlatChoisiActivity : AppCompatActivity() {

    private lateinit var binding:ActivityPlatChoisiBinding

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

            binding = ActivityPlatChoisiBinding.inflate(layoutInflater)
            setContentView(binding.root)

            Toast.makeText(this, intent.getStringExtra("dish") ?: "", Toast.LENGTH_LONG).show()
            val name = intent.getStringExtra("dish")
            findViewById<TextView>(R.id.choixPlat).text = name
    }
}