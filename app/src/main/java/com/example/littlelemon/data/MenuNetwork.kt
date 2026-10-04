package com.example.littlelemon.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

private const val MENU_URL =
    "https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json"

@Serializable
data class MenuNetwork(val menu: List<MenuItemNetwork>)

@Serializable
data class MenuItemNetwork(
    val id: Int,
    val title: String,
    val description: String,
    val price: String,
    val image: String,
    val category: String,
) {
    fun toRoom() = MenuItemRoom(
        id = id,
        title = title,
        description = description,
        price = price.toDoubleOrNull() ?: 0.0,
        image = image,
        category = category,
    )
}

object MenuApi {
    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            // GitHub raw serves this file as text/plain.
            json(Json { ignoreUnknownKeys = true }, contentType = ContentType.Text.Plain)
        }
    }

    suspend fun fetchMenu(): List<MenuItemNetwork> =
        client.get(MENU_URL).body<MenuNetwork>().menu
}
