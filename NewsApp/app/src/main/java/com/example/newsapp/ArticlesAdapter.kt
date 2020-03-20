package com.example.newsapp

import android.content.Intent.getIntent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import java.lang.Exception
import java.util.*


class ArticlesAdapter(private var articles: List<Article>,
                      private val filterByLike : Boolean,
                      private val onArticleClick: (article: Article) -> Unit
                      ): RecyclerView.Adapter<ArticlesAdapter.ArticleViewHolder>() {

    private val dataHandler = DatabaseHelper()
    private var likedArticles: List<Pair<String, String>> = dataHandler.getLikes()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder{
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.newscard,parent,false)

        return ArticleViewHolder(view)
    }

    override fun getItemCount() : Int {
        if(filterByLike)
        {
            return likedArticles.size
        }
        return articles.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        if(filterByLike){
            holder.likeBind(likedArticles[position].second)
        }
        else {
            holder.bind(articles[position])
        }
    }

    fun updateArticles(articles: List<Article>){
        this.articles = articles
        notifyDataSetChanged()
    }

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private val news_image : ImageView? = itemView.findViewById(R.id.photo)
        private val title: TextView = itemView.findViewById(R.id.headline)
        private val author: TextView? = itemView.findViewById(R.id.author)
        val like_button = itemView.findViewById<ToggleButton>(R.id.button_favorite)

        fun bind(story: Article){ //We populate data in article box
            Glide.with(itemView)
                .load(story.urlToImage)
                .transform(CenterCrop())
                .into(news_image!!)

            title.text = story.title
            author?.text = story.author
            like_button.setChecked(false) // by default because the xml file doesnt do it for some reason
            potentialAlreadyLiked(like_button, story)
            like_button.setOnClickListener{
                val status = like_button.isChecked
                if(status) {
                    // record in database
                    dataHandler.updateLikeToDatabase(story)
                }
                else {
                    // otherFunction(story) take out story from user profile
                    dataHandler.removeLike(getKeyFromStory(story.title),story.title)

                }
                likedArticles = dataHandler.getLikes() // update our records on front end
            }
            itemView.setOnClickListener { onArticleClick.invoke(story) }
        }

        fun likeBind (title: String) {
            this.title.text = title
            like_button.setChecked(true)
            like_button.setOnClickListener{
                val status = like_button.isChecked
                if(status) {
                    // record in database
                    val titleStory = Article("", title, "","","", Date(),"")
                    dataHandler.updateLikeToDatabase(titleStory)
                }
                else {
                    // otherFunction(story) take out story from user profile
                    dataHandler.removeLike(getKeyFromStory(title),title)

                }
                likedArticles = dataHandler.getLikes() // update our records on front end
            }
        }

        private fun potentialAlreadyLiked(likeIcon: ToggleButton, story: Article) {
            if (likedArticles == null) { return }
            if (likedArticles.isEmpty()) { return }
            if (storiesAreEqual(story)) { likeIcon.setChecked(true) }
        }

        private fun storiesAreEqual(story: Article): Boolean {
            likedArticles.forEach {
                if (story.title == it.second) {
                    return true
                }
            }
            return false
        }

        private fun getKeyFromStory(headline: String) : String {
            likedArticles.forEach {
                if (headline == it.second) {
                    return it.first
                }
            }
            throw Exception("Request returned nothing")
        }
    }



}