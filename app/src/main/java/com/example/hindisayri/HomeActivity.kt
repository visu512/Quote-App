package com.hindi.hindisayri

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hindisayri.Adapter.AllShayriAdapter
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.admanager.AdManagerAdRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.hindi.hindisayri.Model.ShayariModel
import com.hindi.hindisayri.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize banner ad
        MobileAds.initialize(this@HomeActivity)
        val adRequest = AdManagerAdRequest.Builder().build()
        binding.adManagerAdView.loadAd(adRequest)

        db = FirebaseFirestore.getInstance()

        // Get data from intent
        val id = intent.getStringExtra("id")
        val name = intent.getStringExtra("name")

        binding.backBtn.setOnClickListener {
            onBackPressed() // Back navigation
        }
        binding.catItem.text = name.toString() // Set category name

        // Retrieve Shayari data from Fire store
        id?.let {
            db.collection("Shayari").document(it).collection("all")
                .addSnapshotListener { value, error ->
                    if (error != null) {
                        // Handle Firestore error
                        return@addSnapshotListener
                    }

                    val shayriList = arrayListOf<ShayariModel>()
                    value?.toObjects(ShayariModel::class.java)?.let { data ->
                        shayriList.addAll(data)
                    }

                    // Set up RecyclerView with Shayari list
                    binding.rcvSayriView.layoutManager = LinearLayoutManager(this)
                    binding.rcvSayriView.adapter = AllShayriAdapter(this, shayriList)
                }
        }
    }
}
