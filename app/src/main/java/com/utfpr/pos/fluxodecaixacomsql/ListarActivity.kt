package com.utfpr.pos.fluxodecaixacomsql

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.utfpr.pos.fluxodecaixacomsql.adapter.Adapter
import com.utfpr.pos.fluxodecaixacomsql.database.DatabaseHandler
import com.utfpr.pos.fluxodecaixacomsql.databinding.ActivityListarBinding
import com.utfpr.pos.fluxodecaixacomsql.databinding.ActivityMainBinding

class ListarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListarBinding
    private lateinit var banco : DatabaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        banco = DatabaseHandler( this )

        binding.faAbrirMainActivity.setOnClickListener{
            faAbrirMainActivityOnClick()
        }
    }

    private fun faAbrirMainActivityOnClick() {
        val intent = Intent( this, MainActivity::class.java )
        startActivity( intent )
    }

    override fun onStart() {
        super.onStart()

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        val cursor = banco.cursorList()
        val receitas = banco.cursorToReceitas(cursor)

        val adapter = Adapter(this, receitas)

        binding.recyclerView.adapter = adapter
    }

}