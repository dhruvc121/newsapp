package com.example.newsapp.pages


import android.R.attr.category
import android.widget.Toast
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.example.newsapp.viewmodel.NewsViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.size.Scale
import com.dfl.newsapi.enums.Category
import com.dfl.newsapi.model.ArticleDto
import com.example.newsapp.DetailsPageScreen
import java.nio.file.WatchEvent


@Composable
fun HomePage(newsViewModel: NewsViewModel,navController: NavHostController) {

    val allNews by newsViewModel.allNews.observeAsState(initial = emptyList())

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Categories(newsViewModel)
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
           items(allNews){
               item->
               ArcticlItem(item,navController)
           }
        }


    }
}

@Composable
fun ArcticlItem(article: ArticleDto,navController: NavHostController) {
    Card (
        modifier = Modifier.padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        onClick = {
            navController.navigate(DetailsPageScreen(article.url))
        }
    ){
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            AsyncImage(
                model = article.urlToImage?:"https://placehold.co/150",
                contentDescription = "Article Image",
                modifier = Modifier
                    .size(80.dp)
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 8.dp)
            ) {
                Text(
                    text=article.title,
                    fontWeight = FontWeight.Bold,
                    maxLines = 3
                )
                Text(
                    text=article.source.name,
                    maxLines = 1,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun Categories(newsViewModel: NewsViewModel) {

    var isSearchExpanded by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    val context= LocalContext.current
    Row(
        modifier= Modifier.fillMaxWidth().padding(8.dp).horizontalScroll(rememberScrollState())
    ) {
        if (isSearchExpanded){
            OutlinedTextField(
                value = searchQuery,
                onValueChange = {searchQuery=it},
                placeholder = {Text("Search you interest")},
                trailingIcon = {
                    IconButton(onClick = {
                        if(searchQuery.isNotEmpty()){
                            newsViewModel.getAllQueryHeadline(searchQuery)
                        }else{
                            Toast.makeText(context,"Serach field cannot be empty!!", Toast.LENGTH_SHORT).show()
                        }

                    }) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                    }
                }
            )
        }else{
            IconButton(onClick = {
                isSearchExpanded=true
            }) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
            }
        }
        Category.entries.forEach { item ->
            Button(onClick = { newsViewModel.getAllTopHeadline(item) },modifier= Modifier.padding(4.dp)) {
                Text(text = item.value)
            }
        }
    }
}