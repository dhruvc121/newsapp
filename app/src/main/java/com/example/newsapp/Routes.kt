package com.example.newsapp

import kotlinx.serialization.Serializable


@Serializable
object HomePageScreen
//
//@Serializable
//object DetailsPageScreen


@Serializable
data class DetailsPageScreen(
    val url:String
)