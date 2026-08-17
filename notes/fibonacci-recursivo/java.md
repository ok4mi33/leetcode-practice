# Fibonacci Recursivo - Java

## Problema 
Calcular el n-esimo numero de Fibonacci: fib(0) = 0, fib(1) = 1,
fib(n) = fib(n-1) + fib(n-2) para n > 1.

No es un problema de LeetCode especifico, sino el punto de entrada clasico para
entender recursion antes de pasar a arboles y grafos.

## Enfoque (recursion directa, sin optimizar)
Caso base: si n es 0 o 1, devolver n directamente (son los valores que ya se conocen
sin necesitar mas calculo).

Caso recursivo: para cualquier otro n, la funcion se llama a si misma dos veces,
coon n-1, y n-2, y suma los resultados. Cada llamada recursiva resuelve una version
mas chcia del mismo problema, hasta que ambas ramas llegan a un caso base.

```java
public static int fib(int n) {
    if (n == 0 || n == 1) {
        return n;
    }
    return fib(n-1) + fib(n-2);
}
```

## Complejidad MALA, pendiente de optimizar 
- Tiempo: O(2^n) — exponencial
- Espacio: O(n) - por la pila de llamadas recursivas (profundidad maxima de la 
recursion)

### Por que O(2^n)
Cada llamada a fib(n) genera DOS llamadas nuevas (fib(n-1) y fib(n-2)), y cada una de
esas genera otras dos, y asi sucesivamente hasta llegar a los casos base. Esto arma
un arbol de llamadas donde la cantidad de nodos crece exponencialmente con n.

Ejemplo con fib(4): fib(2) se calcula DOS VECES por separado (una vez dentro de la
rama de fib(3), y otra vez de forma independiente como el segundo hijo de fib(4)) -
nunca se reutiliza un resultado ya calculado. A medida que n crece, esta repeticion
de trabajo se multiplica muchisimo, dando la explosion exponencial.

### Pendiente para una proxima sesion
Existe una tecnica llama MEMOIZACION que evita esta problema: guardar (cachear) los 
resultado de fib(n) ya calculados en una estructura (por ejemplo HashMap), asi si
se necesita el mismo valor de nuevo, se recupera en O(1) en vez de recalcularlo desde
cero. Con memoizacion, Fibonacci pasa de O(2^n) a O(n).

## Sintaxis nueva
- Una funcion puede llamarse a si misma (recursion) - no hace falta ningun loop 
for/while para esto.
- || para "o" logico dentro de una condicion (ya se habia usado && para "y" en Valid
Anagram)
- Toda funcion recursiva necesita: (1) uno o mas CASOS BASE que no dependen de otra 
llamada recursiva, y (2) un CASO RECURSIVO que se acerca al caso base en cada llamada
(aqui, n-1 y n-2 son cada vez mas chicos, acercandose a 0 o 1)

## Errores que cometi en el camino
- Use = (asignacion) en vez de == (comparacion) dentro del if del caso base.
- Intente return n == n y luego return ture, en vez de simplemente return n - confundiendo
"devolver un valor" con "evaluar una condicion booleana".
- Nombre de clase en minuscula, y variable n en main sin declarar su tipo (int).

## Leccion general
Primer problema resuelto con recursion en vez de loops. La logica es distina: en vez
de repetir un bloque de codigo n veces con un for/while, la funcion se llama a si misma
con un input mas chico cada vez, hasta llegar a un caso base. Pero esta primera
version sin optimizar muestra un riesgo real de la recursion: si no se evita 
recalcular lo mismo (con memoizacion), la complejidad puede dispararse a exponencial,
mucho peor que cualquier O(n^2) visto hasta ahora.
