import java.sql.SQLException
// Función para listar todos los clientes de la base de datos
fun listarClientes(): String {
    // Consulta SQL para obtener todos los clientes
    val sql = "SELECT * FROM clientes"
    // StringBuilder para convertir una lista de clientes
    val builder = StringBuilder("Lista de Clientes:\n")

    try {
        // Intentamos obtener la conexión con la base de datos
        Database.getConnection()?.use { conn ->
            // Creamos un statement para ejecutar la consulta
            conn.createStatement().use { stmt ->
                // Ejecutamos la consulta
                val result = stmt.executeQuery(sql)
                // Si no hay ningún cliente en la base de datos
                if (!result.isBeforeFirst) {
                    builder.append("No hay clientes registrados.\n")
                    return builder.toString()
                }
                // Recorremos todos los clientes encontrados
                while (result.next()) {
                    builder.append(
                        """
                        ---------------------------------
                         Numero de cliente: ${result.getInt("num_clie")}
                         Empresa: ${result.getString("empresa")}
                         Representante cliente: ${result.getInt("rep_clie")}
                         Límite de crédito: ${result.getDouble("limite_credito")}
                        ---------------------------------
                        """.trimIndent()
                    )
                }
            }
        }
    } catch (e: SQLException) {
        // Si hay un error al hacer la consulta que se muestra el mensaje
        builder.append("Error al listar clientes: ${e.message}\n")
    }
    // Retornamos la lista armada
    return builder.toString()
}


// Función para insertar un nuevo cliente
fun insertarCliente(num_clie: Int, empresa: String, rep_clie: Int, limite_credito: Double) {
    // Consulta SQL para verificar si el cliente ya existe
    val checkSQL = "SELECT COUNT(*) FROM clientes WHERE num_clie = ?"

    try {
        Database.getConnection()?.use { conn ->
            // Verificamos si ya hay un cliente con ese ID
            conn.prepareStatement(checkSQL).use { stmt ->
                stmt.setInt(1, num_clie)
                val resultSet = stmt.executeQuery()
                resultSet.next()

                if (resultSet.getInt(1) > 0) {
                    println("Cliente con ID $num_clie ya existente.")
                    return
                }
            }
            // Si no existe, insertamos un nuevo cliente
            val insertSQL = "INSERT INTO clientes (num_clie, empresa, rep_clie, limite_credito) VALUES (?, ?, ?, ?)"
            conn.prepareStatement(insertSQL).use { stmt ->
                stmt.setInt(1, num_clie)
                stmt.setString(2, empresa)
                stmt.setInt(3, rep_clie)
                stmt.setDouble(4, limite_credito)
                stmt.executeUpdate()
                println("Nuevo cliente insertado correctamente.")
            }
        }
    } catch (e: Exception) {
        println("Error al insertar cliente: ${e.message}")
    }
}

// Función para actualizar el límite de crédito de un cliente
fun updateClientes(num_clie: Int, nuevo_limite_credito: Double) {
    val checkSQL = "SELECT COUNT(*) FROM clientes WHERE num_clie = ?"

    try {
        Database.getConnection()?.use { conn ->
            // Verificamos si el cliente existe
            conn.prepareStatement(checkSQL).use { stmt ->
                stmt.setInt(1, num_clie)
                val resultSet = stmt.executeQuery()
                resultSet.next()

                if (resultSet.getInt(1) == 0) {
                    println("Error: Cliente con ID $num_clie no existe.")
                    return
                }
            }
            // Si existe, actualizamos el límite de crédito
            val updateSQL = "UPDATE clientes SET limite_credito = ? WHERE num_clie = ?"
            conn.prepareStatement(updateSQL).use { stmt ->
                stmt.setDouble(1, nuevo_limite_credito)
                stmt.setInt(2, num_clie)
                stmt.executeUpdate()
                println("Cliente actualizado: ID $num_clie - Nuevo límite de crédito: $nuevo_limite_credito")
            }
        }
    } catch (e: Exception) {
        println("Error al actualizar cliente: ${e.message}")
    }
}

// Función para eliminar un cliente
fun deleteClientes(num_clie: Int) {
    val checkSQL = "SELECT COUNT(*) FROM clientes WHERE num_clie = ?"

    try {
        Database.getConnection()?.use { conn ->
            conn.prepareStatement(checkSQL).use { stmt ->
                stmt.setInt(1, num_clie)
                val resultSet = stmt.executeQuery()
                resultSet.next()

                if (resultSet.getInt(1) == 0) {
                    println("Error: Cliente con ID $num_clie no existe.")
                    return
                }
            }
            // Si existe, lo eliminamos
            val deleteSQL = "DELETE FROM clientes WHERE num_clie = ?"
            conn.prepareStatement(deleteSQL).use { stmt ->
                stmt.setInt(1, num_clie)
                stmt.executeUpdate()
                println("Cliente con ID $num_clie ha sido eliminado correctamente.")
            }
        }
    } catch (e: Exception) {
        println("Error al eliminar cliente: ${e.message}")
    }
}
