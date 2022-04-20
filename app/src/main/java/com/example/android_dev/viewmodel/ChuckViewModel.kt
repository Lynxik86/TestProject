package com.example.android_dev.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.android_dev.db.MyTestDb
import com.example.android_dev.model.ChuckResult
import com.example.android_dev.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChuckViewModel(application: Application) : AndroidViewModel(application) {

    internal val allChucks: LiveData<List<ChuckResult>> =
        MyTestDb.getDatabase(application).chuckResultDao().readAllData()

     var _chuckId = MutableLiveData<String>()

    private suspend fun getChuck(applicationContext: Context): ChuckResult {
        val chuckResult = RetrofitClient.getChuckApi().randomChuck()

        MyTestDb.getDatabase(applicationContext).chuckResultDao().addRandomChuck(chuckResult)
        //переключает контекст текущей сопрограммы;
        // при выполнении данного блока сопрограмма возвращается в предыдущий контекст
        Log.i("JOKE", chuckResult.id)
        return chuckResult

    }

    fun coroutineGetChuck (applicationContext: Context)= viewModelScope.launch(Dispatchers.Default) {
        _chuckId.postValue(getChuck(applicationContext).id)

        //{
        // Итак, для чего нужны корутины? Если требуется скачать что-то из сети, извлечь данные из базы данных или просто выполнить долгие вычисления и при этом не заблокировать интерфейс пользователю, можно использовать корутины.*/
        //запуск новой сопрограммы в фоне
        //сопрограммы - это легковесные потоки. Они запускаются с помощью билдера сопрограмм launch в контексте некоторого CoroutineScope. В примере выше мы запускаем новую сопрограмму в GlobalScope.
        //Это означает, что время жизни новой сопрограммы ограничено только временем жизни всего приложения.
        //GlobalScope.launch(Dispatchers.Default) {
        /*Log.i("1","first")
        viewModelScope.launch(Dispatchers.Default) {
            chuckId.value = getChuck(applicationContext).id*/
           /* val id=
            withContext(Dispatchers.Main) {
                Log.i("2","second")
                //Send an INFO log message.
              //  binding.textviewFirst.text=id
                id
                Log.i("textviewFirst", binding.textviewFirst.text.toString())

            }*/
      //  }
    }
}