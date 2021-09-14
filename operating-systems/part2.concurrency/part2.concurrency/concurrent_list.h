#pragma once

typedef struct list_t* list_t;

void List_Init(list_t* L);

int List_Insert(list_t* L, int key);

int List_Lookup(list_t* L, int key);