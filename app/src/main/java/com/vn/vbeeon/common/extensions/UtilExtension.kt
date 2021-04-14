package com.vn.vbeeon.extensions

import android.app.Activity
import android.content.Context
import android.util.Base64
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.vn.vbeeon.VBeeOnApplication
import org.bee.rsa.RSAUtils
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo
import org.bouncycastle.cert.X509CertificateHolder
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.openssl.PEMEncryptedKeyPair
import org.bouncycastle.openssl.PEMKeyPair
import org.bouncycastle.openssl.PEMParser
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter
import org.bouncycastle.openssl.jcajce.JcePEMDecryptorProviderBuilder
import timber.log.Timber
import java.io.IOException
import java.io.Reader
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.security.PrivateKey
import java.security.PublicKey
import java.security.cert.CertificateException
import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.NoSuchPaddingException


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

fun enccriptData(data: String): String {
    var strEncryInfoData = ""
    var cipherText: ByteArray? = null
    try {
        val file_name = "vbeeon_admin.crt"
        val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
        // encrypt the plain text using the public key
        cipher.init(Cipher.ENCRYPT_MODE, readPublickey(file_name))
        cipherText = cipher.doFinal(data.toByteArray())
        strEncryInfoData = String(Base64.encode(cipherText, Base64.DEFAULT))
        Timber.e("" + strEncryInfoData)
    } catch (e: java.lang.Exception) {
        e.printStackTrace()

    }
    return strEncryInfoData
}

@Throws(CertificateException::class, IOException::class)
private fun readPublickey(file_name: String): PublicKey? {
    val publicPem = PEMParser(VBeeOnApplication.instance.assets.open(file_name).bufferedReader());
    var publicKey: PublicKey? = null
    val pubObject = publicPem.readObject()
    if (pubObject is X509CertificateHolder) {
        publicKey = JcaX509CertificateConverter()
            .setProvider(BouncyCastleProvider())
            .getCertificate(pubObject).publicKey
    } else {
        println("pubObject = $pubObject")
    }
    return publicKey
}

@Throws(
    NoSuchAlgorithmException::class,
    NoSuchPaddingException::class,
    InvalidKeyException::class,
    IllegalBlockSizeException::class,
    BadPaddingException::class,
    IOException::class
)
fun decryptWithPrivateKey( encryptedData: String): String? {
    val file_private_key = "mobile_app.crt"
    val privateKey = readPrivateKey(file_private_key)
    val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
    cipher.init(2, privateKey)
    val base64 = org.apache.commons.codec.binary.Base64()
    val decoded = base64.decode(encryptedData.toByteArray())
    //String(Base64.encode(cipherText, Base64.DEFAULT))
    val decrypt = cipher.doFinal(decoded)
    return String(decrypt)
}
@Throws(IOException::class)
private fun readPrivateKey(file_name_private_key: String): PrivateKey? {
    val keyPassword = "Bee@123!"
    val keyReader = PEMParser(VBeeOnApplication.instance.assets.open(file_name_private_key).bufferedReader())
    val converter = JcaPEMKeyConverter()
    val decryptionProv = JcePEMDecryptorProviderBuilder().build(keyPassword.toCharArray())
    val keyPair = keyReader.readObject()
    val keyInfo: PrivateKeyInfo
    keyInfo = if (keyPair is PEMEncryptedKeyPair) {
        val decryptedKeyPair = keyPair.decryptKeyPair(decryptionProv)
        decryptedKeyPair.privateKeyInfo
    } else {
        (keyPair as PEMKeyPair).privateKeyInfo
    }
    keyReader.close()
    return converter.getPrivateKey(keyInfo)
}


