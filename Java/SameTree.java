

public class SameTree {

  public static class TreeNode {

    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int val) {
      this.val = val;
    }

  }

  public static boolean SameTree(TreeNode p, TreeNode q) {

    // si p es null y q es null (caso A):
    if(p == null && q == null) {
      return true;

    }

    // si p es null y q no es null o p no es null y q es null) (caso B):

    if((p == null && q != null) || (p != null && q == null)) {

      return false;

    }


    // si llegamos aqui, ninguno de los dos es null

    return (p.val == q.val) && SameTree(p.left, q.left) && SameTree(p.right, q.right);


  }

  public static void main(String[] args) {

    TreeNode p = new TreeNode(1);
    TreeNode p_izq = new TreeNode(2);
    TreeNode p_der = new TreeNode(3);

    p.left = p_izq;
    p.right = p_der;

    TreeNode q = new TreeNode(1);
    TreeNode q_izq = new TreeNode(5);
    TreeNode q_der = new TreeNode(3);

    q.left = q_izq;
    q.right = q_der;
    


    


    boolean resultado = SameTree(p, q);

    System.out.println(resultado);

  }

}
