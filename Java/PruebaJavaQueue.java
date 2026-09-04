import java.util.Queue;
import java.util.LinkedList;


public class PruebaJavaQueue {


  public static void main (String[] args) {

    Queue<Integer> numeros = new LinkedList<>();
   
    numeros.offer(10);
    
    numeros.offer(20);
    
    numeros.offer(30);
    
    System.out.println(numeros.poll());
    
    System.out.println(numeros.poll());

  }
}




