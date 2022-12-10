package blim.enbek.talpynys.api_hero

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.WindowInsetsController
import android.view.WindowManager
import blim.enbek.talpynys.api_hero.databinding.ActivityMainBinding
import blim.enbek.talpynys.api_hero.databinding.FragmentMainBinding
import blim.enbek.talpynys.api_hero.fragments.MainFragment
import blim.enbek.talpynys.api_hero.fragments.SearchFragment
import blim.enbek.talpynys.api_hero.fragments.SplashFragment
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import org.jetbrains.annotations.NotNull
import org.json.JSONObject
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.bottomNav.run {
            setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.navBarHome -> {
                        homeFragment()
                    }
                    R.id.navBarSearch -> {
                        searchFragment()
                    }
                }
                true
            }
        }

        setContentView(binding.root)
        splash()
        toMainFragment()

    }

    fun splash() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.placeForFragment, SplashFragment.newInstance()).commit()
        binding.bottomNav.visibility = View.GONE
    }

    fun toMainFragment() {
        Handler(Looper.getMainLooper()).postDelayed({
            supportFragmentManager.beginTransaction().replace(
                R.id.placeForFragment,
                MainFragment.newInstance()
            ).commit()
            binding.bottomNav.visibility = View.VISIBLE
        }, 2000)

    }

    fun searchFragment() {
        supportFragmentManager.beginTransaction().replace(
            R.id.placeForFragment,
            SearchFragment.newInstance()
        ).commit()
    }

    fun homeFragment() {
        supportFragmentManager.beginTransaction().replace(
            R.id.placeForFragment,
            MainFragment.newInstance()
        ).commit()
    }

}