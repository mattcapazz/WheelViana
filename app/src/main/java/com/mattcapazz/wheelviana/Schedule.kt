package com.mattcapazz.wheelviana

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.*


class Schedule : AppCompatActivity() {
    private lateinit var myList: ArrayList<Place>
    private lateinit var lineAdapter: LineAdapter
    private lateinit var db : FirebaseFirestore



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule)


        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        myList = arrayListOf()

        lineAdapter = LineAdapter(myList)

        recyclerView.adapter = lineAdapter

        EventChangeListener()




        val DeExtra=intent.getStringExtra("De")
        val ParaExtra=intent.getStringExtra("Para")
        Log.e("Extra", DeExtra.toString() + " " + ParaExtra.toString())


    }



    private fun EventChangeListener() {


       db = FirebaseFirestore.getInstance()
        db.collection("autocarros").whereEqualTo("autocarro_id", "DeExtra").addSnapshotListener(object : EventListener<QuerySnapshot>{
            override fun onEvent(
                value: QuerySnapshot?,
                error: FirebaseFirestoreException?
            ) {
                if (error != null){

                    Log.e("Firestore Error", error.message.toString())
                    return

                }
                for(dc : DocumentChange in value?.documentChanges!!){

                    if (dc.type == DocumentChange.Type.ADDED){

                        myList.add(dc.document.toObject(Place::class.java))
                    }
                }


                lineAdapter.notifyDataSetChanged()
            }
        })
    }



    }


