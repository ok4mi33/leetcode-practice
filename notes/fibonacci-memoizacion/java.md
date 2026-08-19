# Fibonacci con Memoizacion - Java

## Problema
Mismo problema que Fibonacci Recursivo (fib(0)=0, fib(1)=1, fib(n)=fib(n-1)+fib(n-2)),
pero optimizando la version recursiva simple que resultaba en O(2^n).

## Enfoque (memoizacion con HashMap)
Se agrega un HashMap<Integer, Integer> a nivel de CLASE (no dentro del metodo fib),
que mape n -> resultado ya calculado. Al ser static y declarado fuera de cualquier
metodo, se crea una sola vez y es compartido por todas las llamadas recursivas.

Dentro de fib(n): primero se pregunta si n ya esta en el mapa (memo.containsKey(n)) -
si esta, se devuelve el valor guardado directamente (memo.get(n)), sin recalcular nada.
Si no esta, se revisa el caso base (n==0 o n==1). Si tampoco es caso base, se calcula
normalmente con la formula recursiva, PERO antes de devolver el resultado, se guarda
en el mapa (memo.put(n, resultado)) para que futuras llamadas con el mismo n lo 
encuentren ya calculado.

- Tiempo: O(n) - cada valor de n se calcula una sola vez en toda la ejecucion; las 
llamadas repetidas se resuelven en O(1) leyendo el mapa
- Espacio: O(n) - por el HashMap (guarda hasta n resultados distintos) mas la pila
llamadas recursivas

## Por que esto arregla el O(2^n) original
En la version sin memoizar, fib(2) (por ejemplo) se recalculaba desde cero cada vez
que alguna rama del arbol de llamadas lo necesitaba, sin ninguna memoria de calculos
anteriores. Con el HashMap compartido, la PRIMERA vez que se calcula fib(2) se guarda;
todas las veces siguientes que cualquier otra rama necesita fib(2), lo encuentra ya
calculado en O(1) en vez de volver a expandir todo el arbol de llamadas para ese valor.

## Variable a nivel de clase vs variable dentro de un metodo
Diferencia clave que motivo esta nota: una variable declarada DENTRO de un metodo
se crea de nuevo cada vez que el metodo se ejecuta, y desaparece cuando termina -
si el HashMap estuviera declarado dentro de fib(), cada llamada recursiva tendria
su propio mapa vacio, sin compartir nada entre si, y la memoizacion no serviria de nada.

Una variable declarada a nivel de CLASE (fuera de cualquier metodo, con static) se 
crea una sola vez y persiste durante toda la ejecucion del programa, compartida 
por todas las llamadas - exactamente lo que se necesita para que la memoria del
cache funcione entre llamadas recursivas distintas.

## Errores que cometi en el camino
- import mal escrito (import.util.HashMap en vez de import java.util.HashMap).
- Intente comparar if(n == memo) - comparando un int contra el HashMap completo en
vez de usar containsKey(n).
- Intente return memo (devolviendo el mapa completo) en vez de return memo.get(n) 
(el valor especifico asociado a la clave n).
- Intente memo(n, resultado) como si memo fuera una funcion, en vez de memo.put(n, resultado)
(memo es una variable/objeto, no un metodo).

## Leccion general
Primera vez usando el patron de MEMOIZACION: mismo patron base de hashing que ya se uso
en problemas anteriores (HashMap clave-valor), pero aplicado a CACHEAR resultados de una
funcion recursiva para evitar recalcular lo mismo multiples veces. Esta tecnica es la 
base de Programacion Dinamica (DP), que se vera mas adelante - DP es, en esencia, recursion
+ memoizacion (o su version iterativa equivalente).

Este problemas cierra el ciclo de recursion basica y deja el terreno preparado para
arboles/grafos (que usan recursion fuertemente) y para DP (que usa memoizacion fuertemente).
