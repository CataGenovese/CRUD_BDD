import javafx.application.Application
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.VBox
import javafx.stage.Stage
import java.sql.ResultSet

class GUIApp : Application() {

    override fun start(stage: Stage) {
        val operationSelector = ComboBox<String>().apply {
            items.addAll(
                "Insertar Oficina", "Actualizar Oficina", "Eliminar Oficina", "Listar Oficinas",
                "Insertar Cliente", "Actualizar Cliente", "Eliminar Cliente", "Listar Clientes"
            )
            value = "Insertar Oficina"
        }

        // Campos Oficina
        val oficinaField = TextField().apply { promptText = "Código Oficina" }
        val ciudadField = TextField().apply { promptText = "Ciudad" }
        val regionField = TextField().apply { promptText = "Región" }
        val dirField = TextField().apply { promptText = "Código Dirección" }
        val objetivoField = TextField().apply { promptText = "Objetivo" }
        val ventasField = TextField().apply { promptText = "Ventas" }

        // Campos Cliente
        val numClieField = TextField().apply { promptText = "Número Cliente" }
        val empresaField = TextField().apply { promptText = "Empresa" }
        val repClieField = TextField().apply { promptText = "Código Representante" }
        val limiteCreditoField = TextField().apply { promptText = "Límite de Crédito" }

        val output = Label()
        val executeButton = Button("Ejecutar operación")

        val container = VBox(10.0).apply { padding = Insets(20.0) }

        fun updateForm(selected: String) {
            container.children.setAll(operationSelector)
            when (selected) {
                "Insertar Oficina" -> container.children.addAll(oficinaField, ciudadField, regionField, dirField, objetivoField, ventasField, executeButton, output)
                "Actualizar Oficina" -> container.children.addAll(oficinaField, ventasField, executeButton, output)
                "Eliminar Oficina" -> container.children.addAll(oficinaField, executeButton, output)
                "Listar Oficinas" -> container.children.addAll(executeButton, output)
                "Insertar Cliente" -> container.children.addAll(numClieField, empresaField, repClieField, limiteCreditoField, executeButton, output)
                "Actualizar Cliente" -> container.children.addAll(numClieField, limiteCreditoField, executeButton, output)
                "Eliminar Cliente" -> container.children.addAll(numClieField, executeButton, output)
                "Listar Clientes" -> container.children.addAll(executeButton, output)
            }
        }

        executeButton.setOnAction {
            val conexion = Database.getConnection()
            try {
                when (operationSelector.value) {
                    "Insertar Oficina" -> {
                        val oficina = oficinaField.text.toInt()
                        val ciudad = ciudadField.text
                        val region = regionField.text
                        val dir = dirField.text.toInt()
                        val objetivo = objetivoField.text.toDouble()
                        val ventas = ventasField.text.toDouble()
                        insertarOficina(oficina, ciudad, region, dir, objetivo, ventas)
                        output.text = "Oficina insertada correctamente."
                    }
                    "Actualizar Oficina" -> {
                        val oficina = oficinaField.text.toInt()
                        val nuevasVentas = ventasField.text.toDouble()
                        updateOficina(oficina, nuevasVentas)
                        output.text = "Ventas de la oficina actualizadas correctamente."
                    }
                    "Eliminar Oficina" -> {
                        val oficina = oficinaField.text.toInt()
                        deleteOficina(oficina)
                        output.text = "Oficina eliminada correctamente."
                    }
                    "Listar Oficinas" -> {
                        output.text = listarOficinas()
                    }
                    "Insertar Cliente" -> {
                        val numClie = numClieField.text.toInt()
                        val empresa = empresaField.text
                        val repClie = repClieField.text.toInt()
                        val limiteCredito = limiteCreditoField.text.toDouble()
                        insertarCliente(numClie, empresa, repClie, limiteCredito)
                        output.text = "Cliente insertado correctamente."
                    }
                    "Actualizar Cliente" -> {
                        val numClie = numClieField.text.toInt()
                        val nuevoLimiteCredito = limiteCreditoField.text.toDouble()
                        updateClientes(numClie, nuevoLimiteCredito)
                        output.text = "Límite de crédito actualizado correctamente."
                    }
                    "Eliminar Cliente" -> {
                        val numClie = numClieField.text.toInt()
                        deleteClientes(numClie)
                        output.text = "Cliente eliminado correctamente."
                    }
                    "Listar Clientes" -> {
                        output.text = listarClientes()
                    }
                }
            } catch (e: Exception) {
                output.text = "Error: ${e.message}"
            }
        }

        operationSelector.setOnAction {
            updateForm(operationSelector.value)
        }

        updateForm(operationSelector.value)

        stage.scene = Scene(container, 600.0, 700.0)
        stage.title = "Gestión CRUD: Oficinas y Clientes"
        stage.show()
    }
}
