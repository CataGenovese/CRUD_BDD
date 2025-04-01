//funcion para listar clientes -> READ
fun listarClientes(num_clie: Int, empresa: String, rep_clie: Int, limit_clie: Double) {
    val sql = "INSERT INTO clientes (num_clie, empresa, rep_clie, limite_credito) VALUES (?, ?, ?, ?)"

    Database.getConnection()?.use { conn ->
        conn.createStatement().use {
            val result = it.executeQuery(sql)
            while (result.next()) {
                println(
                    "Numero de cliente: ${result.getString("num_clie")}, Empresa: ${result.getString("empresa")}, Rep cliente: ${
                        result.getInt(
                            "rep_clie"
                        )
                    }, Limite de credito: ${result.getString("limite_credito")}"
                )
            }
        }
    }
}

fun updateClientes(newClie: Int, new_limite_credito: Double) {
    val sql = "UPDATE clientes SET limite_credito = ? WHERE num_clie = ? "
    Database.getConnection()?.use { conn ->
        conn.prepareStatement(sql).use {
            it.setDouble(1, new_limite_credito)
            it.setInt(2, newClie)
            it.executeUpdate()
            println("Clientes Updated: Nueva empresa $newClie - Nuevo límite credito: $new_limite_credito")
        }
    }
}

fun deleteClientes(num_clie: Int) {
    val sql = "DELETE FROM clientes WHERE num_clie = ? "
    Database.getConnection()?.use { conn ->
        conn.prepareStatement(sql).use {
            it.setInt(1, num_clie)
            it.executeUpdate()
            println("Cliente con número $num_clie ha sido eliminado correctamente")
        }
    }
}

fun insertarCliente(num_clie: Int, empresa: String, rep_clie: Int, limitite_credito: Double) {
    val sql= "INSERT INTO clientes(num_clie, empresa, rep_clie, limite_credito) VALUES (?, ?, ?, ?)"
    Database.getConnection()?.use { conn ->
        conn.prepareStatement(sql).use {
            it.setInt(1, num_clie)
            it.setString(2, empresa)
            it.setInt(3, rep_clie)
            it.setDouble(4, limitite_credito)
            it.executeUpdate()
            println("Nuevo cliente insertado correctamente")
        }
    }
}