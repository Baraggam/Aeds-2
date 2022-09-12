import java.util.Random;

class tp1_4 {

	//Usando o método Character.toUpperCase(char) pois não foi permitido o uso do método da string
	public static boolean isFim(String s) {
		return (
			s.length() == 3 &&
			Character.toUpperCase(s.charAt(0)) == 'F' &&
			Character.toUpperCase(s.charAt(1)) == 'I' &&
			Character.toUpperCase(s.charAt(2)) == 'M'
		);
	}

	public static String modificador(String s, Random gerador) {
		char modificando[] = new char[s.length()];
		char A = (char) ('a' + (Math.abs(gerador.nextInt()) % 26));
		char B = (char) ('a' + (Math.abs(gerador.nextInt()) % 26));
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == A) {
				modificando[i] = B;
			} else {
				modificando[i] = s.charAt(i);
			}
		}
		String modificada = new String(modificando);
		return modificada;
	}

	//Gerencia a resposta final mandando para os métodos necessários
	public static String escrever(String s, Random gerador) {
		return modificador(s, gerador);
	}

	public static void main(String[] args) {
		String entrada;
		Random gerador = new Random();
		gerador.setSeed(4); //Criado na main pois será usado apenas um gerador para todos os casos
		entrada = MyIO.readLine();
		while (!isFim(entrada)) {
			MyIO.println(escrever(entrada, gerador));
			entrada = MyIO.readLine();
		}
	}
}
