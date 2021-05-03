package fr.isen.terrasson.alloresto

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.terrasson.alloresto.databinding.CellCategoryBinding
import fr.isen.terrasson.alloresto.menu.MenuPlatDetail

private lateinit var binding : CellCategoryBinding


class CategoryAdapter(
        val dataSet: List<MenuPlatDetail>,
        private val onItemClickListner: (MenuPlatDetail) -> Unit)
    : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>(){

    class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.titre)
        val detail: TextView = view.findViewById(R.id.detail)
        val image: ImageView = view.findViewById(R.id.imagePlats)
        val prix: TextView = view.findViewById(R.id.prices)
        val layout = view.findViewById<View>(R.id.layout)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : CategoryViewHolder = CategoryViewHolder(
            CellCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ).root
    )

    override fun getItemCount() = dataSet.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int)
    {
        val item = dataSet[position]
        holder.title.text = dataSet[position].name
        holder.detail.text = "Composition : " + dataSet[position].ingredients.map { it.name }.joinToString()
        holder.prix.text = dataSet[position].price[0].price + "€"
        if(dataSet[position].getFirstPicture().isNullOrEmpty()){
            Picasso.get()
                .load("https://img.cuisineaz.com/660x660/2014-04-07/i58810-carpaccio-de-saumon.jpg")
                .into(holder.image)
        }else{
            Picasso.get()
                .load(dataSet[position].getFirstPicture())
                .into(holder.image)
        }
        holder.layout.setOnClickListener { onItemClickListner(dataSet[position]) }
           }

}



