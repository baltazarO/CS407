package com.example.newsapp

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import java.util.*


const val BACKDROP = "extra_backdrop"
const val TITLE = "extra_title"
const val AUTHORS = "extra_authors"
const val DATE = "extra_date"
const val CONTENT = "extra_content"

class ArticleDetailsActivity : AppCompatActivity() {

    private lateinit var backdrop: ImageView
    private lateinit var title: TextView
    private lateinit var authors: TextView
    private lateinit var date: TextView
    private lateinit var content: TextView

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_articles_details)

        backdrop = findViewById(R.id.article_backdrop)
        title = findViewById(R.id.article_title)
        authors = findViewById(R.id.writers)
        date = findViewById(R.id.date)
        content = findViewById(R.id.overview)

        val extras = intent.extras

        if(extras != null){
            populateDetails(extras)
        } else{
            finish()
        }
    }

    private fun populateDetails(extras: Bundle){
        extras.getString(BACKDROP)?.let{ urlToImage ->
            Glide.with(this)
                .load(urlToImage)
                .transform(CenterCrop())
                .into(backdrop)
        }

        val raw_date: Date = (intent.getSerializableExtra(DATE) as Date)
        title.text = extras.getString(TITLE,"")
        authors.text = extras.getString(AUTHORS,"")
        date.text = raw_date.toString()
        val the_content = extras.getString(CONTENT,"")
            .replace("\r\n","")
        content.text = the_content
    }
}