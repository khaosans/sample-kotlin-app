package dropwizard.kotlin.example.api

import java.util.*
import javax.validation.constraints.NotNull

class User(@NotNull val id: UUID = UUID.randomUUID(), @NotNull val username: String = "me", @NotNull val email: String = "me@example.com", @NotNull val password: String = "Test")


