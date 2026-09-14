import java.util.Queue;
import java.util.LinkedList;

public class RateLimiter {

  
  static Queue<Integer> cola = new LinkedList<>();
  
  public static void hit(int timestamp) {

    cola.offer(timestamp);


    }
  
  public static int getHits(int timestamp){
    while(!cola.isEmpty() && cola.peek() <= timestamp-300){

      cola.poll();


      }
    return cola.size();
  }

  public static void main(String[] args){

    // Caso 1
    hit(1);
    hit(2);
    hit(3);
    int resultado1 = getHits(4);
    System.out.println("Caso 1: esperado=3, obtenido=" + resultado1);

    cola.clear();

    // Caso 2
    hit(1);
    hit(2);
    int resultado2 = getHits(301);
    System.out.println("Caso 2: esperado=1, obtenido=" + resultado2);

    cola.clear();

    // Caso 3
    int resultado3 = getHits(100);
    System.out.println("Caso 3: esperado=0, obtenido=" + resultado3);



  }

}
