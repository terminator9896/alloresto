package fr.isen.terrasson.alloresto

import android.content.ClipData
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.squareup.picasso.Picasso
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageListener
import fr.isen.terrasson.alloresto.databinding.ActivityPlatChoisiBinding
import fr.isen.terrasson.alloresto.databinding.LyoCellsBinding
import fr.isen.terrasson.alloresto.menu.MenuCategory
import fr.isen.terrasson.alloresto.menu.MenuPlatDetail

class PlatChoisiActivity : AppCompatActivity() {
    lateinit var binding: ActivityPlatChoisiBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlatChoisiBinding.inflate(layoutInflater)
        val dataItem = intent.getSerializableExtra("items") as? MenuPlatDetail
        setContentView(binding.root)
        var sampleImages = arrayOf(dataItem?.images?.get(0))
        val carouselView = findViewById<CarouselView>(R.id.imagedetail);
        var imageListener: ImageListener = object : ImageListener {
            override fun setImageForPosition(position: Int, imageView: ImageView) {
                if (dataItem != null) {
                    if(dataItem.images[0].isNullOrEmpty()){
                        Picasso.get().load("https://cdn.vox-cdn.com/thumbor/pyedsHD4n0a3uBGicl9o3e580l8=/1400x1400/filters:format(jpeg)/cdn.vox-cdn.com/uploads/chorus_asset/file/18341527/10010.jpeg").into(imageView)
                    } else {
                        Picasso.get().load(dataItem.images[position]).into(imageView)
                    }
                }
            }
        }
        carouselView.setPageCount(sampleImages.size);
        carouselView.setImageListener(imageListener);


        if (dataItem != null) {
            binding.choixPlat.text = dataItem.name
            binding.detailPlat.text = "Compostion : " + dataItem.getIngredients()
            binding.prix.text = "0€"
            binding.imagedetail.pageCount = dataItem.images.size
            var index = 0
            binding.moins.setOnClickListener {
                if(index == 0) {
                    index = 0
                    binding.nombre.text = index.toString()
                    calculPrix(index, dataItem)
                }
                else
                {
                    index -= 1
                    binding.nombre.text = index.toString()
                    calculPrix(index, dataItem)
                }

            }
            binding.plus.setOnClickListener {
                index += 1
                binding.nombre.text = index.toString()
                calculPrix(index,dataItem)
            }

        }
        

    }
    fun calculPrix( quantity: Int, data : MenuPlatDetail){
        var total = quantity * data.getPrice()
        binding.prix.text = total.toString() + "€"
    }

}