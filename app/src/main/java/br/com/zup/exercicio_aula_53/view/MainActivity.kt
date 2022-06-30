package br.com.zup.exercicio_aula_53.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.zup.exercicio_aula_53.R
import br.com.zup.exercicio_aula_53.databinding.ActivityMainBinding
import br.com.zup.exercicio_aula_53.model.CadastroModel
import br.com.zup.exercicio_aula_53.viewmodel.CadastroViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: CadastroViewModel by lazy {
        ViewModelProvider(this)[CadastroViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observers()
        viewModel.getSavedData()

        binding.button.setOnClickListener{
            authenticate()
        }

    }

    private fun authenticate(){
        val produto = binding.editProduto.text.toString()
        val quantidade = binding.editQuantidade.text.toString()
        var cadastro = CadastroModel(produto, quantidade)
        viewModel.authentication(cadastro, binding.switch1.isChecked)
    }

    private fun observers() {
        viewModel.response.observe(this) {
                    if (it.accessAuth) {
                        Toast.makeText(this, "Cadastro sucesso", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this, "Erro", Toast.LENGTH_LONG).show()
                    }
                }
                viewModel.savedData.observe(this) {
                    binding.editProduto.setText(it.produto)
                    binding.editQuantidade.setText(it.quantidade)
                }
                viewModel.saveDataFlag.observe(this) {
                    binding.switch1.isChecked = it
                }
        }
}