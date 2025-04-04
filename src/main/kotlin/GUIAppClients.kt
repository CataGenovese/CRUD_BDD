import javafx.application.Application
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.VBox
import javafx.stage.Stage

// Clase principal de la aplicación con interfaz gráfica
class GUIAppClients: Application() {

    private var currentOffset = 0 // Para paginar la lista de clientes

    override fun start(stage: Stage) {
        // ComboBox para seleccionar qué operación se quiere hacer
        val operationSelector = ComboBox<String>()
        operationSelector.items.addAll(listOf("Insertar", "Actualizar", "Eliminar", "Listar"))
        operationSelector.value = "Insertar"

        // Campos de entrada para los datos del cliente
        val numClie = TextField().apply { promptText = "Num Cliente" }
        val empresa = TextField().apply { promptText = "Empresa" }
        val repClie = TextField().apply { promptText = "Representante de cliente" }
        val limitClie = TextField().apply { promptText = "Limite credito" }

        val output = Label() // Para mostrar mensajes de salida
        val executeButton = Button("Ejecutar") // Botón para ejecutar la acción

        val container = VBox(10.0)
        container.padding = Insets(20.0)

        // Función para actualizar los campos visibles según la operación
        fun updateClientes(selected: String) {
            container.children.setAll(operationSelector)
            when (selected) {
                "Insertar" -> container.children.addAll(numClie, empresa, repClie, limitClie, executeButton, output)
                "Actualizar" -> container.children.addAll(numClie, limitClie, executeButton, output)
                "Eliminar" -> container.children.addAll(numClie, executeButton, output)
                "Listar" -> container.children.addAll(executeButton, output)
            }
        }
        // Lógica cuando se presiona el botón "Ejecutar"
        executeButton.setOnAction {
            when (operationSelector.value) {
                "Insertar" -> {
                    if (numClie.text.isNotBlank() && empresa.text.isNotBlank() && repClie.text.isNotBlank() && limitClie.text.isNotBlank()) {
                        insertarCliente(numClie.text.toInt(), empresa.text, repClie.text.toInt(), limitClie.text.toDouble())
                        output.text = "Cliente insertado correctamente."
                        numClie.clear(); empresa.clear(); repClie.clear(); limitClie.clear()
                    } else {
                        output.text = "Todos los campos son obligatorios."
                    }
                }
                "Actualizar" -> {
                    val id = numClie.text.toIntOrNull()
                    // Verifica que todos los campos estén completos
                    if (id != null && limitClie.text.isNotBlank()) {
                        updateClientes(id, limitClie.text.toDouble())
                        output.text = "Cliente actualizado correctamente :)."
                        // Limpia los campos
                        numClie.clear(); limitClie.clear()
                    } else {
                        output.text = "ID inválido."
                    }
                }
                "Eliminar" -> {
                    val id= numClie.text.toIntOrNull()
                    if(id!= null) {
                        deleteClientes(id)
                        output.text = "Cliente eliminado"
                        numClie.clear()
                    } else {
                        output.text= "ID invalido"
                    }
                }
                "Listar" -> {
                    output.text = listarClientes()
                }
            }
        }
        // Cuando se cambia la opción del combo box, se actualiza la vista
        operationSelector.setOnAction {
            updateClientes(operationSelector.value)
        }

        // Mostrar formulario inicial
        updateClientes(operationSelector.value)

        // Mostrar ventana
        stage.scene = Scene(container, 420.0, 400.0)
        stage.title = "Gestión de Clientes (CRUD)"
        stage.show()
    }
    //mostrar clientes -> READ
    private fun readClientes(): String {
        val sql= "SELECT num_clie, empresa, rep_clie, limite_credito FROM clientes"
        val builder= StringBuilder(" Clientes: \n")
        try {
            Database.getConnection()?.use { conn ->
                conn.createStatement().use { stmt ->
                    val resultado= stmt.executeQuery(sql)
                    var found= false
                    while(resultado.next()){
                        found=true
                        builder.append("${resultado.getInt("num_clie")} - ${resultado.getString("empresa")} - ${resultado.getInt("rep_clie")} - ${resultado.getDouble("limite_credito")}")
                    }
                    if(!found){
                        builder.append("No hay mas clientes \n")
                        currentOffset=0
                    }
                }
            }
        } catch (e: Exception) {
            return "Error al consultar client ${e.message}"
        }
        return builder.toString()
    }

}

