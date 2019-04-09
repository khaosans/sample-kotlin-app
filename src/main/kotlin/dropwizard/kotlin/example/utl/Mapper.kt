package dropwizard.kotlin.example.utl

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule

class Mapper {
    private val mapper = ObjectMapper().registerModule(KotlinModule())!!

    fun getMapper(): ObjectMapper? {
        return mapper
    }
}


