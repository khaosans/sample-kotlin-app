package dropwizard.kotlin.example.api

import java.util.*

class Post(val uuid: UUID? = UUID.randomUUID(), val post: String, val userUuid: UUID)