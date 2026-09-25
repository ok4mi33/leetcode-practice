

public class ValidarIPsV6 {

  public static boolean esIPv6Valida(String ip) {

    // partes = separar ip por ":"
    String[] partes = ip.split(":");

    // si cantidad de partes != 8:
    if(partes.length != 8) {
      return false;
    }

    // para cada parte en partes
    for(int i = 0; i < partes.length; i++) {
      
      String parte = partes[i];

      // si longitud de parte < 1 o > 4:
      if(parte.length() < 1 || parte.length() > 4){
        return false;
      }

      // para cada caracter c en parte:
      for(int j = 0; j < parte.length(); j++){

        char c = parte.charAt(j);

        boolean esHex = (c >= '0' && c <= '9') || (c >= 'a' && c <= 'f') || (c >= 'A' && c <= 'F');
        
        if(!esHex) {

          return false;
        }


      }

    }
    return true;



  }

  public static void main(String[] args) {

    boolean resultado1 = esIPv6Valida("2001:0db8:85a3:0:0:8A2E:0370:7334");
    System.out.println("Caso 1: esperado=true, obtenido=" + resultado1);
    
    boolean resultado2 = esIPv6Valida("2001:0db8:85a3:0:0:8A2E:0370");
    System.out.println("Caso 2: esperado=false, obtenido=" + resultado2);
    
    boolean resultado3 = esIPv6Valida("20011:0db8:85a3:0:0:8A2E:0370:7334");
    System.out.println("Caso 3: esperado=false, obtenido=" + resultado3);
    
    boolean resultado4 = esIPv6Valida("2001:0db8:85a3:0:0:8A2E:0370:733g");
    System.out.println("Caso 4: esperado=false, obtenido=" + resultado4);
    
    boolean resultado5 = esIPv6Valida("2001:db8:85a3:0:0:8A2E:370:7334");
    System.out.println("Caso 5: esperado=true, obtenido=" + resultado5);
    





  }
}
