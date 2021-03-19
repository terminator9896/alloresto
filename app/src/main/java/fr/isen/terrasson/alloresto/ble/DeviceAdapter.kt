package fr.isen.terrasson.alloresto.ble

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.isen.terrasson.alloresto.CategoryAdapter
import fr.isen.terrasson.alloresto.R
import fr.isen.terrasson.alloresto.databinding.CellCategoryBinding
import fr.isen.terrasson.alloresto.databinding.LyoCellBleBinding

private lateinit var binding : LyoCellBleBinding

class DeviceAdapter() : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>(){

    class DeviceViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val title: TextView = view.findViewById(R.id.nameDevice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : CategoryAdapter.CategoryViewHolder = CategoryAdapter.CategoryViewHolder(
            LyoCellBleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
            ).root)

    override fun getItemCount(): Int{
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: CategoryAdapter.CategoryViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}


}

