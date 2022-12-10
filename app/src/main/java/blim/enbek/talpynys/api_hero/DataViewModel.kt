package blim.enbek.talpynys.api_hero

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import blim.enbek.talpynys.api_hero.DataAPI

class DataViewModel: ViewModel(){
    val dataFromJSON = MutableLiveData<DataAPI>()
}