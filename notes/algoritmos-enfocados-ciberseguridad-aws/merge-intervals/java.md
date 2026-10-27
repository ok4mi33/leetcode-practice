# Merge Intervals

> **Quinto problema del nuevo criterio de seleccion** (conexion con seguridad/AWS).
> Basado en LeetCode #56 (Merge Intervals).
>
> Patron nuevo respecto a los anteriores: **intervalos** (ordenar y comparar con el ultimo).

## Problema

Dada una lista de intervalos `[inicio, fin]`, devolver una lista nueva donde los 
intervalos que se solapan quedan fusionados en uno solo.

```
[[1,3],[2,6],[8,10],[15,18]]  →  [[1,6],[8,10],[15,18]]
```

**Conexion con seguridad/AWS:**
Consolidar rangos de puertos o de tiempo que se solapan.
Un Security Group con reglas `80-90`, `85-100` y `443-443` se resume en `80-100` y `443-443`.
Tambien sirve para fusionar ventanas de tiempo de sesiones sospechosas que se pisan entre si.

## Pseudocodigo

```
función mergeIntervalos(intervalos):
    ordenar intervalos por inicio
    resultado = lista con el primer intervalo

    para cada nuevo, desde el segundo:
        ultimo = el último intervalo de resultado
        si nuevo[0] <= ultimo[1]:
            ultimo[1] = max(ultimo[1], nuevo[1])
        sino:
            agregar nuevo a resultado

    devolver resultado
```
## Enfoque

**1. Idea central: ordenar primero.**

Desordenados, un intervalo puede solaparse con otro que esta lejos en la lista.
Ordenados por inicio, si un intervalo se solapa con algo, ese algo es el ultimo intervalo ya fusionado.
Basta una pasada comparando cada intervalo **solo contra el ultimo de `resultado`**.

**2. Condicion de solapamiento: `nuevo[0] <= ultimo[1]`.**

Se compara el inicio del nuevo contra el fin del ultimo, con **`<=`**, no `<`.
`[1,4]` y `[4,5]` comparten el 4, así que se tocan y se fusionan en `[1,5]`.
Con `<`, ese borde (4 contra 4) daría `false` y quedarían separados.

**3. Nuevo fin al fusionar: el mayor de los dos fines.**
`ultimo[1] = Math.max(ultimo[1], nuevo[1])`.
Con `[1,10]` seguido de `[2,5]`, el fin debe seguir en 10.
Poner el fin del nuevo (5) "cerraria" un rango que el intervalo anterior seguia cubriendo,
lo que en un Security Group seria cerrar puertos que una regla abria.

**4. Ordenar con un comparador.**
`Arrays.sort(intervalos, (a, b) -> a[0] - b[0]);`
Compara dos intervalos por su primer numero: si la resta es positiva, `a` va despues de `b`.

**5. Modificar el ultimo en su lugar.**

`ultimo` es una referencia al mismo arreglo que esta dentro de `resultado`.
Al hacer `ultimo[1] = ...` se modifica el intervalo dentro de la lista, sin reemplazarlo.

**6. Caso vacio.**

Con `intervalos = []`, `intervalos[0]` lanzaria `ArrayIndexOutOfBoundsException`.
Se resuelve con un `if` al inicio que devuelve `new int[][]{}`.

```java
import java.util.*;

public class MergeIntervals {

  public static int[][] merge(int[][] intervalos) {

    if (intervalos.length == 0) {
      return new int[][]{};
    }

    Arrays.sort(intervalos, (a, b) -> a[0] - b[0]);

    List<int[]> resultado = new ArrayList<>();
    resultado.add(intervalos[0]);

    for (int i = 1; i < intervalos.length; i++) {

      int[] nuevo = intervalos[i];
      int[] ultimo = resultado.get(resultado.size() - 1);

      if (nuevo[0] <= ultimo[1]) {
        ultimo[1] = Math.max(ultimo[1], nuevo[1]);
      } else {
        resultado.add(nuevo);
      }
    }

    return resultado.toArray(new int[resultado.size()][]);
  }
}
```

## Casos de prueba verificados

| Caso | Input | Qué verifica | Esperado | Obtenido |
|---|---|---|---|---|
| 1 | `[[1,3],[2,6],[8,10],[15,18]]` | ejemplo base | `[[1,6],[8,10],[15,18]]` | igual ✅ |
| 2 | `[[1,4],[4,5]]` | borde `<=` (se tocan en el 4) | `[[1,5]]` | igual ✅ |
| 3 | `[[1,10],[2,5]]` | `Math.max` (uno contenido en otro) | `[[1,10]]` | igual ✅ |
| 4 | `[[8,10],[1,3],[2,6]]` | `sort` (entrada desordenada) | `[[1,6],[8,10]]` | igual ✅ |
| 5 | `[[1,3]]` | un solo intervalo | `[[1,3]]` | igual ✅ |
| 6 | `[]` | vacío | `[]` | igual ✅ |

Los casos 2 y 3 son los mas importantes: son los dos errores de logica que aparecieron
al diseñar (el operador del borde y el nuevo fin).
Para imprimir un `int[][]` se usa `Arrays.deepToString(...)`.

## Complejidad

**Tiempo: O(n log n).**

- `sort`: O(n log n). Es el costo tipico de un buen ordenamiento.
- `for`: una pasada, cada intervalo se mira una vez, trabajo fijo por vuelta: O(n).
- Total: n log n + n. El termino menor se descarta, asi que domina el `sort`.

Si los intervalos ya vinieran ordenados, bajaria a O(n).

**Espacio: O(n).**

En el peor caso (ningun solapamiento) `resultado` guarda un intervalo por cada uno de la entrada.
`Arrays.sort` puede usar espacio auxiliar interno, que algunos analisis cuentan aparte.

## Conceptos nuevos aprendidos (para reutilizar)
 
- **Patron de intervalos:** ordenar por inicio y comparar cada uno solo contra el ultimo fusionado.

- **`Arrays.sort(arreglo, comparador)`:** `(a, b) -> a[0] - b[0]` ordena arreglos por su primera posición.
  Necesita `import java.util.*;`.

- **`List<int[]>`:** una lista cuyos elementos son arreglos de enteros.
  Cada elemento es un intervalo completo, no un número.

- **Ultimo elemento de una lista:** `resultado.get(resultado.size() - 1)`.
    Las posiciones empiezan en 0, asi que con `size()` elementos la ultima es `size() - 1`.
    Pedir `get(size())` lanza `IndexOutOfBoundsException`.

- **Los metodos van con punto, despues del objeto:** `resultado.size()`, no `size(resultado)` (eso es de otros lenguajes).

- **`Maht.max(a, b)`:** `max` pertenece a la clase `Math`. Mismo patron que `Integer.parseInt(...)`.

- **De `List<int[]>` a `int[][]`:** `resultado.toArray(new int[resultado.size()][])`.

- **Un intervalo vs. un numero:** `nuevo` es un intervalo completo (`int[]`), `nuevo[0]` es solo su inicio.
    `resultado.add(nuevo)` agrega el intervalo, `resultado.add(nuevo[0])` intentaria agregar un numero.

- **No se pueden comparar arreglos con `<` :** la condicion tiene que comparar numeros sueltos
    (`nuevo[0]` contra `ultimo[1]`).

- **El tipo del `return` debe coincidir con el del metodo:** `merge` devuelve `int[][]`,
    asi que el caso vacio devuelve `new int[][]{}`, no `false`.

## Errores cometidos en el camino

1. Dudar entre comparar con variables o con intervalos completos (`[4,5] < [1,4]`).
2. Usar `<` en vez de `<=` en la condicion de solapamiento (el borde 4 contra 4).
3. Asignar `ultimo.fin = nuevo[0]`: tres errores a la vez (el campo no existe en Java, era el inicio y no el fin, y no tomaba el mayor).
4. Pensar que el nuevo fin era siempre el del intervalo nuevo, en vez del mayor de los dos.
5. Agregar `ultimo[1]` (un numero) en el `else`, en vez del intervalo `nuevo`.
6. `Math.max(ultimo[1], nuevo[0])`: usar el inicio del nuevo en vez de su fin.
7. `resultado.add(nuevo[0])` al inicio (usando una variable que aun no existia). y luego `resultado.add(intervalos)`, en vez de `intervalos[0]`.
8. Usar `ultimo` en su propia declaracion: `int[] ultimo = (ultimo[1]);`
9. `get(intervalos[i])`, `get(size()-1)`, `get(size(resultado))` y `get(resultado.size())`: cuatro intentos hasta llegar a `get(resultado.size) - 1)`.
10. Escribir `max(...)` suelto en vez de `Math.max(...)` (dos veces).
11. Poner el `return` dentro del `for` (mismo error que `return true` en IPv4).
12. Olvidar el caso vacion: `intervalos[0]` en un arreglo vacio.
13. En el `if` del caso vacio, probar `== 2` y `== 1` antes de llegar a `==0`: se contaba posiciones en vez de elementos.

## Leccion general

En problemas de intervalos, **ordenar primero** convierte un problema de "comparar todos contra todos"
en una sola pasada comparando contra el ultimo.
El costo se desplaza al ordenamiento, y por eso el total es O(n log n).

Los dos bordes que mas fallan son el operador de solapamiento (`<=`, porque tocarse en un extremo cuenta) y el nuevo fin 
(el **mayor** de los dos, porque un intervalo puede estar contenido en otro).
Ambos son errores de tipo "off-by-one" y de "perder cobertura", y en seguridad se traducen en reglas que dejan puertos abiertos o cerrados por error.

## Reflexion del proceso

En este problema el formato fue distinto al de ciclos en grafos: te di la idea completa al inicio
y tu armaste el pseudocodigo, con preguntas solo en las decisiones de diseño.
Eso funciono bien para el pseudocodigo.

Donde mas costo fue en la traduccion a Java, sobre todo `get(resultado.size() - 1)`, que requirio cuatro intentos.
Es una pieza de sintaxis que no se deduce, asi que conviene dartela directamente en vez de hacerte adivinar.
Para proximos problemas: las piezas de sintaxis de la biblioteca (métodos de `List`, `Math`, `Arrays`) van en una tabla desde el inicio, 
y las preguntas quedan para la logica. 

