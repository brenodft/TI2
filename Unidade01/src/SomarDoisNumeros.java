
import java.util.Scanner;
public class SomarDoisNumeros {
	public static void main(String [] args) {
		Scanner sc = new Scanner(System.in);
		int a = sc.nextInt();
		int b = sc.nextInt();
		int soma = a+b;
		System.out.println("A soma dos dois numeros é: "+soma);
		sc.close();
	}
}
