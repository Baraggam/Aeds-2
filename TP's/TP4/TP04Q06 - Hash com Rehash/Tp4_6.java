import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

class Hash {

	Filme tabela[];
	int m;
	private long inicio; //Tempo no início do programa
	private long termino; //Tempo no fim do programa
	private int comparacoes; //Contador de comparações
	private int movimentacoes; //Contador de movimentações

	public Hash(int m) {
		this.m = m;
		this.tabela = new Filme[this.m];
		for (int i = 0; i < m; i++) {
			tabela[i] = null;
		}
	}

	public int h(String f) {
		int ascii = 0;
		for (int i = 0; i < f.length(); i++) {
			ascii += f.charAt(i);
		}
		return ascii % m;
	}

	public int reh(String f) {
		int ascii = 0;
		for (int i = 0; i < f.length(); i++) {
			ascii += f.charAt(i);
		}
		return ++ascii % m;
	}

	public boolean inserir(Filme f) {
		boolean resp = false;
		if (f != null) {
			int pos = h(f.getTitulo_original());
			if (tabela[pos] == null) {
				tabela[pos] = f;
				resp = true;
			} else {
				pos = reh(f.getTitulo_original());
				if (tabela[pos] == null) {
					tabela[pos] = f;
					resp = true;
				}
			}
		}
		return resp;
	}

	public int pesquisar(String f) {
		int resp = -1;
		int pos = h(f);
		if (
			tabela[pos] != null &&
			tabela[pos].getTitulo_original().compareTo(f) == 0
		) {
			resp = pos;
		} else if (tabela[pos] != null) {
			pos = reh(f);
			if (
				tabela[pos] != null &&
				tabela[pos].getTitulo_original().compareTo(f) == 0
			) {
				resp = pos;
			}
		}
		return resp;
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

class Tp4_6 {

	public static boolean isFim(String s) {
		String temp = s.toUpperCase();
		return (
			temp.length() == 3 &&
			temp.charAt(0) == 'F' &&
			temp.charAt(1) == 'I' &&
			temp.charAt(2) == 'M'
		);
	}

	public static void main(String[] args) {
		try {
			MyIO.setCharset("UTF-8");
			Hash filmes = new Hash(21);
			String entrada[] = new String[1000];

			//Primeira parte: Inserindo os filmes
			int numEntrada = 0;
			do {
				entrada[numEntrada] = MyIO.readLine();
			} while (!isFim(entrada[numEntrada++]));
			numEntrada--;
			for (int i = 0; i < numEntrada; i++) {
				filmes.inserir(new Filme("/tmp/filmes/" + entrada[i]));
			}

			//Segunda parte: Pesquisando os filmes
			numEntrada = 0;
			do {
				entrada[numEntrada] = MyIO.readLine();
			} while (!isFim(entrada[numEntrada++]));
			numEntrada--;
			for (int i = 0; i < numEntrada; i++) {
				MyIO.println("=> " + entrada[i]);
				int pos = filmes.pesquisar(entrada[i]);
				System.out.println(pos > -1 ? "Posicao: " + pos : "NAO");
			}
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}
