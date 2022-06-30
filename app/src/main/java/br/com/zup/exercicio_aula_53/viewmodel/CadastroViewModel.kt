package br.com.zup.exercicio_aula_53.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.zup.exercicio_aula_53.*
import br.com.zup.exercicio_aula_53.model.CadastroModel
import br.com.zup.exercicio_aula_53.repository.CadastroRepository

class CadastroViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = CadastroRepository()
    private var _response: MutableLiveData<CadastroModel> = MutableLiveData()
    val response: LiveData<CadastroModel> = _response

    private val _savedData : MutableLiveData<CadastroModel> = MutableLiveData()
    val savedData : LiveData<CadastroModel> = _savedData

    private  val _saveDataFlag : MutableLiveData<Boolean> = MutableLiveData()
    val saveDataFlag : LiveData<Boolean> = _saveDataFlag

    val pref = application.getSharedPreferences(PREFERENCE_KEY, Context.MODE_PRIVATE)
    val prefEditor = pref.edit()

    fun getSavedData(){
        try {
            val produto = pref.getString(USER_NAME_PRODUTO_KEY, "").toString()
            val quantidade = pref.getString(USER_QUANTIDADE_KEY, "").toString()
            val savedUser = CadastroModel(produto, quantidade)
            _savedData.value = savedUser
            _saveDataFlag.value = pref.getBoolean(SAVE_USER_PASS_FLAG_KEY, false)
        }catch (e:Exception){
            Log.i("Error", "------> ${e.message}")
        }
    }
    fun authentication (cadastro : CadastroModel, flagSaveData:Boolean){
        try {
            prefEditor.putBoolean(SAVE_USER_PASS_FLAG_KEY, flagSaveData)
            if (flagSaveData){
                prefEditor.putString(SAVE_USER_PASS_FLAG_KEY, cadastro.produto)
                prefEditor.putString(USER_QUANTIDADE_KEY, cadastro.quantidade)
                prefEditor.apply()
            }else{
                prefEditor.remove(USER_NAME_PRODUTO_KEY)
                prefEditor.remove(USER_QUANTIDADE_KEY)
                prefEditor.apply()
            }
            _response.value = repository.authenticate(cadastro)
        }catch (ex: Exception){
            Log.i("Error", "------> ${ex.message}")
        }

    }
}