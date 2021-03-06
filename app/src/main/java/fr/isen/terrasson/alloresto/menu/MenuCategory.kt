package fr.isen.terrasson.alloresto.menu
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MenuCategory(

    @SerializedName("name_fr") val name: String,
    @SerializedName("items")   val items: List<MenuPlatDetail>
): Serializable