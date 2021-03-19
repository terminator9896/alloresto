package fr.isen.terrasson.alloresto.menu

import com.google.gson.annotations.SerializedName

data class MenuPlatIngredients(
    @SerializedName("id") val id: Int,
    @SerializedName("name_fr") val name: String
)