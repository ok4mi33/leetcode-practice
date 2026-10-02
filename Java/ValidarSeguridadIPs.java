

public class ValidarSeguridadIPs {


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
  

  // funcion esCIDRValido(cidr):
  public static boolean esCIDRValido(String cidr) {
  //  partes = separar cidr por "/"
    String[] partes = cidr.split("/");
  //  si cantidad de partes != 2:
    if(partes.length != 2){
      return false;
    }
  //
  //
  // ipParte = partes[0]
      String ipParte = partes[0];
  // prefijoParte = partes[1]
      String prefijoParte = partes[1];
  // si NO esIPv4Valida(ipParte):
      if(!esIPv4Valida(ipParte)){
        return false;
      }
  //  devolver false
  //
  // intentar:
      try {
  //    prefijo = convertir prefijoParte a numero
        int prefijo = Integer.parseInt(prefijoParte);
  //    si prefijo < 0 o prefijo > 32:
        if(prefijo < 0 || prefijo > 32){
          return false;
        }
  //        devolver false
  // si falla la conversion:
      }catch(NumberFormatException e) {
         System.out.println("Parte invalida (no numerica): " + prefijoParte);
         return false;
      }

  //
  // devolver true
    return true;
    
  }

 
  public static void main (String[] args){

    boolean resultado1 = esCIDRValido("192.168.1.0/24");
    System.out.println("Caso 1: esperado=true, obtenido=" + resultado1);
    
    boolean resultado2 = esCIDRValido("192.168.1.0");
    System.out.println("Caso 2: esperado=false, obtenido=" + resultado2);
    
    boolean resultado3 = esCIDRValido("256.168.1.0/24");
    System.out.println("Caso 3: esperado=false, obtenido=" + resultado3);
    
    boolean resultado4 = esCIDRValido("192.168.1.0/33");
    System.out.println("Caso 4: esperado=false, obtenido=" + resultado4);
    
    boolean resultado5 = esCIDRValido("192.168.1.0/abc");
    System.out.println("Caso 5: esperado=false, obtenido=" + resultado5);
    
    boolean resultado6 = esCIDRValido("0.0.0.0/0");
    System.out.println("Caso 6: esperado=true, obtenido=" + resultado6);
    
    boolean resultado7 = esCIDRValido("102.168.1.1/32");
    System.out.println("Caso 7: esperado=true, obtenido=" + resultado7);
 
  }
}
