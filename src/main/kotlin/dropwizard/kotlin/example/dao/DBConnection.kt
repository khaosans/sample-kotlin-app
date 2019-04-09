package dropwizard.kotlin.example.dao

import dropwizard.kotlin.example.UUIDArgumentFactory
import org.skife.jdbi.v2.DBI

class DBConnection {
    val dbi: DBI = DBI("jdbc:postgresql://localhost:5432/postgres", "main", "myPassword")


    init {
        dbi.registerArgumentFactory(UUIDArgumentFactory())

    }

}