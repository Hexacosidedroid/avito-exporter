package ru.cib.avitoexporter.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.lang.Exception
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.*

@Configuration
class SslTrustAll {

    @Bean
    fun trustEveryone() {
        try {
            HttpsURLConnection.setDefaultHostnameVerifier { _, _ -> true }
            val context: SSLContext = SSLContext.getInstance("TLS")
            context.init(null, arrayOf<X509TrustManager>(object : X509TrustManager {
                @Throws(CertificateException::class)
                override fun checkClientTrusted(
                    chain: Array<X509Certificate?>?,
                    authType: String?
                ) {
                }
                @Throws(CertificateException::class)
                override fun checkServerTrusted(chain: Array<X509Certificate?>?, authType: String?) { }
                override fun getAcceptedIssuers(): Array<X509Certificate?> {
                    return arrayOfNulls(0)
                }
            }), SecureRandom())
            HttpsURLConnection.setDefaultSSLSocketFactory(
                context.socketFactory
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}