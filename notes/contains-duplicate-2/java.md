# Contains Duplicate II - Java

## Problema
Dado un array de enteros nums y un entero k, devolver true si existen indice i y j 
tales que nums[i] == nums[j] y |i - j| <= k.
Variante de Contains Duplicate con restriccion extra de distancia.

## Enfoque (HashMap numero -> indice mas reciente) 
Un solo loop. Por cada numero, si ya esta guardado en el HashMap, calculo la distancia
entre el indice actual y el indice guardado con Math.abs(). Si esa distancia es <= k,
return true de inmediato.

Fuera del if (siempre, haya estado el numero antes o no), actualizo el HashMap con el indice
mas reciente para ese numero.

- Tiempo: O(n) - un solo recorrido, containsKey/get/put son O(1) promedio
- Espacio: O(n) - en el peor caso (sin nignun par valido) el HashMap guarda hasta n
numeros distintos

## Por que siempre se actualiza al indice MAS RECIENTE
Entre dos apariciones consecutivas de un mismo numero, la distancia nunca es mayor
que la distancia entre apariciones separadas. 
Comparar siempre contra la aparecion mas cercana en el tiempo da la mejor oportunidad
posible de encontrar una distancia <= k. Si ni siquiera la comparacion mas favorable
cumple la condicion, ninguna otra combinacion de esas apariciones la va a cumplir 
tampoco. 

## Sintaxis nueva
Math.abs(numero) - valor absoluto en Java (no se usa el simbolo matematico | |,
que no es sintaxis valida)

## Errores que cometi en el camino
- claves.get(i) y claves.put(i, nums[i]) - argumentos invertidos, usando el indice
como si fuera la clave cuando la clave es el numero (nums[i]), no el indice.
- El .put() para guardar/actualizar el indice estaba DENTRO del if(containsKey(...)),
asi que solo se ejecutaba cuando el numero YA existia en el mapa. Nunca se guardaba
la primera aparicion de ningun numero, dejando el HashMap vacio para siempre.
Correcion: mover el .put() fuera del if pero dentro del for, para que se ejecute en
cada vuelta sin importar el resultado del containsKey.
- Intente usar |i - claves.get(i)| (notacion matematica) en vez de Math.abs(), que no
es sintaxis valida en Java.

## Leccion general
Variante directa de un problema ya resuelto (Contains Duplicate), agergando una condicion
extra sobre los VALORES guardados en el HashMap (el indice), no solo sobre su existencia.
Mismo patron base de hashing, pero HashMap en vez de HashSet porque ahora se necesita
recuperar informacion asociada (el indice), no solo preguntar existencia - igual que
en Two Sum.

Leccion tecnica clave: cuando una accion debe ocurrir "siempre, sin importar el resultado
de una condcion", su ubicacion en el codigo debe estar FUERA de esa condicion, no dentro de
una de sus ramas.
