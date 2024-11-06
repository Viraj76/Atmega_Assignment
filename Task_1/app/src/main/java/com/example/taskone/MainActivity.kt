package com.example.taskone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.taskone.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sportNamesList : ArrayList<String>
    private lateinit var arrayAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sportNamesList = arrayListOf()
        arrayAdapter = ArrayAdapter<String>(this, R.layout.showing_category_layout, sportNamesList) //adapter for showing suggestion
        binding.searchEt.setAdapter(arrayAdapter)

        typeAheadFunctionality()


    }

    private fun typeAheadFunctionality() {
        // fetch data from the firestore
        val collectionRef = FirebaseFirestore.getInstance().collection("sports")
        collectionRef.addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                Log.w("TAG", "Listen failed.", exception)
                return@addSnapshotListener
            }
            if (snapshot != null) {
                for (document in snapshot) {
                    val data = document.data // This is a Map<String, Any>
                    val sportName = data["sportName"] as? String // Access a specific field
                    sportNamesList.add(sportName!!)
                }
                // Notify the ArrayAdapter of the changes in data
                arrayAdapter.notifyDataSetChanged()
            }
        }
    }
}