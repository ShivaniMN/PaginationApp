package com.example.paginationapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Scroller
import androidx.core.view.ViewCompat.canScrollVertically
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bekawestberg.loopinglayout.library.LoopingLayoutManager
import com.example.paginationapp.models.DataItem
import com.example.paginationapp.network.ApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.horizontal_list_item.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

const val  BASE_URL = "http://simple-node-app-nkd.herokuapp.com"
class MainActivity : AppCompatActivity() {

    var pageNumHorizontal = 1
    var pageNumVertical = 1
    var isLoading = false
    lateinit var dataItemListHorizontal : MutableList<DataItem>
    lateinit var dataItemListVertical: MutableList<DataItem>
    lateinit var verticalAdapter: VerticalAdapter
    lateinit var horizontalAdapter: HorizontalAdapter
    lateinit var horizontalLinearLayoutManager: LinearLayoutManager
    lateinit var linearLayoutManagerVertical: LinearLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvHorizontal.setHasFixedSize(true)
        horizontalLinearLayoutManager = LinearLayoutManager(this)
        horizontalLinearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        rvHorizontal.layoutManager = horizontalLinearLayoutManager

        rvVertical.setHasFixedSize(true)
        linearLayoutManagerVertical = LinearLayoutManager(this)
        linearLayoutManagerVertical.orientation = LinearLayoutManager.VERTICAL
        rvVertical.layoutManager = linearLayoutManagerVertical

        dataItemListHorizontal = mutableListOf()
        dataItemListVertical = mutableListOf()

        getAllDataHorizontal()
        getAllDataVertical()


        rvHorizontal.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount = horizontalLinearLayoutManager.childCount
                val pastVisibleItem = horizontalLinearLayoutManager.findLastVisibleItemPosition()
                val total = horizontalAdapter.itemCount
                if (!isLoading){
                    if ((visibleItemCount + pastVisibleItem) >= total) {
                        getAllDataHorizontal()
                    }
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })

        rvVertical.addOnScrollListener(object : RecyclerView.OnScrollListener(){

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                    val visibleItemCount = linearLayoutManagerVertical.childCount
                    val pastVisibleItem = linearLayoutManagerVertical.findFirstCompletelyVisibleItemPosition()
                    val total = verticalAdapter.itemCount

                        if (!isLoading) {
                            if ((visibleItemCount + pastVisibleItem) >= total) {
                                getAllDataVertical()
                            }
                        }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }


    private fun getAllDataHorizontal(){
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL)
            .build()
            .create(ApiService::class.java)

        val retrofitData = retrofitBuilder.getAllData()
        retrofitData.enqueue(object:Callback<List<DataItem>>{


            override fun onResponse(call: Call<List<DataItem>>, response: Response<List<DataItem>?>){
                val responseBody = response.body()!!
                if(pageNumHorizontal <= 5){
                    pageNumHorizontal++
                    dataItemListHorizontal.addAll(responseBody)
                    horizontalAdapter = HorizontalAdapter(baseContext, dataItemListHorizontal)
                    horizontalAdapter.notifyDataSetChanged()
                    rvHorizontal.adapter = horizontalAdapter
                    Log.d("message1", dataItemListHorizontal.toString())
                    isLoading = false
                }else{
                    rvHorizontal.stopScroll()
                }


            }

            override fun onFailure(call: Call<List<DataItem>>, t: Throwable) {
                Log.d("MainActivity", "onFailure:" + t.message)
            }
        })
    }


    private fun getAllDataVertical(){
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL)
            .build()
            .create(ApiService::class.java)

        val retrofitData = retrofitBuilder.getAllData()
        retrofitData.enqueue(object:Callback<List<DataItem>>{

            override fun onResponse(call: Call<List<DataItem>>, response: Response<List<DataItem>?>){
                val responseBody = response.body()!!
                if(pageNumVertical <= 5){
                    pageNumVertical++
                    dataItemListVertical.addAll(responseBody)
                    verticalAdapter = VerticalAdapter(baseContext, dataItemListVertical)
                    verticalAdapter.notifyDataSetChanged()
                    rvVertical.adapter = verticalAdapter
                    Log.d("message", dataItemListVertical.toString())
                    isLoading = false
                }else{
                    rvVertical.stopScroll()
                }

            }

            override fun onFailure(call: Call<List<DataItem>>, t: Throwable) {
                Log.d("MainActivity", "onFailure:" + t.message)
            }
        })
    }

}
