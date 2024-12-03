package com.example.newsapp.pages

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView


@Composable
fun DetailsPage(url:String) {
    AndroidView(factory = {context->
        WebView(context).apply {
            settings.javaScriptEnabled=true
            webViewClient= WebViewClient()
            loadUrl(url)
        }
    })

}