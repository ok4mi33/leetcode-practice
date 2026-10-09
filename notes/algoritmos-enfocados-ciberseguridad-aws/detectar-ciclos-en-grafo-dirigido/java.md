# Detectar Ciclos en un Grafo Dirigido

> **Cuarto problema del nuevo criterio de seleccion** (conexion con seguridad/AWS).
> Basado en LeetCode #207 (Course Schedule).
>
> Con este se cierran los tres candidatos de la etapa: Rate Limiter, Validar IPs(IPv4, IPv6 y CIDR)
y Ciclos en grafos.

## Problema

Hay `n` nodos numerados de `0` a `n-1` y una lista de aristas dirigidas `[a, b]`,
donde cada una signifca "`a` depende de `b`" (o "`a` puede asumir a `b`").

Devolver `true` si el grafo **tiene al menos un ciclo**, y `false` si no.

Ejemplos:

- `n = 3`, aristas `[[0,1]],[1,2],[2,0]]`→ hay ciclo (0 → 1 → 2 → 0).
- `n = 3`, aristas `[[0,1],[1,2]]`  → no hay ciclo (la cadena termina en 2).

**Ojo con la diferencia con LeetCode #207:**
Alli la pregunta es "¿se pueden terminar todos los curos?", que da `true` cuando **no** hay ciclo.
Aqui `hayCiclo` devuelve `true` cuando **si** hay ciclo, o sea lo contrario.

**Conexion con seguridad/AWS:**
Dependencias circulares en IAM. El rol A puede asumir el rol B, B al C y C de vuelta al A.
Un ciclo asi es una cadena de permisos que puede escalar privilegios sin que nadie lo note.
En un caso real los nodos serian nombres de roles (`"AdminRole"`), no numeros `0..n-1`,
y ahi un `HashMap` si seria la estructura adecuada.

## Pseudocódigo

```
función tieneCiclo(nodo):
    marcar nodo como "en proceso"
    para cada vecino de nodo:
        si vecino está "en proceso":
            devolver true
        si vecino está "nunca visitado":
            si tieneCiclo(vecino):
                devolver true
    marcar nodo como "terminado"
    devolver false

función hayCiclo(n, aristas):
    construir lista de adyacencia
    para cada nodo de 0 a n-1:
        si el nodo está "nunca visitado":
            si tieneCiclo(nodo):
                devolver true
    devolver false
```

## Enfoque 

**1. Idea central: dos estados no alcanzan, hacen falta tres.**

Con solo "visitado/no visitado" no se distingue un ciclo de un nodo compartido.
En `0→1→3` y `0→2→3`, al llegar al 3 por segunda vez el 3 esta "visitado", pero no hay ciclo.
En `0→1→2→0`, al volver al 0 tambien esta "visitado", y ahi si hay ciclo.

La diferencia es si el nodo todavia se esta explorando o ya termino:

```

0 = nunca visitado
1 = en proceso (sigo dentro de su recorrido)
2 = terminado (ya explore todo lo que sale de el)

```

Regla: llegar a un nodo en `1` es un ciclo. Llegar a uno en `2` no pasa nada. Llegar a uno en 
`0` hay que explorarlo.

**2. Estructuras de datos.**

- `int[] estado = new int[n]` : un estado por nodo.
    Java lo inicializa en `0`, que coincide con "nunca visitado", asi que no hace falta
    un `for` para llenarlo.
    Como los nodos son `0..n-1`, el arreglo es mas simple y rapido que un `HahsMap`.
- `List<List<Integer>> grafo` : lista de adyacencia, una lista de vecinos por nodo.
    Hay que crear las `n` listas vacias con un `for`  antes de cargar las aristas, porque
    `new ArrayList<>()` crea solo la lista de afuera, vacia.

**3. Marcado de entada y salida.**

`estado[nodo] = 1` al entrar y `estado[nodo] = 2` justo antes del `return false` final.
Si al salir se dejara en `1`, un nodo ya terminado pareceria "en proceso" y daria un 
ciclo falso.

**4. Recorrer todos los nodos.**

El grafo puede tener partes desconectadas, asi que `hayCiclo` llama a `tieneCiclo` desde
cada nodo con `estado[i] == 0`.
Con `n = 4` y `[[0,1],[2,3],[3,2]]`, empezar solo en el 0 nunca veria el ciclo `2 → 3 → 2`.

**5. Estado en atributos `static`.**

`grafo` y `estado` se declaran arriba de la clase sin valor y se crean dentro de `hayCiclo`,
dond existe `n`.
Asi cada llamada empieza con estructuras nuevas y no se mezcla con los restos de una 
llamada anterior.

```java
import java.util.*;

public class CourseSchedule {

  static List<List<Integer>> grafo;
  static int[] estado;

  public static boolean hayCiclo(int n, int[][] aristas) {

    grafo = new ArrayList<>();
    estado = new int[n];

    for (int i = 0; i < estado.length; i++) {
      grafo.add(new ArrayList<>());
    }

    for (int[] arista : aristas) {
      grafo.get(arista[0]).add(arista[1]);
    }

    for (int i = 0; i < n; i++) {
      if (estado[i] == 0) {
        if (tieneCiclo(i)) {
          return true;
        }
      }
    }

    return false;
  }

  static boolean tieneCiclo(int nodo) {
    estado[nodo] = 1;

    for (int vecino : grafo.get(nodo)) {
      if (estado[vecino] == 1) {
        return true;
      }

      if (estado[vecino] == 0) {
        if (tieneCiclo(vecino)) {
          return true;
        }
      }
    }

    estado[nodo] = 2;

    return false;
  }
}
```

## Casos de prueba verificados

| Caso | Input | Motivo | Esperado | Obtenido |
|---|---|---|---|---|
| 1. Ciclo simple | `n=3, [[0,1],[1,2],[2,0]]` | 0→1→2→0 | true | true ✅ |
| 2. Cadena sin ciclo | `n=3, [[0,1],[1,2]]` | termina en 2 | false | false ✅ |
| 3. Nodo compartido sin ciclo | `n=4, [[0,1],[0,2],[1,3],[2,3]]` | dos caminos al 3, nadie vuelve | false | false ✅ |
| 4. Ciclo en parte desconectada | `n=4, [[0,1],[2,3],[3,2]]` | el ciclo 2↔3 no se alcanza desde el 0 | true | true ✅ |
| 5. Sin aristas (borde) | `n=3, []` | grafo de nodos aislados | false | false ✅ |
| 6. Auto-ciclo (borde) | `n=1, [[0,0]]` | el nodo se apunta a sí mismo, está en `1` | true | true ✅ |
| 7. Ciclo de dos nodos | `n=2, [[0,1],[1,0]]` | 0→1→0 | true | true ✅ |


El caso 3 es el mas importante: es el que justifica los tres estados.
Con solo dos estados daria `true` por error.

Dos incidentes en las pruebas:

- En la primera corrida el caso 3 mostro `esperado=true`. Era un error al copiar el texto
del `println`, no un bug del codigo, que ya devolvia `false`, lo correcto.
- El caso 5 con `new int[][]{{}}` lanzo `ArrayIndexOutOfBoundsException`. Eso crea
**una** arista vacia. Para cero aristas es `new int[][]{}`.


## Complejidad

Sea `V` el numero de nodos y `E` el numero de aristas.

**Tiempo: O(V + E)**.

- Crear las `n` listas vacias: O(V).
- Cargar las aristas: O(E).
- DFS: cada nodo se "entra" una sola vez (porque pasa a `1` y luego a `2`, y solo se 
entra si esta en `0`) y cada arista se mira una sola vez. O(V + E).

Total: V + E + (V + E) = 2V + 2E. Big O descarta el 2, asi que queda O(V + E). 
Se escribe una suma porque V y E crecen de forma independiente.

**Espacio: O(V + E).**

- `estado`: un numero por nodo, O(V).
- `grafo`: V listas mas una entrada por arista, O(V + E). Es la pieza dominante.
- Pila de recursion: en una cadena `0→1→2→...` hay hasta V llamadas esperando, O(V)
en el peor caso.

Limitacion real: en una cadena de 100,000 nodos, el DFS recursivo puede terminar en `StackOverflowError`
en Java.
La alternativa es un DFS iterativo con pila explicita, o BFS con el algoritmo de Kahn (grado de entrada).

## Conceptos nuevos aprendidos (para reutilizar)

- **Grafo dirigido vs. "ya lo visite":** en grafos dirigidos, volver a un nodo ya visitado
no siempre es un ciclo.
Hay que distinguir "en proceso" de "terminado".
Esto es distinto de Number of Islands, donde "visitado" bastaba.

- **Lista de adyacencia:** `List<List<Integer>>`, una lista de vecinos por nodo.
Evita recorrer toda la lista de aristas cada vez que se quiere saber a donde puede ir un nodo.

- **Metodo de `List`:**

| Método | Qué hace | Ejemplo |
  |---|---|---|
  | `add(x)` | agrega `x` al final | `grafo.add(new ArrayList<>())` |
  | `get(i)` | devuelve el elemento en la posición `i` | `grafo.get(1)` |
  | `size()` | cantidad de elementos | `grafo.size()` |
  | `isEmpty()` | `true` si no tiene elementos | `grafo.get(1).isEmpty()` |


`get(i)` no existe en `Queue`, donde se usan `offer`, `poll` y `peek`.

- **`for` mejorado:** `for (int vecino : grafo.get(nodo))` recorre cada elemento sin
manejar un indice.
    `for (int[] arista : aristas)` recorre un arreglo de arreglos.

- **`for` vs `while`:** `for` cuando se conoce de antemano cuantas vueltas son (aqui, `n`).
    `while` cuando se repite hasta que algo cambie (Rate Limites: sacar expirados).

- **Recursion en Java:** una funcion se llama a si misma por su nombre, sin nada tipo
    `self.` de python.
    El `true` encontrado mas adentro debe **propagarse hacia arriba** con  `return true`,
    o el ciclo se pierde.
- **Instrucciones sueltas:** en Java, `for`, `if` y llamadas como `grafo.add(...)` solo
    pueden vivir dentro de un metodo, no a nivel de clase.

- **Orden de los metodos:** en una clase no importa (el compilador lee la clase completa).
    Dentro de un metodo, las variables locales si deben declararse antes de usarse.

- **Atributos `static` asignados dentro del metodo:** se declaran arriba sin valor y se
crean dentro de `hayCiclo` cuando se conoce `n`.
    Dentro del metodo ya no se repite el tipo.

- **Arreglo de arreglos en Java:** `new int[][]{{0,1},{1,2}}` para varias aristas y 
    `new int[][]{}` para ninguna.

- **`import java.util.*;` es necesario para `List` y `ArrayList`.

- **Encadenar llamadas:** `grafo.get(arista[0]).add(arista[1])` primero cierra `get(...)`
y luego se le pide `add` a la lista que devuelve.

## Errores cometidos en el camino

1. Pensar que dos estados (visitado / no visitado) bastaban para detectar ciclos.
2. Proponer `TreeNode` pare representar el grafo, cuando un nodo de grafo puede tener
cualquier cantidad de vecinos y ciclos.
3. Escribir `int[] estado = new int[n]` sin el `;`.
4. `grafo.add(i)` y `grafo.add(estado)` : agregar tipos incorrectas a una `List<List<Integer>>`,
que espera listas.
5. Dejar numeros fijos (`grafo.get(0).add(1)`) en vez de usar `arista[0]` y `arista[1]`.
6. Parentesis mal cerrado: `grafo.get(arista[0].add(arista[1]))`, que le pide `add` a un `int`.
7. Escribir `estado[nodo] == 1` en vez de `estado[vecino] == 1`: miraba el nodo actual, que siempre esta en `1`.
8. Intentar llamar a `tieneCiclo(nodo)` en vez de `tieneCiclo(vecino)`, sin avanzar al siguiente nodo.
9. Proponer `estado[nodo] = 1` tambien al salir, en vez de `2`, lo que genera ciclos falsos.
10. Usar `estado[i] == 2` en el `for` final, en vez de `== 0` : nunca se exploraba nada.
11. Dejar `for` sueltos fuera de cualquier metodo, y usar `n`, `aristas` y `nodo` donde no existian.
12. Olvidar `import java.util.*;`.
13. Caso 5 con `new int[][]{{}}` (una arista vacia) en vez de `new int[][]{}` (cero aristas).
14. Texto `esperado=true` mal copiado en el caso 3.

## Leccion general

En un grafo dirigido, "ya lo visite", no basta: importa **cuando** se llega al nodo.
Si esta en proceso, el camino se cerro sobre si mismo (ciclo).
Si ya termino, es solo un destino compartido.
El estado tiene que reflejar el ciclo de vida del nodo (nuevo, en proceso, terminado) y no solo si se toco o no.

Tambien quedo la idea de donde debe vivir el estado:
construirlo dentro del metodo que recibe los datos, para que cada llamada empiece limpia.

En seguridad aplica directo.
Una validacion de permisos que no detecte ciclos en cadenas de roles puede entrar en bucle
infinito, o peor, aprobar una escalada de privilegios que nadie diseño.

## Reflexion del proceso

Al terminar este problema comete que me senti mas perdido y que lo hice de forma mas
automatica, sin entender del todo como funcionaba cada pieza. 
Lo anoto como parte del proceso, no como un fallo.

Posibles causas: 

- Muchas piezas nuevas juntas: recursion en grafos, tres estados, lista de adyacencia, atributos `static`, armado de la clase.
- Los pasos guiados fueron muy pequeños, y es posible contestar cada uno sin ver el mapa completo.
- No se hizo una traza completa propia del recorrido antes de pasar al codigo.

Que probar en el siguiente problema:

- Escribir primero el pseudocodigo completo por cuenta propia (como en IPv6), antes de las preguntas.
- Hacer una traza escrita a mano de un grafo antes de tocar Java.
- Dar la explicacion general de la idea en el planteamiento y reservar las preguntas para las decisiones.
- **Ejercicio de reconstruccion:** volver a escribir `hayCiclo` y `tieneCiclo` desde cero, sin 
mirar esta nota, y comparar. Lo que no salga sin ayuda es lo que realmente falta fijar.
