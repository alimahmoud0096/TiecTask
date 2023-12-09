package com.smart4apps.gopathontask.ui.view

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.smart4apps.gopathontask.ui.viewmodel.MainViewModel
import com.smart4apps.gopathontask.databinding.ActivityMainBinding
import com.smart4apps.gopathontask.ui.viewmodel.HistoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModels()
    private val historyViewModel: HistoryViewModel by viewModels()




    lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
       /* setupUI()
        setupAPICall()*/
/*
        lifecycleScope.launch {  }
//        GlobalScope.launch(Dispatchers.Main) {
        lifecycleScope.launch {
            userViewModel.insertUser(User(name = "Ali mahmoud", id = 1))
            userViewModel.insertUser(User(name = "Ali mahmoud", id = 2))
        }

        userViewModel.getAllUsers()

        lifecycleScope.launch {
            userViewModel.usersListLiv.collectLatest {

                it.forEach {
                    withContext(Dispatchers.Main) {
                        Log.d("TAG", "onCreate: ${it}")
                    }
                }


            }

        }
        //}*/

    }

    /*private fun setupUI() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
//        adapter = MainAdapter()
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                binding.recyclerView.context,
                (binding.recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding.recyclerView.adapter = adapter
    }

    private fun setupAPICall() {
        mainViewModel.fetchUsers().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    it.data?.let { usersData -> renderList(usersData) }
                    binding.recyclerView.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                }
                Status.ERROR -> {
                    //Handle Error
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }*/


    fun showProgress(){
        binding.progressBar.visibility = View.VISIBLE
    }

    fun hideProgress(){
        binding.progressBar.visibility = View.GONE
    }

}
