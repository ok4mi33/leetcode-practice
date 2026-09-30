

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
  //  partes = separar cidr por "/"
  //  si cantidad de partes != 2:
  //    devolver false
  //
  //
  // ipParte = partes[0]
  // prefijoParte = partes[1]
  //
  //
  // si NO esIPv4Valida(ipParte):
  //  devolver false
  //
  // intentar:
  //    prefijo = convertir prefijoParte a numero
  //    si prefijo < 0 o prefijo > 32:
  //        devolver false
  // si falla la conversion:
  //    devolver false
  //
  // devolver true

 
  public static void main (String[] args){
  }
}
