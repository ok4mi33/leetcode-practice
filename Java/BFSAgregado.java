import java.util.Queue;

import java.util.LinkedList;


public class BFSAgregado {


  public static class TreeNode {

    int val;
    TreeNode left;
    TreeNode right;
    TreeNode (int val){
      this.val = val;
    }


  }

        

  public static void main(String[] args) {
    TreeNode raiz = new TreeNode(3);

    TreeNode izq = new TreeNode(9);
    TreeNode der = new TreeNode(20);

    raiz.left = izq;
    raiz.right = der;

    TreeNode derizq = new TreeNode(15);
    TreeNode derder = new TreeNode (7);

    der.left = derizq;
    der.right = derder;


    Queue<TreeNode> cola = new LinkedList<>();

    cola.offer(raiz);

    while (!cola.isEmpty()) {
      int cantidadEnNivel = cola.size();
      
      for(int i = 0;i < cantidadEnNivel; i++) {
        TreeNode actual = cola.poll();
        System.out.print(actual.val + " ");
        if (actual.left != null) {
          cola.offer(actual.left);
        }
        if (actual.right != null) {
          cola.offer(actual.right);

        }

      }
  
      System.out.println();
    }
  }

    
}

