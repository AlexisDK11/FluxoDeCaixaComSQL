package com.utfpr.pos.fluxodecaixacomsql.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.utfpr.pos.fluxodecaixacomsql.R
import com.utfpr.pos.fluxodecaixacomsql.entity.Receita

class Adapter(
    private val context: Context,
    private val receitas: List<Receita> // Lista de objetos Receita em vez de Cursor
) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    // ViewHolder interno
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivImagemLista: ImageView = itemView.findViewById(R.id.ivImagemLista)
        val tvTipoLista: TextView = itemView.findViewById(R.id.tvTipoLista)
        val tvDetalheLista: TextView = itemView.findViewById(R.id.tvDetalheLista)
        val tvValorLista: TextView = itemView.findViewById(R.id.tvValorLista)
        val tvDataLista: TextView = itemView.findViewById(R.id.tvDataLista)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.elemento_lista, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val receita = receitas[position]

        holder.tvTipoLista.text = receita.tipo
        holder.tvDetalheLista.text = receita.detalhe
        holder.tvValorLista.text = receita.valor.toString()
        holder.tvDataLista.text = receita.data

        Log.e("TIPO", receita.tipo.toString())
        println(receita.tipo.toString())
        if (receita.tipo == "Crédito") {
            holder.ivImagemLista.setImageResource(R.drawable.baseline_add_24)
        } else {
            holder.ivImagemLista.setImageResource(R.drawable.baseline_remove_24)
        }
    }

    override fun getItemCount(): Int {
        return receitas.size
    }
}
