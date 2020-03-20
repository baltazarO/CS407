package com.example.newsapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class ArticlesActivity : AppCompatActivity() {

        private lateinit var WSJArticles: RecyclerView
        private lateinit var WSJArticlesAdapter: ArticlesAdapter
        private lateinit var WSJArticlesLayoutMgr: LinearLayoutManager

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_articles)

            val sign_out = findViewById<Button>(R.id.sign_off)
            val see_likes = findViewById<Button>(R.id.see_likes)

            WSJArticles = findViewById(R.id.WSJArticles)
            WSJArticlesLayoutMgr = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)

            WSJArticles.layoutManager = WSJArticlesLayoutMgr
            val areWeFiltering = intent.getBooleanExtra("filter_by_likes", false)
            WSJArticlesAdapter = ArticlesAdapter(mutableListOf(), areWeFiltering) { story -> showArticleDetails(story) }

            WSJArticles.adapter = WSJArticlesAdapter

            getWSJArticles()

            sign_out.setOnClickListener{
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
            }

            see_likes.setOnClickListener{
                val intent = Intent(this,ArticlesActivity::class.java)
                intent.putExtra("filter_by_likes", true)
                startActivity(intent)
            }
        }

        private fun getWSJArticles() {
            ArticlesRepository.getWSJArticles(
                ::onWSJFetched, ::onError
            ) }

        private fun onWSJFetched(articles: List<Article>) {
            WSJArticlesAdapter.updateArticles(articles) }

        private fun onError() {
            Toast.makeText(this, getString(R.string.error_toast), Toast.LENGTH_SHORT).show() }

        private fun showArticleDetails(article: Article){
            val intent = Intent(this,ArticleDetailsActivity::class.java)
            intent.putExtra(BACKDROP,article.urlToImage)
            intent.putExtra(TITLE,article.title)
            intent.putExtra(AUTHORS,article.author)
            intent.putExtra(DATE,article.publishedAt)
            intent.putExtra(CONTENT,article.content)
            startActivity(intent)
        }

}