package com.example.newsapp.viewmodel



import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dfl.newsapi.NewsApiRepository
import com.dfl.newsapi.enums.Category
import com.dfl.newsapi.enums.Country
import com.dfl.newsapi.model.ArticleDto
import com.example.newsapp.Constants
import io.reactivex.schedulers.Schedulers

class NewsViewModel: ViewModel() {
    private val _allNews=MutableLiveData<List<ArticleDto>>()
    val allNews:LiveData<List<ArticleDto>> = _allNews
    init {
        getAllTopHeadline()
    }
    @SuppressLint("CheckResult")
    fun getAllTopHeadline(category: Category= Category.GENERAL){
        val newsApiRepository = NewsApiRepository(Constants.apiKey)
        //val url=newsApiRepository.getTopHeadlines(category = Category.GENERAL, country = Country.US, q = "trump", pageSize = 20, page = 1).
        newsApiRepository.getTopHeadlines(country = Country.US, category = category,pageSize = 10, page = 1)
            .subscribeOn(Schedulers.io())
            .subscribe(
                { response -> _allNews.postValue(response.articles) }, // Success: Update LiveData
                { error -> Log.e("NewsViewModel", "Error fetching news", error) } // Error: Log exception
            )

    }
    fun getAllQueryHeadline(query: String=""){
        val newsApiRepository = NewsApiRepository(Constants.apiKey)
        //val url=newsApiRepository.getTopHeadlines(category = Category.GENERAL, country = Country.US, q = "trump", pageSize = 20, page = 1).
        newsApiRepository.getTopHeadlines(country = Country.US, q = query,pageSize = 10, page = 1)
            .subscribeOn(Schedulers.io())
            .subscribe(
                { response -> _allNews.postValue(response.articles) }, // Success: Update LiveData
                { error -> Log.e("NewsViewModel", "Error fetching news", error) } // Error: Log exception
            )

    }

}