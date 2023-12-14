package pl.edu.agh.utp.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import pl.edu.agh.utp.GroupsFragment
import pl.edu.agh.utp.ProfileFragment
import pl.edu.agh.utp.R
import pl.edu.agh.utp.SettingsFragment
import pl.edu.agh.utp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the binding object
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.profile -> {
                    replaceFragment(ProfileFragment())
                    true
                }

                R.id.settings -> {
                    replaceFragment(SettingsFragment()) // Fixed typo in "Settings"
                    true
                }

                R.id.groups -> {
                    replaceFragment(GroupsFragment())
                    true
                }

                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }
}
