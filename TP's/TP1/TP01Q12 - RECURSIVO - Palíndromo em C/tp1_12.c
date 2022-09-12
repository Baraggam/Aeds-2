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

bool ehPalindromoRec(char s[], int i, int j)
{
	bool ehPalindromo = true;
	if (i < j)
	{
		ehPalindromo = s[i] == s[j];
		{
			if (ehPalindromo)
			{
				ehPalindromo = ehPalindromoRec(s, ++i, --j);
			}
		}
	}
	return ehPalindromo;
}

bool ehPalindromo(char s[])
{
	return ehPalindromoRec(s, 0, strlen(s) - 1);
}

// Gerencia a resposta final mandando para os métodos necessários
char *escrever(char s[])
{
	static char resp[4];
	if (ehPalindromo(s))
	{
		strcpy(resp, "SIM");
	}
	else
	{
		strcpy(resp, "NAO");
	}
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
