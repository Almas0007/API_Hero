package blim.enbek.talpynys.api_hero.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import blim.enbek.talpynys.api_hero.R
import blim.enbek.talpynys.api_hero.databinding.FragmentMainBinding
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import org.json.JSONObject

class MainFragment : Fragment() {
    companion object {

        @JvmStatic
        fun newInstance() =
            MainFragment()
    }
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(layoutInflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mainFragmentInformation.visibility=View.INVISIBLE
        binding.mainId.visibility = View.INVISIBLE
        binding.heroId.visibility = View.INVISIBLE
        binding.randomButton.setOnClickListener {
            getRequestAPI((1..734).random())
            binding.mainFragmentInformation.visibility = View.VISIBLE
            binding.mainId.visibility = View.VISIBLE
            binding.heroId.visibility = View.VISIBLE
        }

    }

    fun getRequestAPI(randomNumber:Int){
        val url ="https://cdn.jsdelivr.net/gh/akabab/superhero-api@0.3.0/api/id/$randomNumber.json"
        val queue = Volley.newRequestQueue(context)
        val stringRequest = StringRequest(
            Request.Method.GET, url, {
                result->  workWithJSON(result)
            //Log.d("TestLog","error API: $result")


        },
            { error -> Log.d("TestLog","error API: $error")
            }
        )

        queue.add(stringRequest)
    }

    fun workWithJSON(result:String){
        val jsonObject = JSONObject(result)
        val urlImage = jsonObject.getJSONObject("images").getString("lg").toString()
        binding.heroId.text = jsonObject.getString("id").toString()
        Picasso.get().load(urlImage).into(binding.mainImage)

        binding.heroNameS.text = jsonObject.getString("name")
        binding.heroIntelligenceS.text =
            jsonObject.getJSONObject("powerstats")
                .getString("intelligence")
        binding.heroStrengthS.text =
            jsonObject.getJSONObject("powerstats")
                .getString("strength")

        binding.heroSpeedS.text =
            jsonObject.getJSONObject("powerstats")
                .getString("speed")

        binding.heroGenderS.text =
            jsonObject.getJSONObject("appearance")
                .getString("gender")
        binding.heroRaceS.text =
            jsonObject.getJSONObject("appearance")
                .getString("race")
        binding.heroHeightS.text =
            jsonObject.getJSONObject("appearance")
                .getJSONArray("height")[1].toString()

        binding.heroPublisherS.text= jsonObject.getJSONObject("biography").getString("publisher")
    }


}