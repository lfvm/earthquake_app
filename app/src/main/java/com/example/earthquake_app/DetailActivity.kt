package com.example.earthquake_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.earthquake_app.databinding.ActivityDetailBinding
import java.text.SimpleDateFormat
import java.util.*

class DetailActivity : AppCompatActivity() {

    companion object{
        const val EARTHQUAKE = "earthquake"
    }


    private lateinit var binder : ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binder.root)

        val bundle = intent.extras!!
        val earthquake = bundle.getParcelable<Earthquake>(EARTHQUAKE)!!


        binder.latMag.text = earthquake.latitude.toString()
        binder.longMag.text = earthquake.longitude.toString()
        binder.magnitudeValueText.text = earthquake.magnitude.toString()
        binder.placeText.text = earthquake.place
        //getting date format

        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val date = Date(earthquake.time)
        val dateString = simpleDateFormat.format(date)


        binder.dateText.text = dateString


    }
}