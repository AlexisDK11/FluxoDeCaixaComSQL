package com.utfpr.pos.fluxodecaixacomsql

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.get
import com.utfpr.pos.fluxodecaixacomsql.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        carregarSpinner()

        binding.spTipo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ){
                var selectedItem = parent.getItemAtPosition(position) as String
                if(selectedItem.equals("Crédito")){

                    val credito_itens = resources.getStringArray(R.array.credito_array)
                    val adapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_item, credito_itens)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.spDetalhe.adapter = adapter
                    binding.spDetalhe.isEnabled = true

                }else if(selectedItem.equals("Débito")){

                    val debito_itens = resources.getStringArray(R.array.debito_array)
                    val adapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_item, debito_itens)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.spDetalhe.adapter = adapter
                    binding.spDetalhe.isEnabled = true

                }else if(selectedItem.equals("Selecione...")){

                    binding.spDetalhe.isEnabled = false
                    binding.overlay.visibility = View.VISIBLE
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        binding.overlay.setOnClickListener {
            if(!binding.spDetalhe.isEnabled){
                Toast.makeText(this, "Selecione o tipo primeiro", Toast.LENGTH_SHORT).show()
            }else{
                binding.spDetalhe.visibility = View.VISIBLE
                binding.overlay.visibility = View.GONE
            }

        }

    }
    private fun carregarSpinner(){
        val items = resources.getStringArray(R.array.tipo_array)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spTipo.adapter = adapter
    }
}