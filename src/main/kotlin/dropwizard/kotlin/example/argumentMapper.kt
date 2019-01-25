package dropwizard.kotlin.example

import java.sql.SQLException
import org.skife.jdbi.v2.StatementContext
import org.skife.jdbi.v2.tweak.Argument
import java.sql.PreparedStatement
import java.util.UUID
import org.skife.jdbi.v2.tweak.ArgumentFactory


class UUIDArgumentFactory : ArgumentFactory<UUID> {

    override fun accepts(expectedType: Class<*>, value: Any, ctx: StatementContext): Boolean {
        return value is UUID
    }

    override fun build(expectedType: Class<*>, value: UUID, ctx: StatementContext): Argument {
        return UUIDArgument(value)
    }
}


class UUIDArgument(value: UUID) : Argument {
    private var value: UUID? = null

    init {
        this.value = value
    }

    @Throws(SQLException::class)
    override fun apply(position: Int, statement: PreparedStatement, ctx: StatementContext) {
        statement.setString(position, value!!.toString())
    }
}
