# Best time to Buy and Sell Stock - Java

## Problema
Dado un array de precios (indice = dia), encontrar la maxima ganancia posible
comprando en un dia y vendiendo en un dia posterior. Si no hay ganancia posible,
devolver 0.

## Enfoque (Sliding Window / un solo recorrido)
Llevo registro de dos cosas mientras recorro el array una sola vez:
el precio minimo visto hasta el momento (mejor punto de compra posible hasta ahora),
y la maxima ganancia encontrada hasta el momento.

En cada dia (empezando desde el segundo, i=1, porque el primero se usa para 
inicializar precioMinimo): calculo la ganancia si vendiera hoy (prices[i] - precioMinimo).
Si es mejor que la maxima ganancia registrada, actualizo. Por separado (no exclusion
mutua, dos if independientes), si el precio de hoy es menor al minimo registrado,
actualizo el minimo.

- Tiempo: O(n) - un solo recorrido, trabajo constante por paso
- Espacio: O(1) - solo dos variables sueltas, sin estructura extra

## La razon matematica detras de "guardar el minimo"
ganancia = precio_venta - precio_compra. Pra maximizar la resta con precio_venta fijo,
precio_compra debe ser los mas chico posible. Por eso conviene llevar el minimo visto
hasta el momento, no comparar contra todos los precios anteriores uno por uno.

## Por que dos if separados y no if/else
Las dos verificaciones (¿mejora la ganancia? / ¿es nuevo minimo?) son independientes
entre si. Un mismo dia podria cumplir ambas, ninguna, o solo una - no son mutuamente
excluyentes.

## Fuerza bruta (para comparar)
Dos loops anidados probando todas las combinaciones (i=dia de compra, j=dia de venta
desde i+1). O(n^2). La razon matematica de por que da O(n^2): el loop externo hace
n pasos, y por cada uno el loop interno hace en promedio otros n pasos - el total
se comporta como nxn aunque el conteo exacto sea una serie decreciente
(n-1 + n-2 + ... + 1).

## Errores que cometi en el camino
- El return estaba dentro del for en vez de despues: cortada en la primera vuelta
del loop en vez de recorres el array completo. 
- El loop arrancaba en i=0 en vez de i=1, aunque precioMinimo ya usaba prices[0] como 
valor inicial (comparacion redundante).
- Nombre del parametro (dias) no coincidia con las variables usadas dentro del metodo
(prices) - Java no reconocia prices.
- Variables declaradas sin su tipo (precioMinimo, maxGanancia sin el int inicial).

## Leccion general
Mismo principio de fondo que Two Pointers en Valid Palindrome: 
Un solo recorrido en vez de loops anidados, sin necesitar estructura hash extra (O(1)
en espacio). La clave para optimizar fue encontrar la observacion matematica correcta
(el minimo visto hasta ahora es siempre el mejor punto de compra), no una tecnica de 
estructura de datos.
