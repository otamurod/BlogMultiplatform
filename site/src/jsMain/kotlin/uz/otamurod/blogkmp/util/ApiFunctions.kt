package uz.otamurod.blogkmp.util

import com.varabyte.kobweb.browser.api
import kotlinx.browser.window
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import uz.otamurod.blogkmp.models.User
import uz.otamurod.blogkmp.models.UserWithoutPassword

private val json = Json {
    ignoreUnknownKeys = true
}

suspend fun checkUserExistence(user: User): UserWithoutPassword? {
    return try {
        window.api.tryPost(
            apiPath = "usercheck",
            body = json.encodeToString(user).encodeToByteArray()
        )?.decodeToString().parseData()
    } catch (e: Exception) {
        println("CURRENT_USER")
        println(e.message)
        null
    }
}

inline fun <reified T> String?.parseData(): T {
    return Json.decodeFromString(this.toString())
}