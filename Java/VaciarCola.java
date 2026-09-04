import java.util.Queue;
import java.util.LinkedList;

public class VaciarCola {

  public static void main(String[] args) {

    Queue<Integer> numeros = new LinkedList<>();
    numeros.offer(10);
    numeros.offer(20);
    numeros.offer(30);
    numeros.offer(40);

    while(!numeros.isEmpty()) {

      System.out.println(numeros.poll());
    }
  }
}
