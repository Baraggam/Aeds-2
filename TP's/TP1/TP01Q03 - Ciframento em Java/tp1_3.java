class tp1_3 {

	//Usando o método Character.toUpperCase(char) pois não foi permitido o uso do método da string
	public static boolean isFim(String s) {
		return (
			s.length() == 3 &&
			Character.toUpperCase(s.charAt(0)) == 'F' &&
			Character.toUpperCase(s.charAt(1)) == 'I' &&
			Character.toUpperCase(s.charAt(2)) == 'M'
		);
	}

	public static String cifrador(String s) {
		char cifrando[] = new char[s.length()];
		for (int i = 0; i < s.length(); i++) {
			cifrando[i] = (char) (s.charAt(i) + 3);
		}
		String cifrada = new String(cifrando);
		return cifrada;
	}

	//Gerencia a resposta final mandando para os métodos necessários
	public static String escrever(String s) {
		return cifrador(s);
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
