package com.malikali.customviewapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.malikali.customviewapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mutableList:MutableList<String> = ArrayList()

        mutableList.add("helo")
        mutableList.add("ko")
        mutableList.add("dao")
        mutableList.add("elo")
        mutableList.add("plo")

        binding.customView.setData(mutableList)
        binding.customView.setTitle("Languages")

        binding.btnSubmit.setOnClickListener {

        Toast.makeText(this,binding.customView.getSelectedData().toString(),Toast.LENGTH_LONG).show()
        }
    }
}