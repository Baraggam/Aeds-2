import java.io.*;
import java.util.Scanner;

class No {

	public char elemento;
	public No esq, dir;

	public No(char elemento) {
		this(elemento, null, null);
	}

	public No(char elemento, No esq, No dir) {
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

	public void inserir(char c) throws Exception {
		raiz = inserir(c, raiz);
	}

	private No inserir(char c, No i) throws Exception {
		if (i == null) {
			i = new No(c);
		} else if (c < i.elemento) {
			i.esq = inserir(c, i.esq);
		} else if (c > i.elemento) {
			i.dir = inserir(c, i.dir);
		} else {
			throw new Exception("Erro ao inserir!");
		}
		return i;
	}

	public void remover(int c) throws Exception {
		raiz = remover(c, raiz);
	}

	private No remover(int c, No i) throws Exception {
		if (i == null) {
			throw new Exception("Erro ao remover!");
		} else if (c < i.elemento) {
			i.esq = remover(c, i.esq);
		} else if (c > i.elemento) {
			i.dir = remover(c, i.dir);
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
			System.out.print(i.elemento + " ");
			caminharCentral(i.dir);
		}
	}

	public void caminharPre() {
		caminharPre(raiz);
	}

	private void caminharPre(No i) {
		if (i != null) {
			System.out.print(i.elemento + " ");
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
			System.out.print(i.elemento + " ");
		}
	}

	public boolean pesquisar(char c) {
		return pesquisar(c, raiz);
	}

	private boolean pesquisar(char c, No i) {
		boolean resp;
		if (i == null) {
			resp = false;
		} else if (c == i.elemento) {
			resp = true;
		} else if (c < i.elemento) {
			resp = pesquisar(c, i.esq);
		} else {
			resp = pesquisar(c, i.dir);
		}
		return resp;
	}
}

class Tp4_9 {

	public static void gerenciarArvore(ArvoreBinaria arvore, String comando)
		throws Exception {
		String temp[] = comando.split(" ");
		switch (temp[0]) {
			case "I":
				arvore.inserir(temp[1].charAt(0));
				break;
			case "P":
				System.out.println(
					arvore.pesquisar(temp[1].charAt(0)) == true
						? temp[1] + " existe"
						: "nao existe"
				);
				break;
			case "INFIXA":
				arvore.caminharCentral();
				System.out.println("");
				break;
			case "PREFIXA":
				arvore.caminharPre();
				System.out.println("");
				break;
			case "POSFIXA":
				arvore.caminharPos();
				System.out.println("");
				break;
			default:
				System.out.println(""); //Erro
		}
	}

	public static void main(String[] args) {
		try {
			//Trasnformando o System.out em uma string para poder apagar valores indesejados
			ByteArrayOutputStream texto = new ByteArrayOutputStream();
			PrintStream ps = new PrintStream(texto);
			PrintStream old = System.out;
			System.setOut(ps);

			Scanner scan = new Scanner(System.in);
			ArvoreBinaria arvore = new ArvoreBinaria();
			while (scan.hasNext()) {
				gerenciarArvore(arvore, scan.nextLine());
			}

			//Voltando o sytem.out para seu valor padrão
			System.out.flush();
			System.setOut(old);
			System.out.print(texto.toString().replaceAll(" \n", "\n"));
			scan.close();
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}
