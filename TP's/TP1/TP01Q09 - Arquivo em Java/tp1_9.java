/*
Como não pode usar o método Floar.valueOf(), fiz de outra forma a conversão da string em float
*/

import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DecimalFormat; //Usado para formatar o float ao ser exibido
import java.text.DecimalFormatSymbols; //Usado para trocar para '.' um método que exibia ',' como separador de ponto flutuante
import java.util.Locale; //Necessário para auxiliar a operação acima

class tp1_9 {

	//Essa método pega uma string e faz os devidos ajustes para transforma-la em float
	public static float stof(String s) {
		float resp = 0;
		boolean ehDecimal = false;
		int tam = s.length(), virgula = 0; //Controlando em que posição a vírgula aparece
		for (int i = 0; i < tam; i++) {
			if (!ehDecimal && (s.charAt(i) == ',' || s.charAt(i) == '.')) { //Verificando se chegamos vírgula
				if (i > 0) { //Caso já tenha sido realizadas operações, o número precisa ser divido por uma certa potência de 10 para corrigir erros
					ehDecimal = true;
					virgula = i;
					resp /= Math.pow(10, tam - i);
				} else {
					ehDecimal = true;
					virgula = i;
				}
			} else {
				if (!ehDecimal) {
					resp +=
						Math.pow(10, (tam - i - 1)) * (int) (s.charAt(i) - 48);
				} else {
					resp +=
						Math.pow(10, -(i - virgula)) * (int) (s.charAt(i) - 48);
				}
			}
		}
		return resp;
	}

	public static void escreverArquivo(String arq, int quant) {
		{
			try {
				FileWriter file = new FileWriter(arq);
				for (int i = 0; i < quant; i++) {
					file.write(MyIO.readFloat() + "\n");
				}
				file.close();
			} catch (IOException e) {
				System.err.println(e);
			}
		}
	}

	public static void lerArquivo(String arq, int quant) {
		try {
			RandomAccessFile file = new RandomAccessFile(arq, "r");
			long ponteiro = file.length() - 1; //-1 para ler um valor válido
			file.seek(ponteiro);
			for (int i = 0; i < quant; i++) {
				file.seek(--ponteiro); // Saindo dos '\n'
				int charAtual = (int) file.read();
				while (charAtual != (int) '\n' && ponteiro > 0) {
					file.seek(--ponteiro);
					charAtual = file.read();
				}
				if (ponteiro == 0) {
					file.seek(0);
				}
				float linha = stof(file.readLine());
				DecimalFormat formatador = new DecimalFormat("0.###"); //Satisfazendo a saída do verde
				//Por padrão o método acima estava usando vírgulas e não pontos
				formatador.setDecimalFormatSymbols(
					DecimalFormatSymbols.getInstance(Locale.ENGLISH)
				);
				MyIO.println(formatador.format(linha));
			}
			file.close();
		} catch (IOException e) {
			System.err.println(e);
		}
	}

	public static void main(String[] args) {
		String arq = "arq.dat";
		int quant = MyIO.readInt();
		escreverArquivo(arq, quant);
		lerArquivo(arq, quant);
	}
}
