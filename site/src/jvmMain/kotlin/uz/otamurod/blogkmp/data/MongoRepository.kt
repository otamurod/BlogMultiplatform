package uz.otamurod.blogkmp.data

import uz.otamurod.blogkmp.models.User

interface MongoRepository {
    suspend fun checkUserExistence(user: User): User?
}