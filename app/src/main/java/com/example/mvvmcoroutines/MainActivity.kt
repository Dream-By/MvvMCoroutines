package com.example.mvvmcoroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var activityViewModel: ActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activityViewModel = ViewModelProviders.of(this).get(ActivityViewModel::class.java)
        observeGetPosts()

        buttonOneClickListener()
        buttonTwoClickListener()
    }

    // Наблюдаем за нашей лайвдатой
    // В зависимости от Ивента устанавливаем нужное состояние вью
    private fun observeGetPosts() {
        activityViewModel.simpleLiveData.observe(this, Observer {
            when (it.status) {
                Event.Status.LOADING -> viewOneLoading()
                Event.Status.SUCCESS -> viewOneSuccess(it.data)
                Event.Status.ERROR -> viewOneError(it.error)
            }
        })
    }

    private fun buttonOneClickListener() {
        btn_test_one.setOnClickListener {
            activityViewModel.getUsers(page = 1)
        }
    }

    // Здесь так же наблюдаем за Ивентом, используя колбек
    private fun buttonTwoClickListener() {
        btn_test_two.setOnClickListener {
            activityViewModel.getUsersError(page = 2) {
                when (it.status) {
                    Event.Status.LOADING -> viewTwoLoading()
                    Event.Status.SUCCESS -> viewTwoSuccess(it.data)
                    Event.Status.ERROR -> viewTwoError(it.error)
                }
            }
        }
    }

    private fun viewOneLoading() {
        // Загрузка, изменение состояние вьюхи
    }

    private fun viewOneSuccess(data: Users?) {
        val usersList: MutableList<Users.Item>? = data?.items as MutableList<Users.Item>?
        usersList?.shuffle()
        usersList?.let {
            Toast.makeText(applicationContext, "${it}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun viewOneError(error: Error?) {
        // Показываем ошибку
    }

    private fun viewTwoLoading() {}

    private fun viewTwoSuccess(data: Users?) {}

    private fun viewTwoError(error: Error?) {
        error?.let {
            Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT).show()
        }
    }
}