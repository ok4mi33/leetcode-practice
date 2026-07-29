public class Two_Sum {
	public static void main(String[] args) {
		// defino el array de números
		int[] nums;

		nums = new int[]{3, 4, 5, 3, 5, 3, 9}; //declaracion

		// defino el numero que busco (target)
		int target = 9;

		// recorro cada numero del array (loop externo)
		for(int i = 0; i < nums.length; i++){

			// por cada numero, vuelvo a recorrer el array buscando el complemento (loop anidado)
			for(int j = i+1; j < nums.length; j++){

				
				// si la suma de los dos numeros da el target, se imprime
				if (nums[i] + nums[j] == target ) {
					System.out.println("Output: " + i + "," + j);
				}
			}
		}
	}
}
