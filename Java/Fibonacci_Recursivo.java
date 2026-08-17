
public class Fibonacci_Recursivo{

  public static int fib(int n) {

    // caso base: si n es tan chico que ya conoces la respuesta directamente
    if ( n == 0 || n == 1 ) {
      return n;
    }

    // caso recursivo: la funcion se llama a si misma con valores mas chicos
    return fib(n-1) + fib(n-2);
  }


  public static void main(String[] args) {

    int n = 4;

    int resultado = fib(n);

    System.out.println(resultado);
  
  

  }

}

