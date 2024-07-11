package com.aliny.prova02.model

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

object DAOBicicleta {

    val db = Firebase.firestore

    class CampoInvalidoException(message: String) : IllegalArgumentException(message)

    fun validarCampo(valor: Any, nomeCampo: String) {
        if (valor is String && valor.isBlank()) {
            throw CampoInvalidoException("O campo $nomeCampo não pode estar vazio.")
        } else if (valor is Int && valor <= 0) {
            throw CampoInvalidoException("O campo $nomeCampo deve ser um valor positivo.")
        } else if (valor is Float && valor <= 0) {
            throw CampoInvalidoException("O campo $nomeCampo deve ser um valor positivo.")
        }
    }

    fun addBicicleta(
        cpfCliente: String,
        codigo: String,
        modelo: String,
        materialDoChassi: String,
        aro: String,
        preco: String,
        qtdDeMarchas: String,
        context: Context
    ){
        try {
            validarCampo(cpfCliente, "cpf cliente")
            validarCampo(codigo, "código")
            validarCampo(modelo, "modelo")
            validarCampo(materialDoChassi, "material do chassi")
            validarCampo(aro, "aro")
            validarCampo(preco, "preço")
            validarCampo(qtdDeMarchas, "quantidade de marchas")

            val precoDouble = preco.toDouble()

            db.collection("clientes")
                .document(cpfCliente)
                .get()
                .addOnSuccessListener { cliente ->
                    if (cliente.exists()){

                        val bicicleta = Bicicleta(cpfCliente, codigo, modelo, materialDoChassi, aro, precoDouble, qtdDeMarchas)

                        db.collection("bicicletas")
                            .document(codigo)
                            .get()
                            .addOnSuccessListener { document ->
                                if (document.exists()) {
                                    Toast.makeText(context, "Um pedido com este código já foi feito", Toast.LENGTH_SHORT).show()
                                } else {
                                    db.collection("bicicletas")
                                        .document(codigo)
                                        .set(bicicleta)
                                        .addOnSuccessListener {
                                            Toast.makeText(context, "Novo pedido de bicicleta feito com sucesso!", Toast.LENGTH_SHORT).show()
                                        }
                                        .addOnFailureListener { e ->
                                            Toast.makeText(context, "Erro ao adicionar pedido no Firestore: ${e.message}", Toast.LENGTH_SHORT).show()
                                        }
                                }
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(context, "Erro ao verificar código no Firestore: ${e.message}", Toast.LENGTH_SHORT).show()
                            }

                    } else {
                        Toast.makeText(context, "Não existe um cliente com este CPF cadastrado.", Toast.LENGTH_SHORT).show()
                    }
                }

        }catch (e: CampoInvalidoException) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Log.e("TESTE", "Erro inesperado: ${e.message}", e)
        }
    }

    fun altBicicleta(
        cpfCliente: String,
        codigo: String,
        modelo: String,
        materialDoChassi: String,
        aro: String,
        preco: String,
        qtdDeMarchas: String,
        context: Context
    ){
        try {
            validarCampo(cpfCliente, "cpf cliente")
            validarCampo(codigo, "código")
            validarCampo(modelo, "modelo")
            validarCampo(materialDoChassi, "material do chassi")
            validarCampo(aro, "aro")
            validarCampo(preco, "preço")
            validarCampo(qtdDeMarchas, "quantidade de marchas")

            val precoDouble = preco.toDouble()
            db.collection("clientes")
                .document(cpfCliente)
                .get()
                .addOnSuccessListener { cliente ->
                    if (cliente.exists()){

                        db.collection("bicicletas")
                            .document(codigo)
                            .update("cpfCliente", cpfCliente,"codigo", codigo, "modelo", modelo, "materialDoChassi", materialDoChassi, "aro", aro, "preco", precoDouble, "qtdDeMarchas", qtdDeMarchas)
                            .addOnSuccessListener {
                                Toast.makeText(context, "Pedido de bicicleta atualizado com sucesso!", Toast.LENGTH_SHORT).show()
                            }
                            .addOnFailureListener {e->
                                Toast.makeText(context, "Erro ao atualizar Pedido de bicicleta: ${e.message}", Toast.LENGTH_SHORT).show()
                            }

                    } else {
                        Toast.makeText(context, "Não existe um cliente com este CPF cadastrado.", Toast.LENGTH_SHORT).show()
                    }
                }

        }catch (e:CampoInvalidoException) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Log.e("TESTE", "Erro inesperado: ${e.message}", e)
        }
    }

    fun excluirBicicleta(valor: String, context: Context) {
        db.collection("bicicletas")
            .whereEqualTo("codigo", valor)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val bicicleta = task.result?.toObjects(Bicicleta::class.java)?.firstOrNull()
                    if (bicicleta != null) {
                        //encontrou pelo código
                        excluirBicicletaPorCodigo(bicicleta.codigo, context)
                    } else {
                        //busca pelo modelo caso não encontre pelo código
                        buscarBicicletaPorModelo(valor, context) { bicicletaPorModelo, erro ->
                            if (bicicletaPorModelo != null) {
                                excluirBicicletaPorCodigo(bicicletaPorModelo.codigo, context)
                            } else {
                                Toast.makeText(context, erro ?: "Bicicleta não encontrada.", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                } else {
                    Toast.makeText(context, "Erro ao buscar bicicleta: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun excluirBicicletaPorCodigo(codigo: String, context: Context) {
        db.collection("bicicletas")
            .document(codigo)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    //bicicleta existe pode excluir
                    db.collection("bicicletas")
                        .document(codigo)
                        .delete()
                        .addOnSuccessListener {
                            Toast.makeText(context, "Bicicleta excluída com sucesso!", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(context, "Erro ao remover bicicleta: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    //bicicleta não existe então exibe mensagem de erro
                    Toast.makeText(context, "Bicicleta não encontrada", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Erro ao verificar bicicleta: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }


    fun getAllBikes(context: Context, onComplete: (List<Bicicleta>?, String?) -> Unit) {
        db.collection("bicicletas")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val listaBikes = task.result?.toObjects(Bicicleta::class.java)
                    if (listaBikes.isNullOrEmpty()) {
                        onComplete(null, "Não há nenhuma bicicleta cadastrada")
                    } else {
                        onComplete(listaBikes, null)
                    }
                } else {
                    Toast.makeText(context, "Erro ao obter bicicletas: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    onComplete(null, "Erro ao obter bicicletas: ${task.exception?.message}")
                }
            }
    }

    fun buscarBicicleta(valor: String, context: Context, onComplete: (Bicicleta?, String?) -> Unit) {
        db.collection("bicicletas")
            .whereEqualTo("codigo", valor)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val bicicleta = task.result?.toObjects(Bicicleta::class.java)?.firstOrNull()
                    if (bicicleta != null) {
                        onComplete(bicicleta, null)
                    } else {
                        buscarBicicletaPorModelo(valor, context, onComplete)
                    }
                } else {
                    Toast.makeText(context, "Erro ao buscar bicicleta por código: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    onComplete(null, "Erro ao buscar bicicleta por código: ${task.exception?.message}")
                }
            }
    }

    private fun buscarBicicletaPorModelo(modelo: String, context: Context, onComplete: (Bicicleta?, String?) -> Unit) {
        db.collection("bicicletas")
            .whereEqualTo("modelo", modelo)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val bicicleta = task.result?.toObjects(Bicicleta::class.java)?.firstOrNull()
                    if (bicicleta != null) {
                        onComplete(bicicleta, null)
                    } else {
                        Toast.makeText(context, "Bicicleta não encontrada.", Toast.LENGTH_SHORT).show()
                        onComplete(null, "Bicicleta não encontrada.")
                    }
                } else {
                    Toast.makeText(context, "Erro ao buscar bicicleta por modelo: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    onComplete(null, "Erro ao buscar bicicleta por modelo: ${task.exception?.message}")
                }
            }
    }


}