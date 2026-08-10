

public class Valid_Palindrome{

  public static boolean esPalindrome(String s){

  //limpio el string (quito caracteres no alfanumericos, paso todo a minusculas)

     s = s.toLowerCase().replaceAll("[^a-zA-Z0-9]", ""); // elimina todo lo que no sea letra y lo pone en minuscula


    // izquierda = 0
    int izquierda = 0;


    // derecha = length() - 1 (del string limpio)
    int derecha = s.length() - 1; 


    // mientras izquierda sea menor que derecha:
    while (izquierda < derecha) {
      // codigo que se repite mientras la condicion sea true
     
        // si el caracter en izquierda es distinto al caracter en derecha:
        if (s.charAt(izquierda) != s.charAt(derecha)) {

          return false;
        }
        izquierda++;
        derecha--;
      }
    return true;




    }

  public static void main(String[] args) {

    String s = "race a car";

    System.out.println(esPalindrome(s));
  }
}


     
