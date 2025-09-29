# Kotlin Trees Library 🌳

Библиотека на Kotlin, реализующая классические структуры данных на деревьях:  
- **Binary Search Tree (BST)**  
- **2-3 Tree**

Сборка — **Gradle (Kotlin DSL)**.  

## Возможности

### Binary Search Tree
- Вставка (`insert`)
- Удаление (`delete`)
- Поиск (`search`, `contains`)
- Обход in-order через `iterator()`

### 2-3-4 Tree
- Вставка (`insert`) с автоматическим расщеплением узлов
- Удаление (`delete`) с слиянием и балансировкой
- Поиск (`search`, `contains`)
- Обход in-order через `iterator()`

## Установка и сборка

```bash
git clone https://github.com/spbu-coding-2024/trees-trees-team-14.git
cd trees-lib
./gradlew build
```

## Лицензия
[MIT](https://choosealicense.com/licenses/mit/)
