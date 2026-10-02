# Validar Direccion IPv6

> **Continuacion del problema de validacion de IPs** (LeetCode #468). 
Con esta parte se completa el enunciado original: ya se habia resulto **IPv4** en una
nota separada.

> Queda pendiente como problema futuro separado: **validacion de rangos CIDR** (ej.
`192.168.1.0/24`), con conexion directa a AWS Security Groups / firewall rules.

## Problema

Dado un string `queryIP` , determinar si es una direccion IPv6 valida.

Reglas de IPv6 valida:

- Formato `x1:x2:x3:x4:x5:x6:x7:8`, exactamente 8 partes separadas por `:`.
- Cada `xi` es un string hexadecimal de 1 a 4 caracteres.
- Digitos hexadecimales validos: `0-9`, `a-f`, `A-F` (mayúsculas y minúsculas permitidas).
- A diferencia de IPv4, **si se permiten ceros a la izquierda** (`"0000"`, `"01"` son válidos).

**Conexion con seguridad/AWS:**
Las VPCs de AWS soportan IPv6, y las reglas de Security Groups pueden incluir rangos IPv6.
Un parser de headers o de logs que solo contempla IPv4 puede pasar por alto trafico
malicioso disfrazado en direcciones IPv6.

## Pseudocodigo (planteado antes de escribir Java)

función esIPv6Valida(ip):
partes = separar ip por ":"
si cantidad de partes != 8:
devolver false
para cada parte en partes:
si longitud de parte < 1 o > 4:
devolver false
para cada caracter c en parte:
si c no es hexadecimal válido:
devolver false
devolver true

Plantea el pseudocodigo completo antes de tocar Java ayudo a no perderse en detalles
de sintaxis mientras se construia la logica general - a diferencia de IPv4, donde
se fue armando linea por linea desde el inicio.

## Enfoque

1. Separar el string por `:` con `split(":")`.
    A diferencia del punto (`.`) en IPv4, los dos puntos (`:`) **no son un caracter
    especial en regex**, asi que no necesitan escaparse.

2. Verificar que el array tenga exactamente 8 elementos - si no, `return false`.

3. Recorrer las 8 partes con un `for` externo. Para cada parte:
    - Verificar longitud: `parte.length() < 1 || parte.length() > 4`  → si se cumple, `return false`.
    (Nota: la condicion correcta compara contra `1`, no contra `0` - la longitud de un string
    nunca es negativa, asi que comparar contra `0` habria generado una condicion que nunca
    se cumple, codigo muerto).
    - Recorrer los **caracteres** de esa parte con un `for` interno (`j` desde `0` hasta
    `parte.length()`).
    Para cada caracter `c = parte.charAt(j)`, verificar si es hexadecimal valido combinado
    tres comparaciones con `||`:
    `(c >='0' && <= '9') || (c >= 'a' && c <= 'f') || (c >= 'A' && c <= 'F')`.
    Si el caracter no cumple ninguna (`!esHex`), `return false`.

4. Si ambos `for` completan sin disparar ningun `return false`, entonces `return true`.

```java
public static boolean esIPv6Valida(String ip) {

    String[] partes = ip.split(":");

    if (partes.length != 8) {
        return false;
    }

    for (int i = 0; i < partes.length; i++) {

        String parte = partes[i];

        if (parte.length() < 1 || parte.length() > 4) {
            return false;
        }

        for (int j = 0; j < parte.length(); j++) {

            char c = parte.charAt(j);

            boolean esHex = (c >= '0' && c <= '9') || (c >= 'a' && c <= 'f') || (c >= 'A' && c <= 'F');

            if (!esHex) {
                return false;
            }
        }
    }

    return true;
}
```

## Casos de prueba verificados

| Caso | Input | Motivo | Esperado | Obtenido |
|---|---|---|---|---|
| 1. Válida (mayúsc./minúsc. mezcladas) | `"2001:0db8:85a3:0:0:8A2E:0370:7334"` | — | true | true ✅ |
| 2. Menos de 8 partes | `"2001:0db8:85a3:0:0:8A2E:0370"` | solo 7 partes | false | false ✅ |
| 3. Parte con más de 4 caracteres | `"20011:0db8:85a3:0:0:8A2E:0370:7334"` | primera parte tiene 5 | false | false ✅ |
| 4. Carácter no hexadecimal | `"2001:0db8:85a3:0:0:8A2E:0370:733g"` | `'g'` no es hex válido | false | false ✅ |
| 5. Longitud mínima (borde) | `"2001:db8:85a3:0:0:8A2E:370:7334"` | partes de 1 y 3 caracteres, no solo 4 | true | true ✅ |

El caso 5 es el más importante: verifica que la regla real ("entre 1 y 4 caracteres") esté bien implementada, y no una versión más estricta por error (como exigir exactamente 4).

## Complejidad

**Tiempo:** O(1).
El `for` externo siempre recorre 8 partes (fijo), y el `for` interno como maximo 4
caracteres por parte (fijo)- maximo 32 comparaciones en total, sin importar el input.

**Espacio:** O(1).
El array `partes` siempre tiene como maximo 8 elementos de maximo 4 caracteres cada uno.

Nota conceptual: un loop anidado no es automaticamente "lento". Lo que determina la notacion
de Big O es si el trabajo **crece cuando el inpu crece**, no la cantidad absoluta de 
operaciones. Aqui, 8x4 es una cantidad fija definida por la propia estructura de una 
IPv6 - nunca cambia sin importar que se le pase como `queryIP`.

## Conceptos nuevos aprendidos (para reutilizar en futuros problemas)

- **Caracteres especiales en regex:** simbolos como  `.`, `*`, `+`, `?`, `(`, `)`, `[`, `]`, `\`, `^`, `$`, `|`
tienen significado propio dentro de una expresion regular y deben escaparse (`\\`) para
usarse como literales. Los dos puntos (`:`) **no** son especiales, asi que `split(":")` no
necesita escape, a diferencia de `split("\\.")` en IPv4.

- **Comparacion de `char` con operadores relacionales (`<`, `<=`, `>`, `>=`):** en Java,
los caracteres se pueden comparar como si fueran numeros, aprovechando su valor numerico
subyacente (codigo de caracter). Esto permite expresar rangos como `c >= '0' && c <= '9'`
para preguntar "¿es un digito?".

- **Condicion de "fuera de rango" con dos limites (`||`, no `&&`):** cuando algo es invalido
por estar *por debajo* de un minimo *o* *por encima* de un maximo, son dos formas
independientes de fallar - se combinan con `||`. Combinarlas con `&&` exigiria que 
ambas ocurrieran a la vez, lo cual nunca pasa (un valor no puede ser simultaneamente
menor que 1 y mayor que 4).

- **Pseudocodigo antes de codigo real:** para logica con varias piezas nuevas en 
juego (loops anidados, comparaciones de caracteres, negaciones de expresiones compuestas),
plantear primero el esqueleto en pseudocodigo ayuda a fijar el flujo completo antes
de perderse en detalles de sintaxis de Java.

## Errores cometidos en el camino

1. Confundir el `for` externo usando la variable de indice como si fuera el string
de la parte (`for (int parte = 0; ...) ` en vez de separar indice `i` y variable
`parte`).
2. Usar `partes.length()` con parentesis (correcto solo para `String`) en vez de 
`partes.length` (correcto para arrays).
3. Escribir una comparacion incompleta en el `||` (`parte < 1 || > 4`), sin repetir
el metodo/variable en cada lado de la comparacion.
4. Mezclar `c.charAt(j)` como limite del `for` interno, cuando `c` todavia no existia
y el limite real debia ser `parte.length()`.
5. Declarar `c` como `String` en vez de `char`, cuando `charAt()` siempre devuelve
un solo caracter (`char`), no un texto.
6. Comparar `c != esHex` (comparar un `char` contra un `boolean`, algo sin sentido)
en vez de usar `esHex` directamente como condicion negada (`!esHex`).
7. Usar `parte.length(i)` con un parametro dentro de `length()`, cuando ese metodo
nunca recibe argumentos.
8. Olvidar el punto y coma (`;`) al declarar `char c = parte.charAt(j)`.

## Leccion general

Cuando la logica de un problema tiene varias piezas nuevas entrelazadas (loops anidados,
un nuevo tipo de dato como `char`, comparaciones poco habituales), plantear el 
pseudocodigo completo primero - antes de escribir cualquier line de Java - ayuda 
a mantener claro el flujo general y evita mezclar indices, variables y tipos de datos
que pertenecen a niveles distintos del loop (por ejemplo, confundir el indice del 
loop externo `i` con el del loop interno `j`, o el array completo `partes` con un
elemento individual `parte`).

Tambien quedo reforzada una leccion de Big O: la complejidad no se mide por "cuantas 
operaciones hay en total" en un sentido absoluto, sino por si esa cantidad de 
operaciones **crece con el tamaño de la entrada**. Un loop anidado que siempre
opera sobre una cantidad fija de datos (8 partes x 4 caracteres, definido por 
el propio formato de una IPv6) sigue siendo O(1), aunque "se sienta" como mas trabajo
que una validacion simple de una sola pasada.
