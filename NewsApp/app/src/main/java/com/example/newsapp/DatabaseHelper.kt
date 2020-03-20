package com.example.newsapp

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class DatabaseHelper {

    private val user = FirebaseAuth.getInstance().currentUser

    private val database = FirebaseDatabase.getInstance().reference

    private var list = mutableListOf<Pair<String, String>>()

    public fun updateLikeToDatabase(story: Article) {
        val referenceToUser = database.child("users").child(user!!.uid)
        val userListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val userBack = dataSnapshot.getValue()
                if (userBack != null) {
                    appendStory(user!!.uid, story)
                }
                else {
                    writeNewUser(user!!.uid, user.email, story)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())

            }
        }
        referenceToUser.addListenerForSingleValueEvent(userListener)
    }

    private fun writeNewUser(userId: String, userEmail: String?, story: Article) {
        database.child("users").child(userId).child("email").setValue(userEmail)
        val theKey = database.child("users").child(userId).push().key
        database.child("users").child(userId).child(theKey!!).setValue(story.title)
        list.add(Pair(theKey,story.title))
    }

    private fun appendStory(userId: String, story: Article) {
        val theKey = database.child("users").child(userId).push().key
        database.child("users").child(userId).child(theKey!!).setValue(story.title)
        list.add(Pair(theKey,story.title))
    }

    public fun removeLike(key :String, headline: String) {
        database.child("users").child(user!!.uid).child(key).removeValue()
        list.remove(Pair(key, headline))
    }

    private fun getAllMyLikes() {
        val referenceToUser = database.child("users").child(user!!.uid)
        val userListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val children = dataSnapshot!!.children
                children.forEach{
                    if (it.key == "email"){

                    }
                    else {
                        val castedArticle = it.getValue(String::class.java)
                        list.add(Pair(it.key!! , castedArticle!!))
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())

            }
        }
        referenceToUser.addListenerForSingleValueEvent(userListener)
    }

    public fun getLikes() : MutableList<Pair<String, String>> {
        getAllMyLikes()
        return list
    }

}