package com.example.tobjobs.ui.theme

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import com.example.tobjobs.R

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

// kiểu Changa One
val changa_one = GoogleFont("Changa One")
val Changa_one = FontFamily(
    Font(googleFont = changa_one, fontProvider = provider)
)

// kiểu Open Sans
val open_sans = GoogleFont("Open Sans")
val Open_sans = FontFamily(
    Font(googleFont = open_sans, fontProvider = provider)
)

// kiểu Roboto
val roboto = GoogleFont("Roboto")
val Roboto = FontFamily(
    Font(googleFont = roboto, fontProvider = provider)
)

// kiểu Montserrat
val montserrat = GoogleFont("Montserrat")
val Montserrat = FontFamily(
    Font(googleFont = montserrat, fontProvider = provider)
)

// kiểu Inter
val inter = GoogleFont("Inter")
val Inter = FontFamily(
    Font(googleFont = inter, fontProvider = provider)
)

// kiểu Lato
val lato = GoogleFont("Lato")
val Lato = FontFamily(
    Font(googleFont = lato, fontProvider = provider)
)

// kiểu Ubuntu
val ubuntu = GoogleFont("Ubuntu")
val Ubuntu = FontFamily(
    Font(googleFont = ubuntu, fontProvider = provider)
)