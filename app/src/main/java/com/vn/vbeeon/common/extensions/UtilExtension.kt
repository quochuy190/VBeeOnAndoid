package com.vn.vbeeon.extensions

import android.app.Activity
import android.content.Context
import android.util.Base64
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.vn.vbeeon.VBeeOnApplication
import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher


fun Context.hideKeyboardFrom(view: View) {
    val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Activity.hideKeyboard() {
    val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    //Find the currently focused view, so we can grab the correct window token from it.
    var view = this.currentFocus
    //If no view currently has focus, create a new one, just so we can grab a window token from it
    if (view == null) {
        view = View(this)
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}
var PUBLIC_KEY = "MIIDojCCAooCFHCX5+GUsBmdVs9wdarmNGtKXR8AMA0GCSqGSIb3DQEBCwUAMIGN\n" +
        "MQswCQYDVQQGEwJWTjEOMAwGA1UECAwFSGFOb2kxDjAMBgNVBAcMBUhhTm9pMQ8w\n" +
        "DQYDVQQKDAZCZWVJTkMxDzANBgNVBAsMBlZCZWVvbjEaMBgGA1UEAwwRYmFvLmJ1\n" +
        "aUBiZWVpbmMudm4xIDAeBgkqhkiG9w0BCQEWEWJhby5idWlAYmVlaW5jLnZuMB4X\n" +
        "DTIxMDQwOTA4NDEyMFoXDTI5MDYyNjA4NDEyMFowgYwxCzAJBgNVBAYTAlZOMQ4w\n" +
        "DAYDVQQIDAVIYU5vaTEOMAwGA1UEBwwFSGFOb2kxDzANBgNVBAoMBkJlZUlOQzEP\n" +
        "MA0GA1UECwwGVkJlZW9uMRMwEQYDVQQDDAptb2JpbGVfYXBwMSYwJAYJKoZIhvcN\n" +
        "AQkBFhd2YmVlb24ubW9iaWxlQGJlZWluYy52bjCCASIwDQYJKoZIhvcNAQEBBQAD\n" +
        "ggEPADCCAQoCggEBAMYFQ+UWtrua++MFswWocX/wZdPhhTjittv80LcuUAiEBWI2\n" +
        "ymxuz59N37tTfqCNbJmsK0cQFgtyy89ykt7ePv/Z/G5htenTYscjEXAolZUXruiR\n" +
        "1e78GMjcSS5PdByJvjTYOsDNKv+SkMxXKpfqY9fH/vfcH+w4brWSBswTesY7TjNY\n" +
        "bmWkp0aDdpnrLr8r1Nck4yM4TpkVNxTelScpz3kTKBlwN9pZqZO8VFgGuPQ/c4Um\n" +
        "wBRmUpPXSb3ue+OfA/HKW2vFL+3vwV/s6QGZpGlZdBz0wu61SRHFAq5+IjAVF3In\n" +
        "lXOKbS/DWunXetFsSWFT3kYsKYZflQXoA9eNVikCAwEAATANBgkqhkiG9w0BAQsF\n" +
        "AAOCAQEAG2FK3W592fKMzFckHqhmj+nEuhbPQ4r41W4UXSes/jfsLLy/p6fsd1oD\n" +
        "aOya0HFspPIBDreWoEk9aqxribQnO1SwTUMcO7ZVLB40pAqKV2g0C6fxeKkgF7Bm\n" +
        "MPSqHO58eLNyfb0I+pLgk0q0FC8p2syhs8QXUdZOMU1TOT+lB2RaW89q1guLhCTI\n" +
        "AwvrZCP9ZExFjSsBbeq9LN4uGt0hTipAJpH1PealCi2h0i3yy81mjchQWDhuFqSH\n" +
        "/eakOY7dIl9Qpzk7b9GDIK7ybIl1nYeLf05qePfWvg4tBLH95Np58ibL7yISeVkW\n" +
        "abuXqKdLec/e1l+G3Qd+42GzgdOWhw=="
fun enccriptData(txt: String): String? {
    var encoded = ""
    var encrypted: ByteArray? = null
    try {
        val publicBytes: ByteArray = Base64.decode(PUBLIC_KEY, Base64.DEFAULT)
        val keySpec = X509EncodedKeySpec(publicBytes)
        val keyFactory: KeyFactory = KeyFactory.getInstance("RSA")
        val pubKey: PublicKey = keyFactory.generatePublic(keySpec)
        val cipher: Cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING") //or try with "RSA"
        cipher.init(Cipher.ENCRYPT_MODE, pubKey)
        encrypted = cipher.doFinal(txt.toByteArray())
        encoded = Base64.encodeToString(encrypted, Base64.DEFAULT)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return encoded
}

fun encryptRSAToString(text: String): String? {
    val file_name = "mobile_app.crt"
    var publicKey = VBeeOnApplication.instance.assets.open(file_name).bufferedReader().use{
        it.readText()
    }
    publicKey = publicKey.replace("-----BEGIN CERTIFICATE-----", "");
    publicKey = publicKey.replace("-----END CERTIFICATE-----", "");
    var cipherText: ByteArray? = null
    var strEncryInfoData = ""
    try {
        val keyFac = KeyFactory.getInstance("RSA")
       // val keySpec: KeySpec = X509EncodedKeySpec(Base64.decode(PUBLIC_KEY.trim { it <= ' ' }.toByteArray(), Base64.DEFAULT))
      //  val publicKey: Key = keyFac.generatePublic(keySpec)
        // get an RSA cipher object and print the provider
        val publicBytes = Base64.decode(publicKey, Base64.DEFAULT)
        val keySpec = X509EncodedKeySpec(publicBytes)
        val keyFactory = KeyFactory.getInstance("RSA")
        val publicKey = keyFactory.generatePublic(keySpec)

        val cipher = Cipher.getInstance("RSA/ECB/OAEPWITHSHA-256ANDMGF1PADDING")
        // encrypt the plain text using the public key
        cipher.init(Cipher.ENCRYPT_MODE, publicKey)
        cipherText = cipher.doFinal(text.toByteArray())
        //cipherText = cipher.doFinal(text.getBytes("UTF-8"));
        strEncryInfoData = String(Base64.encode(cipherText, Base64.DEFAULT))
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
    }
    return strEncryInfoData.replace("(\\r|\\n)".toRegex(), "")
}