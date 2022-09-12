import javax.lang.model.element.Element;

class Celula {

	public int elemento;
	public Celula sup, esq, dir, inf;

	public Celula() {
		this(null, null, null, null);
	}

	public Celula(int elemento) {
		this(elemento, null, null, null, null);
	}

	public Celula(Celula sup, Celula esq, Celula dir, Celula inf) {
		this.sup = sup;
		this.esq = esq;
		this.dir = dir;
		this.inf = inf;
	}

	public Celula(
		int elemento,
		Celula sup,
		Celula esq,
		Celula dir,
		Celula inf
	) {
		this.elemento = elemento;
		this.sup = sup;
		this.esq = esq;
		this.dir = dir;
		this.inf = inf;
	}
}

class Matriz {

	private Celula inicio;
	private int linhas, colunas;

	public Matriz() {
		this(3, 3);
	}

	public Matriz(int linhas, int colunas) {
		this.linhas = linhas;
		this.colunas = colunas;
		this.inicio = new Celula();
		Celula aux = this.inicio, sup = null, supAux = null;
		for (int i = 0; i < linhas; i++) {
			for (int j = 1; j < colunas; j++) {
				Celula temp = new Celula(sup, aux, null, null);
				aux.dir = temp;
				aux = temp;
				if (sup != null) {
					sup.inf = aux;
					sup = sup.dir;
				}
			}
			if (i + 1 < linhas) {
				if (supAux == null) {
					supAux = this.inicio;
				} else {
					supAux = supAux.inf;
				}
				supAux.inf = new Celula(supAux, null, null, null);
				supAux.inf.sup = supAux;
				sup = supAux.dir;
				aux = supAux.inf;
			}
		}
	}

	public void atribuir(int elemento, int linha, int coluna) {
		if (linha < this.linhas && coluna < this.colunas) {
			Celula atual = this.inicio;
			for (int i = 0; i < linha; i++, atual = atual.inf);
			for (int j = 0; j < coluna; j++, atual = atual.dir);
			atual.elemento = elemento;
		}
	}

	public Matriz soma(Matriz b) {
		Matriz c = null;
		if (this.linhas == b.linhas && this.colunas == b.colunas) {
			c = new Matriz(this.linhas, this.colunas);
			Celula linha1, linha2, linha3, matriz1, matriz2, matriz3;
			linha1 = matriz1 = this.inicio;
			linha2 = matriz2 = b.inicio;
			linha3 = matriz3 = c.inicio;
			for (
				int i = 0;
				i < linhas;
				i++, linha1 = linha1.inf, matriz1 = linha1, linha2 =
					linha2.inf, matriz2 = linha2, linha3 = linha3.inf, matriz3 =
					linha3
			) {
				for (
					int j = 0;
					j < colunas;
					j++, matriz1 = matriz1.dir, matriz2 = matriz2.dir, matriz3 =
						matriz3.dir
				) {
					matriz3.elemento = matriz1.elemento + matriz2.elemento;
				}
			}
		}
		return c;
	}

	public Matriz multiplicacao(Matriz b) {
		Matriz c = null;
		if (this.colunas == b.linhas) {
			c = new Matriz(this.linhas, b.colunas);
			Celula linha1, linha2, linha3, matriz1, matriz2, matriz3;
			linha1 = matriz1 = this.inicio;
			linha2 = matriz2 = b.inicio;
			linha3 = matriz3 = c.inicio;
			for (
				int i = 0;
				i < this.colunas;
				i++, linha1 = linha1.inf, matriz1 = linha1, linha2 =
					b.inicio, matriz2 = linha2, linha3 = linha3.inf, matriz3 =
					linha3
			) {
				for (
					int k = 0;
					k < b.colunas;
					k++, matriz1 = linha1, linha2 = linha2.dir, matriz2 =
						linha2, matriz3 = matriz3.dir
				) {
					int soma = 0;
					for (
						int j = 0;
						j < b.linhas;
						j++, matriz1 = matriz1.dir, matriz2 = matriz2.inf
					) {
						soma += matriz1.elemento * matriz2.elemento;
					}
					matriz3.elemento = soma;
				}
			}
		}
		return c;
	}

	public boolean isQuadrada() {
		return (this.linhas == this.colunas);
	}

	public void mostrar() {
		for (Celula aux = this.inicio; aux != null; aux = aux.inf) {
			for (Celula atual = aux; atual != null; atual = atual.dir) {
				MyIO.print(atual.elemento + " ");
			}
			MyIO.println("");
		}
	}

	public void mostrarDiagonalPrincipal() {
		if (isQuadrada()) {
			for (Celula atual = this.inicio; atual != null; atual = atual.inf) {
				MyIO.print(atual.elemento + " ");
				if (atual.dir != null) {
					atual = atual.dir;
				}
			}
			MyIO.println("");
		}
	}

	public void mostrarDiagonalSecundaria() {
		if (isQuadrada()) {
			Celula atual;
			for (atual = this.inicio; atual.dir != null; atual = atual.dir);
			for (; atual != null; atual = atual.inf) {
				MyIO.print(atual.elemento + " ");
				if (atual.esq != null) {
					atual = atual.esq;
				}
			}
			MyIO.println("");
		}
	}
}

class Tp3_16 {

	public static void main(String args[]) {
		int testes = MyIO.readInt();
		Matriz matrizes[] = new Matriz[testes * 2];
		for (int i = 0; i < testes * 2; i++) { //Atribuindo as matrizes
			int linhas = MyIO.readInt();
			int colunas = MyIO.readInt();
			matrizes[i] = new Matriz(linhas, colunas);
			for (int j = 0; j < linhas * colunas; j++) {
				int elemento = MyIO.readInt();
				matrizes[i].atribuir(elemento, j / colunas, j % colunas);
			}
		}
		for (int i = 0; i < testes * 2; i += 2) { //Printando as matrizes
			Matriz soma = matrizes[i].soma(matrizes[i + 1]);
			Matriz multiplicacao = matrizes[i].multiplicacao(matrizes[i + 1]);
			matrizes[i].mostrarDiagonalPrincipal();
			matrizes[i].mostrarDiagonalSecundaria();
			soma.mostrar();
			multiplicacao.mostrar();
		}
	}
}
