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

## Enfoque optimo (HashMap) - implementado

Un solo loop. Por cada numero, calculo el complemento con la ecuacion target - nums[i].
Si el complemento ya esta guardado en el HashMap (como clave), encontre la respuesta.
Si no, guardo el numero actual como clave y su indice como valor.

- Tiempo: O(n) - un solo recorrido, containsKey/get/put son O(1) promedio
- Espacio: O(n) - por el HashMap

### De donde sale la variable "complemento"
Es la incognita de una ecuacion simple: num[i] + X = target, 
despejando X = target - nums[i]. El complemento es el numero que necesito haber
visto antes para completar la suma.

### Detalle importante sobre HashMap con valores duplicados
Si el array tiene numeros repetidos, el HashMap reemplaza el indice guardado cada vez
que la misma clave se vuelve a guardar (solo puede haber un valor por clave). Esto
no afecta la correctitud del problema tal como esta definido, porque el enunciado 
garantiza una unica solucioni. Solo seria un problema real si la variante del ejercicio
pidiera encontrar TODOS los pares posibles (ahi se necesitaria guardar una lista de
indices por clave, no un solo indice).

### Errores que cometi en el camino
- Intente declarar el HashMap con nombre de variable (i, j) en vez de tipos de dato
(Integer, Integer) dentro de los <>.
- Le puse "int" antes de HashMap<.....>, cuando el tipo completo ya es HashMap<Integer, Integer>.
- Al principio no separe if/else - el put() quedo dentro del mismo bloque if que el println,
en vez de ser la rama alternativa.
- Confundi que iba dentro de .put() - intente pasar target o solo un argumento, cuando
necesita clave (nums[i] y valor (i).
