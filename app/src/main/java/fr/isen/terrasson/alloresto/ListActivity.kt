package fr.isen.terrasson.alloresto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import fr.isen.terrasson.alloresto.ble.BLEActivity
import fr.isen.terrasson.alloresto.databinding.ActivityListBinding

class ListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.entree.setOnClickListener {
            Toast.makeText(this,"Entrez dans les entrées",Toast.LENGTH_SHORT).show()

            val intent = Intent(this, CategoryActivity::class.java)
            intent.putExtra("choix", "Entrées")


            startActivity(intent)
        }
        binding.plat.setOnClickListener {
            Toast.makeText(this,"Des plats assez résistants",Toast.LENGTH_SHORT).show()


            val intent = Intent(this, CategoryActivity::class.java)
            intent.putExtra("choix", "Plats")



            startActivity(intent)
        }
        binding.dessert.setOnClickListener {
            Toast.makeText(this,"Desert de desserts",Toast.LENGTH_SHORT).show()


            val intent = Intent(this, CategoryActivity::class.java)
            intent.putExtra("choix", "Desserts")

            startActivity(intent)
        }
        binding.ble.setOnClickListener {

            val intent = Intent(this, BLEActivity::class.java)

            startActivity(intent)
        }

    }
}