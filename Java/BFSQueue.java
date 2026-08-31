
import java.util.Queue;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;




public class BFSQueue {

  public static class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int val) {
      this.val = val;
    }
  }


  // funcion levelOrder(raiz):
  public static List<List<Integer>> levelOrder(TreeNode raiz) {


    Queue<TreeNode> cola = new LinkedList<>();

    cola.offer(raiz);
  
    // resultado = lista vacia (de listas)
    List<List<Integer>> resultado = new ArrayList<>();

    // cola = nueva Queue

    // cola.offer(raiz)
    


    // mientras cola no este vacia:
    while (!cola.isEmpty()){

      int tamañoNivel = cola.size();
      List<Integer> nivelActual = new ArrayList<>();


      // repetir tamañoNivel veces:
      for (int i = 0; i < tamañoNivel; i++) {

        // actual = cola.poll()
        TreeNode actual = cola.poll();

        // agregar actual.val a nivel actaul
        nivelActual.add(actual.val);

        // si actual.left no es null:
        if(actual.left != null) {

          cola.offer(actual.left);

        }
        if (actual.right != null) {
          
          cola.offer(actual.right);
        }

        // agregar nivelActual a resultado

      }
      resultado.add(nivelActual);
      
    }
    return resultado;

  }
  public static void main(String[] args) {


    TreeNode raiz = new TreeNode(3);
    TreeNode izq = new TreeNode(9);
    TreeNode der = new TreeNode(20);

    raiz.left = izq;
    
    raiz.right = der;

    TreeNode derizq = new TreeNode(15);
    TreeNode derder = new TreeNode(7);

    der.left = derizq;

    der.right = derder;

    List<List<Integer>> resultado = levelOrder(raiz);


    System.out.println(resultado);








    

  }




}
