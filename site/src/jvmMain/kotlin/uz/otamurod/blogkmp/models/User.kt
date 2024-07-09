package uz.otamurod.blogkmp.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.ObjectIdGenerator

@Serializable
actual data class User(
    @SerialName("_id")
    actual val id: String = ObjectIdGenerator().generate().toString(),
    actual val username: String = "",
    actual val password: String = ""
)

@Serializable
actual data class UserWithoutPassword(
    @SerialName("_id")
    actual val id: String = ObjectIdGenerator().generate().toString(),
    actual val username: String = ""
)