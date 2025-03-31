import javafx.application.Application
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.VBox
import javafx.stage.Stage
import kotlin.math.sqrt


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
                "Actualizar" -> container.children.addAll(empresa, limitClie)
                "Eliminar" -> container.children.addAll(numClie, empresa, repClie, limitClie)
                "Listar" -> container.children.addAll(numClie, empresa, limitClie)
            }
        }

        executeButton.setOnAction {
            when(operationSelector.value) {
                "Insertar" -> {
                    if()
                }
            }
        }
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
                            builder.append("${resultado.getDouble(num_clie)} - ${resultado.getString(repClie)} - ${resultado.getString(limitClie)}\n")
                        }
                    }
                }
            }
        }

    }

