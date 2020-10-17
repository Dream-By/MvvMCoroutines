package com.example.mvvmcoroutines

import androidx.lifecycle.MutableLiveData

class ActivityViewModel : BaseViewModel() {
    // Создаем лайвдату для нашего списка юзеров
    val simpleLiveData = MutableLiveData<Event<Users>>()

    // Получение юзеров. Обращаемся к функции  requestWithLiveData
    // из BaseViewModel передаем нашу лайвдату и говорим,
    // какой сетевой запрос нужно выполнить и с какими параметрами
    // В данном случае это api.getUsers
    // Теперь функция сама выполнит запрос и засетит нужные
    // данные в лайвдату

    fun getUsers(page: Int) {
        requestWithLiveData(simpleLiveData) {
            api.getUsers(
                page = page
            )
        }
    }

    // Здесь аналогично, но вместо лайвдаты используем котлиновский колбек
    // UPD Полученный результат мы можем обработать здесь перед отправкой во вью

    fun getUsersError(page: Int, callback: (data: Event<Users>) -> Unit) {
        requestWithCallback({
            api.getUsersError(
                page = page
            )
        }) {
            callback(it)
        }
    }
}