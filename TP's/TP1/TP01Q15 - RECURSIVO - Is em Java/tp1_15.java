class tp1_15 {

	//Usando o método Character.toUpperCase(char) pois não foi permitido o uso do método da string
	public static boolean isFim(String s) {
		return (
			s.length() == 3 &&
			Character.toUpperCase(s.charAt(0)) == 'F' &&
			Character.toUpperCase(s.charAt(1)) == 'I' &&
			Character.toUpperCase(s.charAt(2)) == 'M'
		);
	}

	public static boolean soVogalRec(String s, int i, int tam) {
		boolean ehVogal = true;
		if (i < tam) {
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
			if (ehVogal) {
				ehVogal = soVogalRec(s, ++i, tam);
			}
		}
		return ehVogal;
	}

	public static boolean soVogal(String s) {
		return soVogalRec(s, 0, s.length());
	}

	public static boolean soConsoanteRec(String s, int i, int tam) {
		boolean ehConsoante = true;
		if (i < tam) {
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
			if (ehConsoante) {
				ehConsoante = soConsoanteRec(s, ++i, tam);
			}
		}
		return ehConsoante;
	}

	public static boolean soConsoante(String s) {
		return soConsoanteRec(s, 0, s.length());
	}

	public static boolean soInteiroRec(String s, int i, int tam) {
		boolean ehInteiro = true;
		if (i < tam) {
			if (!Character.isDigit(s.charAt(i))) {
				ehInteiro = false;
			}
			if (ehInteiro) {
				ehInteiro = soInteiroRec(s, ++i, tam);
			}
		}
		return ehInteiro;
	}

	public static boolean soInteiro(String s) {
		return soInteiroRec(s, 0, s.length());
	}

	public static boolean soRealRec(String s, int i, int virgula, int tam) {
		boolean ehReal = true;
		if (i < tam) {
			if (s.charAt(i) == ',' || s.charAt(i) == '.') {
				virgula++;
			} else if (!Character.isDigit(s.charAt(i))) {
				ehReal = false;
			}

			if (virgula == 2) {
				ehReal = false;
			}
			if (ehReal) {
				ehReal = soRealRec(s, ++i, virgula, tam);
			}
		}
		return ehReal;
	}

	public static boolean soReal(String s) {
		return soRealRec(s, 0, 0, s.length());
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
