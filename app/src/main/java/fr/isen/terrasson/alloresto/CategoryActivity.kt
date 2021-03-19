package fr.isen.terrasson.alloresto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import fr.isen.terrasson.alloresto.menu.MenuChoix
import fr.isen.terrasson.alloresto.databinding.LyoCellsBinding
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


        //json receive
        var categoryFilter: Int = 0
        if (name == "Entrées"){ categoryFilter = 0}
        if (name == "Plats"){ categoryFilter = 1}
        if (name == "Desserts"){ categoryFilter = 2}

        lateinit var meals:MenuChoix
        val queue = Volley.newRequestQueue(this)
        val url = "http://test.api.catering.bluecodegames.com/menu"
        val json : JSONObject = JSONObject().put("id_shop", "1")
        val result: MenuChoix
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url, json,
            { response ->
                val gson = Gson()
                meals = gson.fromJson(response.toString(), MenuChoix::class.java)
                if (name != null) {
                    displayMeals(meals, name)
                }

            },
            { error ->
                error.printStackTrace()
            }
        )
        queue.add(jsonObjectRequest)


    }
    fun displayMeals(meals: MenuChoix, title:String)
    {
        val dishesByCategory = meals.data.firstOrNull{it.name == title}?.platsDetail ?: listOf()
        binding.recView.layoutManager = LinearLayoutManager(this)
        binding.recView.adapter = CategoryAdapter(dishesByCategory)
        { item ->
            val intent = Intent(this, PlatChoisiActivity::class.java)
            intent.putExtra("dish", item)
            startActivity(intent)
        }
    }

}