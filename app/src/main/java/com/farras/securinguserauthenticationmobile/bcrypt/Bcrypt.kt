package com.farras.securinguserauthenticationmobile.bcrypt

import at.favre.lib.bytes.Bytes
import java.security.SecureRandom
import java.io.File
import kotlin.experimental.and
import kotlin.experimental.xor

class Bcrypt {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val bcrypt = Bcrypt()
            val password = arrayOf(
                "Muhammad Farraa",
                "Muhammad Farrab",
                "Muhammad Farrac",
                "Muhammad Farrad",
                "Muhammad farras",
                "Muhammad farraa",
                "Muhammad farrab",
                "Muhammad farrac",
                "Muhammad farrad",
                "muhammad farras",
                "muhammad farraa",
                "muhammad farrab",
                "muhammad farrac",
                "muhammad farrad",
            )
            val outputFile = "app/src/main/java/com/farras/securinguserauthenticationmobile/bcrypt/encryptedPasswordWithLibrary.txt"
            val file = File(outputFile)
            val basePassword = bcrypt.encrypt(10, "Muhammad Farras")
            val avalancheEffectCount: FloatArray = FloatArray(password.size)
            for (i in password.indices) {
                val encryptedPassword = bcrypt.encrypt(10, password[i])
                val avalancheEffect = bcrypt.bitDifference(basePassword, encryptedPassword)
                avalancheEffectCount[i] = avalancheEffect
                file.appendText(password[i] + "       " + encryptedPassword + "       " + avalancheEffect +  "%\n")
            }
            val avalancheEffectAverage = avalancheEffectCount.average()
            file.appendText("Avalanche Effect Average: $avalancheEffectAverage%\n")
        }
    }
    // Bcrypt parameters
    // Using UTF-8 charset for encoding and decoding the password to byte array
    // Using SecureRandom for generating salt for hashing the password with the length of 16
    val charset = Charsets.UTF_8
    val secureRandom = SecureRandom()

    fun encrypt(cost: Int, password: String): String {
        // Create salt for hashing with Secure Random
        val salt = ByteArray(16)
        secureRandom.nextBytes(salt)

        return format(cost, salt, hash(cost, salt, password))
    }

    fun hash(cost: Int, salt: ByteArray, password: String): ByteArray {
        val passwordBytes = password.toByteArray(charset)
        try {
            // Hashing the password with BCrypt
            val eksBlowfish : ByteArray? = EksBlowfish().eksBlowfish(cost, salt, passwordBytes)
            return eksBlowfish!!
        } catch (e: Exception) {
            throw RuntimeException(e)
        } finally {
            Bytes.wrap(passwordBytes).mutable().secureWipe()
        }
    }

    fun format(cost: Int, salt: ByteArray, hash: ByteArray): String {
        val results = "$2a$${cost}$${Bytes.wrap(salt).encodeBase64()}$${Bytes.wrap(hash).encodeBase64()}"
        // Clearing the salt and hash byte array
        Bytes.wrap(salt).mutable().secureWipe()
        Bytes.wrap(hash).mutable().secureWipe()
        return results
    }

    fun bitDifference(a: String, b: String): Float {
        val aByte = a.toByteArray(charset)
        val bByte = b.toByteArray(charset)
        var bitDifference = 0
        for (i in aByte.indices) {
            val xor = aByte[i] xor bByte[i]
            for (j in 0..7) {
                if (xor and (1 shl j).toByte() != 0.toByte()) {
                    bitDifference++
                }
            }
        }
        val totalbit = aByte.size * 8
        // Clearing the password byte array
        Bytes.wrap(aByte).mutable().secureWipe()
        Bytes.wrap(bByte).mutable().secureWipe()
        return (1f - (bitDifference.toFloat() / (totalbit))) * 100
    }
}
