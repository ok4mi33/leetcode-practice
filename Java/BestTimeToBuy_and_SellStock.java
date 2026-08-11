
public class BestTimeToBuy_and_SellStock {
  
  //Declaracion de metodos
  public static int MejorDia(int[] prices) {

  // Declaraciones de variables
  
  // Precio minimo que se guarda como array
  int precioMinimo = prices[0]; // el primer precio, como punto de partida
  
  // Ganancia Maxia que se inicializa con 0
  int maxGanancia = 0;

  // loops

  // para cada precio del array, empezando desde el segundo (i = 1)
  for(int i = 0; i < prices.length; i++) {

    // calculo la ganancia si vendiera hoy = prices[i] - precioMinimo
    int ganancia = prices[i] - precioMinimo;

    //condicionales

    // si esa ganancia es mayor que maxGanacia
    if(ganancia > maxGanancia){
      maxGanancia = ganancia;
    }

    // si prices[i] es menor que precioMinimo
    if(prices[i] < precioMinimo) {
      precioMinimo = prices[i];
                          

    }


    }

    return maxGanancia;
  }
}
