#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>
#include <ctype.h>

///////////////////////////////////////////////////////////////FUNÇÕES FERRAMENTAS
char *substring(char *texto, int i, int j);
char *trim(char *texto);
char *replaceAll(char *texto, char *chave, char *termo);
char **split(char *texto, char *chave); // Necessita de free
char *ctos(char c);

///////////////////////////////////////////////////////////////CLASS DATE
typedef struct
{
	int dia, mes, ano;
} Date;

///////////////////////////////////////////////////////////////CLASS FILME
typedef struct
{
	char *nome,
		*titulo_original;
	Date data_de_lancamento[11];
	int duracao;
	char *genero,
		*idioma_original,
		*situacao;
	float orcamento;
	char **palavras_chave;
} Filme;

void setNome(Filme *f, char *nome)
{
	f->nome = malloc((strlen(nome) + 1) * sizeof(char));
	strcpy(f->nome, nome);
}

void setTitulo_original(Filme *f, char *titulo_original)
{
	f->titulo_original = malloc((strlen(titulo_original) + 1) * sizeof(char));
	strcpy(f->titulo_original, titulo_original);
}

void setData_de_lancamento(Filme *f, char *data_de_lancamento)
{
	char dia[3], mes[3], ano[5];
	strcpy(dia, substring(data_de_lancamento, 0, 2));
	strcpy(mes, substring(data_de_lancamento, 3, 5));
	strcpy(ano, substring(data_de_lancamento, 6, 10));
	if (dia[0] == '0')
	{
		strcpy(dia, substring(dia, 1, strlen(dia)));
	}
	if (mes[0] == '0')
	{
		strcpy(mes, substring(mes, 1, strlen(mes)));
	}
	if (ano[0] == '0')
	{
		strcpy(ano, substring(ano, 1, strlen(ano)));
	}
	sscanf(dia, "%i", &f->data_de_lancamento->dia);
	sscanf(mes, "%i", &f->data_de_lancamento->mes);
	sscanf(ano, "%i", &f->data_de_lancamento->ano);
}

// Irá lidar com número inteiro (150) ou com texto (2h 30m)
void setDuracao(Filme *f, char *duracao)
{
	f->duracao = 0;
	char temp[100];
	strcpy(temp, "");
	bool somenteNumero = true;
	int i = 0, aux = 0;
	for (; somenteNumero && i < strlen(duracao); i++)
	{
		if (duracao[i] == 'h' || duracao[i] == 'm')
		{
			somenteNumero = false;
		}
		else
		{
			strcat(temp, ctos(duracao[i]));
		}
	}
	if (somenteNumero)
	{
		sscanf(temp, "%i", &aux);
		f->duracao = aux;
	}
	else
	{
		if (duracao[i - 1] == 'h')
		{
			sscanf(temp, "%i", &aux);
			f->duracao = aux * 60;
			for (strcpy(temp, ""); i < strlen(duracao) && duracao[i] != 'm'; i++)
			{
				strcat(temp, ctos(duracao[i]));
			}
			if (strlen(temp) > 0)
			{
				sscanf(temp, "%i", &aux);
				f->duracao += aux;
			}
		}
		else
		{
			sscanf(temp, "%i", &aux);
			f->duracao += aux;
		}
	}
}

void setGenero(Filme *f, char *genero)
{
	f->genero = malloc((strlen(genero) + 1) * sizeof(char));
	strcpy(f->genero, genero);
}

void setIdioma_original(Filme *f, char *idioma_original)
{
	f->idioma_original = malloc((strlen(idioma_original) + 1) * sizeof(char));
	strcpy(f->idioma_original, idioma_original);
}

void setSituacao(Filme *f, char *situacao)
{
	f->situacao = malloc((strlen(situacao) + 1) * sizeof(char));
	strcpy(f->situacao, situacao);
}

void setOrcamento(Filme *f, float orcamento)
{
	f->orcamento = orcamento;
}

// Método feito para ser compatível tanto em Java quanto em C
void setPalavras_chave(Filme *f, char *palavras_chave)
{
	char **temp = split(palavras_chave, ", ");
	int quant;
	sscanf(temp[0], "%i", &quant);
	quant++;
	f->palavras_chave = malloc(quant * sizeof(char *));
	for (int i = 0; i < quant; ++i)
	{
		f->palavras_chave[i] = malloc((strlen(temp[i]) + 1) * sizeof(char));
		strcpy(f->palavras_chave[i], temp[i]);
	}
	free(temp);
}

char *getNome(Filme *f)
{
	return f->nome;
}

char *getTitulo_original(Filme *f)
{
	return f->titulo_original;
}

char *getData_de_lancamento(Filme *f)
{
	static char data_de_lancamento[11];
	strcpy(data_de_lancamento, "");
	char temp[5];
	if (f->data_de_lancamento->dia < 10)
	{
		strcpy(data_de_lancamento, "0");
		sprintf(temp, "%i/", f->data_de_lancamento->dia);
		strcat(data_de_lancamento, temp);
	}
	else
	{
		sprintf(temp, "%i/", f->data_de_lancamento->dia);
		strcat(data_de_lancamento, temp);
	}
	if (f->data_de_lancamento->mes < 10)
	{
		strcat(data_de_lancamento, "0");
		sprintf(temp, "%i/", f->data_de_lancamento->mes);
		strcat(data_de_lancamento, temp);
	}
	else
	{
		sprintf(temp, "%i/", f->data_de_lancamento->mes);
		strcat(data_de_lancamento, temp);
	}
	if (f->data_de_lancamento->ano < 10)
	{
		strcat(data_de_lancamento, "0");
		sprintf(temp, "%i", f->data_de_lancamento->ano);
		strcat(data_de_lancamento, temp);
	}
	else
	{
		sprintf(temp, "%i", f->data_de_lancamento->ano);
		strcat(data_de_lancamento, temp);
	}
	return data_de_lancamento;
}

int getDuracao(Filme *f)
{
	return f->duracao;
}

char *getGenero(Filme *f)
{
	return f->genero;
}

char *getIdioma_original(Filme *f)
{
	return f->idioma_original;
}

char *getSituacao(Filme *f)
{
	return f->situacao;
}

float getOrcamento(Filme *f)
{
	return f->orcamento;
}

char *getPalavras_chave(Filme *f)
{
	int quant;
	sscanf(f->palavras_chave[0], "%i", &quant);
	static char resp[1000];
	strcpy(resp, "[");
	strcat(resp, f->palavras_chave[1]);
	for (int i = 2; i < quant + 1; i++)
	{
		strcat(resp, ", ");
		strcat(resp, f->palavras_chave[i]);
	}
	strcat(resp, "]");
	return resp;
}

// Limpa termos inuteis do nome do filme
char *limparNome(char *linha)
{
	int parenteses = 0, i = strlen(linha) - 1;
	for (; parenteses < 2 && i >= 0; i--)
	{
		if (linha[i] == '(')
		{
			parenteses++;
		}
	}
	static char temp[1000];
	strcpy(temp, substring(linha, 0, ++i));
	return temp;
}

// Remove todas as tags da string recebida
char *noTag(char *linha)
{
	static char temp[1000];
	strcpy(temp, "");
	for (int i = 0; i < strlen(linha); i++)
	{
		if (linha[i] != '<')
		{
			strcat(temp, ctos(linha[i]));
		}
		else
		{
			while (linha[i] != '>')
			{
				i++;
			}
		}
	}
	return temp;
}

// Este método irá pecorrer toda o texto do arquivo e retornar um array de string contendo as informações relevantes
char **reduzir(char *arq)
{
	// Alocando um array de string com 9 linhas e 100 colunas
	char **dados = malloc(9 * sizeof(char *));
	for (int i = 0; i < 9; ++i)
	{
		dados[i] = malloc(10000 * sizeof(char));
	}
	FILE *file = fopen(arq, "rb");
	char linha[100000];
	char aux[100000]; // Usada para o uso do método replaceAll
	fscanf(file, "%[^\n]s", linha);
	while (!strstr(linha, "<title>"))
	{
		fscanf(file, " %[^\n]s", linha);
	}
	strcpy(dados[0], noTag(trim(limparNome(linha))));
	fscanf(file, " %[^\n]s", linha);
	while (!strstr(linha, "<span class=\"release\">"))
	{
		fscanf(file, " %[^\n]s", linha);
	}
	fscanf(file, " %[^\n]s", linha);
	strcpy(dados[2], trim(substring(linha, 0, strlen(linha) - 4)));
	fscanf(file, " %[^\n]s", linha);
	while (!strstr(linha, "<span class=\"genres\">"))
	{
		fscanf(file, " %[^\n]s", linha);
	}
	fscanf(file, " %[^\n]s", linha);
	strcpy(aux, replaceAll(linha, "</a>,", ","));
	strcpy(linha, trim(aux));
	strcpy(aux, replaceAll(linha, "</a>", ""));
	strcpy(linha, aux);
	strcpy(aux, replaceAll(linha, "&nbsp;", ""));
	strcpy(linha, aux);
	strcpy(dados[4], noTag(linha));
	fscanf(file, " %[^\n]s", linha);
	while (!strstr(linha, "<span class=\"runtime\">"))
	{
		fscanf(file, " %[^\n]s", linha);
	}
	fscanf(file, " %[^\n]s", linha);
	strcpy(aux, replaceAll(linha, " ", ""));
	strcpy(dados[3], aux);
	fscanf(file, " %[^\n]s", linha);
	while (!strstr(linha, "Título original</strong>") && !strstr(linha, "Situação</bdi></strong>"))
	{
		fscanf(file, " %[^\n]s", linha);
	}
	bool temTitulo = true;
	if (strstr(linha, "Título original</strong>"))
	{
		strcpy(aux, replaceAll(noTag(linha), "Título original", ""));
		strcpy(dados[1], trim(aux));
	}
	else
	{
		strcpy(dados[1], dados[0]);
		temTitulo = false;
	}
	if (temTitulo)
	{
		fscanf(file, " %[^\n]s", linha);
		while (!strstr(linha, "Situação</bdi></strong>"))
		{
			fscanf(file, " %[^\n]s", linha);
		}
	}
	strcpy(aux, replaceAll(noTag(linha), "Situação", ""));
	strcpy(dados[6], trim(aux));
	fscanf(file, " %[^\n]s", linha);
	while (!strstr(linha, "Idioma original</bdi></strong>"))
	{
		fscanf(file, " %[^\n]s", linha);
	}
	strcpy(aux, replaceAll(noTag(linha), "Idioma original", ""));
	strcpy(dados[5], trim(aux));
	fscanf(file, " %[^\n]s", linha);
	while (!strstr(linha, "Orçamento</bdi></strong>"))
	{
		fscanf(file, " %[^\n]s", linha);
	}
	strcpy(aux, replaceAll(noTag(linha), "Orçamento", ""));
	strcpy(linha, trim(aux));
	if (strcmp(linha, "-") != 0)
	{
		strcpy(aux, replaceAll(substring(linha, 1, strlen(linha)), ",", ""));
		strcpy(dados[7], aux);
	}
	else
	{
		strcpy(dados[7], "0.00");
	}
	fscanf(file, " %[^\n]s", linha);
	while (!strstr(linha, "<h4><bdi>Palavras-chave</bdi></h4>"))
	{
		fscanf(file, " %[^\n]s", linha);
	}
	fscanf(file, " %[^\n]s", linha);
	strcpy(linha, trim(linha));
	char temp[10000];
	strcpy(temp, "");
	if (strcmp(linha, "<ul>") == 0)
	{
		while (strcmp(linha, "</ul>") != 0)
		{
			strcat(temp, linha);
			fscanf(file, " %[^\n]s", linha);
			strcpy(linha, trim(linha));
		}
		strcpy(aux, replaceAll(temp, "</a>", ", "));
		strcpy(temp, trim(noTag(aux)));
		strcpy(temp, substring(temp, 0, strlen(temp) - 1));
	}
	strcpy(dados[8], temp);
	fclose(file);
	return dados;
}

void ler(Filme *f, char *arq)
{
	char **dados = reduzir(arq);
	setNome(f, dados[0]);
	setTitulo_original(f, dados[1]);
	setData_de_lancamento(f, dados[2]);
	setDuracao(f, dados[3]);
	setGenero(f, dados[4]);
	setIdioma_original(f, dados[5]);
	setSituacao(f, dados[6]);
	float aux;
	sscanf(dados[7], "%f", &aux);
	setOrcamento(f, aux);
	setPalavras_chave(f, dados[8]);
	free(dados);
}

Filme *clone(Filme *f)
{
	Filme *clone = (Filme *)malloc(sizeof(Filme));
	setNome(clone, getNome(f));
	setTitulo_original(clone, getTitulo_original(f));
	setData_de_lancamento(clone, getData_de_lancamento(f));
	clone->duracao = f->duracao;
	setGenero(clone, getGenero(f));
	setIdioma_original(clone, getIdioma_original(f));
	setSituacao(clone, getSituacao(f));
	clone->orcamento = f->orcamento;
	setPalavras_chave(clone, getPalavras_chave(f));
	return clone;
}

void imprimir(Filme *f)
{
	printf("%s %s %s %i %s %s %s %g %s",
		   getNome(f), getTitulo_original(f), getData_de_lancamento(f),
		   getDuracao(f), getGenero(f), getIdioma_original(f), getSituacao(f),
		   getOrcamento(f), getPalavras_chave(f));
}

Filme *newFilme(char *arq)
{
	Filme *filme = (Filme *)malloc(sizeof(Filme));
	ler(filme, arq);
	return filme;
}

Filme *newFilme2()
{
	Filme *filme = (Filme *)malloc(sizeof(Filme));
	return filme;
}

///////////////////////////////////////////////////////////////MAIN
bool isFim(char s[])
{
	return (
		strlen(s) == 3 &&
		toupper(s[0]) == 'F' &&
		toupper(s[1]) == 'I' &&
		toupper(s[2]) == 'M');
}

char *substring(char *texto, int i, int j)
{
	static char resp[1000];
	int tam = 0;
	for (; i < j; tam++, i++)
		resp[tam] = texto[i];
	resp[tam] = '\0';
	return resp;
}

char *trim(char *texto)
{
	bool trim = texto[0] == ' ';
	static char resp[1000];
	strcpy(resp, "");
	int i = 0, j = strlen(texto) - 1;
	while (texto[i] == ' ')
	{
		i++;
	}
	while (texto[j] == ' ')
	{
		j--;
	}
	for (; i <= j; i++)
	{
		strcat(resp, ctos(texto[i]));
	}
	return resp;
}

// ReplaceAll identico ao do Java
// NÃO PODE ser executado em sequência Ex: replaceAll(replaceAll, " ", ""), " ", "")
char *replaceAll(char *texto, char *chave, char *termo)
{
	static char resp[10000];
	strcpy(resp, "");
	for (int i = 0; i < strlen(texto); i++)
	{
		bool encontrado = false;
		for (int j = 0; !encontrado && texto[i + j] == chave[j] && i + j < strlen(texto); j++)
		{
			if (j == strlen(chave) - 1)
			{
				strcat(resp, termo);
				encontrado = true;
				i += j;
			}
		}
		if (!encontrado)
		{
			strcat(resp, ctos(texto[i]));
		}
	}
	return resp;
}

// Split identico ao do Java
// Primeiro indice contem o tamanho do array
// Necessário um free após o uso para evitar consumo elevado de memória
char **split(char *texto, char *chave)
{
	int quant = 1; // Tamanho do array
	int aux = 0;   // Início da String atual (auxiliar para cálculo do tamanho da String)
	int i = 0;
	for (; i < strlen(texto); i++)
	{
		bool encontrado = false;
		for (int j = 0; !encontrado && texto[i + j] == chave[j] && i + j < strlen(texto); j++)
		{
			if (j == strlen(chave) - 1)
			{
				encontrado = true;
				i += j;
				quant++;
			}
		}
	}
	char **dados = malloc((quant + 1) * sizeof(char *));
	char temp[1000];
	sprintf(temp, "%i", quant);
	dados[0] = malloc(((strlen(temp)) + 1) * sizeof(char));
	strcpy(dados[0], temp); // Primeiro valor do array de palavras chaves contem a quantidade de palavras
	quant = 1;
	for (int i = 0; i < strlen(texto); i++)
	{
		bool encontrado = false;
		for (int j = 0; !encontrado && texto[i + j] == chave[j] && i + j < strlen(texto); j++)
		{
			if (j == strlen(chave) - 1)
			{
				encontrado = true;
				dados[quant] = malloc((i - aux + 1) * sizeof(char));
				strcpy(dados[quant++], substring(texto, aux, i));
				i += j;
				aux = i + 1;
			}
		}
	}
	dados[quant] = malloc((i - aux + 1) * sizeof(char));
	strcpy(dados[quant], substring(texto, aux, strlen(texto)));
	return dados;
}

// Método para converter um único char em uma string para ser usado na função strcat()
char *ctos(char c)
{
	static char resp[2];
	resp[0] = c;
	resp[1] = '\0';
	return resp;
}

int main()
{
	char **entrada = malloc(100 * sizeof(char *));
	int numEntrada = 0;
	char aux[1000];
	scanf("%[^\n]s", aux);
	while (!isFim(aux))
	{
		entrada[numEntrada] = malloc((strlen(aux) + 1) * sizeof(char));
		strcpy(entrada[numEntrada++], aux);
		scanf(" %[^\n]s", aux);
	}
	Filme *filmes[numEntrada];
	char temp[1000];
	for (int i = 0; i < numEntrada; i++)
	{
		strcpy(temp, "/tmp/filmes/");
		strcat(temp, entrada[i]);
		filmes[i] = newFilme(temp);
		imprimir(filmes[i]);
		printf("\n");
	}
	free(entrada);
	return 0;
}