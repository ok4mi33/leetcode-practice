# Validar Direccion IPv4

> **Segundo problema del nuevo criterio de seleccion** (conexion con seguridad/AWS)
> Basado en LeetCode #468 (Validate IP Address) - de este problema se resolvio solo la
parte de **IPv4**.

> Quedan pendientes como problemas futuros separados: **IPv6** (para completar el enunciado
original de LeetCode #468) y **validacion de rangos CIDR** (ej. `192.168.1.0/24`), 
esta última con conexión más directa a AWS Security Groups / firewall rules.

## Problema

Dado un string `queryIP`, determinar si es una direccion IPv4 valida.

Reglas de IPv4 valida:

- Formato `x1.x2.x3.x4`, exactamente 4 partes separadas por punto.
- Cada `xi` es un numero entre 0 y 255.
- Cada `xi` no puede tener ceros a la izquierda (`"01` invalido, `"0"` solo si es valido).


**Conexion con seguridad/AWS:**
Es el tipo de validacion que hace un firewall, un Security Group, o cualquier sistema
que recibe una IP como input (headers HTTP, configuracion de reglas, logs).
Un validacion de formato mal hecha puede permitir bypasses de filtros o interpretaciones
incorrectas de una regla de acceso.


## Enfoque

1. Separar el string por el punto con `split("\\.")`.
    El punto se escapa porque en regex significa "cualquier caracter".

2. Verificar que el array resultante tenga exactamente 4 elementos.
    Si no, `return false` de inmediato.

3. Recorrer las 4 partes con un `for`. Para cada parte:
    - Verificar cero a la izquierda: `parte.length() > 1 && parte.charAt(0) == '0'` → si se cumple, `return false`.
    - Convertir a numero con `Integer.parseInt(parte)` **dentro de un `try`**.
        El input puede no ser numerico (ej. `"abc"`), y esa conversion lanza `NumberFormatException` si falla.
        Sin el `try-catch`, un input malformado tumaria el programa completo 
        (relevante en seguridad: un input inesperado no deberia cause un crash).
    - Dentro del mismo `try`, verificar el rango: `numero > 255 || numero < 0` → si se cumple, `return false`.
    - En el catch `catch`, imprimir que parte causo el problema (log informativo) y `return false`.

4. Si el `for` completa las 4 vueltas sin disparar ningun `return false`, entonces `return true` (fuera del `for`).

Dos validaciones independientes (cero a la izquierda y fuera de rango) se implementan
como **dos `if` separados**, no uno solo con `&&`.
Cualquiera de las dos, por si sola, ya invalida la IP - no se necesita que ambas 
ocurran a la vez.
Un `&&` habria hecho que, por ejemplo, `"300"` pasara sin problema, ya que no tiene
cero a la izquierda y por short-circuit nunca se evaluaria el rango.

```java
public static boolean esIPv4Valida(String ip) {

    String[] partes = ip.split("\\.");

    if (partes.length != 4) {
        return false;
    }

    for (int i = 0; i < partes.length; i++) {

        String parte = partes[i];

        if (parte.length() > 1 && parte.charAt(0) == '0') {
            return false;
        }

        try {
            int numero = Integer.parseInt(parte);

            if (numero > 255 || numero < 0) {
                return false;
            }

        } catch (NumberFormatException e) {
            System.out.println("Parte invalida (no numerica): " + parte);
            return false;
        }
    }

    return true;
}
```

## Casos de prueba verificados

| Caso | Input | Motivo | Esperado | Obtenido |
|---|---|---|---|---|
| 1. Válida simple | `"192.168.1.1"` | — | true | true ✅ |
| 2. Cero a la izquierda | `"192.168.01.1"` | `"01"` mal formado | false | false ✅ |
| 3. Fuera de rango | `"256.1.1.1"` | `256 > 255` | false | false ✅ |
| 4. Partes incorrectas | `"192.168.1"` | solo 3 partes, faltan | false | false ✅ |
| 5. No numérica | `"192.168.1.abc"` | dispara `NumberFormatException`, capturada | false | false ✅ |
| 6. Cero solo (borde) | `"0.0.0.0"` | `"0"` de un solo carácter es válido | true | true ✅ |
| 7. Límite superior (borde) | `"255.255.255.255"` | `255` es el máximo permitido, no debe rechazarse | true | true ✅ |

Los casos 6 y 7 son los mas importantes.
Verifican los bordes exactos de las reglas (cero solo vs. cero con mas digitos, y el
limite superior del rango) - el tipo de caso que expone errores de  `>` vs `>=` o de condiciones demasiado estrictas.

## Complejidad

**Tiempo:** O(1).
Una IPv4 siempre tiene exactamente 4 partes como maximo 3 digitos cada una.
El trabajo no depende de ningun tamaño de entrada variable, es una cantidad fija de pasos.

**Espacio:** O(1).
El array `partes` siempre tiene como maximo 4 elementos, sin importar el input.

## Conceptos nuevos aprendidos (para reutilizar en futuros problemas)

- **`split(regex)`**: separa un `String` en un array de `String` usando el patron dado.
    El separador es una expresion regular, no un caracter literal - por eso el punto
    debe escaparse como `"\\."` (en regex, `.` sin escapar significa "cualquier carácter").

- **`join`** (mencionado, no usado aquí): hace lo opuesto a `split` - junta un array de
    strings en un solo con un separador.

- **`String.length()` vs `array.length`**: `length()` (con parentesis) es un metodo,
    usado en `String` para contar caracteres.
    `length` (sin parentesis) es un atributo, usado en arrays para contar elementos.
    Confundirlos es un error comun.

- **`charAt(indice)`**: devuelve el caracter de un `String` en una posicion especifica.
    Se uso para comparar el primer caracter(`charAt(0)`) contra el caracter literal
    `'0'` (comillas simples, no dobles - un `char`, no un `String`).

- **`Integer.parseInt(String)`**: convierte un `String` a `int`. 
    Lanza `NumberFormatException` si el string no representa un numero valido.

- **`try-catch`**: estructura para intentar ejecutar codigo que puede lanzar una excepcion, 
    sin que el programa se detenga abruptamente si eso ocurre.
    - `try { codigo_que_puede_fallar } catch (TipoExcepcion e) { que_hacer_si_fall}`
    - Si ocurre la excepcion, Java salta inmediatamente al `catch`, sin ejecutar resto del `try`.
    - **Por qué importa en seguridad:** un sistema que recibe inputs no confiables
        (headers, configuracion, datos de usuario) puede recibir datos malformados
        en cualquier momento.
        Sin manejo de excepciones, un input inesperado puede tumbar el programa completo 
        una forma simple de que un input malicioso cause una interrupcion de servicio 
        (Denial of Service) si el sistema no esta preparado para fallar de forma controlada.
    - Buena practica: loguear informacion del error(`System.out.println` u otro mecanismo
        de logging) **antes** del `return`/manejo del error, ya que el codigo despues
        un `return` detnro del mismo bloque nunca se ejecuta.

## Errores cometidos en el camino

1. Confundir `split` con `join` (este ultimo hace lo opuesto: unir, no separar).
2. Usar el string fijo `"192.168.1.1"` hardcodeado dentro del metodo en vez del parametro
    `ip` recibido.
3. Referenciar una variable `parte` sin haberla declarado (`partes[i]` nunca asignado a una variables dentro del `for`).
4. Confundir `parte.length` (elementos de un array) con `parte.length()` (caracteres de un string).
5. Anidar las dos validaciones (cero a la izquierda y fuera de rango) con `&&` en un
    solo `if`, lo cual, por el short-circuit, dejaba pasar casos como `"300"` sin detectar el problema de rango.
6. Colocar `return true` **dentro** del `for` en vez de despues - esto hacia que el
    metodo terminara con la primera parte valida, sin revisar las 3 restantes.
7. Llaves mal anidadas entre el `if` de rango y el `catch` del `try`.
8. No validar la cantidad de partes del array (`partes.length != 4`), dejando pasar IPs incompletas como `"192.168.1"`.
9. No manejar la excepcion `NumberFormatException` al inicio, lo que hacia que un input no
    numerico (`"abc"`) tumbara el programa en vez de simplemente invalidar la IP.

## Leccion general

Cuando una validacion tiene **varias reglas independientes**, cada una capaz de invalidar
el resultado por si sola, deben implementarse como condiciones **separadas** (varios
`if`, cada uno con su propio `return`), no combinadas con `&&` en un sola condicion.

Combinarlas asume erroneamente que todas las reglas deben violarse a la vez para que 
algo sea invalido, cuando en realidad basta con que una sola falle.

Ademas, cualquier conversion de datos externos (`Integer.parseInt`, parseo de strings,
etc.) debe asumir que el input puede no tener el formato esperado, y manejarse con 
`try-catch` en vez de asumir que siempre va a funcionar.

Esto es una leccion directamente aplicable a seguridad: todo input que viene de una
fuente no controlada (usuario, red, headers, configuracion externa) debe tratarse
como potencialmente malformado, y el programa debe fallar de forma controlada, no de
forma abrupta.



