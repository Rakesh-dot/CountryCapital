package com.rakesh.countrycapital

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintStream
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var lstCountries: ListView
    private lateinit var btnAddCountry: Button
    private lateinit var etCapital: EditText
    private lateinit var etCountry: EditText
    private var countryCapitalMap=mutableMapOf<String,String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etCountry=findViewById(R.id.etCountry)
        etCapital=findViewById(R.id.etCapital)
        btnAddCountry=findViewById(R.id.btnAddCountry)
        lstCountries=findViewById(R.id.lstCountries)

        btnAddCountry.setOnClickListener {
            addCountryToText()
            loadCountriesFromText()
        }
        loadCountriesFromText()

    }

    private fun loadCountriesFromText() {
        try{
            val fileInputStream=openFileInput("Country.txt")
            val inputStream=InputStreamReader(fileInputStream)
            val bufferedReader=BufferedReader(inputStream)
            for(line in bufferedReader.lines()){
                val countryCapital=line.split(" -> ")
                val country=countryCapital[0]
                val capital=countryCapital[1]
                countryCapitalMap[country]=capital
            }
            displayCountries(countryCapitalMap)
        }
        catch (e:IOException){
            Toast.makeText(this,"Error ${e.localizedMessage}",Toast.LENGTH_SHORT)
        }
    }

    private fun displayCountries(countryCapitalMap: MutableMap<String, String>) {
        val adapter=ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                countryCapitalMap.keys.toTypedArray()
        )
        lstCountries.adapter=adapter

    }

    private fun addCountryToText() {
        try {
            val country=etCountry.text.toString()
            val capital=etCapital.text.toString()
            val printStream=PrintStream(
                    openFileOutput(
                            "Country.txt",
                            MODE_APPEND
                    )
            )
            printStream.println("$country -> $capital")
            Toast.makeText(
                    this,"$country saved",
                    Toast.LENGTH_SHORT
            ).show()
        }catch (e: IOException){
            Toast.makeText(this,"Error ${e.localizedMessage}",
            Toast.LENGTH_SHORT).show()
        }
    }


}