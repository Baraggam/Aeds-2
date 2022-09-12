/*
Para evitar problemas com encoding de caracteres especiais, foi utilizado o código unicode ao invés de letras acentuadas
*/

import java.io.*;
import java.net.*;

class tp1_8 {

	//Usando o método Character.toUpperCase(char) pois não foi permitido o uso do método da string
	public static boolean isFim(String s) {
		return (
			s.length() == 3 &&
			Character.toUpperCase(s.charAt(0)) == 'F' &&
			Character.toUpperCase(s.charAt(1)) == 'I' &&
			Character.toUpperCase(s.charAt(2)) == 'M'
		);
	}

	public static String lerHtml(String site) {
		try {
			URL url = new URL(site);
			InputStream is = url.openStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader leitor = new BufferedReader(isr);
			String linha = leitor.readLine();
			String texto = "";
			while (linha != null) {
				texto += linha;
				linha = leitor.readLine();
			}
			return texto;
		} catch (IOException e) {
			System.err.println(e);
			return "";
		}
	}

	public static void lerLetras(String texto, int c[]) {
		for (int i = 0; i < texto.length(); i++) {
			if (texto.charAt(i) == 'a') {
				c[0]++;
			} else if (texto.charAt(i) == 'e') {
				c[1]++;
			} else if (texto.charAt(i) == 'i') {
				c[2]++;
			} else if (texto.charAt(i) == 'o') {
				c[3]++;
			} else if (texto.charAt(i) == 'u') {
				c[4]++;
			} else if (texto.charAt(i) == '\u00E1') { //á
				c[5]++;
			} else if (texto.charAt(i) == '\u00E9') { //é
				c[6]++;
			} else if (texto.charAt(i) == '\u00ED') { //í
				c[7]++;
			} else if (texto.charAt(i) == '\u00F3') { //ó
				c[8]++;
			} else if (texto.charAt(i) == '\u00FA') { //ú
				c[9]++;
			} else if (texto.charAt(i) == '\u00E0') { //à
				c[10]++;
			} else if (texto.charAt(i) == '\u00E8') { //è
				c[11]++;
			} else if (texto.charAt(i) == '\u00EC') { //ì
				c[12]++;
			} else if (texto.charAt(i) == '\u00F2') { //ò
				c[13]++;
			} else if (texto.charAt(i) == '\u00F9') { //ù
				c[14]++;
			} else if (texto.charAt(i) == '\u00E3') { //ã
				c[15]++;
			} else if (texto.charAt(i) == '\u00F5') { //õ
				c[16]++;
			} else if (texto.charAt(i) == '\u00E2') { //â
				c[17]++;
			} else if (texto.charAt(i) == '\u00EA') { //ê
				c[18]++;
			} else if (texto.charAt(i) == '\u00EE') { //î
				c[19]++;
			} else if (texto.charAt(i) == '\u00F4') { //ô
				c[20]++;
			} else if (texto.charAt(i) == '\u00FB') { //û
				c[21]++;
			} else if (texto.charAt(i) >= 'b' && texto.charAt(i) <= 'z') {
				c[22]++;
			}
		}
	}

	public static int lerBr(String texto) {
		int br = 0;
		for (int i = 0; i < texto.length(); i++) {
			if (
				texto.charAt(i) == '<' &&
				texto.charAt(++i) == 'b' &&
				texto.charAt(++i) == 'r' &&
				texto.charAt(++i) == '>'
			) {
				br++;
			}
		}
		return br;
	}

	public static int lerTable(String texto) {
		int table = 0;
		for (int i = 0; i < texto.length(); i++) {
			if (
				texto.charAt(i) == '<' &&
				texto.charAt(++i) == 't' &&
				texto.charAt(++i) == 'a' &&
				texto.charAt(++i) == 'b' &&
				texto.charAt(++i) == 'l' &&
				texto.charAt(++i) == 'e' &&
				texto.charAt(++i) == '>'
			) {
				table++;
			}
		}

		return table;
	}

	//Gerencia a resposta final mandando para os métodos necessários
	public static String escrever(String nomeSite, String url) {
		int c[] = new int[23], br = 0, table = 0;
		String texto = lerHtml(url);
		br = lerBr(texto);
		table = lerTable(texto);
		lerLetras(texto, c);
		//Retirar as letras que aparecem em <table> e <br>
		c[0] -= table; //a
		c[1] -= table; //e
		c[22] -= (3 * table) + (2 * br); //b - l - r - t
		String resp =
			"a(" +
			c[0] +
			") e(" +
			c[1] +
			") i(" +
			c[2] +
			") o(" +
			c[3] +
			") u(" +
			c[4] +
			") á(" +
			c[5] +
			") é(" +
			c[6] +
			") í(" +
			c[7] +
			") ó(" +
			c[8] +
			") ú(" +
			c[9] +
			") à(" +
			c[10] +
			") è(" +
			c[11] +
			") ì(" +
			c[12] +
			") ò(" +
			c[13] +
			") ù(" +
			c[14] +
			") ã(" +
			c[15] +
			") õ(" +
			c[16] +
			") â(" +
			c[17] +
			") ê(" +
			c[18] +
			") î(" +
			c[19] +
			") ô(" +
			c[20] +
			") û(" +
			c[21] +
			") consoante(" +
			c[22] +
			") <br>(" +
			br +
			") <table>(" +
			table +
			") " +
			nomeSite;
		return resp;
	}

	public static void main(String[] args) {
		String entrada[] = new String[1000];
		int count = 0;
		do {
			entrada[count] = MyIO.readLine();
		} while (!isFim(entrada[count++]));
		count--;
		MyIO.setCharset("UTF-8"); //Para imprimir certos caracteres
		for (int i = 0; i < count; i++) {
			MyIO.println(escrever(entrada[i], entrada[++i]));
		}
	}
}
