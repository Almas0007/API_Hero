package blim.enbek.talpynys.api_hero.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import blim.enbek.talpynys.api_hero.DataAPI
import blim.enbek.talpynys.api_hero.DataViewModel
import blim.enbek.talpynys.api_hero.R
import blim.enbek.talpynys.api_hero.databinding.FragmentSearchBinding
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import org.json.JSONObject


class SearchFragment : Fragment() {
    companion object {
        @JvmStatic
        fun newInstance() =
            SearchFragment()
    }

    private lateinit var binding: FragmentSearchBinding
    private val model:DataViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchFragmentInformation.visibility = View.GONE
        binding.buttonSearch.setOnClickListener {

            if(binding.getIDHero.text.isEmpty()){
                Toast.makeText(context,"Search area is EMPTY, please enter any number...",Toast.LENGTH_SHORT).show()
            }
            else{
                binding.searchFragmentInformation.visibility = View.VISIBLE
                getRequestAPIWithSearch(getEditText())
                updateInformation()
            }
        }


    }
    private fun getEditText():String{
        val id = binding.getIDHero.text
        return id.toString()
    }

    private fun getRequestAPIWithSearch(id: String) {
        val url = "https://cdn.jsdelivr.net/gh/akabab/superhero-api@0.3.0/api/id/$id.json"
        val queue = Volley.newRequestQueue(context)
        val stringRequest = StringRequest(
            Request.Method.GET, url, {
                    result ->
                dataFromJSONatAPI(JSONObject(result))
            },
            {
                    error -> Log.d("TestLog", "error API: $error")
            }
        )

        queue.add(stringRequest)
    }

//    fun parseInformationJSON(result: String) {
//        val jsonObject = JSONObject(result)
//
//        val urlImage = jsonObject.getJSONObject("images").getString("lg").toString()
//        Picasso.get().load(urlImage).into(binding.heroImageS)
//
//        binding.heroNameS.text = jsonObject.getString("name")
//        binding.heroIntelligenceS.text =
//            jsonObject.getJSONObject("powerstats")
//                .getString("intelligence")
//        binding.heroStrengthS.text =
//            jsonObject.getJSONObject("powerstats")
//                .getString("strength")
//
//        binding.heroSpeedS.text =
//            jsonObject.getJSONObject("powerstats")
//                .getString("speed")
//
//        binding.heroPowerS.text =
//            jsonObject.getJSONObject("powerstats")
//                .getString("power")
//
//        binding.heroGenderS.text =
//            jsonObject.getJSONObject("appearance")
//                .getString("gender")
//        binding.heroRaceS.text =
//            jsonObject.getJSONObject("appearance")
//                .getString("race")
//        binding.heroHeightS.text =
//            jsonObject.getJSONObject("appearance")
//                .getJSONArray("height")[1].toString()
//        binding.heroWeightS.text =
//            jsonObject.getJSONObject("appearance")
//                .getJSONArray("weight")[1].toString()
//
//        binding.heroPublisherS.text= jsonObject.getJSONObject("biography").getString("publisher")
//
//    }
    fun dataFromJSONatAPI(jsonObject: JSONObject){
        val data = DataAPI(
            jsonObject.getString("id"),
            jsonObject.getJSONObject("images").getString("lg").toString(),
            jsonObject.getString("name"),
            jsonObject.getJSONObject("powerstats").getString("intelligence"),
            jsonObject.getJSONObject("powerstats").getString("strength"),
            jsonObject.getJSONObject("powerstats").getString("speed"),
            jsonObject.getJSONObject("powerstats").getString("power"),
            jsonObject.getJSONObject("appearance").getString("gender"),
            jsonObject.getJSONObject("appearance").getString("race"),
            jsonObject.getJSONObject("appearance").getJSONArray("height")[1].toString(),
            jsonObject.getJSONObject("appearance").getJSONArray("weight")[1].toString(),
            jsonObject.getJSONObject("biography").getString("publisher")
        )

        model.dataFromJSON.value = data
    }
    fun updateInformation() = with(binding){
        model.dataFromJSON.observe(viewLifecycleOwner){
            heroNameS.text = it.name
            Picasso.get().load(it.url).into(heroImageS)
            heroIntelligenceS.text =it.intelligence
            heroStrengthS.text=it.strength
            heroSpeedS.text= it.speed
            heroPowerS.text = it.power
            heroGenderS.text = it.gender
            heroRaceS.text =it.race
            heroHeightS.text = it.height
            heroWeightS.text = it.weight
            heroPublisherS.text = it.publisher
        }

    }
}