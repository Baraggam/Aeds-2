class tp1_6 {

	//Usando o método Character.toUpperCase(char) pois não foi permitido o uso do método da string
	public static boolean isFim(String s) {
		return (
			s.length() == 3 &&
			Character.toUpperCase(s.charAt(0)) == 'F' &&
			Character.toUpperCase(s.charAt(1)) == 'I' &&
			Character.toUpperCase(s.charAt(2)) == 'M'
		);
	}

	public static boolean soVogal(String s) {
		boolean ehVogal = true;
		for (int i = 0; i < s.length() && ehVogal; i++) {
			if (Character.isDigit(s.charAt(i))) { //Teste de letra ou número
				ehVogal = false;
			} else if ( //Teste de vogal ou Consoante
				!(
					Character.toUpperCase(s.charAt(i)) == 'A' ||
					Character.toUpperCase(s.charAt(i)) == 'E' ||
					Character.toUpperCase(s.charAt(i)) == 'I' ||
					Character.toUpperCase(s.charAt(i)) == 'O' ||
					Character.toUpperCase(s.charAt(i)) == 'U'
				)
			) {
				ehVogal = false;
			}
		}
		return ehVogal;
	}

	public static boolean soConsoante(String s) {
		boolean ehConsoante = true;
		for (int i = 0; i < s.length() && ehConsoante; i++) {
			if (Character.isDigit(s.charAt(i))) { //Teste de letra ou número
				ehConsoante = false;
			} else if ( //Teste de vogal ou Consoante
				(
					Character.toUpperCase(s.charAt(i)) == 'A' ||
					Character.toUpperCase(s.charAt(i)) == 'E' ||
					Character.toUpperCase(s.charAt(i)) == 'I' ||
					Character.toUpperCase(s.charAt(i)) == 'O' ||
					Character.toUpperCase(s.charAt(i)) == 'U'
				)
			) {
				ehConsoante = false;
			}
		}
		return ehConsoante;
	}

	public static boolean soInteiro(String s) {
		boolean ehInteiro = true;
		for (int i = 0; i < s.length() && ehInteiro; i++) {
			if (!Character.isDigit(s.charAt(i))) {
				ehInteiro = false;
			}
		}
		return ehInteiro;
	}

	public static boolean soReal(String s) {
		boolean ehReal = true;
		int virgula = 0;
		for (int i = 0; i < s.length() && ehReal && virgula < 2; i++) {
			if (s.charAt(i) == ',' || s.charAt(i) == '.') {
				virgula++;
			} else if (!Character.isDigit(s.charAt(i))) {
				ehReal = false;
			}
		}
		if (virgula == 2) ehReal = false;
		return ehReal;
	}

	//Converte Valores booleanos em SIM ou NAO
	public static String converter(boolean b) {
		String resp = "SIM";
		if (!b) {
			resp = "NAO";
		}
		return resp;
	}

	//Gerencia a resposta final mandando para os métodos necessários
	public static String escrever(String s) {
		return (
			converter(soVogal(s)) +
			" " +
			converter(soConsoante(s)) +
			" " +
			converter(soInteiro(s)) +
			" " +
			converter(soReal(s))
		);
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
