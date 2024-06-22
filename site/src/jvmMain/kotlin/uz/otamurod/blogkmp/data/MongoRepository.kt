package uz.otamurod.blogkmp.data

import uz.otamurod.blogkmp.models.User

interface MongoRepository {
    suspend fun checkUserExistance(user: User): User?
}