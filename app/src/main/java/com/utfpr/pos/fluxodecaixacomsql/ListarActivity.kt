package com.utfpr.pos.fluxodecaixacomsql

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.utfpr.pos.fluxodecaixacomsql.databinding.ActivityListarBinding
import com.utfpr.pos.fluxodecaixacomsql.databinding.ActivityMainBinding

class ListarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.faAbrirMainActivity.setOnClickListener{
            faAbrirMainActivityOnClick()
        }
    }

    private fun faAbrirMainActivityOnClick() {
        val intent = Intent( this, MainActivity::class.java )
        startActivity( intent )
    }
}