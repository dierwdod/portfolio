#include<iostream>

using std::cout;
using std::endl;
using std::cin;

const int MAX_STACK_SIZE = 100;
const int EXIT_ROW = 11;
const int EXIT_COL = 15;
const int START_ROW = 1;
const int START_COL = 1;
const int ROWS = 13;
const int COLS = 17;

enum { FALSE, TRUE };

typedef struct {
	short int x;
	short int y;
} offsets;
offsets move[8];

typedef struct {
	short int row;
	short int col;
	short int dir[8];
	short int dfs_dir;
}element;
element stack[MAX_STACK_SIZE];

struct distance_dir_index {
	int distance;
	int index;
};

const int maze[ROWS][COLS] = {
	{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
	{ 1, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1 },
	{ 1, 1, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1 },
	{ 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1 },
	{ 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1 },
	{ 1, 1, 0, 1, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1, 1, 0, 1 },
	{ 1, 0, 1, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1 },
	{ 1, 0, 1, 0, 1, 1, 0, 0, 1, 0, 0, 1, 1, 1, 1, 1, 1 },
	{ 1, 0, 0, 1, 0, 0, 1, 1, 0, 1, 1, 1, 0, 0, 0, 1, 1 },
	{ 1, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 1, 0, 0, 1 },
	{ 1, 0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 0, 1 },
	{ 1, 0, 1, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 1 },
	{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }
};

void init(element*);
void add_stack(int*, element*);
element delete_stack(int*);
void dfs_maze();

int main() {
	system("cls");
	cout << "\n\t [DFS 미로찾기]" << endl << endl;

	dfs_maze();
	cout << "아무 키를 누르면 종료됩니다." << endl;
	system("pause");
}

void init(element* visit_arr) {
	move[0].x = -1;
	move[0].y = 0;
	move[1].x = -1;
	move[1].y = 1;
	move[2].x = 0;
	move[2].y = 1;
	move[3].x = 1;
	move[3].y = 1;
	move[4].x = 1;
	move[4].y = 0;
	move[5].x = 1;
	move[5].y = -1;
	move[6].x = 0;
	move[6].y = -1;
	move[7].x = -1;
	move[7].y = -1;

	stack[0].row = 1;
	stack[0].col = 1;
	stack[0].dfs_dir = 2;
}


void dfs_maze() {
	char maze_draw[ROWS][COLS][3];
	int top = 0, found = FALSE, next_row, next_col, visit_index = 1, cnt = 0, answer_cnt = 0;
	short int row, col, dir;
	int mark[13][17] = { 0 };
	mark[1][1] = 1;
	element pos, visit_node_arr[100];

	init(visit_node_arr);

	while (top > -1 && !found) {
		pos = delete_stack(&top);
		row = pos.row;
		col = pos.col;
		dir = pos.dfs_dir;

		while (dir < 8 && !found) {
			next_row = row + move[dir].x;
			next_col = col + move[dir].y;
			if (next_row == EXIT_ROW && next_col == EXIT_COL) {
				found = TRUE;
			}
			else if (maze[next_row][next_col] != 1 && mark[next_row][next_col] != 1) {
				mark[next_row][next_col] = 1;
				pos.row = row;
				pos.col = col;
				pos.dfs_dir = ++dir;
				add_stack(&top, &pos);
				visit_node_arr[visit_index++] = pos;
				row = next_row;
				col = next_col;
				dir = 0;
			}
			else {
				++dir;
			}
		}
	}
	if (found) {
		for (int i = 0; i <= top; ++i) {
			mark[stack[i].row][stack[i].col] = 2;
		}
		for (int i = 0; i < ROWS; ++i) {
			for (int j = 0; j < COLS; ++j) {
				if (maze[i][j] == 1 && mark[i][j] == 0)
					strcpy_s(maze_draw[i][j], "■");
				else if (maze[i][j] == 0 && mark[i][j] == 1)
					strcpy_s(maze_draw[i][j], "△");
				else if (maze[i][j] == 0 && mark[i][j] == 2) {
					strcpy_s(maze_draw[i][j], "★");
					++answer_cnt;
				}
				else if (maze[i][j] == 0 && mark[i][j] == 0)
					strcpy_s(maze_draw[i][j], "　");
			}
		}
		strcpy_s(maze_draw[row][col], "★");
		strcpy_s(maze_draw[START_ROW][START_COL], "Ｓ");
		strcpy_s(maze_draw[EXIT_ROW][EXIT_COL], "Ｆ");

		for (int i = 0; i < ROWS; ++i) {
			for (int j = 0; j < COLS; ++j) {
				cout << maze_draw[i][j];
			}
			cout << endl;
		}
		cout << "\n\t답 경로 무늬 : ★" << endl;
		cout << "\t방문경로무늬 : △" << endl;
		cout << "\t최종경로길이 : " << answer_cnt << endl;
		cout << "\t방문 노드 수 : " << visit_index + 1 << endl << endl;
	}
	else {
		cout << "\t경로를 찾을 수 없습니다!!!\n" << endl;
	}
}

void add_stack(int *t, element *p) {
	++(*t);
	stack[*t] = *p;
}

element delete_stack(int *t) {
	element temp;
	temp = stack[*t];
	--(*t);
	return temp;
}