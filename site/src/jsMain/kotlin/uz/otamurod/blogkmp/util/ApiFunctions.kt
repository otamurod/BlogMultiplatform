package uz.otamurod.blogkmp.util

import com.varabyte.kobweb.browser.api
import kotlinx.browser.window
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import uz.otamurod.blogkmp.models.User
import uz.otamurod.blogkmp.models.UserWithoutPassword

suspend fun checkUserExistance(user: User): UserWithoutPassword? {
    return try {
        val result = window.api.tryPost(
            apiPath = "usercheck",
            body = Json.encodeToString(user).encodeToByteArray()
        )

        Json.decodeFromString<UserWithoutPassword>(result.toString())
    } catch (e: Exception) {
        println(e.message)
        null
    }
}