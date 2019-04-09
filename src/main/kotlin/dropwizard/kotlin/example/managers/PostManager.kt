package dropwizard.kotlin.example.managers

import dropwizard.kotlin.example.api.Post
import dropwizard.kotlin.example.dao.DBConnection
import dropwizard.kotlin.example.dao.PostDao
import java.util.*

class PostManager {

    val db = DBConnection().dbi.open(PostDao::class.java)

    fun getPost(id: UUID): Post? {
        return db.findPostById(id)
    }

    fun updatePost(post: Post) {
        db.updatePost(post.uuid!!, post.post, post.userUuid)
    }

}
