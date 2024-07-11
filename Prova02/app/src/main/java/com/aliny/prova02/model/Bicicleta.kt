package com.aliny.prova02.model

data class Bicicleta(
    var cpfCliente: String = "",
    var codigo: String = "",
    var modelo: String = "",
    var materialDoChassi: String = "",
    var aro: String = "",
    var preco: Double = 0.0,
    var qtdDeMarchas: String = ""
) {
    constructor() : this("","", "", "", "", 0.0, "")

    override fun toString(): String {
        return "Codigo da bicicleta: $codigo\n" +
                "CPF do Cliente: $cpfCliente\n" +
                "Modelo: $modelo\n" +
                "Material Do Chassi: $materialDoChassi\n" +
                "Aro: $aro\n" +
                "Preço: $preco\n" +
                "Quantidade De Marchas: $qtdDeMarchas\n"
    }
}
