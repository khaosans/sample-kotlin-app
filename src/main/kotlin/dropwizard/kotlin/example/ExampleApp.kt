package dropwizard.kotlin.example

import dropwizard.kotlin.example.filter.AuthenticationFilter
import dropwizard.kotlin.example.filter.DiagnosticContextFilter
import dropwizard.kotlin.example.healthcheck.DefaultHealthCheck
import dropwizard.kotlin.example.resource.AuthResource
import dropwizard.kotlin.example.resource.RootResource
import dropwizard.kotlin.example.resource.UserResource
import io.dropwizard.Application
import io.dropwizard.setup.Environment

class ExampleApp : Application<ExampleAppConfig>() {

    override fun run(config: ExampleAppConfig, env: Environment) {
        env.jersey().register(RootResource())
        env.jersey().register(UserResource())
        env.jersey().register(DiagnosticContextFilter())
        env.jersey().register(AuthenticationFilter())
        env.jersey().register(AuthResource())
        env.healthChecks().register("default", DefaultHealthCheck())
    }

    companion object {
        @JvmStatic fun main(args: Array<String>) {
            ExampleApp().run(*args)
        }
    }

}

