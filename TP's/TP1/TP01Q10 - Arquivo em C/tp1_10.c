#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>
#include <ctype.h>

void escreverArquivo(char arq[], int quant)
{
	FILE *file = fopen(arq, "w");
	for (int i = 0; i < quant; i++)
	{
		float linha;
		scanf("%f", &linha);
		fprintf(file, "%.3f\n", linha); // Números com mais de 4 decimais estavam perdendo a precisão
	}
	fclose(file);
}

void lerArquivo(char arq[], int quant)
{
	FILE *file = fopen(arq, "r");
	fseek(file, -1, SEEK_END); //-1 para ler um valor válido
	long ponteiro = ftell(file);
	for (int i = 0; i < quant; i++)
	{
		fseek(file, --ponteiro, SEEK_SET); // Saindo dos '\n'
		int charAtual = fgetc(file);
		while (charAtual != (int)'\n' && ponteiro > 0)
		{
			fseek(file, --ponteiro, SEEK_SET);
			charAtual = fgetc(file);
		}
		fseek(file, ponteiro, SEEK_SET);
		float linha;
		fscanf(file, "%f", &linha);
		printf("%g\n", linha);
	}
	fclose(file);
}

int main()
{
	char arq[] = "arq.dat";
	int quant;
	scanf("%i", &quant);
	escreverArquivo(arq, quant);
	lerArquivo(arq, quant);
	return 0;
}