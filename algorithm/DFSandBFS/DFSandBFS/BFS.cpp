#include<iostream>
#include<cmath>

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
	short int bfs_dir;
}element;

struct queue {
	element data;
	struct queue* link;
};

struct save_pre_pos {
	element cur_pos;
	element pre_pos;
	struct save_pre_pos* link;
};

struct distance_dir_index {
	int distance;
	int index;
};

struct for_queue_sort {
	struct queue *addr;
	int dist;
};

void init(struct queue**, struct queue**, struct save_pre_pos**, element*);
void add_queue(struct queue**, struct queue**, element*);
element delete_queue(struct queue**);
void add_cur_pos_circlelist(element*, element*, struct save_pre_pos**);
void sort_dir(short int*, short int, short int);
void bfs_maze();

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

int main() {
	system("cls");
	cout << "\n\t [BFS 미로찾기]" << endl << endl;

	bfs_maze();
	cout << "아무 키를 누르면 종료됩니다." << endl;
	system("pause");

}

void init(struct queue** r, struct queue** f, struct save_pre_pos** p, element* visit_arr) {
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

	short int dir[8];
	element temp;
	temp.col = 1;
	temp.row = 1;

	sort_dir(dir, temp.col, temp.row);

	for (int i = 0; i < 8; ++i) {
		temp.dir[i] = dir[i];
	}

	add_queue(r, f, &temp);

	(*p) = new struct save_pre_pos;
	(*p)->cur_pos.col = -1;
	(*p)->cur_pos.row = -1;
	(*p)->pre_pos.col = -1;
	(*p)->pre_pos.row = -1;
	(*p)->link = (*p);
}


void bfs_maze() {
	char maze_draw[ROWS][COLS][3];
	int found = FALSE, next_row, next_col, path_index = 0, cnt = 0, dir_index = 0, visit_index = 1, answer_cnt = 0;
	short int row, col, dir[8];
	int mark[13][17] = { 0 };
	element pos, path_arr[100], temp, visit_node_arr[100];
	struct queue *rear = NULL, *front = NULL;
	struct save_pre_pos *circle_list_head = NULL, *mov_point = NULL;

	mark[1][1] = 1;
	init(&rear, &front, &circle_list_head, visit_node_arr);

	while (front && !found) {
		pos = delete_queue(&front);
		row = pos.row;
		col = pos.col;
		for (int i = 0; i < 8; ++i) {
			dir[i] = pos.dir[i];
		}
		temp = pos;

		while (cnt < 8 && !found) {
			next_row = row + move[dir[dir_index]].x;
			next_col = col + move[dir[dir_index++]].y;
			if (next_row == EXIT_ROW && next_col == EXIT_COL) {
				found = TRUE;
			}
			else if (maze[next_row][next_col] != 1 && mark[next_row][next_col] != 1) {
				mark[next_row][next_col] = 1;
				pos.row = next_row;
				pos.col = next_col;
				sort_dir(pos.dir, pos.col, pos.row);
				add_queue(&rear, &front, &pos);
				visit_node_arr[visit_index++] = pos;
				add_cur_pos_circlelist(&temp, &pos, &circle_list_head);
				++cnt;
			}
			else {
				++cnt;
			}
		}
		cnt = 0;
		dir_index = 0;
	}
	path_arr[path_index].row = row;
	path_arr[path_index].col = col;

	mov_point = circle_list_head;

	if (found) {
		while (1) {
			mov_point = mov_point->link;
			if (mov_point->cur_pos.row == path_arr[path_index].row && mov_point->cur_pos.col == path_arr[path_index].col) {
				path_arr[++path_index].row = mov_point->pre_pos.row;
				path_arr[path_index].col = mov_point->pre_pos.col;
			}

			if (path_arr[path_index].row == 1 && path_arr[path_index].col == 1) {
				break;
			}
		}

		for (int i = 0; i <= path_index; ++i) {
			mark[path_arr[i].row][path_arr[i].col] = 2;
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
		strcpy_s(maze_draw[START_ROW][START_COL], "Ｓ");
		strcpy_s(maze_draw[EXIT_ROW][EXIT_COL], "Ｆ");
		for (int i = 0; i < ROWS; ++i) {
			for (int j = 0; j < COLS; ++j) {
				cout << maze_draw[i][j];
			}
			cout << endl;
		}
		cout << "\n\t답 경로 무늬 : ☆" << endl;
		cout << "\t방문경로무늬 : ◇" << endl;
		cout << "\t답 경로 길이 : " << answer_cnt << endl;
		cout << "\t방문 노드 수 : " << visit_index + 1 << endl << endl;
	}
	else
		printf("The maze does not have a path \n");

	while (1) {
		mov_point = circle_list_head->link;
		circle_list_head->link = mov_point->link;
		if (circle_list_head->link == circle_list_head)
			break;

		delete mov_point;
	}
	delete circle_list_head;
}


void add_queue(struct queue** r, struct queue** f, element* data) {
	struct queue *temp = new struct queue;
	if (!temp) {
		cout << "error!, Memory is full" << endl;
		exit(1);
	}

	temp->data = *data;
	temp->link = NULL;

	if (*f) {
		(*r)->link = temp;
	}
	else {
		(*f) = temp;
	}
	(*r) = temp;
}

element delete_queue(struct queue** f) {
	struct queue* temp = *f;
	element item;

	if (!(*f)) {
		cout << "error!, Queue is empty" << endl;
	}

	item = (*f)->data;
	*f = temp->link;
	delete temp;

	return item;
}


void add_cur_pos_circlelist(element* pre_data, element* data, struct save_pre_pos** p) {
	struct save_pre_pos* temp = new struct save_pre_pos;
	struct save_pre_pos* final_link = (*p);
	temp->cur_pos = *data;
	temp->pre_pos = *pre_data;

	for (; final_link->link != (*p); final_link = final_link->link);

	final_link->link = temp;
	temp->link = (*p);
}

void sort_dir(short int *d, short int col, short int row) {
	struct distance_dir_index dist[8];
	int next_row, next_col;

	for (int i = 0; i < 8; ++i) {
		next_row = row + move[i].x;
		next_col = col + move[i].y;
		dist[i].distance = (int)sqrt((double)(EXIT_COL - next_col)*(EXIT_COL - next_col) + (EXIT_ROW - next_row)*(EXIT_ROW - next_row));
		dist[i].index = i;
	}

	for (int i = 0; i < 7; ++i) {
		for (int j = i + 1; j < 8; ++j) {
			if (dist[i].distance > dist[j].distance) {
				int temp = dist[i].distance;
				dist[i].distance = dist[j].distance;
				dist[j].distance = temp;

				temp = dist[i].index;
				dist[i].index = dist[j].index;
				dist[j].index = temp;
			}
		}
	}

	for (int i = 0; i < 8; ++i) {
		d[i] = dist[i].index;
	}

}