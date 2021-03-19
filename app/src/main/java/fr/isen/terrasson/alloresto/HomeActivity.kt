package fr.isen.terrasson.alloresto

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import fr.isen.terrasson.alloresto.databinding.ActivityHomeBinding


class HomeActivity : AppCompatActivity() {

    private val TAG = "HomeActivity"
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.connexion.setOnClickListener {
            // action après connexion
            val intent = Intent(this, ListActivity::class.java)
            startActivity(intent)
        }
        binding.withoutConnexion.setOnClickListener {
            // action sans connexion
            val intent = Intent(this, ListActivity::class.java)
            startActivity(intent)
        }
   }

    override fun onDestroy() {
        super.onDestroy()

        Log.i(TAG, "Target neutralisée")
    }
}