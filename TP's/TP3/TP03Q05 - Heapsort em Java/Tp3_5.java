import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

class Lista {

	private Filme[] lista;
	private int n;
	private static SimpleDateFormat dtob = new SimpleDateFormat("dd/MM/yyyy"); //data brasileira
	private static long inicio; //Tempo no início do programa
	private static long termino; //Tempo no fim do programa
	private static int comparacoes; //Contador de comparações
	private static int movimentacoes; //Contador de comparações

	public Lista(int tamanho) {
		lista = new Filme[tamanho];
		n = 0;
	}

	public void inserirInicio(Filme f) throws Exception {
		if (n >= lista.length) {
			throw new Exception("Erro ao inserir!");
		}

		for (int i = n; i > 0; i--) {
			lista[i] = lista[i - 1];
		}
		lista[0] = f;
		n++;
	}

	public void inserirFim(Filme f) throws Exception {
		if (n >= lista.length) {
			throw new Exception("Erro ao inserir!");
		}
		lista[n++] = f;
	}

	public void inserir(Filme f, int pos) throws Exception {
		if (n >= lista.length || pos < 0 || pos > n) {
			throw new Exception("Erro ao inserir!");
		}
		for (int i = n; i > pos; i--) {
			lista[i] = lista[i - 1];
		}
		lista[pos] = f;
		n++;
	}

	public Filme removerInicio() throws Exception {
		if (n == 0) {
			throw new Exception("Erro ao remover!");
		}
		Filme resp = lista[0];
		n--;
		for (int i = 0; i < n; i++) {
			lista[i] = lista[i + 1];
		}
		return resp;
	}

	public Filme removerFim() throws Exception {
		if (n == 0) {
			throw new Exception("Erro ao remover!");
		}
		return lista[--n];
	}

	public Filme remover(int pos) throws Exception {
		if (n == 0 || pos < 0 || pos >= n) {
			throw new Exception("Erro ao remover!");
		}
		Filme resp = lista[pos];
		n--;
		for (int i = pos; i < n; i++) {
			lista[i] = lista[i + 1];
		}
		return resp;
	}

	public void mostrar() {
		for (int i = 0; i < n; i++) {
			lista[i].imprimir();
			MyIO.println("");
		}
	}

	//Inserção modificado para o desempate
	public void insercao() {
		for (int inicio = 0; inicio < n; inicio++) {
			int fim = inicio + 1; //Variável de controle para ocorrencias iguais
			for (
				String s = lista[inicio].getGenero();
				fim < n && s.equals(lista[fim].getGenero());
				fim++
			);
			for (int i = inicio + 1; i < fim; i++) {
				Filme tmp = lista[i];
				int j = i - 1;
				while (
					(j >= inicio) &&
					(lista[j].getNome().compareTo(tmp.getNome()) > 0)
				) {
					lista[j + 1] = lista[j];
					j--;
				}
				lista[j + 1] = tmp;
			}
			inicio += fim - inicio - 1;
		}
	}

	//Heapsort
	public void sort() {
		Filme tmp[] = new Filme[n + 1];
		for (int i = 0; i < n; i++) {
			movimentacoes++;
			tmp[i + 1] = lista[i];
		}
		lista = tmp;
		for (int tamHeap = 2; tamHeap <= n; tamHeap++) {
			construir(tamHeap);
		}
		int tamHeap = n;
		while (tamHeap > 1) {
			swap(1, tamHeap--);
			reconstruir(tamHeap);
		}
		tmp = lista;
		lista = new Filme[n];
		for (int i = 0; i < n; i++) {
			movimentacoes++;
			lista[i] = tmp[i + 1];
		}
		insercao();
	}

	private void construir(int tamHeap) {
		comparacoes++;
		for (
			int i = tamHeap;
			i > 1 &&
			lista[i].getGenero().compareTo(lista[i / 2].getGenero()) > 0;
			i /= 2
		) {
			swap(i, i / 2);
			comparacoes++;
		}
	}

	private void reconstruir(int tamHeap) {
		int i = 1;
		while (i <= (tamHeap / 2)) {
			int filho = getMaiorFilho(i, tamHeap);
			comparacoes++;
			if (lista[i].getGenero().compareTo(lista[filho].getGenero()) < 0) {
				swap(i, filho);
				i = filho;
			} else {
				i = tamHeap;
			}
		}
	}

	private int getMaiorFilho(int i, int tamHeap) {
		int filho;
		comparacoes++;
		if (
			2 * i == tamHeap ||
			lista[2 * i].getGenero().compareTo(lista[2 * i + 1].getGenero()) > 0
		) {
			filho = 2 * i;
		} else {
			filho = 2 * i + 1;
		}
		return filho;
	}

	private void swap(int i, int j) {
		movimentacoes += 3;
		Filme temp = lista[i];
		lista[i] = lista[j];
		lista[j] = temp;
	}

	public boolean pesquisar(String f) {
		boolean encontrado = false;
		for (int i = 0; i < n && !encontrado; i++) {
			encontrado = lista[i].getNome().equals(f);
			comparacoes++;
		}
		return encontrado;
	}

	public boolean pesqBin(String f) {
		boolean encontrado = false;
		int dir = (n - 1), esq = 0, meio;
		while (esq <= dir) {
			meio = (esq + dir) / 2;
			if (lista[meio].getNome().compareTo(f) == 0) {
				encontrado = true;
				esq = dir + 1;
				comparacoes++;
			} else if (lista[meio].getNome().compareTo(f) < 0) {
				esq = meio + 1;
				comparacoes += 2;
			} else {
				dir = meio - 1;
				comparacoes += 2;
			}
		}
		return encontrado;
	}

	public void setInicio() {
		inicio = new Date().getTime();
		comparacoes = 0;
	}

	public void setTermino() {
		termino = new Date().getTime();
	}

	public String log() {
		return (
			"536013\t" +
			comparacoes +
			"\t" +
			movimentacoes +
			"\t" +
			((termino - inicio) / 1000.0) +
			"s"
		);
	}
}

class Filme {

	private static SimpleDateFormat dtob = new SimpleDateFormat("dd/MM/yyyy"); //data brasileira
	private String nome;
	private String titulo_original;
	private Date data_de_lancamento;
	private int duracao;
	private String genero;
	private String idioma_original;
	private String situacao;
	private float orcamento;
	private String[] palavras_chave;

	public Filme() {}

	public Filme(String arq) {
		ler(arq);
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setTitulo_original(String titulo_original) {
		this.titulo_original = titulo_original;
	}

	public void setData_de_lancamento(String data_de_lancamento) {
		try {
			this.data_de_lancamento = dtob.parse(data_de_lancamento);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	//Irá lidar com número inteiro (150) ou com texto (2h 30m)
	public void setDuracao(String duracao) {
		String temp = "";
		boolean somenteNumero = true;
		int i = 0;
		for (; somenteNumero && i < duracao.length(); i++) {
			if (duracao.charAt(i) == 'h' || duracao.charAt(i) == 'm') {
				somenteNumero = false;
			} else {
				temp += duracao.charAt(i);
			}
		}
		if (somenteNumero) {
			this.duracao = Integer.valueOf(temp);
		} else {
			if (duracao.charAt(i - 1) == 'h') {
				this.duracao = Integer.valueOf(temp) * 60;
				for (
					temp = "";
					i < duracao.length() && duracao.charAt(i) != 'm';
					i++
				) {
					temp += duracao.charAt(i);
				}
				if (temp.length() > 0) {
					this.duracao += Integer.valueOf(temp);
				}
			} else {
				this.duracao += Integer.valueOf(temp);
			}
		}
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public void setIdioma_original(String idioma_original) {
		this.idioma_original = idioma_original;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public void setOrcamento(float orcamento) {
		this.orcamento = orcamento;
	}

	//Método feito para ser compatível tanto em Java quanto em C
	public void setPalavras_chave(String palavras_chave) {
		String[] temp = palavras_chave.split(", ");
		this.palavras_chave = new String[temp.length];
		for (int i = 0; i < temp.length; i++) {
			this.palavras_chave[i] = temp[i];
		}
	}

	public String getNome() {
		return this.nome;
	}

	public String getTitulo_original() {
		return this.titulo_original;
	}

	public String getData_de_lancamento() {
		return dtob.format(this.data_de_lancamento);
	}

	public int getDuracao() {
		return this.duracao;
	}

	public String getGenero() {
		return this.genero;
	}

	public String getIdioma_original() {
		return this.idioma_original;
	}

	public String getSituacao() {
		return this.situacao;
	}

	public float getOrcamento() {
		return this.orcamento;
	}

	public String getPalavras_chave() {
		String temp = "[" + this.palavras_chave[0];

		for (int i = 1; i < this.palavras_chave.length; i++) {
			temp += ", " + this.palavras_chave[i];
		}
		return temp + "]";
	}

	//Método para retornar a string com o enconing ISO_8859_1
	public static String iso(String temp) {
		byte[] bytes = temp.getBytes(StandardCharsets.ISO_8859_1);
		String iso = new String(bytes);
		return iso;
	}

	//Limpa termos inuteis do nome do filme
	private static String limparNome(String linha) {
		int parenteses = 0, i = linha.length() - 1;
		for (; parenteses < 2 && i >= 0; i--) {
			if (linha.charAt(i) == '(') {
				parenteses++;
			}
		}
		return linha.substring(0, ++i);
	}

	//Remove todas as tags da string recebida
	private static String noTag(String linha) {
		String temp = "";
		for (int i = 0; i < linha.length(); i++) {
			if (linha.charAt(i) != '<') {
				temp += linha.charAt(i);
			} else {
				while (linha.charAt(i) != '>') {
					i++;
				}
			}
		}
		return temp;
	}

	//Este método irá pecorrer toda o texto do arquivo e retornar um array de string contendo as informações relevantes
	private static String[] reduzir(String arq) {
		try {
			String[] dados = new String[9];
			RandomAccessFile file = new RandomAccessFile(arq, "r");
			String linha = iso(file.readLine());
			while (!linha.contains("<title>")) {
				linha = iso(file.readLine());
			}
			dados[0] = noTag(limparNome(linha)).trim();
			linha = iso(file.readLine());
			while (!linha.contains("<span class=\"release\">")) {
				linha = iso(file.readLine());
			}
			linha = iso(file.readLine());
			dados[2] = linha.substring(0, linha.length() - 4).trim();
			linha = iso(file.readLine());
			while (!linha.contains("<span class=\"genres\">")) {
				linha = iso(file.readLine());
			}
			file.readLine();
			linha = iso(file.readLine()).replaceAll("</a>,", ",").trim();
			dados[4] =
				noTag(linha.replaceAll("</a>", "").replaceAll("&nbsp;", ""));
			linha = iso(file.readLine());
			while (!linha.contains("<span class=\"runtime\">")) {
				linha = iso(file.readLine());
			}
			file.readLine();
			dados[3] = iso(file.readLine()).replaceAll(" ", "");
			linha = iso(file.readLine());
			while (
				!linha.contains("Título original</strong>") &&
				!linha.contains("Situação</bdi></strong>")
			) {
				linha = iso(file.readLine());
			}
			boolean temTitulo = true;
			if (linha.contains("Título original</strong>")) {
				dados[1] =
					noTag(linha).replaceAll("Título original", "").trim();
			} else {
				dados[1] = dados[0];
				temTitulo = false;
			}
			if (temTitulo) {
				linha = iso(file.readLine());
				while (!linha.contains("Situação</bdi></strong>")) {
					linha = iso(file.readLine());
				}
			}
			dados[6] = noTag(linha).replaceAll("Situação", "").trim();
			linha = iso(file.readLine());
			while (!linha.contains("Idioma original</bdi></strong>")) {
				linha = iso(file.readLine());
			}
			dados[5] = noTag(linha).replaceAll("Idioma original", "").trim();
			linha = iso(file.readLine());
			while (!linha.contains("Orçamento</bdi></strong>")) {
				linha = iso(file.readLine());
			}
			linha = noTag(linha).replaceAll("Orçamento", "").trim();
			if (!linha.equals("-")) {
				dados[7] =
					linha.substring(1, linha.length()).replaceAll(",", "");
			} else {
				dados[7] = "0.00";
			}
			linha = iso(file.readLine());
			while (!linha.contains("<h4><bdi>Palavras-chave</bdi></h4>")) {
				linha = iso(file.readLine());
			}
			file.readLine();
			linha = iso(file.readLine()).trim();
			String temp = "";
			if (linha.equals("<ul>")) {
				while (!linha.equals("</ul>")) {
					temp += linha;
					linha = iso(file.readLine()).trim();
				}
				temp = noTag(temp.replaceAll("</a>", ", ")).trim();
				temp = temp.substring(0, temp.length() - 1);
			}
			dados[8] = temp;
			file.close();
			return dados;
		} catch (IOException e) {
			System.err.println(e);
			String[] erro = new String[1];
			return erro;
		}
	}

	public void ler(String arq) {
		String dados[] = reduzir(arq);
		this.setNome(dados[0]);
		this.setTitulo_original(dados[1]);
		this.setData_de_lancamento(dados[2]);
		this.setDuracao(dados[3]);
		this.setGenero(dados[4]);
		this.setIdioma_original(dados[5]);
		this.setSituacao(dados[6]);
		this.setOrcamento(Float.valueOf(dados[7]));
		this.setPalavras_chave(dados[8]);
	}

	public Filme clone() {
		Filme clone = new Filme();
		clone.setNome(this.getNome());
		clone.setTitulo_original(this.getTitulo_original());
		clone.setData_de_lancamento(this.getData_de_lancamento());
		clone.duracao = this.duracao;
		clone.setGenero(this.getGenero());
		clone.setIdioma_original(this.getIdioma_original());
		clone.setSituacao(this.getSituacao());
		clone.orcamento = this.orcamento;
		clone.setPalavras_chave(
			this.getPalavras_chave()
				.substring(1, this.getPalavras_chave().length() - 1)
		);
		return clone;
	}

	public void imprimir() {
		MyIO.print(
			this.getNome() +
			" " +
			this.getTitulo_original() +
			" " +
			this.getData_de_lancamento() +
			" " +
			this.getDuracao() +
			" " +
			this.getGenero() +
			" " +
			this.getIdioma_original() +
			" " +
			this.getSituacao() +
			" " +
			this.getOrcamento() +
			" " +
			this.getPalavras_chave()
		);
	}
}

class Tp3_5 {

	public static boolean isFim(String s) {
		String temp = s.toUpperCase();
		return (
			temp.length() == 3 &&
			temp.charAt(0) == 'F' &&
			temp.charAt(1) == 'I' &&
			temp.charAt(2) == 'M'
		);
	}

	public static void gerenciarLista(
		Lista filmes,
		String comandos[],
		int quant
	)
		throws Exception {
		for (int i = 0; i < quant; i++) {
			String temp[] = comandos[i].split(" ");
			int tam = comandos[i].length();
			if (temp[0].equals("II")) {
				filmes.inserirInicio(
					new Filme("/tmp/filmes/" + comandos[i].substring(3, tam))
				);
			} else if (temp[0].equals("IF")) {
				filmes.inserirFim(
					new Filme("/tmp/filmes/" + comandos[i].substring(3, tam))
				);
			} else if (temp[0].equals("I*")) {
				filmes.inserir(
					new Filme("/tmp/filmes/" + comandos[i].substring(6, tam)),
					Integer.valueOf(temp[1])
				);
			} else if (temp[0].equals("RI")) {
				MyIO.println("(R) " + filmes.removerInicio().getNome());
			} else if (temp[0].equals("RF")) {
				MyIO.println("(R) " + filmes.removerFim().getNome());
			} else {
				MyIO.println(
					"(R) " + filmes.remover(Integer.valueOf(temp[1])).getNome()
				);
			}
		}
	}

	public static void main(String[] args) {
		try {
			Lista filmes = new Lista(100);
			filmes.setInicio();
			MyIO.setCharset("UTF-8");
			String entrada[] = new String[1000];
			int numEntrada = 0;
			do {
				entrada[numEntrada] = MyIO.readLine();
			} while (!isFim(entrada[numEntrada++]));
			numEntrada--;
			for (int i = 0; i < numEntrada; i++) {
				filmes.inserirFim(new Filme("/tmp/filmes/" + entrada[i]));
			}
			filmes.sort();
			filmes.mostrar();
			FileWriter file = new FileWriter("536013_heapsort.txt");
			filmes.setTermino();
			file.write(filmes.log());
			file.close();
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}
