# Binary Search - Java

## Problema
Dado un array de enteros ORDENADO de menor a mayor, y un target, devolver el indice
de target si existe, o -1 si no existe.
Restriccion: debe resolverse en O(log n).

## Enfoque (dos punteros recortando el rango)
izquierda arranca en 0, derecha arranca en el ultimo indice
(nums.length -1). Mientras izquierda <= derecha: calculo el medio rango actual, 
(izquierda + derecha) / 2.

- Si nums[medio] == target: encontrado, return medio.
- Si target > nums[medio]: el target, de existir, esta en la mitad derecha (el array
esta ordenado, todo lo de la izquierda es menor).
Descarto la mitad izquierda completa: izquierda = medio + 1.
- Si target < nums[medio]: simetrico, descarto la mitad derecha:
derecha = medio - 1.

Si el while termina sin encontrar nada (izquierda > derecha, el rango de busqueda se
agoto), return -1.

## Por que O(long n) - el enfoque de esta nota

### La intuicion: el juego de "adivina el numero"
En vez de probar 1, 2, 3, 4... uno por uno (lo que seria O(n)), cada intento va
directo al MEDIO del rango posible restante. Cada respuesta ("mas alto"/"mas bajo")
elimina la MITAD de las posibilidades de una sola vez, no una posibilidad a la vez.

### La cuenta exacta
En cada vuelta del while se descarta la mitad de lo que quedaba del rango:
    n -> n/2 -> n/4 -> n/8 -> ... -> 1

La pregunta clave: ¿cuantas veces se puede dividir n a la mitad antes de llegar a 1?
Esa cantidad de divisiones ES el algoritmo en base 2 de n. Por ejemplo, con n=16:
  16 -> 8 -> 4 -> 2 -> 1   (4 pasos, y log2(16) = 4)

Por eso la complejidad es O(log n): el numero de vueltas del while en el peor caso
es proporcional a log2(n), no a n.

### Por que importa la diferencia con O(n)
Con n = 1,000,000 elementos:
- Busqueda lineal (recorrer uno por uno): hasta 1,000,000 de pasos
- Busqueda binaria: hasta ~20 pasos (2^20 ≈ 1,000,000)

Es la primera tecnica que vi que logra esta escala de mejora - todas las anteriores 
(hashing, two pointers, sliding window) llegaban a O(n), pero nunca por debajo de eso.
O(log n) solo es posible porque el array esta ORDENADO: es la unica razon por la
que se puede descartar la mitad del espacio de busqueda sin revisarlo.

- Tiempo: O(log n)
- Espacio: O(1) - solo tres variables sueltas (izquierda, derecha, medio), sin estructura
extra

## Precondicion importante
Binary search REQUIERE que el array ya este ordenado. Si no lo estuviera, habria que ordenarlo
primero (Arrays.sort() en Java), lo cual cuesta O(n log n) y anularia la ventaja de hacer
la busqueda en O(log n) si solo se va a buscar una vez.

## Errores que cometi en el camino
- La firma del metodo al principio solo recibia nums, faltaba agregar target como
segundo parametro.
- Intente mover los punteros con izquierda += medio (que suma el valor actual de izquierda
mas medio) en vez de izquierda = medio + 1 (que asigna directamente el nuevo limite).
Daban resultados distintos en la mayoria de los casos, aunque coincidieron por casualidad
en la primera traza que probamos.
- En la llamada desde main, olvide pasar al segundo argumento (targe) al llamar al metodo.

## Leccion general
Primer problema resuelto con complejidad O(log n), la mayor vista hasta ahora. La clave
no fue una estructura de datos (como hashing) ni evitar loops anidados (como two pointers/
sliding window), sino aprovechar que el array esta ORDENADO para descartar la mitad del
espacio de busqueda en cada paso, en vez de revisarlo elemento por elemento.

