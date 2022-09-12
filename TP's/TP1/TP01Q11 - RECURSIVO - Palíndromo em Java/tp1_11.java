class tp1_11 {

	//Usando o método Character.toUpperCase(char) pois não foi permitido o uso do método da string
	public static boolean isFim(String s) {
		return (
			s.length() == 3 &&
			Character.toUpperCase(s.charAt(0)) == 'F' &&
			Character.toUpperCase(s.charAt(1)) == 'I' &&
			Character.toUpperCase(s.charAt(2)) == 'M'
		);
	}

	public static boolean ehPalindromoRec(String s, int i, int j) {
		boolean ehPalindromo = true;
		if (i < j) {
			ehPalindromo = s.charAt(i) == s.charAt(j);
			{
				if (ehPalindromo) {
					ehPalindromo = ehPalindromoRec(s, ++i, --j);
				}
			}
		}
		return ehPalindromo;
	}

	public static boolean ehPalindromo(String s) {
		return ehPalindromoRec(s, 0, s.length() - 1);
	}

	//Gerencia a resposta final mandando para os métodos necessários
	public static String escrever(String s) {
		String resp = "SIM";
		if (!ehPalindromo(s)) {
			resp = "NAO";
		}
		return resp;
	}

	public static void main(String[] args) {
		String entrada;
		entrada = MyIO.readLine();
		while (!isFim(entrada)) {
			MyIO.println(escrever(entrada));
			entrada = MyIO.readLine();
		}
	}
}
