package com.aliny.prova02.model

data class Cliente(
    var cpf: String = "",
    var nome: String = "",
    var email: String = "",
    var instagram: String = ""
) {
    constructor() : this("", "", "", "")

    override fun toString(): String {
        return "Nome cliente: $nome\n" +
                "CPF: $cpf\n" +
                "E-mail: $email\n" +
                "Instagram: $instagram\n"
    }
}
