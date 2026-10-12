# FitGym — Gestión de socios

Proyecto en Java realizado por el **Equipo 3** como ejercicio de Estructura de Datos. La aplicación permite registrar socios de un centro deportivo y administrar sus membresías mediante una **lista simplemente enlazada**.

## Integrantes

- Ariela Ivón Corrales Rodríguez
- Mélany Fernández Díaz
- Miriam Elena Pérez Hdez
- Julio César León Gato
- Ronal Hernández Castillo

## Problemática

En el centro deportivo **FitGym** se desea gestionar el registro de sus socios. De cada socio se almacena un identificador, su nombre, el tipo de membresía —Básica, Premium o VIP— y los días restantes antes de que venza.

El sistema permite consultar las membresías próximas a vencer, revisar los socios ordenados por días restantes, contar los socios de cada categoría, renovar una membresía y retirar los registros cuya membresía ya no tiene días disponibles.

## Estructura de datos

Los socios se almacenan en una **lista simplemente enlazada** implementada por el proyecto, no en una colección estándar de Java. Cada nodo (`Nodo<E>`) contiene un dato y una referencia al nodo siguiente; el último nodo apunta a `null`. La lista mantiene una referencia al primer nodo (`head`) y el número de elementos (`size`).

La lista es genérica (`LinkedList<E>`), mientras que `ListaSocio` especializa su uso para los objetos `Socio` e incorpora las operaciones propias del ejercicio. Rechaza con una excepción los socios `null` y los IDs repetidos. El orden de inserción se conserva, salvo en los resultados de la consulta ordenada, que se construyen en una copia para no modificar la lista original. Para ordenar se usa el método burbuja.

## Funcionalidades

- Agregar socio: registra un socio con ID, nombre, membresía y días restantes. La interfaz comprueba que el ID no esté registrado.
- Mostrar socios: muestra los registros en el orden en que fueron agregados.
- `MembresiasPorVencer(int diasLimite)`: devuelve los IDs de los socios con días restantes menores o iguales al límite. Rechaza límites negativos.
- `MostrarOrdenadosPorDias()`: devuelve una copia de los socios ordenada de mayor a menor por días restantes. Si hay empate, conserva el orden de llegada.
- `CantidadSociosPorMembresia()`: devuelve un texto con el conteo de membresías Básica, Premium y VIP.
- `RenovarMembresia(String idSocio, int diasAdicionales)`: suma días a la membresía del socio indicado. Devuelve `true` si lo encuentra y `false` si no existe. Rechaza IDs nulos o vacíos, días negativos y sumas que excedan el máximo de un entero.
- `EliminarSociosSinDias()`: elimina todos los socios con cero días restantes y devuelve la cantidad de registros eliminados.

Los datos se validan tanto al crear un `Socio` como al ingresarlos por el menú. Solo se admiten las tres membresías definidas y días restantes no negativos. Por el menú, los IDs se limitan a 10 caracteres alfanuméricos y se convierten a mayúsculas; los nombres solo admiten letras y espacios. La interfaz limita a 365 días los valores de días restantes, los límites de consulta y los días que se añaden al renovar.

## Estructura del proyecto

```text
.
├── Equipo3Parcial/
│   ├── LinkedList.java   # Implementación genérica de la lista enlazada
│   ├── List.java         # Interfaz de operaciones de lista
│   ├── ListaSocio.java   # Lista de socios y métodos del ejercicio
│   ├── Main.java         # Punto de entrada del programa
│   ├── Nodo.java         # Nodo genérico de la lista
│   ├── Sistema.java      # Menú, interacción y casos de prueba
│   ├── Socio.java        # Modelo y validaciones del socio
│   └── Validaciones.java # Validación de entradas por consola 
├── presentacion/
│   └── FitGym.pptx
└── README.md
```

## Compilación y ejecución

Se requiere un JDK instalado. Ejecuta los comandos desde la carpeta raíz del proyecto.

### Windows (PowerShell)

```powershell
New-Item -ItemType Directory -Force bin | Out-Null
javac -encoding UTF-8 -d bin src\*.java
java -cp bin Main
```

### Linux y macOS (Terminal)

```sh
mkdir -p bin
javac -encoding UTF-8 -d bin src/*.java
java -cp bin Main
```

En el menú, selecciona **8. Ejecutar casos de prueba** para ejecutar los escenarios definidos en `Sistema.java`.

## Casos de prueba incluidos

Los casos se ejecutan desde la opción 8 del menú. Los datos de referencia que crea `Sistema` son:

| ID | Nombre | Membresía | Días restantes |
|---|---|---|---:|
| S01 | Carlos | Básica | 5 |
| S02 | Ana | Premium | 30 |
| S03 | Pedro | VIP | 2 |
| S04 | María | Básica | 15 |
| S05 | Lucía | Premium | 60 |

### Consultar membresías por vencer

- Límite **5**: muestra los IDs `S01, S03`.
- Límite **1**: no devuelve IDs.
- Límite **100**: muestra los cinco IDs.
- Lista vacía con límite 5: no devuelve IDs.

### Ordenar por días restantes

- Con los datos de referencia, muestra Lucía (60), Ana (30), María (15), Carlos (5) y Pedro (2).
- Se muestra también la lista original para comprobar que ordenar no la modifica.
- Caso de empate: se agregan socios con 15, 40 y 15 días; el resultado esperado es Dos (40), Uno (15), Tres (15), manteniendo el orden de llegada entre los empatados.
- Lista vacía: devuelve una lista vacía.

### Contar por tipo de membresía

- Con los datos de referencia: **Básica: 2, Premium: 2, VIP: 1**.
- Lista vacía: los tres conteos son cero.

### Renovar membresía

- Renovar `S01` con 10 días: devuelve `true` y Carlos queda con 15 días.
- Renovar el ID inexistente `S99`: devuelve `false`.
- Renovar en una lista vacía: devuelve `false`.

### Eliminar socios sin días

- Caso mixto con ceros al inicio, consecutivos, en medio y al final: elimina **5** socios; permanecen Inoue (5 días) e Isabella (7 días), con tamaño 2.
- Todos los socios con cero días: elimina **3** y deja la lista vacía; luego se agrega otro socio para comprobar que la lista sigue operativa.
- Ningún socio con cero días: elimina **0** y conserva los cinco socios de referencia.
- Lista vacía: devuelve **0**.

### Casos negativos y validaciones

`Sistema` prueba las siguientes entradas inválidas. Cada una debe lanzar `IllegalArgumentException`; el método de prueba imprime `[Correcto]` si se lanza y `[Fallo]` si no se lanza:

- Límite `-1` en la consulta de membresías por vencer.
- Renovación con `-5` días, ID `null`, ID compuesto solo por espacios o una suma que excede `Integer.MAX_VALUE`.
- Agregar `null` directamente a la lista de socios.
- Agregar directamente un socio con el ID `S01`, que ya está registrado.
- Construir un socio con ID vacío, membresía no admitida (`Pro`) o días restantes negativos.

Después de las operaciones inválidas de renovación, se comprueba que Carlos conserva sus 5 días. Después de intentar agregar el socio duplicado, se comprueba que la lista conserva sus 5 socios. La opción de pruebas también muestra el resultado de la consulta para una lista vacía.


## Presentación para exposición oral

La presentación del proyecto está integrada en el repositorio:
[Abrir presentación de FitGym (PowerPoint)](presentacion/FitGym.pptx)

## Declaración sobre el uso de Inteligencia Artificial

Para este programa se utilizó Inteligencia Artificial para revisar el código, como apoyo en la realizacion de los casos de prueba de la opción 8 (sugerencia de casos negativos) y en la revisión y mejora de este README.
