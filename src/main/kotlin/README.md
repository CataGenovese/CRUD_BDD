## Introducción al Proyecto CRUD de Gestión de Clientes

Este proyecto tiene como objetivo gestionar una base de datos de la tabla clientes utilizando las operaciones CRUD (Crear, Leer, Actualizar y Eliminar). El sistema está diseñado para interactuar con la BDD training_kotlin, donde almacenamos la información de los clientes.

## Componentes del proyecto
Database Object: Se encarga de generar la conexión a la base de datos. Dentro de este archivo se encuentra la lógica para conectarse y desconectarse de la base de datos training_kotlin


GUIApp: Esta es la parte visual del proyecto donde los usuarios pueden interactuar con la base de datos. Gracias a JavaFX, se crea una interfaz gráfica con las 4 operaciones crud:

## Operaciones CRUD

`Crear (Insertar)`: Añade un nuevo cliente a la base de datos proporcionando la información del cliente como número de cliente, empresa, representante y límite de crédito.

`Leer (Listar)`: Muestra todos los clientes registrados en la base de datos. Si no hay clientes registrados, muestra un mensaje indicándolo.

`Actualizar:` Modifica los detalles de un cliente existente, como su límite de crédito, proporcionando su ID (número de cliente) y el nuevo límite.

`Eliminar:` Elimina un cliente de la base de datos proporcionando su ID (número de cliente).

## Funcionamiento
Al iniciar la aplicación, se presenta una interfaz gráfica con un combo box para seleccionar la operación.

Según la operación seleccionada, se muestran diferentes campos de entrada.

El usuario puede ingresar los datos correspondientes y presionar el botón Ejecutar para realizar la acción.

El resultado de la operación se muestra en la interfaz para que el usuario pueda ver si la operación fue exitosa o si hubo algún error.

**`Repetimos el procedimiento con la tabla oficinas`**