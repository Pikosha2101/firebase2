package com.example.firebase2

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val regBut = findViewById<Button>(R.id.regBut)
        val nameEditText = findViewById<EditText>(R.id.nameEditText)
        val lastnameEditText = findViewById<EditText>(R.id.lastnameEditText)
        val yearEditText = findViewById<EditText>(R.id.yearEditText)
        val resTextView = findViewById<TextView>(R.id.resTextView)
        val resTextView2 = findViewById<TextView>(R.id.resTextView2)
        val db = Firebase.firestore



        regBut.setOnClickListener {
            val user = hashMapOf(
                "first" to nameEditText.text.toString(),
                "last" to lastnameEditText.text.toString(),
                "born" to yearEditText.text.toString().toInt()
            )

            db.collection("users")
                .add(user)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                }

            var resText = ""
            db.collection("users")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        resText += "${document.data}\n"
                        Log.d(TAG, "${document.id} => ${document.data}")
                    }
                    resTextView.text = resText
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents.", exception)
                }
        }
        var resText2 = ""
        db.collection("Student")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    resText2 += "${document.data}\n"
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
                resTextView2.text = resText2
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }
}