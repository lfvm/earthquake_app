package com.example.earthquake_app.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.earthquake_app.DB.ApiResponseStatus
import com.example.earthquake_app.DetailActivity
import com.example.earthquake_app.Earthquake
import com.example.earthquake_app.R
import com.example.earthquake_app.api.WorkerUtil
import com.example.earthquake_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binder : ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binder = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binder.root)

        binder.eqRecycler.layoutManager = LinearLayoutManager(this)

        //Work manager
        WorkerUtil.scheduleSync(this)
        //Getting live data

        val sortType = getsortype()
        viewModel = ViewModelProvider(this,ViewModelFactory(application,sortType)).get(MainViewModel::class.java) //Viewmodelfactory le pasa el contexto

        val adapter = EqAdapter()
        binder.eqRecycler.adapter = adapter


        //Observers
        viewModel.eqlist.observe(this, Observer {

            adapter.submitList(it)

            //Mostrar un texto si la lista esta vacia

            HandleEmptyList(it)
        })

        //Mostrar ruedita de loading utilizando el status de la api.
        viewModel.status.observe(this, Observer {

            if( it == ApiResponseStatus.LOADING ){
                //Mostrar el progress bar
                binder.loagingIcon.visibility = View.VISIBLE


            }else if (it == ApiResponseStatus.DONE){
                //Ocultar el progress bar

                binder.loagingIcon.visibility = View.GONE


            }else
            {
                //Mostrar un error
                binder.loagingIcon.visibility = View.GONE

            }
        })





        adapter.onItemClickListener = {
            Toast.makeText(this, it.place,Toast.LENGTH_SHORT).show()

            val intent = Intent(this, DetailActivity::class.java)

            val eq = Earthquake(it.id,it.place,it.magnitude,it.time,it.longitude,it.latitude)

            intent.putExtra(DetailActivity.EARTHQUAKE,eq)

            startActivity(intent)
        }


    }

    private fun getsortype(): Boolean {

        val prefs = getSharedPreferences("eq_prefeneces", MODE_PRIVATE)
        return prefs.getBoolean("sort_type",false)

    }


    //Funcion para mostrar el menu en la parte superior de la pantalla
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }
    /*
       Aqui se usara el codigo que le dara logica la menu de la aplicacion
        */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemid = item.itemId

        if(itemid == R.id.mainmenutime){

            viewModel.reload_eq(getsortype())
            saveSortType(false)
        }
        else if (itemid == R.id.mainmenumagnitude){
            viewModel.reload_eq(getsortype())
            saveSortType(true)
        }

        return super.onOptionsItemSelected(item)
    }


    private fun HandleEmptyList(eqList: MutableList<Earthquake>) {
        if (eqList.isEmpty()) {
            binder.emptyView.visibility = View.VISIBLE
        } else {
            binder.emptyView.visibility = View.GONE
        }
    }

    private fun saveSortType(sortbymagnitude: Boolean){
        val prefs = getSharedPreferences("eq_prefeneces", MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putBoolean("sort_type",sortbymagnitude)
        editor.apply()
    }

}