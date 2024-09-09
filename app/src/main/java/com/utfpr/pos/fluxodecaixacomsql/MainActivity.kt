package com.utfpr.pos.fluxodecaixacomsql

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
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
import com.utfpr.pos.fluxodecaixacomsql.database.DatabaseHandler
import com.utfpr.pos.fluxodecaixacomsql.databinding.ActivityMainBinding
import com.utfpr.pos.fluxodecaixacomsql.entity.Receita
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val calendar = Calendar.getInstance()
    private lateinit var banco: DatabaseHandler

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        carregarSpinner()

        banco = DatabaseHandler(this)

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

        binding.btData.setOnClickListener{
            btDataOnClickListener()
        }
        binding.btLimpar.setOnClickListener{
            btLimparOnClickListener()
        }
        binding.btSalvar.setOnClickListener{
            btSalvarOnClickListener()
        }
        binding.btVerLanAmentos.setOnClickListener{
            btVerLanAmentosOnClickListener()
        }
        binding.btSaldo.setOnClickListener{
            btSaldoOnClickListener()
        }
    }

    private fun btSaldoOnClickListener() {

    }

    private fun btVerLanAmentosOnClickListener() {
        val intent = Intent( this, ListarActivity::class.java )
        startActivity( intent )
    }

    private fun btSalvarOnClickListener() {
        if(binding.spTipo.selectedItem.toString() == "Selecione..."){
            Toast.makeText(this, "Tipo não selecionado", Toast.LENGTH_LONG).show()
        }
        else if (binding.spDetalhe.selectedItem.toString() == "Selecione..."){
            Toast.makeText(this, "Tipo não selecionado", Toast.LENGTH_LONG).show()
        }
        else if (binding.etValor.text.isEmpty()){
            Toast.makeText(this, "Valor não inserido", Toast.LENGTH_LONG).show()
        }
        else{
            banco.insert(objetoReceita())
            btLimparOnClickListener()
        }
    }

    private fun objetoReceita(): Receita {
        var receita = Receita(
            _id = 0,
            tipo = "",
            detalhe = "",
            valor = 0.00,
            data = ""
        )

        receita.tipo = binding.spTipo.selectedItem.toString()
        receita.detalhe = binding.spDetalhe.selectedItem.toString()
        receita.valor = binding.etValor.text.toString().toDouble()
        receita.data = binding.btData.text.toString()

        return receita
    }

    private fun btLimparOnClickListener() {
        binding.spTipo.setSelection(0)
        binding.spDetalhe.isEnabled = false
        binding.spDetalhe.setSelection(0)
        binding.overlay.visibility = View.VISIBLE
        binding.etValor.setText("")
        binding.btData.text = "Selecione a data"
    }

    private fun btDataOnClickListener() {
        val datePickerDialog = DatePickerDialog(
            this, {DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, monthOfYear, dayOfMonth)
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val formattedDate = dateFormat.format(selectedDate.time)

                binding.btData.text = "$formattedDate"
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        // Show the DatePicker dialog
        datePickerDialog.show()
    }

    private fun carregarSpinner(){
        val items = resources.getStringArray(R.array.tipo_array)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spTipo.adapter = adapter
    }


}