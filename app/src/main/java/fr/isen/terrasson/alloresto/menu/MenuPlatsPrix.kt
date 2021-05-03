package fr.isen.terrasson.alloresto.menu

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MenuPlatsPrix (
    @SerializedName("id") val id: String,
    @SerializedName("price") val price: String,
    @SerializedName("size") val size: String
): Serializable