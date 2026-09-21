

public class ValidarIPs {

  public static boolean esIPv4Valida(String ip) {
    

    String[] partes = ip.split("\\.");

    if(partes.length != 4){
      return false;
    }

    for(int i = 0; i < partes.length; i++){

      String parte = partes[i];

      if(parte.length() > 1 && parte.charAt(0) == '0'){
        return false;
      }
      try {
        int numero = Integer.parseInt(parte);

        if (numero > 255 || numero < 0) {
          return false;
      
        } 

       } catch (NumberFormatException e) {
         System.out.println("Parte invalida (no numerica): " + parte);
         return false;

       }
    }

      return true;
  }
    

  

  public static void main(String[] args) {

    boolean resultado1 = esIPv4Valida("192.168.1.1");
    System.out.println("Caso 1: esperado=true, obtenido=" + resultado1);
    
    boolean resultado2 = esIPv4Valida("192.168.01.1");
    System.out.println("Caso 2: esperado=false, obtenido=" + resultado2);
    
    boolean resultado3 = esIPv4Valida("256.1.1.1");
    System.out.println("Caso 3: esperado=false, obtenido=" + resultado3);
    
    boolean resultado4 = esIPv4Valida("192.168.1");
    System.out.println("Caso 4: esperado=false, obtenido=" + resultado4);
    
    boolean resultado5 = esIPv4Valida("192.168.1.abc");
    System.out.println("Caso 5: esperado=false, obtenido=" + resultado5);
    
    boolean resultado6 = esIPv4Valida("0.0.0.0");
    System.out.println("Caso 6: esperado=true, obtenido=" + resultado6);
    
    boolean resultado7 = esIPv4Valida("255.255.255.255");
    System.out.println("Caso 7: esperado=true, obtenido=" + resultado7);
  }
}
