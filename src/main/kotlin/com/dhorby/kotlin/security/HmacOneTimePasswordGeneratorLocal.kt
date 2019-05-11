package com.dhorby.kotlin.security

import com.warrenstrange.googleauth.GoogleAuthenticator
import java.lang.Thread.sleep



fun main() {

    val gAuth = GoogleAuthenticator()

    // QR code:
    // https@ //www.google.com/chart?chs=200x200&chld=M|0&cht=qr&chl=otpauth://totp/Example%3Adhorby%40gmail.com%3Fsecret%3Dcheese%26issuer%3DcheeseCorp

    for (i in 1..100) {
        println("-->" + gAuth.getTotpPassword("cheese"))
        sleep(5000)
    }
}


