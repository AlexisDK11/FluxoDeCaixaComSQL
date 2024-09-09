package com.utfpr.pos.fluxodecaixacomsql.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.utfpr.pos.fluxodecaixacomsql.entity.Receita

class DatabaseHandler (context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE IF NOT EXISTS ${TABLE_NAME} (_id INTEGER PRIMARY KEY AUTOINCREMENT, tipo TEXT, detalhe TEXT, valor DOUBLE, data TEXT )")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${TABLE_NAME}")
        onCreate(db)
    }

    companion object {
        private const val DATABASE_NAME = "dbfile.sqlite"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "receita"
        private const val COD = 0
        private const val TIPO = 1
        private const val DETALHE = 2
        private const val VALOR = 3
        private const val DATA = 4

    }

    fun insert(receita : Receita){
        val db = this.writableDatabase

        val registro = ContentValues()
        registro.put("tipo", receita.tipo)
        registro.put("detalhe", receita.detalhe)
        registro.put("valor", receita.valor)
        registro.put("data", receita.data)

        db.insert(TABLE_NAME, null, registro)
    }

    fun list() : MutableList<Receita>{
        val db = this.writableDatabase
        val registro = db.query(
            TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )

        var registros = mutableListOf<Receita>()

        while (registro.moveToNext()){
            val receita = Receita(
                registro.getInt(COD),
                registro.getString(TIPO),
                registro.getString(DETALHE),
                registro.getDouble(VALOR),
                registro.getString(DATA)
            )
            registros.add(receita)
        }
        return registros
    }
    fun cursorList() : Cursor {
        val db = this.writableDatabase

        val registro = db.query(
            "receita",
            null,
            null,
            null,
            null,
            null,
            null
        )

        return registro
    }
    fun cursorToReceitas(cursor: Cursor): List<Receita> {
        val receitas = mutableListOf<Receita>()
        while (cursor.moveToNext()) {
            val receita = Receita(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getDouble(3),
                cursor.getString(4)
            )
            receitas.add(receita)
        }
        cursor.close()
        return receitas
    }

}