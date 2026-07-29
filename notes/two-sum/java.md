#Two Sum - Java

## Problema 

Dado a un array y un targe, encontrar los indices de dos numeros cuya suma sea el target.
No se puede usar el mismo elemento dos veces.

## Mi primer enfoque (fuerza bruta)

Dos loops anidados: por cada numero, recorro el resto del array buscando su complemento.

- Tiempo: O(n2)
- Espacio: O(1)

### Errores que cometi en el camino
- Use dos arrays en vez de uno solo (confundi el enunciado al principio).
- El loop interno miraba hacia atras (`j < i`) en vez de recorrer todo el array, asi que no encontraba pares donde el complemento estaba mas adelante.
- Al arreglar eso con`j < nums.length` empecé a encontrar el mismo par dos veces (en ambos órdenes) por números repetidos en el array.
Se resolvió con `j = i + 1`, asi cada para se compara una sola vez.

## Enfoque óptimo (HashMap)
Un solo loop. Por cada número, calculo el complemento (target-número).
Si el complemento ya está guardado en el HashMap, encontré la respuesta.
Si no, guardo el número actual con su índice y sigo.

- Tiempo: O(n) - un solo recorrido, cada operación de HashMap es O(1) promedio.
- Espacio: O(n) - por el HashMap

## Notas especificas de Java

- `HashMap<Integer, Integer>` — clave = número, valor = índice
- `.containsKey(x)` para preguntar si ya existe
- `.get(x)` para recuperar el índice guardado
- `.put(clave, valor)` para guardar

## Leccion general
Cuando hay una busqueda repetida dentro de un loop (algo tipo "ya vi esto antes?"), pensar si un HashMap puede convertir esa busqueda de O(n) a O(1), a cambio de usar espacio extra.

