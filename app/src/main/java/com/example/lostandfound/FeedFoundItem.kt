package com.example.lostandfound

import android.os.Bundle;
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase


class FeedFoundItem : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var recyclerView: RecyclerView
    private lateinit var userArrayList: ArrayList<FoundModel>
    private lateinit var myadapter: FoundAdapter
    private lateinit var db: FirebaseFirestore



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed_found_item)

        recyclerView = findViewById(R.id.recyclerView_feedFoundItem)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        userArrayList = arrayListOf()

        myadapter = FoundAdapter(userArrayList, this)
        recyclerView.adapter = myadapter

        EventChangeListener()









    }

    private fun EventChangeListener() {
        db = FirebaseFirestore.getInstance()
        db.collection("Found Item Post").
        addSnapshotListener(object: EventListener<QuerySnapshot>{
            override fun onEvent(
                value: QuerySnapshot?,
                error: FirebaseFirestoreException?
            ){
                if(error!= null){
                    Log.e("Firestore Error", error.message.toString())
                    return
                }

                for(dc : DocumentChange in value?.documentChanges!!){

                    if(dc.type == DocumentChange.Type.ADDED){
                        userArrayList.add(dc.document.toObject(FoundModel::class.java))
                    }

                }
                myadapter.notifyDataSetChanged()

            }

        })
    }
}