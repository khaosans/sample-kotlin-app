package dropwizard.kotlin.example.api

import com.fasterxml.jackson.annotation.JsonProperty

data class Task(
        @JsonProperty("name") val name: String,
        val completed: Boolean,
        val user: User
)