//importamos hashmap


import java.util.HashMap;

// declaracion: HashMap<TipoDeClave, TipoDeValor> nombre = new HashMap<>();
// .put(clave, valor)
// .containsKey(algo)
// .get(clave)


// llamamos primero la clase

public class ValidAnagram {

  public static boolean esAnagrama(String s,String t) {

   // si la longitud de s es distinta a la longitud de t:
    if(s.length() != t.length()) {
      return false; // ni siquiera hace falta seguir ya se sabe que no son anagramas)
    }

    HashMap<Character, Integer> letras = new HashMap<>();

  

    // se recorre s completo:

    for(int i = 0; i < s.length() ; i++) {

      //si la letra ya esta en el mapa:
    
      if(letras.containsKey(s.charAt(i))) {

        // le sumo 1 a su contador
      
        letras.put(s.charAt(i), letras.get(s.charAt(i)) +1);

        // si no esta:

      } else
        letras.put(s.charAt(i), 1);
    }
  
    // se recorre t completo:

    for(int i = 0; i < t.length(); i++) {

      // si la letra NO esta en el mapa:

      if(!letras.containsKey(t.charAt(i))) {
        return false; // t tiene una letra que s no tiene

      //si esta:

      } else {
      letras.put(t.charAt(i), letras.get(t.charAt(i)) -1);
 
      }
    }
    return true;
  }

  public static void main(String[] args) {

    String s = "rat";
    String t = "car";

    System.out.println(esAnagrama(s, t));
  }
}


