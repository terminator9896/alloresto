package fr.isen.terrasson.alloresto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fr.isen.terrasson.alloresto.menu.MenuChoix
import fr.isen.terrasson.alloresto.databinding.LyoCellsBinding
import fr.isen.terrasson.alloresto.menu.MenuPlatDetail
import org.json.JSONObject


class CategoryActivity : AppCompatActivity(){

    private lateinit var binding: LyoCellsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //create binding instance
        binding = LyoCellsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //set RecycleView title
        val name = intent.getStringExtra("choix")
        findViewById<TextView>(R.id.choix).text = name


        getDataItems(name)



    }
    private fun displayMeals(items: List<MenuPlatDetail>?) {
        items?.let {
            val adapter = CategoryAdapter(it) { item ->
                val intent = Intent(this, PlatChoisiActivity::class.java)
                intent.putExtra("items", item)
                startActivity(intent)
            }
            binding.recview.layoutManager = LinearLayoutManager(this)
            binding.recview.adapter = adapter
        }
    }
    private fun parseResult(res: String, selectedItem: String?) {
        val menuResult = GsonBuilder().create().fromJson(res, MenuChoix::class.java)
        val items = menuResult.data.firstOrNull { it.name == selectedItem }
        displayMeals(items?.items)
    }

    private fun getDataItems(category: String?) {
        val url = "http://test.api.catering.bluecodegames.com/menu"
        val requestQueue = Volley.newRequestQueue(this)
        // Initialisation de la RequestQueue instance
        val jsonData = JSONObject().put("id_shop", 1)
        val jsonObjectRequest = JsonObjectRequest(Request.Method.POST, url, jsonData,
                {it ->
                    Log.d("Json result", it.toString())
                    parseResult(it.toString(), category)
                },
                { error ->
                    error.printStackTrace()
                }    )
        requestQueue.add(jsonObjectRequest)
    }

}