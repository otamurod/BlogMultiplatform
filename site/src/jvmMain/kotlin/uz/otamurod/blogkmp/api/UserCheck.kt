package uz.otamurod.blogkmp.api

import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.data.getValue
import com.varabyte.kobweb.api.http.setBodyText
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import uz.otamurod.blogkmp.data.MongoDB
import uz.otamurod.blogkmp.models.User
import uz.otamurod.blogkmp.models.UserWithoutPassword
import java.nio.charset.StandardCharsets
import java.security.MessageDigest

@Api(routeOverride = "usercheck")
suspend fun userCheck(context: ApiContext) {
    try {
        val userRequest = context.req.body?.decodeToString()?.let {
            Json.decodeFromString<User>(it)
        }
        val user = userRequest?.let {
            context.data.getValue<MongoDB>()
                .checkUserExistance(
                    user = User(username = it.username, password = hashPassword(it.password))
                )
        }
        if (user != null) {
            context.res.setBodyText(
                Json.encodeToString<UserWithoutPassword>(
                    UserWithoutPassword(
                        id = user.id,
                        username = user.username
                    )
                )
            )
        } else {
            context.res.setBodyText(Json.encodeToString(Exception("User not found")))
        }
    } catch (e: Exception) {
        context.res.setBodyText(Json.encodeToString(Exception(e.message)))
    }
}

private fun hashPassword(password: String): String {
    val messageDigest = MessageDigest.getInstance("SHA-256")
    val hashBytes = messageDigest.digest(password.toByteArray(StandardCharsets.UTF_8))
    val hexString = StringBuffer()

    for (byte in hashBytes) {
        hexString.append(String.format("%02x", byte))
    }

    return hexString.toString()
}