package com.example.android_dev.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.android_dev.db.MyTestDb
import com.example.android_dev.model.FormResult
import com.example.android_dev.repository.TasksRepositoryRegister
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

val FIRSTNAME_REGEX = Regex("^[a-zA-Z][a-zA-Z0-9]{1,10}$")
val LASTNAME_REGEX = Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z!@#\$%^&*]{6,}$")
val MAIL_REGEX = Regex("^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}\$")
val PHONE_REGEX = Regex("^\\+3\\d{9}$")

class RegistryViewModel(application: Application) : AndroidViewModel(application) {

    /*private val tasksRepositoryRegister: TasksRepositoryRegister =
        TasksRepositoryRegister(
           MyTestDb.getDatabase(application).formResultDao()
        )*/

    private val tasksRepositoryRegister: TasksRepositoryRegister =
        TasksRepositoryRegister.getInstance(MyTestDb.getDatabase(application))

    // private val formResultDao = MyTestDb.getDatabase(application).formResultDao()

    internal var errorMessage = MutableLiveData<String?>()
    internal var successfulLogin = MutableLiveData<Boolean>()


    fun checkCredentials(firstname: String, password: String) =
        viewModelScope.launch(Dispatchers.Default) {

            val formResult = tasksRepositoryRegister.getFormResultByLogin(firstname)
            when {
                formResult?.first_name.isNullOrEmpty() -> {
                    errorMessage.postValue("There is no such user")
                    successfulLogin.postValue(false)
                }
                formResult!!.last_name != password -> {
                    errorMessage.postValue("Password is incorrect")
                    successfulLogin.postValue(false)
                }
                else -> {
                    successfulLogin.postValue(true)
                }
            }
        }

    /*fun checkCredentials(firstname: String, password: String) = viewModelScope.launch(Dispatchers.Default) {

          val formResult: FormResult? = formResultDao.getFormByLogin(firstname)

          when {
              formResult?.first_name.isNullOrEmpty() -> {
                  errorMessage.postValue("There is no such user")
                  successfulLogin.postValue(false)
              }
              formResult!!.last_name != password -> {
                  errorMessage.postValue("Password is incorrect")
                  successfulLogin.postValue(false)
              }
              else -> {
                  successfulLogin.postValue(true)
              }
          }
      }*/

    fun loginComplete() {
        errorMessage.value = null
        successfulLogin.value = false
    }

    fun writeRegistryData(firstname: String, lastname: String, email: String, phone: String) {
        val formResult =
            FormResult(first_name = firstname, last_name = lastname, email = email, phone = phone)
        tasksRepositoryRegister.addFormResult(formResult)
    }

    /* fun writeRegistryData(firstname: String, lastname: String, email: String, phone: String) {
         val formResult = FormResult(first_name = firstname, last_name = lastname, email = email, phone = phone)
         formResultDao.addRegistryData(formResult)
     }*/

    private fun checkTextByRegex(text: String, regex: Regex): Boolean {
        return text.matches(regex)
    }

    fun checkFirstNameByRegex(firstname: String): Boolean {
        return checkTextByRegex(firstname, FIRSTNAME_REGEX)
    }

    fun checkLastNameByRegex(lastname: String): Boolean {
        return checkTextByRegex(lastname, LASTNAME_REGEX)
    }

    fun checkEmailByRegex(email: String): Boolean {
        return checkTextByRegex(email, MAIL_REGEX)
    }

    fun checkPhoneByRegex(phone: String): Boolean {
        return checkTextByRegex(phone, PHONE_REGEX)
    }

    fun checkAllTextByRegex(
        firstname: String,
        lastname: String,
        email: String,
        phone: String
    ): Boolean {
        return checkFirstNameByRegex(firstname) &&
                checkLastNameByRegex(lastname) &&
                checkEmailByRegex(email) &&
                checkPhoneByRegex(phone)
    }
}