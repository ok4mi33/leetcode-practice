
public class NumberIslands {


  // funcion explorarIsla(grid, fila, columna):
  public static void explorarIsla(char[][] grid, int fila, int columna) {
  // si fila < 0 O fila >= cantidad de filas O columna < 0 O columna >= cantidad de columnas:
  if(fila < 0  || fila >= grid.length || columna < 0 || columna >= grid[0].length) {

    return; // fuera de limites, no hay nada que explorar
  }
  if (grid[fila][columna] == '0'){
    return;
  }

  // si llegamos aqui: es una posicion valida y es tierra sin vistar
  grid[fila][columna] = '0'; // la hundo
                            

  explorarIsla(grid, fila-1, columna); // arriba
  explorarIsla(grid, fila+1, columna); // abajo
  explorarIsla(grid, fila, columna-1); // izquierda
  explorarIsla(grid, fila, columna+1); // derecha                                      

  }

  public static int numIslands(char[][] grid){

    int contador = 0;

    for(int i = 0; i < grid.length; i++) {

      for(int j = 0; j < grid[0].length; j++){

        if(grid[i][j] == '1') {
          contador++;
          explorarIsla(grid, i, j);
        }
      }
    }
    return contador;

  }

  public static void main(String[] args) {

    char[][] grid = {{'1','1','0','0','0'},
      {'1','1','0','0','0'},
      {'0','0','1','0','0'},
      {'0','0','0','1','1'}};


      int resultado = numIslands(grid);

      System.out.println(resultado);


  }




}
