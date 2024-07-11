package com.aliny.prova02.model

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore


object DAOCliente {

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

    fun addCliente(
        cpf: String,
        nome: String,
        email: String,
        instagram: String,
        context: Context
    ){
        try {
            validarCampo(cpf, "cpf")
            validarCampo(nome, "nome")
            validarCampo(email, "email")
            validarCampo(instagram, "instagram")

            val cliente = Cliente(cpf, nome, email, instagram)

            db.collection("clientes")
                .document(cpf)
                .get()
                .addOnSuccessListener { document ->
                    //verifica se já existe um cliente com o mesmo cpf
                    if (document.exists()) {
                        Toast.makeText(context, "Cliente com este CPF já está cadastrado.", Toast.LENGTH_SHORT).show()
                    } else {
                        db.collection("clientes")
                            .document(cliente.cpf)
                            .set(cliente)
                            .addOnSuccessListener {
                                Toast.makeText(context, "Novo cliente adicionado com sucesso!", Toast.LENGTH_SHORT).show()
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(context, "Erro ao adicionar usuário no Firestore: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(context, "Erro ao verificar CPF no Firestore: ${e.message}", Toast.LENGTH_SHORT).show()
                }

        }catch (e: CampoInvalidoException) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Log.e("TESTE", "Erro inesperado: ${e.message}", e)
        }
    }

    fun altCliente(
        cpf: String,
        nome: String,
        email: String,
        instagram: String,
        context: Context
    ){
        try {
            validarCampo(cpf, "cpf")
            validarCampo(nome, "nome")
            validarCampo(email, "email")
            validarCampo(instagram, "instagram")

            db.collection("clientes")
                .document(cpf)
                .update("cpf", cpf,"nome", nome, "email", email, "instagram", instagram)
                .addOnSuccessListener {
                    Toast.makeText(context, "Cliente atualizado com sucesso!", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {e->
                    Toast.makeText(context, "Erro ao atualizar dados de cliente: ${e.message}", Toast.LENGTH_SHORT).show()
                }

        }catch (e: CampoInvalidoException) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Log.e("TESTE", "Erro inesperado: ${e.message}", e)
        }
    }

    fun excluirCliente(valor: String, context: Context) {
        db.collection("clientes")
            .whereEqualTo("cpf", valor)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val cliente = task.result?.toObjects(Cliente::class.java)?.firstOrNull()
                    if (cliente != null) {
                        //encontrou pelo CPF
                        excluirClientePorCPF(cliente.cpf, context)
                    } else {
                        //busca pelo nome caso não encontre pelo CPF
                        buscarClientePorNome(valor, context) { clientePorNome, erro ->
                            if (clientePorNome != null) {
                                excluirClientePorCPF(clientePorNome.cpf, context)
                            } else {
                                Toast.makeText(context, erro ?: "Cliente não encontrado.", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                } else {
                    Toast.makeText(context, "Erro ao buscar cliente: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    //metodo que ira excluir pelo cpf caso faça a busca pelo nome
    private fun excluirClientePorCPF(cpf: String, context: Context) {
        db.collection("clientes")
            .document(cpf)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    //cliente existe pode excluir
                    db.collection("clientes")
                        .document(cpf)
                        .delete()
                        .addOnSuccessListener {
                            Toast.makeText(context, "Cliente excluído com sucesso!", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(context, "Erro ao remover cliente: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    //cliente não existe então exibe mensagem de erro
                    Toast.makeText(context, "Cliente não encontrado.", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Erro ao verificar cliente: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    //metodo que retorna todos os cliente sou mensagem de erro
    fun getAllClientes(context: Context, onComplete: (List<Cliente>?, String?) -> Unit) {
        db.collection("clientes")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val listaClientes = task.result?.toObjects(Cliente::class.java)
                    if (listaClientes.isNullOrEmpty()) {
                        onComplete(null, "Não há nenhum cliente cadastrado.")
                    } else {
                        onComplete(listaClientes, null)
                    }
                } else {
                    Toast.makeText(context, "Erro ao obter clientes: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    onComplete(null, "Erro ao obter clientes: ${task.exception?.message}")
                }
            }
    }


    fun buscarCliente(valor: String, context: Context, onComplete: (Cliente?, String?) -> Unit) {
        db.collection("clientes")
            .whereEqualTo("cpf", valor)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val cliente = task.result?.toObjects(Cliente::class.java)?.firstOrNull()
                    if (cliente != null) {
                        onComplete(cliente, null)
                    } else {
                        //caso não encontre pelo cpf faz a busca pelo nome
                        buscarClientePorNome(valor, context, onComplete)
                    }
                } else {
                    Toast.makeText(context, "Erro ao buscar cliente por CPF: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    onComplete(null, "Erro ao buscar cliente por CPF: ${task.exception?.message}")
                }
            }
    }

    private fun buscarClientePorNome(nome: String, context: Context, onComplete: (Cliente?, String?) -> Unit) {
        db.collection("clientes")
            .whereEqualTo("nome", nome)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val cliente = task.result?.toObjects(Cliente::class.java)?.firstOrNull()
                    if (cliente != null) {
                        onComplete(cliente, null)
                    } else {
                        Toast.makeText(context, "Cliente não encontrado.", Toast.LENGTH_SHORT).show()
                        onComplete(null, "Cliente não encontrado.")
                    }
                } else {
                    Toast.makeText(context, "Erro ao buscar cliente por nome: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    onComplete(null, "Erro ao buscar cliente por nome: ${task.exception?.message}")
                }
            }
    }

}