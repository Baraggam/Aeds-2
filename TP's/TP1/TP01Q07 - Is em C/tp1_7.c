#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>
#include <ctype.h>

bool isFim(char s[])
{
	return (
		strlen(s) == 3 &&
		toupper(s[0]) == 'F' &&
		toupper(s[1]) == 'I' &&
		toupper(s[2]) == 'M');
}

bool soVogal(char s[])
{
	bool ehVogal = true;
	for (int i = 0; i < strlen(s) && ehVogal; i++)
	{
		if (isdigit(s[i]))
		{ // Teste de letra ou número
			ehVogal = false;
		}
		else if ( // Teste de vogal ou Consoante
			!(
				toupper(s[i]) == 'A' ||
				toupper(s[i]) == 'E' ||
				toupper(s[i]) == 'I' ||
				toupper(s[i]) == 'O' ||
				toupper(s[i]) == 'U'))
		{
			ehVogal = false;
		}
	}
	return ehVogal;
}

bool soConsoante(char s[])
{
	bool ehConsoante = true;
	for (int i = 0; i < strlen(s) && ehConsoante; i++)
	{
		if (isdigit(s[i]))
		{ // Teste de letra ou número
			ehConsoante = false;
		}
		else if ( // Teste de vogal ou Consoante
			(
				toupper(s[i]) == 'A' ||
				toupper(s[i]) == 'E' ||
				toupper(s[i]) == 'I' ||
				toupper(s[i]) == 'O' ||
				toupper(s[i]) == 'U'))
		{
			ehConsoante = false;
		}
	}
	return ehConsoante;
}

bool soInteiro(char s[])
{
	bool ehInteiro = true;
	for (int i = 0; i < strlen(s) && ehInteiro; i++)
	{
		if (!isdigit(s[i]))
		{
			ehInteiro = false;
		}
	}
	return ehInteiro;
}

bool soReal(char s[])
{
	bool ehReal = true;
	int virgula = 0;
	for (int i = 0; i < strlen(s) && ehReal && virgula < 2; i++)
	{
		if (s[i] == ',' || s[i] == '.')
		{
			virgula++;
		}
		else if (!isdigit(s[i]))
		{
			ehReal = false;
		}
	}
	if (virgula == 2)
		ehReal = false;
	return ehReal;
}

// Converte Valores booleanos em SIM ou NAO
char *converter(bool b)
{
	static char resp[4];
	if (b)
	{
		strcpy(resp, "SIM");
	}
	if (!b)
	{
		strcpy(resp, "NAO");
	}
	return resp;
}

// Gerencia a resposta final mandando para os métodos necessários
char *escrever(char s[])
{
	static char resp[16];
	strcpy(resp, converter(soVogal(s)));
	strcat(resp, " ");
	strcat(resp, converter(soConsoante(s)));
	strcat(resp, " ");
	strcat(resp, converter(soInteiro(s)));
	strcat(resp, " ");
	strcat(resp, converter(soReal(s)));
	return resp;
}

int main()
{
	char entrada[1000];
	scanf("%[^\n]s", entrada);
	while (!isFim(entrada))
	{
		printf("%s\n", escrever(entrada));
		scanf(" %[^\n]s", entrada);
	}
	return 0;
}