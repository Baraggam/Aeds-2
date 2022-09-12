import java.io.*;
import java.util.Scanner;

class No {

	public int elemento;
	public No esq, dir;

	public No(int elemento) {
		this(elemento, null, null);
	}

	public No(int elemento, No esq, No dir) {
		this.elemento = elemento;
		this.esq = esq;
		this.dir = dir;
	}
}

class ArvoreBinaria {

	private No raiz;

	public ArvoreBinaria() {
		raiz = null;
	}

	public void inserir(int x) throws Exception {
		raiz = inserir(x, raiz);
	}

	private No inserir(int x, No i) throws Exception {
		if (i == null) {
			i = new No(x);
		} else if (x < i.elemento) {
			i.esq = inserir(x, i.esq);
		} else if (x > i.elemento) {
			i.dir = inserir(x, i.dir);
		} else {
			throw new Exception("Erro ao inserir!");
		}
		return i;
	}

	public void remover(int x) throws Exception {
		raiz = remover(x, raiz);
	}

	private No remover(int x, No i) throws Exception {
		if (i == null) {
			throw new Exception("Erro ao remover!");
		} else if (x < i.elemento) {
			i.esq = remover(x, i.esq);
		} else if (x > i.elemento) {
			i.dir = remover(x, i.dir);
		} else if (i.dir == null) {
			i = i.esq;
		} else if (i.esq == null) {
			i = i.dir;
		} else {
			i.esq = maiorEsq(i, i.esq);
		}
		return i;
	}

	private No maiorEsq(No i, No j) {
		if (j.dir == null) {
			i.elemento = j.elemento;
			j = j.esq;
		} else {
			j.dir = maiorEsq(i, j.dir);
		}
		return j;
	}

	public void caminharCentral() {
		caminharCentral(raiz);
	}

	private void caminharCentral(No i) {
		if (i != null) {
			caminharCentral(i.esq);
			System.out.print(" " + i.elemento);
			caminharCentral(i.dir);
		}
	}

	public void caminharPre() {
		caminharPre(raiz);
	}

	private void caminharPre(No i) {
		if (i != null) {
			System.out.print(" " + i.elemento);
			caminharPre(i.esq);
			caminharPre(i.dir);
		}
	}

	public void caminharPos() {
		caminharPos(raiz);
	}

	private void caminharPos(No i) {
		if (i != null) {
			caminharPos(i.esq);
			caminharPos(i.dir);
			System.out.print(" " + i.elemento);
		}
	}
}

class Tp4_8 {

	public static void imprimir(ArvoreBinaria arvore, int caso) {
		System.out.println("Case " + caso + ":");
		System.out.print("Pre.:");
		arvore.caminharPre();
		System.out.print("\nIn..:");
		arvore.caminharCentral();
		System.out.print("\nPost:");
		arvore.caminharPos();
		System.out.println("");
	}

	public static void main(String[] args) {
		try {
			//Trasnformando o System.out em uma string para poder apagar valores indesejados
			ByteArrayOutputStream texto = new ByteArrayOutputStream();
			PrintStream ps = new PrintStream(texto);
			PrintStream old = System.out;
			System.setOut(ps);

			Scanner scan = new Scanner(System.in);
			int casos = scan.nextInt();
			ArvoreBinaria arvore[] = new ArvoreBinaria[casos];
			for (int i = 0; i < casos; i++) {
				arvore[i] = new ArvoreBinaria();
				int quant = scan.nextInt();
				for (int j = 0; j < quant; j++) {
					arvore[i].inserir(scan.nextInt());
				}
				imprimir(arvore[i], i + 1);
			}
			//Voltando o sytem.out para seu valor padrão
			System.out.flush();
			System.setOut(old);
			System.out.print(texto.toString().replaceAll("\nC", "\n\nC"));
			scan.close();
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}
