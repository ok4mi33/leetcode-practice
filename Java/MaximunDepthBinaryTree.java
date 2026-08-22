

/* Estructura de un nodo de arbol en Java, esto se te da siempre en el problema.
 *
 * public class TreeNode {
 *  int val;
 *  TreeNode left;
 *  TreeNode right;
 *  TreeNode(int val) {
 *    this.val = val;
 *    }
 *   }
 *
 *   cada nodo tiene un valor (val) y dos referencias left y right, puede ser null
 *   si no existe ese hijo.
 */


public class MaximunDepthBinaryTree {
  
  public static class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int val) {
          this.val = val;
      }
  }

//Declaracion de funcion: funcion profundidadMaxima(nodo):
  public static int profundidadMaxima(TreeNode nodo) {

    // si nodo es null (caso base):
    
    if(nodo == null){

      return 0;
    }

    //Declaracion de variables
    
    //profundidadIzquierda = profundidadMaxima(nodo.left)
    int profundidadIzquierda = profundidadMaxima(nodo.left);

    //profundidadDerecha = profundidadMaxima(nodo.right)
    int profundidadDerecha = profundidadMaxima(nodo.right);


    //return el mayor entre (profundidadIzquierda, profundidadDerecha) + 1
    return Math.max(profundidadIzquierda, profundidadDerecha) + 1;
  



  }

  public static void main(String[] args) {


    TreeNode raiz = new TreeNode(3);
    TreeNode nueve = new TreeNode(9);
    TreeNode veinte = new TreeNode(20);

    raiz.left = nueve;
    raiz.right = veinte;

    TreeNode quince = new TreeNode(15);

    veinte.left = quince;

    TreeNode siete = new TreeNode(7);

    veinte.right = siete;

    int resultado = profundidadMaxima(raiz); 

    System.out.println(resultado);

  }




}

