// Importa las bibliotecas necesarias para la aplicación gráfica de JavaFX
import javafx.application.Application
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.VBox
import javafx.stage.Stage
import java.sql.ResultSet

// La clase GUIApp hereda de Application, lo que permite crear una aplicación JavaFX
class GUIApp : Application() {

    override fun start(stage: Stage) {

        // Crea un ComboBox que permite seleccionar las operaciones a realizar (CRUD sobre oficinas y clientes)
        val operationSelector = ComboBox<String>().apply {
            items.addAll(
                "Insertar Oficina", "Actualizar Oficina", "Eliminar Oficina", "Listar Oficinas",
                "Insertar Cliente", "Actualizar Cliente", "Eliminar Cliente", "Listar Clientes"
            )
            value = "Insertar Oficina" // Valor por defecto
        }

        // Campos de entrada para la gestión de oficinas
        val oficinaField = TextField().apply { promptText = "Código Oficina" }
        val ciudadField = TextField().apply { promptText = "Ciudad" }
        val regionField = TextField().apply { promptText = "Región" }
        val dirField = TextField().apply { promptText = "Código Dirección" }
        val objetivoField = TextField().apply { promptText = "Objetivo" }
        val ventasField = TextField().apply { promptText = "Ventas" }

        // Campos de entrada para la gestión de clientes
        val numClieField = TextField().apply { promptText = "Número Cliente" }
        val empresaField = TextField().apply { promptText = "Empresa" }
        val repClieField = TextField().apply { promptText = "Código Representante" }
        val limiteCreditoField = TextField().apply { promptText = "Límite de Crédito" }

        // Etiqueta para mostrar mensajes de salida y botón de ejecución
        val output = Label()
        val executeButton = Button("Ejecutar operación")

        // Contenedor principal para los elementos de la interfaz, con un espacio de 10 unidades entre cada elemento
        val container = VBox(10.0).apply { padding = Insets(20.0) }

        // Esta función actualiza el formulario según la operación seleccionada
        fun updateForm(selected: String) {
            container.children.setAll(operationSelector) // Siempre mostrar el selector de operaciones
            when (selected) {
                // Define los campos y botones que se mostrarán dependiendo de la operación seleccionada
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

        // Acción que se ejecuta cuando se hace clic en el botón "Ejecutar operación"
        executeButton.setOnAction {
            val conexion = Database.getConnection() // Establece la conexión a la base de datos
            try {
                // Según la operación seleccionada, realiza la acción correspondiente sobre la base de datos
                when (operationSelector.value) {
                    // Para la operación "Insertar Oficina", obtiene los datos de los campos y llama a la función correspondiente
                    "Insertar Oficina" -> {
                        val oficina = oficinaField.text.toInt()
                        val ciudad = ciudadField.text
                        val region = regionField.text
                        val dir = dirField.text.toInt()
                        val objetivo = objetivoField.text.toDouble()
                        val ventas = ventasField.text.toDouble()
                        insertarOficina(oficina, ciudad, region, dir, objetivo, ventas)
                        output.text = "Oficina insertada correctamente." // Muestra mensaje de éxito
                    }
                    // Similar para otras operaciones (Actualizar, Eliminar, Listar) sobre oficinas y clientes
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
                // Si ocurre algún error, muestra el mensaje de error en el output
                output.text = "Error: ${e.message}"
            }
        }

        // Actualiza el formulario cada vez que se cambia la operación seleccionada
        operationSelector.setOnAction {
            updateForm(operationSelector.value)
        }

        // Inicializa el formulario con la operación seleccionada por defecto
        updateForm(operationSelector.value)

        // Crea la escena con el contenedor y la establece en el escenario
        stage.scene = Scene(container, 600.0, 700.0)
        stage.title = "Gestión CRUD: Oficinas y Clientes" // Título de la ventana
        stage.show() // Muestra la ventana
    }
}
