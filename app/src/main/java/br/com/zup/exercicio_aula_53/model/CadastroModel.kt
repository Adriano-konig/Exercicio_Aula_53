package br.com.zup.exercicio_aula_53.model

data class CadastroModel(
    var produto : String,
    var quantidade : String,
    var accessAuth : Boolean = false
)