package com.hindi.hindisayri

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.hindisayri.PrivicyPolicyActivity
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.admanager.AdManagerAdRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.hindi.hindisayri.Adapter.CatogeryAdapter
import com.hindi.hindisayri.Model.CategoryModel
import com.hindi.hindisayri.databinding.ActivityMainBinding
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // banner ads implement
        MobileAds.initialize(this@MainActivity) {}

        val adRequest = AdManagerAdRequest.Builder().build()
        binding.adManagerAdView.loadAd(adRequest)



        db = FirebaseFirestore.getInstance() // Initialize Firestore instance

        // Listen for data changes in Firestore
        db.collection("Shayari").addSnapshotListener { value, error ->
            if (error != null) { // Corrected: Check if error is not null
                Log.e("MainActivity", "Error fetching data", error)
                Toast.makeText(this, "Error fetching data: ${error.message}", Toast.LENGTH_SHORT)
                    .show()
                return@addSnapshotListener
            }

            val list = arrayListOf<CategoryModel>()
            value?.let { snapshot ->
                val data = snapshot.toObjects(CategoryModel::class.java)
                list.addAll(data)

                // Set up RecyclerView
                binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
                binding.recyclerView.adapter = CatogeryAdapter(this, list)
            } ?: run {
                Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show()
            }
        }

        // Open navigation drawer with hamburger icon click
        binding.hum.setOnClickListener {
            binding.DrawerLayout.open()
        }

        // Handle navigation item clicks
        binding.navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.share -> {
                    try {
                        val intent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_SUBJECT, "App Name")
                            putExtra(Intent.EXTRA_TEXT, "App link here")
                        }
                        startActivity(Intent.createChooser(intent, "Share With"))
                    } catch (e: Exception) {
                        Toast.makeText(this, "Unable to share this app.", Toast.LENGTH_SHORT).show()
                    }
                }

                R.id.rate -> {
                    try {
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("market://details?id=$packageName")
                            )
                        )
                    } catch (e: ActivityNotFoundException) {
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
                            )
                        )
                    }
                }

                R.id.more -> {
                    val shareIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_SUBJECT, "Check out this app!")
                        putExtra(
                            Intent.EXTRA_TEXT,
                            "Hey, check out this app: https://play.google.com/store/apps/details?id=$packageName"
                        )
                    }
                    startActivity(Intent.createChooser(shareIntent, "Share via"))
                }
                // exit app
                R.id.exit -> {
                    finishAffinity() // This closes all activities and exits the app
                }
                // privacy and policy
                R.id.condition -> {
                   startActivity(Intent(this,PrivicyPolicyActivity::class.java))
                }
            }
            binding.DrawerLayout.close()
            true
        }
    }

    override fun onBackPressed() {
        if (binding.DrawerLayout.isDrawerOpen(Gravity.LEFT)) {
            binding.DrawerLayout.closeDrawer(Gravity.LEFT)
        } else {
            super.onBackPressed()
        }
    }
}
