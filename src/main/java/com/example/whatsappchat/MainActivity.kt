package com.example.whatsappchat

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.whatsappchat.adapter.UserAdapter
import com.example.whatsappchat.data.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class MainActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var userRecyclerView : RecyclerView
    private lateinit var userList : ArrayList<Users>
    private lateinit var adapter : UserAdapter
    private lateinit var auth: FirebaseAuth
    private lateinit var mDbRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().getReference()

        userList = ArrayList()
        adapter = UserAdapter(this, userList)

        userRecyclerView = findViewById(R.id.userRecyclerView)
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.adapter = adapter

        mDbRef.child("user").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                userList.clear()
                for (postSnapshot in snapshot.children){
                    val currentUser = postSnapshot.getValue(Users::class.java)

                    if (auth.currentUser?.uid != currentUser?.uid){
                        userList.add(currentUser!!)

                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })

//        // Get NavHost and NavController
        val navHostFrag = supportFragmentManager.findFragmentById(R.id.nav_host_frag) as NavHostFragment
        navController = navHostFrag.navController
//        // Get AppBarConfiguration
        appBarConfiguration = AppBarConfiguration(navController.graph)
//
//        // Link ActionBar with NavController
        setupActionBarWithNavController(navController, appBarConfiguration)

        val sharedPreference = PreferenceManager.getDefaultSharedPreferences(this)
        val autoReplyTime = sharedPreference.getString(getString(R.string.key_auto_reply_time), "")
        Log.i("MainActivity", "Auto Reply Time: $autoReplyTime")
        val publicInfo: Set<String>? = sharedPreference.getStringSet("key_public_info", null)
        Log.i("MainActivity", "Public Info: $publicInfo")

    }

    override fun onSupportNavigateUp(): Boolean {

        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String){
        if (key == "key_status"){
            val newStatus = sharedPreferences.getString(key, "")
            Toast.makeText(this, "New Status: $newStatus", Toast.LENGTH_SHORT).show()
        }

        if (key == "key_auto_reply"){

            val autoReply = sharedPreferences.getBoolean(key, false)
            if (autoReply){
                Toast.makeText(this, "Auto Reply: ON", Toast.LENGTH_SHORT).show()

            }else{

                Toast.makeText(this, "Auto Reply: OFF", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onResume() {
        super.onResume()
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this)
    }



}
