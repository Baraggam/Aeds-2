import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

class No1 {

	public char elemento;
	public No1 esq, dir;
	public ArvoreBinaria2 arvoreBinaria2;

	public No1(char elemento) {
		this(elemento, null, null, null);
	}

	public No1(char elemento, No1 esq, No1 dir, ArvoreBinaria2 arvoreBinaria2) {
		this.elemento = elemento;
		this.esq = esq;
		this.dir = dir;
		this.arvoreBinaria2 = arvoreBinaria2;
	}
}

class ArvoreBinaria1 {

	private No1 raiz;
	private long inicio; //Tempo no início do programa
	private long termino; //Tempo no fim do programa
	private int comparacoes; //Contador de comparações
	private int movimentacoes; //Contador de movimentações

	public ArvoreBinaria1() {
		raiz = null;
	}

	public void inicializar() throws Exception {
		String letras = "DRZXVBFPUIGEJLHTAWSOMNKCYQ";
		for (int i = 0; i < letras.length(); i++) {
			raiz = inicializar(letras.charAt(i), raiz);
		}
	}

	public No1 inicializar(char c, No1 i) throws Exception {
		if (i == null) {
			i = new No1(c);
			i.arvoreBinaria2 = new ArvoreBinaria2();
		} else if (c < i.elemento) {
			i.esq = inicializar(c, i.esq);
		} else if (c > i.elemento) {
			i.dir = inicializar(c, i.dir);
		} else {
			throw new Exception("Erro ao inserir!");
		}
		return i;
	}

	public void inserir(Filme f) throws Exception {
		raiz = inserir(f, raiz);
	}

	private No1 inserir(Filme f, No1 i) throws Exception {
		if (i == null) {
			i = new No1(f.getTitulo_original().charAt(0));
			i.arvoreBinaria2 = new ArvoreBinaria2();
			i.arvoreBinaria2.inserir(f);
		} else if (f.getTitulo_original().charAt(0) < i.elemento) {
			i.esq = inserir(f, i.esq);
		} else if (f.getTitulo_original().charAt(0) > i.elemento) {
			i.dir = inserir(f, i.dir);
		} else {
			i.arvoreBinaria2.inserir(f);
		}
		return i;
	}

	public void remover(String f) throws Exception {
		raiz = remover(f, raiz);
	}

	private No1 remover(String f, No1 i) throws Exception {
		if (i == null) {
			throw new Exception("Erro ao remover!");
		} else if (f.charAt(0) < i.elemento) {
			i.esq = remover(f, i.esq);
		} else if (f.charAt(0) > i.elemento) {
			i.dir = remover(f, i.dir);
		} else {
			i.arvoreBinaria2.remover(f);
		}
		return i;
	}

	public int getAltura() {
		return getAltura(raiz, 0);
	}

	public int getAltura(No1 i, int altura) {
		if (i == null) {
			altura--;
		} else {
			int alturaEsq = getAltura(i.esq, altura + 1);
			int alturaDir = getAltura(i.dir, altura + 1);
			altura = (alturaEsq > alturaDir) ? alturaEsq : alturaDir;
		}
		return altura;
	}

	public void pesquisar(String f) {
		MyIO.print("=> " + f + "\nraiz ");
		pesquisar(f, raiz);
	}

	private void pesquisar(String f, No1 i) {
		if (i == null) {
			MyIO.print("NAO\n");
		} else if (f.charAt(0) == i.elemento) {
			i.arvoreBinaria2.pesquisar(f);
		} else if (f.charAt(0) < i.elemento) {
			MyIO.print("esq ");
			pesquisar(f, i.esq);
		} else {
			MyIO.print("dir ");
			pesquisar(f, i.dir);
		}
	}

	public void caminharCentral() {
		MyIO.print("[ ");
		caminharCentral(raiz);
		MyIO.println("]");
	}

	private void caminharCentral(No1 i) {
		if (i != null) {
			caminharCentral(i.esq);
			//i.arvoreBinaria2.caminharCentral();
			MyIO.println(i.elemento);
			caminharCentral(i.dir);
		}
	}

	public void caminharPre() {
		MyIO.print("[ ");
		caminharPre(raiz);
		MyIO.println("]");
	}

	private void caminharPre(No1 i) {
		if (i != null) {
			i.arvoreBinaria2.caminharPre();
			caminharPre(i.esq);
			caminharPre(i.dir);
		}
	}

	public void caminharPos() {
		MyIO.print("[ ");
		caminharPos(raiz);
		MyIO.println("]");
	}

	private void caminharPos(No1 i) {
		if (i != null) {
			caminharPos(i.esq);
			caminharPos(i.dir);
			i.arvoreBinaria2.caminharPos();
		}
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

class No2 {

	public Filme elemento;
	public No2 esq, dir;

	public No2(Filme elemento) {
		this(elemento, null, null);
	}

	public No2(Filme elemento, No2 esq, No2 dir) {
		this.elemento = elemento;
		this.esq = esq;
		this.dir = dir;
	}
}

class ArvoreBinaria2 {

	private No2 raiz;

	public ArvoreBinaria2() {
		raiz = null;
	}

	public void inserir(Filme f) throws Exception {
		raiz = inserir(f, raiz);
	}

	private No2 inserir(Filme f, No2 i) throws Exception {
		if (i == null) {
			i = new No2(f);
		} else if (
			f.getTitulo_original().compareTo(i.elemento.getTitulo_original()) <
			0
		) {
			i.esq = inserir(f, i.esq);
		} else if (
			f.getTitulo_original().compareTo(i.elemento.getTitulo_original()) >
			0
		) {
			i.dir = inserir(f, i.dir);
		} else {
			throw new Exception("Erro ao inserir!");
		}
		return i;
	}

	public void remover(String f) throws Exception {
		raiz = remover(f, raiz);
	}

	private No2 remover(String f, No2 i) throws Exception {
		if (i == null) {
			throw new Exception("Erro ao remover!");
		} else if (f.compareTo(i.elemento.getTitulo_original()) < 0) {
			i.esq = remover(f, i.esq);
		} else if (f.compareTo(i.elemento.getTitulo_original()) > 0) {
			i.dir = remover(f, i.dir);
		} else if (i.dir == null) {
			i = i.esq;
		} else if (i.esq == null) {
			i = i.dir;
		} else {
			i.esq = maiorEsq(i, i.esq);
		}
		return i;
	}

	private No2 maiorEsq(No2 i, No2 j) {
		if (j.dir == null) {
			i.elemento = j.elemento;
			j = j.esq;
		} else {
			j.dir = maiorEsq(i, j.dir);
		}
		return j;
	}

	public int getAltura() {
		return getAltura(raiz, 0);
	}

	public int getAltura(No2 i, int altura) {
		if (i == null) {
			altura--;
		} else {
			int alturaEsq = getAltura(i.esq, altura + 1);
			int alturaDir = getAltura(i.dir, altura + 1);
			altura = (alturaEsq > alturaDir) ? alturaEsq : alturaDir;
		}
		return altura;
	}

	public void pesquisar(String f) {
		this.pesquisar(f, raiz);
	}

	private void pesquisar(String f, No2 i) {
		if (i == null) {
			MyIO.print("NAO\n");
		} else if (f.compareTo(i.elemento.getTitulo_original()) == 0) {
			MyIO.print("SIM\n");
		} else if (f.compareTo(i.elemento.getTitulo_original()) < 0) {
			MyIO.print("ESQ ");
			pesquisar(f, i.esq);
		} else {
			MyIO.print("DIR ");
			pesquisar(f, i.dir);
		}
	}

	public void caminharCentral() {
		MyIO.print("[ ");
		caminharCentral(raiz);
		MyIO.println("]");
	}

	private void caminharCentral(No2 i) {
		if (i != null) {
			caminharCentral(i.esq);
			i.elemento.imprimir();
			caminharCentral(i.dir);
		}
	}

	public void caminharPre() {
		MyIO.print("[ ");
		caminharPre(raiz);
		MyIO.println("]");
	}

	private void caminharPre(No2 i) {
		if (i != null) {
			i.elemento.imprimir();
			caminharPre(i.esq);
			caminharPre(i.dir);
		}
	}

	public void caminharPos() {
		MyIO.print("[ ");
		caminharPos(raiz);
		MyIO.println("]");
	}

	private void caminharPos(No2 i) {
		if (i != null) {
			caminharPos(i.esq);
			caminharPos(i.dir);
			i.elemento.imprimir();
		}
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

class Tp4_2 {

	public static boolean isFim(String s) {
		String temp = s.toUpperCase();
		return (
			temp.length() == 3 &&
			temp.charAt(0) == 'F' &&
			temp.charAt(1) == 'I' &&
			temp.charAt(2) == 'M'
		);
	}

	public static void gerenciarArvore(
		ArvoreBinaria1 filmes,
		String comandos[],
		int quant
	)
		throws Exception {
		for (int i = 0; i < quant; i++) {
			String temp[] = comandos[i].split(" ");
			int tam = temp[0].length();
			if (temp[0].equals("I")) {
				filmes.inserir(
					new Filme(
						"/tmp/filmes/" +
						comandos[i].substring(tam + 1, comandos[i].length())
					)
				);
			} else {
				filmes.remover(
					comandos[i].substring(tam + 1, comandos[i].length())
				);
			}
		}
	}

	public static void main(String[] args) {
		try {
			MyIO.setCharset("UTF-8");
			ArvoreBinaria1 filmes = new ArvoreBinaria1();
			filmes.inicializar(); //Iniciando a árvore como foi pedido
			String entrada[] = new String[1000];
			int numEntrada = 0;

			//Primeira parte: Inserindo os filmes
			do {
				entrada[numEntrada] = MyIO.readLine();
			} while (!isFim(entrada[numEntrada++]));
			numEntrada--;
			for (int i = 0; i < numEntrada; i++) {
				filmes.inserir(new Filme("/tmp/filmes/" + entrada[i]));
			}

			//Segunda parte: Inserindo ou removendo os filmes
			numEntrada = 0;
			int quant = MyIO.readInt();
			for (int i = 0; i < quant; i++) {
				entrada[i] = MyIO.readLine();
			}
			gerenciarArvore(filmes, entrada, quant);

			//Terceira parte: Pesquisando os filmes
			numEntrada = 0;
			do {
				entrada[numEntrada] = MyIO.readLine();
			} while (!isFim(entrada[numEntrada++]));
			numEntrada--;
			for (int i = 0; i < numEntrada; i++) {
				filmes.pesquisar(entrada[i]);
			}
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}
