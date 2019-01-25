package dropwizard.kotlin.example.filter

import dropwizard.kotlin.example.resource.parseJWT
import java.io.IOException
import javax.annotation.Priority
import javax.ws.rs.Priorities
import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.container.ContainerRequestFilter
import javax.ws.rs.core.HttpHeaders
import javax.ws.rs.core.Response
import javax.ws.rs.ext.Provider


@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
class AuthenticationFilter : ContainerRequestFilter {

    @Throws(IOException::class)
    override fun filter(requestContext: ContainerRequestContext) {

        // Get the Authorization header from the request
        val authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION)

        // Validate the Authorization header
        if (!isTokenBasedAuthentication(authorizationHeader)) {
            abortWithUnauthorized(requestContext)
            return
        }

        // Extract the token from the Authorization header
        val token = authorizationHeader.substring(AUTHENTICATION_SCHEME.length).trim { it <= ' ' }

        try {

            // Validate the token
            validateToken(token)

        } catch (e: Exception) {
            abortWithUnauthorized(requestContext)
        }

    }

    private fun isTokenBasedAuthentication(authorizationHeader: String?): Boolean {

        // Check if the Authorization header is valid
        // It must not be null and must be prefixed with "Bearer" plus a whitespace
        // The authentication scheme comparison must be case-insensitive
        return authorizationHeader != null && authorizationHeader.toLowerCase()
                .startsWith(AUTHENTICATION_SCHEME.toLowerCase() + " ")
    }

    private fun abortWithUnauthorized(requestContext: ContainerRequestContext) {

        // Abort the filter chain with a 401 status code response
        // The WWW-Authenticate header is sent along with the response
        requestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED)
                        .header(HttpHeaders.WWW_AUTHENTICATE,
                                "$AUTHENTICATION_SCHEME realm=\"$REALM\"")
                        .build())
    }

    @Throws(Exception::class)
    private fun validateToken(token: String) {
        // Check if the token was issued by the server and if it's not expired
        // Throw an Exception if the token is invalid
        parseJWT(token)

    }

    companion object {

        private val REALM = "example"
        private val AUTHENTICATION_SCHEME = "Bearer"
    }
}