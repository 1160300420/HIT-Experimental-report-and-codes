#define _CRT_SECURE_NO_WARNINGS
#include <iostream>
#include <cstring>
#include <cstdio>
#include <cstdlib>
#include <queue>
#include <stack>

using namespace std;

struct node
{
	int currentCharacter;
	int fatherCharacter;
} modelSet[100][100];

struct nextNode
{
	int state;
	int character;
} next[256];

struct outputNode
{
	int state;
	char str[100];
	bool available;
} output[256];

struct zhongyu
{
	int pos;
	int state;
}result[100];

queue<int>q;
int characterAppear[256];
int base[256];
int check[256];
int fail[256];
int currentState = 0;
int fatherState = 0;

int readyToInsert[256];
int index = 0;
int curNodeNum = 0;
int nextNodeNum = 0;
int inputNum;
char input[100][100];
int tongji = 0;
int currentPos;

void buildTable();
int gotoFunc(int state, int c);
void buildFailTable();
void buildOutputTable();

int main()
{
	freopen("C:/Users/lovebear96/Desktop/1.txt", "r", stdin);
	memset(input, 0, sizeof(input));
	scanf("%d", &inputNum);
	int rowLen;
	int maxRowLen = -1;
	for (int i = 0; i<inputNum; i++)
	{
		scanf(" %s", input[i]);
		rowLen = strlen(input[i]);
		if (rowLen>maxRowLen) maxRowLen = rowLen;
		for (int j = 0; j < rowLen; j++)
		{
			modelSet[i][j].currentCharacter = input[i][j];
			//printf("cur=%c  ",modelSet[i][j].currentCharacter);
			if (!j) modelSet[i][j].fatherCharacter = 0;
			else modelSet[i][j].fatherCharacter = modelSet[i][j - 1].currentCharacter;
			//printf("fat=%c\n",modelSet[i][j].fatherCharacter);
		}
	}

	memset(characterAppear, 0, sizeof(characterAppear));
	for (int i = 0; i < inputNum; i++)
	{
		characterAppear[modelSet[i][0].currentCharacter] = 1;
	}
	for (int j = 0; j < 256; j++)
	{
		if (characterAppear[j])
		{
			q.push(j);
			readyToInsert[index++] = j;
		}
	}
	curNodeNum = index;
	buildTable();

	int k = 1;
	while (!q.empty())
	{
		memset(characterAppear, 0, sizeof(characterAppear));
		int qhead = q.front();
		q.pop();
		curNodeNum--;
		for (int i = 0; i < inputNum; i++)
		{
			if (modelSet[i][k].currentCharacter == 0) continue;
			if (qhead == modelSet[i][k].fatherCharacter)
			{
				characterAppear[modelSet[i][k].currentCharacter] = 1;
			}
		}
		for (int j = 0; j < 256; j++)
		{
			if (characterAppear[j])
			{
				q.push(j);
				readyToInsert[index++] = j;
				nextNodeNum++;
			}
		}
		buildTable();
		if (!curNodeNum)
		{
			curNodeNum = nextNodeNum;
			k++;
			nextNodeNum = 0;
		}
	}
	printf("check:\n");
	for (int i = 0; i <= 12; i++)
	{
		printf("%d ", check[i]);
	}
	printf("\nnext:\n");
	for (int i = 0; i <= 12; i++)
	{
		printf("%d ", ::next[i].state);
	}
	printf("\nbase:\n");
	for (int i = 0; i <= currentState; i++)
	{
		printf("%d ", base[i]);
	}
	buildFailTable();
	printf("\nfail:\n");
	for (int i = 0; i <= currentState; i++)
	{
		printf("%d ", fail[i]);
	}
	buildOutputTable();

	system("pause");
	printf("\nlujing:\n");
	//char testchar[100]="yasherhs";
	char testchar[100];
	getchar();
	fgets(testchar,100,stdin);
	int nowState = gotoFunc(0, testchar[0]);
	printf("%d ", nowState);
	result[tongji].state = nowState;
	result[tongji++].pos = 1;
	for (currentPos = 1; currentPos < (int)strlen(testchar); currentPos++)
	{
		nowState = gotoFunc(nowState, testchar[currentPos]);
		printf("%d ", nowState);
		result[tongji].state = nowState;
		result[tongji++].pos = currentPos + 1;
	}
	printf("\n\nmingzhong:\n");
	for (int j = 0; j < tongji; j++)
	{
		for (int i = 0; i <= currentState; i++)
		{
			if (output[i].available == 1 && output[i].state == result[j].state)
				printf("%d   %s\n", result[j].pos - strlen(output[i].str), output[i].str);
		}
	}
	fclose(stdin);

	return 0;
}

void buildTable()
{
	if (!index)return;
	int j = 1;
	for (; j < 256; j++)
	{
		if (!::next[j].state) break;
	}
	base[fatherState] = j - (readyToInsert[0]);
	//adjust base[]
	bool flag = 1;
	while (flag)
	{
		int p = 0;
		for (; p < index; p++)
		{
			if ((::next[base[fatherState] + readyToInsert[p]].state) != 0) break;
		}
		if (p == index) flag = 0;
		else base[fatherState]++;
	}
	//deal check & next table
	for (int i = 0; i < index; i++)
	{
		::next[base[fatherState] + readyToInsert[i]].state = ++currentState;
		::next[base[fatherState] + readyToInsert[i]].character = readyToInsert[i];
		check[currentState] = fatherState;
	}

	memset(readyToInsert, 0, sizeof(0));
	index = 0;
	fatherState++;
}

void buildFailTable()
{
	for (int i = 0; i <= currentState; i++)
	{
		if (!check[i]) continue;
		else
		{
			for (int j = 0; j < 256; j++)
			{
				if (::next[j].state == i)
				{
					fail[i] = gotoFunc(fail[check[i]], ::next[j].character);
					//  printf("\ni=%d j=%d  %d %c\n",i,j,fail[check[i]],next[j].character);
					break;
				}
			}
		}
	}
}

int gotoFunc(int state, int c)
{
	int t = ::next[base[state] + c].state;
	if (check[t] == state) return t;
	else if (state == 0) return 0;
	else
	{
		printf("%d ", fail[state]);
		result[tongji].state = fail[state];
		result[tongji++].pos = currentPos;
		return gotoFunc(fail[state], c);
	}
}

void buildOutputTable()
{
	char keng1[256];
	for (int i = 1; i <= currentState; i++)
	{
		memset(keng1, 0, sizeof(keng1));
		output[i].state = i;
		int temp = i;
		int num = 0;
		while (temp)
		{
			for (int j = 0; j < 256; j++)
			{
				if (temp == ::next[j].state)
				{
					keng1[num++] = ::next[j].character;
					break;
				}
			}
			temp = check[temp];
		}
		int k = 0;
		for (int p = num - 1; p >= 0; p--)
		{
			output[i].str[k++] = keng1[p];
		}
		for (int q = 0; q < inputNum; q++)
		{
			if (!strcmp(input[q], output[i].str))
			{
				output[i].available = 1;
				break;
			}
		}
	}
}