package com.aliny.prova02.model

import android.content.Context
import android.os.Environment
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import java.io.File
import java.io.FileWriter
import java.io.IOException

object FileUtils {
    //variavel de tag no Logcat
    private const val TAG = "FileUtils"

    fun saveDataToFile(context: Context, fileName: String, data: String): Boolean {
        return try {
            val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
            //verifica se o diretório existe ou não para ser criado
            if (!storageDir?.exists()!! == true) {
                storageDir?.mkdirs()//cria o diretorio
            }
            val file = File(storageDir, fileName) //cria o objeto do arquivo com o nome e diretório criados
            //escreve os dados no arquivo e em seguida fecha
            val writer = FileWriter(file)
            writer.append(data) //inseri os dados
            writer.flush()
            writer.close()
            Toast.makeText(context, "Dados salvos em $fileName", Toast.LENGTH_SHORT).show()
            true
        } catch (e: IOException) {
            Log.e(TAG, "Erro ao salvar dados no arquivo: ${e.message}", e)
            Toast.makeText(context, "Erro ao salvar dados no arquivo", Toast.LENGTH_SHORT).show()
            false
        }
    }

    fun saveAllData(context: Context) {
        val db = FirebaseFirestore.getInstance()

        db.collection("clientes")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val clientes = task.result?.toObjects(Cliente::class.java) ?: emptyList()
                    //varivel para guardar a string com os dados
                    val clientesData = clientes.joinToString("\n") { cliente ->
                        "CPF: ${cliente.cpf}\nNome: ${cliente.nome}\nEmail: ${cliente.email}\nInstagram: ${cliente.instagram}\n"
                    }

                    db.collection("bicicletas")
                        .get()
                        .addOnCompleteListener { bikeTask ->
                            if (bikeTask.isSuccessful) {
                                val bicicletas = bikeTask.result?.toObjects(Bicicleta::class.java) ?: emptyList()
                                //varivel para guardar a string com os dados
                                val bicicletasData = bicicletas.joinToString("\n") { bike ->
                                    "CPF Cliente: ${bike.cpfCliente}\nCodigo: ${bike.codigo}\nModelo: ${bike.modelo}\nMaterial do Chassi: ${bike.materialDoChassi}\nAro: ${bike.aro}\nPreco: ${bike.preco}\nQuantidade de Marchas: ${bike.qtdDeMarchas}\n"
                                }
                                //string com todos os dados
                                val allData = "Clientes:\n$clientesData\n\nBicicletas:\n$bicicletasData"
                                //varivel que chama o metódo para salvar o arquivo
                                val allDataFileSaved = saveDataToFile(context, "dados.txt", allData)
                                //verifica se salvou
                                if (allDataFileSaved) {
                                    Toast.makeText(context, "Todos os dados foram salvos com sucesso", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                Log.e(TAG, "Erro ao obter bicicletas: ${bikeTask.exception?.message}", bikeTask.exception)
                                Toast.makeText(context, "Erro ao obter bicicletas", Toast.LENGTH_SHORT).show()
                            }
                        }
                } else {
                    Log.e(TAG, "Erro ao obter clientes: ${task.exception?.message}", task.exception)
                    Toast.makeText(context, "Erro ao obter clientes", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
