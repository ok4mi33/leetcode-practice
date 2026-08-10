# Valid Palindrome - Java

## Problema
Dado un string, determinar si es palindromo considerando solo caracteres alfanumericos
e ignorando mayusculas/minusculas.

## Enfoque (Two Pointers)
Primero limpio el string: lo paso a minusculas y elimino todo caracter que no sea
letra o numero, usando regex.

Despues, dos punteros (izquierda en 0, derecha en length()-1) que se acercan al 
centro en cada vuelta de un while. Si en algun punto el caracter en izquierda es distinto
al caracter en derecha, no es palindromo (return false de inmediato). Si los punteros
se cruzan sin encontrar ninguna diferencia (izquierda deja de ser menor que derecha),
es palindromo (return true).

- Tiempo: O(n) - la limpieza del string es O(n), y los punteros recorren el string 
una sola vez entre ambos (se acercan al centro, no lo recorren dos veces)

- Espacio: O(n) - toLowerCase() y replaceAll() crean strings nuevos porque los Strings
son inmutables en Java, no modifican el original

## Sintaxis nueva en este problema
- s.toLowerCase() - pasa todo a minusculas, devuelve un string NUEVO
- s.replaceAll(regex, reemplazo) - reemplaza todo lo que matchea el patron regex.
Para no "alfanumerico": "[^a-zA-Z0-9]" reemplazo por ""
- while (condicion) {...} - se repite mientras la condicion sea true, se detiene solo
cuando deja de cumplirse (no hace falta break si los valores dentro del loop van
cambiando hacia que la condicion se vuelva false)

## String inmutables - concepto clave
s.toLowerCase() NO modifica el string original, devuelve uno nuevo.
Si no guarda el resultado (s = s.toLowerCase()), el cambio se pierde. Es distinto a 
modificar un valor "en el lugar" comos e hace con un HashMap (.put() si cambia el
contenido del mapa mismo).

## Direccion de los punteros
izquierda arranca en 0 y AUMENTA en cada vuelta (izquierda++).
derecha arranca en length()-1 y DISMINUYE en cada vuelta (derecha--).
Ambos se acercan al centro desde extremos opuestos. Confundir la direccion (por ejemplo,
hacer izquierda-- cuando arranca en 0) saca el indice fuera de los limites del string.

## Errores que cometi en el camino.
- Encadene metodos mal: escribi s.toLowerCase().s.replaceAll(...) agregando "s" de mas
en medio de la cadena, cuando el resultado de toLowerCase() ya se puede seguir 
encadenando directo con un punto.
- Use .charAt() sobre las variables de indice (izquierda.charAt()) en vez de sobre el
string (s.charAt(izquierda)).
- Al principio no guarde el resultado toLowerCase()/replaceAll() de vuelta en una 
variable, olvidando que los String son inmutables.


## Leccion general
Primer problema resulto con Two Pointers en vez de estructura hash.
Logra O(n) en tiempo SIN necesitar espacio extra de una estructura de datos (a 
diferencia de Two Sum, Contains Duplicate, Valid Anagram, que usaban HashMap/HashSet).
Cuando el problema es sobre arrays o strings donde se puede comparar desde los extremos
hacia el centro, pensar en Two Pointers antes que en hashing - suele ser mas eficiente
en espacio.
