package ru.geekbrains.notes

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import ru.geekbrains.notes.observation.Publisher
import ru.geekbrains.notes.view.Navigation
import ru.geekbrains.notes.view.NoteFragment
import ru.geekbrains.notes.view.NotesFragment

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var toolbar: Toolbar? = null
    private var drawerLayout: DrawerLayout? = null
    private var navigationView: NavigationView? = null
    val publisher = Publisher()
    var navigation: Navigation? = null
        private set

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation = Navigation(supportFragmentManager)
        initSideBar()
        navigation!!.addFragment(NotesFragment.newInstance(), false)
    }

    override fun onResume() {
        super.onResume()
        val backStackFragment = supportFragmentManager
            .findFragmentById(R.id.notes_container)
        if (backStackFragment is NoteFragment) {
            onBackPressed()
        }
    }

    private fun initSideBar() {
        toolbar = findViewById(R.id.main_toolbar)
        setSupportActionBar(toolbar)
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)
        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.openNavDrawer,
            R.string.closeNavDrawer
        )
        drawerLayout?.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        navigationView?.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> {
                Toast.makeText(baseContext, "settings selected", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.action_about -> {
                Toast.makeText(baseContext, "about application selected", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return false
    }
}
