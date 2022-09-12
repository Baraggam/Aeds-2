/*
- Como não é possível a utilização do método Integer.parseInt(), foi usado o número inteiro correspondente do char '0' para int: 0 = 48;
- Como não é possível a utilização do método String.trim(), apenas foi mostrado na tela o primeiro valor da string,
	mas ela possui vários espaços em branco devido as transformaçoes de char[] para string;
- Como o código possui várias iterações e é para ser feito usando recursividade, apenas o maior loop será 'recursivado';
*/

class tp1_14 {

	public static boolean isFim(String s) {
		return s.charAt(0) == '0';
	}

	//Contador de parênteses, logo, contador de operações a serem feitas
	public static int Contador(String s) {
		int parenteses = 0;
		for (int i = 0, j = 0; i < s.length(); i++) {
			if (s.charAt(i) == '(') {
				parenteses++;
			}
		}

		return parenteses;
	}

	//Apagando itens desnecessários e trocando letras por seus valores booleanos
	public static String organizador(String s) {
		char modificando[] = new char[s.length()];
		int count = 0, termos = (int) s.charAt(0) - 48; //Usando a base dita no início
		for (int i = termos * 2 + 2; i < s.length(); i++) { //Ignorando os números no início da string e apagando espaços subsequentes
			if (s.charAt(i) != ' ') {
				modificando[count++] = s.charAt(i);
			}
		}
		boolean encontrado;
		for (int i = 0; i < count; i++) { //Trocando as letras por números booleanos
			encontrado = false;
			for (int j = 0; j < termos && !encontrado; j++) {
				if (modificando[i] == (char) ('A' + j)) {
					modificando[i] = s.charAt((j + 1) * 2);
					encontrado = true;
				}
			}
		}
		String modificada = new String(modificando);
		return modificada;
	}

	//Altera uma parte selecionada da string com o valor desejado
	public static String modificador(
		String s,
		char valor,
		int inicio,
		int fim
	) {
		char temp[] = new char[s.length()];
		int count = 0;
		for (; count < inicio; count++) {
			temp[count] = s.charAt(count);
		}
		temp[count++] = valor;
		for (int i = fim + 1; i < s.length(); i++, count++) {
			temp[count] = s.charAt(i);
		}
		temp[count] = '\0';
		String resp = new String(temp);
		return resp;
	}

	//Realiza as operações boolenas, modifica e retorna a própria string que recebe
	public static String analisadorRec(String s, int parenteses) {
		if (parenteses > 0) {
			int i = 0;
			for (; s.charAt(i) != ')'; i++);
			if (s.charAt(i - 3) == 't' && s.charAt(i - 1) == '0') { //Transformando not 0 e not 1
				s = modificador(s, '1', i - 5, i);
			} else if (s.charAt(i - 3) == 't' && s.charAt(i - 1) == '1') {
				s = modificador(s, '0', i - 5, i);
			} else { // Calculando and e or
				int j = i;
				for (; s.charAt(i) != 'd' && s.charAt(i) != 'r'; i--);
				int termos = (j - i - 1) / 2, inicio = i;
				if (s.charAt(i) == 'd') { //and
					i += 2;
					boolean and = true;
					while (termos > 0 && and) {
						and = s.charAt(i) == '1';
						i += 2;
						termos--;
					}
					if (and) {
						s = modificador(s, '1', inicio - 2, j);
					} else {
						s = modificador(s, '0', inicio - 2, j);
					}
				} else { //or
					i += 2;
					boolean or = false;
					while (termos > 0 && !or) {
						or = s.charAt(i) == '1';
						i += 2;
						termos--;
					}
					if (or) {
						s = modificador(s, '1', inicio - 1, j);
					} else {
						s = modificador(s, '0', inicio - 1, j);
					}
				}
			}
			s = analisadorRec(s, --parenteses);
		}
		return s;
	}

	public static String analisador(String s) {
		return analisadorRec(s, Contador(s)); //"Contador(s) é a quantidade de operações
	}

	public static char escrever(String s) { //Gerencia a resposta final mandando para os métodos necessários
		return analisador(organizador(s)).charAt(0); //Retorna apenas o primeiro char da string, como dito no início
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
