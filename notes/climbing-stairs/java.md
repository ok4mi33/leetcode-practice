# Climbing Stairs - Java (DP)

## Problema
Una escalera de n escalones, se puede subir de a 1 o 2 escalones por vez. ¿De cuantas
formas distintas se puede llegar arriba?

## Enfoque (recursion + memoizacion, igual que Fibonacci)
Intuicion: pensar en el ULTIMO paso dado para llegar al escalon n. 
Solo hay dos posibilidades: vino del escalon n-1 (dando un ultimo paso de 1) o vino
del escalon n-2 (dando un ultimo paso de 2).
Estas dos formas de llegar NUNCA se solapan entre si (se verifico a mano con n=3: 
los grupos {(1,1,1),(2,1)} y {(1,2)} no comparten ningun elemento), asi que el total
se obtiene sumando ambos casos:

    climbStairs(n) = climbStairs(n-1) + climbStairs(n-2)

Formula matematicamente identica a Fibonacci - mismo patron de recurrencia, aplicado
a "contar caminos" en vez de una secuencia numerica abstracta.

Casos base: climbStairs(1) = 1 (una sola forma), climbStairs(2) = 2
(dos formas: 1+1 o 2).

```java
static HashMap<Integer, Integer> calculo = new HashMap<>();

public static int climbStairs(int n) {
    if (calculo.containsKey(n)) {
        return calculo.get(n);
    }
    if (n == 1) return 1;
    if (n == 2) return 2;
    int resultado = climbStairs(n-1) + climbStairs(n-2);
    calculo.put(n, resultado);
    return resultado;
}
```

- Tiempo: O(n) - con memoizacion, cada n se calcula una sola vez
- Espacio: O(n) - HashMap mas pila de llamadas recursivas

## Sin memoizacion: mismo problema que Fibonacci
Sin el HashMpa, esta recursion seria O(2^n) — subproblemas
como climbStairs(3) se recalcularian multiples veces en ramas distintas del arbol
de llamadas, exactamente el mismo problema que Fibonacci sin memoizar.

## Orden critico: verificar ANTES de calcular
Error importante detectado en el camino: calcular la formula recursiva ANTES de revisar
el mapa y los casos base causa que la funcion se llame a si misma sin ningun limite 
(nunca llega a n=1 o n=2 para detenerse). El orden correcto SIEMPRE es:
1. Revisar si ya esta en el mapa (ahorra trabajo)
2. Revisar casos base (valores que no necesitan calculo)
3. Solo si ninguno aplica, calcular con la formula recursiva
4. Guardar el resultado en el mapa
5. Devolver el resultado

## Errores que cometi en el camino
- HashMap<Integer> con un solo tipo, en vez de HashMap<Integer, Integer> (clave y valor)
- HashMap sin static, rompiendo el acceso desde metodos static.
- return n en vez de return calculo.get(n) al verificar el mapa - confundir la clave
que se busca con el valor que se quiere recuperar.
- calculo.put(n) con un solo argumento, faltando el valor a guardar.
- El calculo recursivo (climbStairs(n-1)) + climbStairs(n-2)) escrito ANTES de los casos
base, causando recursion infinita.
- static ubicado en el medio de la declaracion en vez de al principio (static va antes
del tipo, igual que en las firmas de metodo: static TIPO nombre).

## Leccion general
Primer problema formal de Programacion Dinamica (DP), aunque conceptualmente ya se
habia visto la tecnica completa en Fibonacci con Memoizacion. La clave de reconocer un
problema de DP: pensar en el "ultimo paso" o "ultima decision" tomada, e identificar de
cuantas formas distintas y NO SOLAPADAS se pudo haber llegado al estado actual desde
estados mas chicos ya resueltos. Muchos problemas de DP distintos en apariencia terminan
reduciendose a la misma estructura matematica de recurrencia (aca, identica a Fibonacci)
una vez identificados los subproblemas correctos.
