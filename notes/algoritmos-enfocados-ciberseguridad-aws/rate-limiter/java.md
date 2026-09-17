# Rate Limiter (Design Hit Counter)

> ** Punto de inflexion en el proceso.**
> Primer problema elegido bajo un **nuevo criterio de seleccion**, distinto al de los 17
problemas anteriores. Antes se elegia "el siguiente patron de una lista generica" 
(hashing, two pointer, sliding window, binary search, recursion, arboles, grafos, DP).
A partir de aqui, los problemas se eligen porque:
> 1. Son problemas reales y reconocidos (LeetCode u otro banco conocido).
> 2. Tienen una conexion **genuina** con ciberseguridad o AWS - no para "aplicar a un trabajo de seguridad",
sino para generar intuicion sobre situaciones reales de ese entorno.
>
> Basado en LeetCode #362 (Design Hit Counter).

## Problema
Diseñar una estructura que registre "hits" a un sistema y permita consultar cuantos ocurrieron
en los ultimos 300 segundos.
- `hit(timestamp)`: registra un hit en el segundo `timestamp`. No devuelve nada.
- `getHits(timestamp)`: devuelve el número de hits en el rango `(timestamp - 300, timestamp]`.

Se asume que los timestamps llegan en orden no decreciente

**Conexion con seguridad:** es la logica base de un rate limiter de API real - detectar abuso
de un endpoint (fuerza bruta, scraping, throttling por IP/usuario).

## Enfoque
Se usa una `Queue<Integer>`(`LinkedList`) para guardar timestamps en orden FIFO.
- `hit`: agrega al final con `offer()`. O(1), sin limpieza en este momento.
- `getHits`: antes de contar, descarta (`poll()`) desde el frente todo lo que cumpla 
`cola.peek() <= timestamp - 300`. Como es FIFO, el mas viejo siempre esta al frente,
asi que no hace falta revisar toda la cola. Luego `return cola.size()`.

Punto de diseño clave: comparar con `cola.peek()` **directo en la condicion del `while`**,
no guardarlo en una variable (`front`) previa, porque esa copia se desincroniza del
estado real tras cada `poll()` si no se refresca manualmente.

```java
import java.util.Queue;
import java.util.LinkedList;

public class RateLimiter {

  static Queue<Integer> cola = new LinkedList<>();

  public static void hit(int timestamp) {
    cola.offer(timestamp);
  }

  public static int getHits(int timestamp) {
    while (!cola.isEmpty() && cola.peek() <= timestamp - 300) {
      cola.poll();
    }
    return cola.size();
  }
}
```

## Casos de prueba verificados
A partir de este problema se documenta explicitamente que trazas/casos borde se pensaron y
verificaron.

| Caso | Secuencia | Rango evaluado | Esperado | Obtenido |
|---|---|---|---|---|
| 1. Simple (todo dentro de ventana) | `hit(1); hit(2); hit(3); getHits(4)` | `(4-300,4]=(-296,4]` | 3 | 3 ✅ |
| 2. Borde exacto de expiración | `hit(1); hit(2); getHits(301)` | `(301-300,301]=(1,301]` | 1 (el `1` expira por ser límite excluido; el `2` sobrevive) | 1 ✅ |
| 3. Cola vacía desde el inicio | `getHits(100)` sin `hit()` previo | — | 0 | 0 ✅ |

El caso 2 es el mas importante: verifica el borde exacto de un rango abierto-cerrado.
Un error de `<` vs `<=`habria pasado desapercibido con pruebas menos especificas.

## Complejidad
**Tiempo:**
- `hit`: **O(1)**. Agregar al final de una `LinkedList` no depende del tamaño.
- `getHits`: **O(n) en el peor caso de una llamada aislada** (n = elementos en la cola),
pero **O(1) amortizado** considerando muchas llamadas: cada timestamp solo se saca 
(`poll()`) una vez en toda su vida util, asi que el costo caro de una llamada no se
repite en la siguientes.

**Espacio:** **O(w)**, w = hits que caben dentro de la ventana activa de 300 segundos. No depende del total historico de hits

## Errores cometidos en el camino
1. ** Diseño inicial como metodo estatico unico con estado local.** La `Queue`era variable
local dentro de un solo metodo `designhit(hits, timestamp)`, perdiendo el historial
entre llamadas. Corregido moviendo la cola a atributo de clase (`static`) y separando
en dos metodos con responsabilidades distintas.
2. **Confusion de API de `Queue`:** se intento `.put()` (no existe en `Queue`) y se
llamo `.peek()`/`.poll()` con parametro, cuando ninguno recibe argumentos.
3. **Condicion de expiracion con `<`en vez de `<=`.** El limite inferior del rango es
excluido, asi que un timestamp igual a `timestamp - 300`debe expirar.
4. **Variable `front` como copia congelada.** Guardarla antes del `while`y olvidar 
refrescarla producia comparaciones contra un valor viejo. Se resolvio eliminandola
y llamando `cola.peek()` directo en cada iteracion.
5. **`NullPointerException` potencial** por no verificar cola vacia antes de `peek()`
(devuelve `null`, que rompe al asignarse a un `int`). Resuelto con `!cola.isEmpty()` 
como primera condicion del `while` (shot-circuit de `&&`).}
6. **Errores de sintaxis por desbalance de llaves `{}`** al mover codigo entre metodos.
7. **`cola.clear;` sin parentesis** al limpiar la cola entre casos de prueba en `main`.

## Leccion general
Cuando un valor puede cambiar dentro de un loop, es mas seguro **consultar la fuente de
verdad directamente en cada iteraccion** que guardar una copia y tener que refrescarla
manualmente. Cachear un valor mutable sin invalidacion es una fuente clasica de bugs -
tanto en ejercicios como en sistemas reales (un rate limiter en produccion que no
recalcula al expirar entradas podria bloquear o dejar pasar trafico con datos 
desactualizados).

Verificar casos borde con trazas numericas concretas expone errores off-by-one (`<` vs `<=`) que pasan inadvertidos con pruebas
superficiales - habito directamente relevante en seguridad, donde ese tipo de error es
fuente comun de bypass en validaciones y controles de acceso.

