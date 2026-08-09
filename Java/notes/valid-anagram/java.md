# Valid Anagram - Java

## Problema

Dados dos strings s y t, devolver true si t es un anagrama de s (mismas letras, misma
cantidad de cada una, en cualquier orden).

## Enfoque (HashMap con contador)
Primero un atajo: si las longitudes de s y t son distintas, no pueden ser anagramas
, se corta de inmediato con return false sin recorrer nada.

Si las longitudes coinciden: recorro s completo, sumando 1 al contador de cada letra
en un HashMap (letra -> cantidad de veces vista). Despues recorro t completo: si una
letra de t no esta en el mapa, ya se sabe que no son anagramas (return false). Si esta,
le resto 1 a su contador.

Si termino de recorrer t sin cortar con false, son anagramas (return true). No hace
falta verificar que todos los contadores terminen en 0 explicitamente, porque la 
verificacion de longitud al inicio ya garantiza que la cantidad total de letras es
igual en ambos strings.

- Tiempo: O(n) - dos recorridos separados (no anidados), cada uno lineal.
containsKey/get/put son O(1) promedio
- Espacio: O(n) - el HashMap guarda hasta n letras distintas

## Sintaxis especifica de Strings en Java
- s.length() -- con parentesis (a diferencia de nums.length en arrays, que no lleva
parentesis)
- s.charAt(i) -- para acceder al caracter en la posicion i (los strings no usan s[i]
como los arrays)
- HashMap<Character, Integer> -- el tipo envuelto de char es Character, no Char

## Patron sumar/restar en HashMap
Para actualizar un contador existente: leer el valor actual con .get(), sumarle o restarle
1, y volver a guardar con .put() usando la misma clave.
    letras.put(clave, letras.get(clave) + 1);

## Errores que cometi en el camino
- Intente usar una sola variable int como contador general, en vez de un contador
por la letra dentro del HashMap.
- Escribi . charAt() mal como .chartAt() varias veces.
- Compare containsKey() (que da true/false) directamente con un caracter usando != o
==, en vez de usar el resultado como condicion booleana directa.
- Necesite el operador de negacion (!) para invertir una condicion de containsKey(), 
en vez de comparar dos containsKey() entre si.
- El primer intento de logica invirtio el if/else del segundo loop:
cortaba con false cuando la letra SI estaba en el mapa, en vez de cuando NO estaba.

## Leccion general
Tercer problema seguido resuelto con estructura hash, cada uno con un ajuste distinto
segun la necesidad:
- Two Sum: HashMap con indice como valor (necesitaba recuperar una posicion)
- Constains Duplicate: HashSet (Solo necesitaba saber si existia)
- Valid Anagram: HashMap con contador (necesitaba saber cuantas veces)

Cuando un problema pregunta "Ya vi esto?" o "¿Cuantas veces lo vi?", pensar primero
en estructuras hash antes que en loops anidados.


