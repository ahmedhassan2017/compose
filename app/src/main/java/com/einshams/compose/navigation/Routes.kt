package com.einshams.compose.navigation

object Routes {
    const val Splash = "splash"
    const val Login = "login"
    const val Home = "home"
    const val Details = "details"

    const val DetailsIdArg = "id"
    const val DetailsPattern = "$Details/{$DetailsIdArg}"

    fun details(id: String): String = "$Details/$id"
}
