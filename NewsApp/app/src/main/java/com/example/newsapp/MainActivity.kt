package com.example.newsapp
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()


        val login_button = findViewById<Button>(R.id.login_btn)
        val register_button = findViewById<Button>(R.id.reg_btn)
        val email_view = findViewById<EditText>(R.id.emailView)
        val password_view = findViewById<EditText>(R.id.password)

        login_button.setOnClickListener {

            val emailEntry = email_view.text.toString()
            val passwordEntry = password_view.text.toString()

            if (emailEntry.isEmpty() || passwordEntry.isEmpty()) {
                Toast.makeText(this, "Please fill all fields.", Toast.LENGTH_LONG).show()
            }

            auth.signInWithEmailAndPassword(emailEntry, passwordEntry)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")
                        val user = auth.currentUser
                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                    // ...
                }
        }

        register_button.setOnClickListener {
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }


    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser == null) {
            return
        }
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?){
        val intent = Intent(this, ArticlesActivity::class.java)
        //val intent = Intent(baseContext, ArticlesActivity::class.java)
        intent.putExtra("CURRENT_USER", currentUser)
        intent.putExtra("filter_by_likes", false)
        startActivity(intent)
    }

}
