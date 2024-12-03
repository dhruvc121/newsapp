package com.example.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.newsapp.pages.DetailsPage
import com.example.newsapp.pages.HomePage
import com.example.newsapp.ui.theme.NewsAppTheme
import com.example.newsapp.viewmodel.NewsViewModel

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val newsViewModel= ViewModelProvider(this)[NewsViewModel::class.java]
        enableEdgeToEdge()
        setContent {
            val navController= rememberNavController()
            NewsAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()
                    , topBar = {
                        CenterAlignedTopAppBar(
                            title={
                                Text("News App")
                            }
                        )
                    }
                ) { innerPadding->
                    Column(
                        modifier = Modifier.fillMaxSize().padding(innerPadding)
                    ) {
                        NavHost(navController = navController, startDestination = HomePageScreen,) {
                            composable<HomePageScreen> {
                                HomePage(newsViewModel,navController)
                            }
                            composable<DetailsPageScreen> {
                                val args = it.toRoute<DetailsPageScreen>()
                                DetailsPage(args.url)
                            }
                        }

                    }

                }
            }
        }
    }
}
