package fr.isen.terrasson.alloresto.menu

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MenuPlatDetail(
    @SerializedName("id") val id: Int,
    @SerializedName("name_fr") val name: String,
    @SerializedName("images") val images: List<String>,
    @SerializedName("ingredients") val ingredients: List<MenuPlatIngredients>,
    @SerializedName("prices") val price: List<MenuPlatsPrix>

) : Serializable {
    fun getAffichagePrice() = price[0].price + "€"
    fun getPrice() = price[0].price.toDouble()
    fun getFirstPicture() = if (images.isNotEmpty() && images[0].isNotEmpty()) {
        images[0]
    } else {
        null
    }

    fun getIngredients(): String = ingredients.map(MenuPlatIngredients::name).joinToString(", ")
}
