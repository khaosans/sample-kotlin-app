package dropwizard.kotlin.example.resource


import dropwizard.kotlin.example.UserManager
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.util.*
import javax.crypto.spec.SecretKeySpec
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.xml.bind.DatatypeConverter

const val apiSecret = "A19C7"

@Path("/authentication")
class AuthResource {
    private val userManager: UserManager = UserManager()


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    fun authenticateUser(@FormParam("email") username: String,
                         @FormParam("password") password: String): Response {

        return try {

            // Authenticate the user using the credentials provided
            authenticate(username, password)

            // Issue a token for the user
            val token = issueToken(username)

            // Return the token on the response
            Response.ok(token).build()

        } catch (e: Exception) {
            Response.status(Response.Status.FORBIDDEN).build()
        }

    }


    @GET
    fun validateToken(@QueryParam("token") token: String): Response {

        return try {

            // Authenticate the user using the credentials provided
            parseJWT(token)

            return Response.ok().build()
        } catch (e: Exception) {
            Response.status(Response.Status.FORBIDDEN).build()
        }

    }

    @Throws(Exception::class)
    private fun authenticate(email: String, password: String) {
        // Authenticate against a database, LDAP, file or whatever
        // Throw an Exception if the credentials are invalid

        val user = userManager.findUserByEmail(email)

        if (user.password != password) {
            throw Exception()
        }

    }

    private fun issueToken(username: String): String {
        // Issue a token (can be a random String persisted to a database or a JWT token)
        // The issued token must be associated to a user
        // Return the issued token

        return createJWT(UUID.randomUUID().toString(), "auth", "Subject", 10000)
    }
}


//Sample method to construct a JWT
private fun createJWT(id: String, issuer: String, subject: String, ttlMillis: Long): String {


    //The JWT signature algorithm we will be using to sign the token
    val signatureAlgorithm = SignatureAlgorithm.HS256

    val nowMillis = System.currentTimeMillis()
    val now = Date(nowMillis)

    //We will sign our JWT with our ApiKey secret
    val apiKeySecretBytes = DatatypeConverter.parseBase64Binary(apiSecret)
    val signingKey = SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.jcaName)

    //Let's set the JWT Claims
    val builder = Jwts.builder().setId(id)
            .setIssuedAt(now)
            .setSubject(subject)
            .setIssuer(issuer)
            .signWith(signatureAlgorithm, signingKey)

    //if it has been specified, let's add the expiration
    if (ttlMillis >= 0) {
        val expMillis = nowMillis + ttlMillis
        val exp = Date(expMillis)
        builder.setExpiration(exp)
    }

    //Builds the JWT and serializes it to a compact, URL-safe string
    return builder.compact()
}

//Sample method to validate and read the JWT
fun parseJWT(jwt: String) {

    //This line will throw an exception if it is not a signed JWS (as expected)
    val claims = Jwts.parser()
            .setSigningKey(DatatypeConverter.parseBase64Binary(apiSecret))
            .parseClaimsJws(jwt).body


    if (Date().after(claims.expiration)) {
        throw Exception()
    }
    println("ID: " + claims.id)
    println("Subject: " + claims.subject)
    println("Issuer: " + claims.issuer)
    println("Expiration: " + claims.expiration)
}
