import java.sql.SQLException

// Inserta una nueva oficina si no existe
fun insertarOficina(oficina: Int, ciudad: String, region: String, dir: Int, objetivo: Double, ventas: Double) {
    val checkSQL = "SELECT COUNT(*) FROM oficinas WHERE oficina = ?"
    val insertSQL = "INSERT INTO oficinas (oficina, ciudad, region, dir, objetivo, ventas) VALUES (?, ?, ?, ?, ?, ?)"

    try {
        Database.getConnection()?.use { conn ->
            // Verifica si la oficina ya existe
            conn.prepareStatement(checkSQL).use { stmt ->
                stmt.setInt(1, oficina)
                val result = stmt.executeQuery()
                result.next()
                if (result.getInt(1) > 0) {
                    println("Ya existe la oficina con ID $oficina")
                    return
                }
            }

            // Si no existe, se inserta
            conn.prepareStatement(insertSQL).use { stmt ->
                stmt.setInt(1, oficina)
                stmt.setString(2, ciudad)
                stmt.setString(3, region)
                stmt.setInt(4, dir)
                stmt.setDouble(5, objetivo)
                stmt.setDouble(6, ventas)
                stmt.executeUpdate()
            }
        }
    } catch (e: Exception) {
        println("Error al insertar oficina: ${e.message}")
    }
}

// Actualiza las ventas de una oficina existente
fun updateOficina(oficina: Int, nuevasVentas: Double) {
    val sql = "UPDATE oficinas SET ventas = ? WHERE oficina = ?"
    try {
        Database.getConnection()?.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setDouble(1, nuevasVentas)
                stmt.setInt(2, oficina)
                stmt.executeUpdate()
            }
        }
    } catch (e: Exception) {
        println("Error al actualizar oficina: ${e.message}")
    }
}

// Elimina una oficina si existe
fun deleteOficina(oficina: Int) {
    val sql = "DELETE FROM oficinas WHERE oficina = ?"
    try {
        Database.getConnection()?.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setInt(1, oficina)
                stmt.executeUpdate()
            }
        }
    } catch (e: Exception) {
        println("Error al eliminar oficina: ${e.message}")
    }
}

// Lista todas las oficinas registradas
fun listarOficinas(): String {
    val sql = "SELECT * FROM oficinas"
    val builder = StringBuilder("Lista de Oficinas:\n")
    try {
        Database.getConnection()?.use { conn ->
            conn.createStatement().use { stmt ->
                val rs = stmt.executeQuery(sql)
                if (!rs.isBeforeFirst) {
                    builder.append("No hay oficinas registradas.\n")
                    return builder.toString()
                }
                while (rs.next()) {
                    builder.append(
                        """
                        -------------------------------
                        ID Oficina: ${rs.getInt("oficina")}
                        Ciudad: ${rs.getString("ciudad")}
                        Región: ${rs.getString("region")}
                        Director: ${rs.getInt("dir")}
                        Objetivo: ${rs.getDouble("objetivo")}
                        Ventas: ${rs.getDouble("ventas")}
                        -------------------------------
                        """.trimIndent()
                    )
                }
            }
        }
    } catch (e: SQLException) {
        builder.append("Error al listar oficinas: ${e.message}")
    }
    return builder.toString()
}
