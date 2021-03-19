package fr.isen.terrasson.alloresto.menu
import com.google.gson.annotations.SerializedName

data class MenuCategory(

    @SerializedName("name_fr") val name: String,
    @SerializedName("items")   val platsDetail: List<MenuPlatDetail>
)