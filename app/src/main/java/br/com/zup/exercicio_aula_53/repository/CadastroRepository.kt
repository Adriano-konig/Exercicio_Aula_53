package br.com.zup.exercicio_aula_53.repository

import br.com.zup.exercicio_aula_53.model.CadastroModel

class CadastroRepository {

    fun authenticate(cadastro : CadastroModel) : CadastroModel{

        if (cadastro.produto != null && cadastro.quantidade != null){
            cadastro.accessAuth = true
        }else{
            cadastro.accessAuth = false
        }
        return cadastro
    }
}