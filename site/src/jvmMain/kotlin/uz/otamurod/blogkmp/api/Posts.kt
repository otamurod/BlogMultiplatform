package uz.otamurod.blogkmp.api

import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.data.getValue
import com.varabyte.kobweb.api.http.setBodyText
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.litote.kmongo.id.ObjectIdGenerator
import uz.otamurod.blogkmp.data.MongoDB
import uz.otamurod.blogkmp.models.Post

@Api(routeOverride = "addpost")
suspend fun addPost(context: ApiContext) {
    try {
        val postRequest =
            context.req.body?.decodeToString()?.let { Json.decodeFromString<Post>(it) }
        val newPost =
            postRequest?.copy(id = ObjectIdGenerator.newObjectId<String>().id.toHexString())

        context.res.setBodyText(
            newPost?.let {
                context.data.getValue<MongoDB>().addPost(it).toString()
            } ?: false.toString()
        )
    } catch (e: Exception) {
        context.res.setBodyText(Json.encodeToString(e.message))
    }
}