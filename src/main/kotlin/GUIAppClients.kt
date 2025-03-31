import javafx.application.Application
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.VBox
import javafx.stage.Stage
import kotlin.math.sqrt


private fun GUIAppClients.listarClientes() {
    TODO("Not yet implemented")
}

class GUIAppClients: Application() {

    private var currentOffset = 0 // Para paginar la lista de clientes

    override fun start(stage: Stage) {
        val operationSelector = ComboBox<String>()
        operationSelector.items.addAll(listOf("Insertar", "Actualizar", "Eliminar", "Listar"))
        operationSelector.value = "Insertar"

        val numClie = TextField().apply { promptText = "Num Cliente" }
        val empresa = TextField().apply { promptText = "Empresa" }
        val repClie = TextField().apply { promptText = "Representante de cliente" }
        val limitClie = TextField().apply { promptText = "Limite credito" }

        val output = Label()
        val executeButton = Button("Ejecutar")

        val container = VBox(10.0)
        container.padding = Insets(20.0)

        // Función para actualizar los campos visibles según la operación
        fun updateClientes(selected: String) {
            container.children.setAll(operationSelector)
            when (selected) {
                "Insertar" -> container.children.addAll(numClie, empresa, repClie, limitClie)
                "Actualizar" -> container.children.addAll(empresa, limitClie, executeButton, output)
                "Eliminar" -> container.children.addAll(numClie, executeButton, output)
                "Listar" -> container.children.addAll(executeButton, output)
            }
        }

        executeButton.setOnAction {
            when (operationSelector.value) {
                "Insertar" -> {
                    if (numClie.text.isNotBlank() && empresa.text.isNotBlank() && repClie.text.isNotBlank() && limitClie.text.isNotBlank()) {
                        readClientes(
                            numClie.text.toInt(),
                            empresa.text,
                            repClie.text.toInt(),
                            limitClie.text.toDouble()
                        )
                        output.text = "✅ Cliente insertado correctamente."
                        numClie.clear(); empresa.clear(); repClie.clear(); limitClie.clear()
                    } else {
                        output.text = "❌ Todos los campos son obligatorios."
                    }
                }

                "Actualizar" -> {
                    val id = numClie.text.toIntOrNull()
                    if (id != null && limitClie.text.isNotBlank()) {
                        updateClientes(id, limitClie.text.toDouble())
                        output.text = "✅ Email actualizado."
                        numClie.clear(); limitClie.clear()
                    } else {
                        output.text = "❌ ID inválido o email vacío."
                    }
                }

                "Eliminar" -> {
                    val id = numClie.text.toIntOrNull()
                    if (id != null) {
                        deleteClientes(id)
                        output.text = "Cliente eliminado"
                        numClie.clear()
                    } else {
                        output.text = "ID invalido"
                    }
                }

                "Listar" -> {
                    output.text = listarClientes()
                }
            }
        }
    }
    private fun listarClientes() {

    }
}
