package com.example.android_dev.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.android_dev.R
import com.example.android_dev.databinding.ActivityMainBinding
import com.example.android_dev.databinding.ActivityTestBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    //проект приложения с нижней панелью навигации BottomNavigationView на главном экране
    //В любое объявление var-свойства можно добавить ключевое слово lateinit.
    // Тогда Kotlin позволит отложить инициализацию свойства до того момента, когда такая возможность появится.
    private lateinit var appBarConfiguration: AppBarConfiguration

    //При включении View Binding в определенном модуле он генерирует binding классы для каждого файла разметки (layout) в модуле.
    private lateinit var binding: ActivityMainBinding
    private lateinit var bindingTest: ActivityTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        //добавить идентификаторы пунктов навигации в конфигурацию контроллера навигации в классе MainActivity.
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        /*Переменная класса АppBarConfiguration содержит набор идентификаторов пунктов навигации и передается вместе с контроллером –
        хостом навигации – в функцию setupActionBarWithNavController.
        Это нужно для того, чтобы система считала каждый из пунктов назначения пунктом верхнего уровня. */
        setupActionBarWithNavController(navController, appBarConfiguration)

        //SnackBar — виджет библиотеки материального дизайна (Material Design) для замены всплывающего сообщения Toast.
        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()

    }
}

