package blood.blooddonation.authentication

import java.security.MessageDigest

class Utils {
    fun hashString(string: String, algorithm: String = "SHA-256"): String {
        val bytes = MessageDigest.getInstance(algorithm).digest(string.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }
}