package org.example;


import org.example.model.Task;
import org.example.model.UrgentTask;
import org.example.service.TaskManager;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        TaskManager.TaskFilter filter = taskManager.new TaskFilter();
Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== ToDo Manager =====");
            System.out.println("1. Добавить обычную задачу");
            System.out.println("2. Добавить срочную задачу");
            System.out.println("3. Показать все задачи");
            System.out.println("4. Отметить задачу как выполненную");
            System.out.println("5. Удалить задачу");
            System.out.println("6. Фильтр по категории");
            System.out.println("7. Фильтр по статусу");
            System.out.println("8. Фильтр по дате");
            System.out.println("9. Поиск по ключевому слову");
            System.out.println("10. Показать статистику");
            System.out.println("11. Сохранить в файл");
            System.out.println("12. Загрузить из файла");
            System.out.println("0. Выход");
            System.out.print("Выберите действие: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1": {
                    System.out.println("=== Добавление обычной задачи ===");

                    System.out.print("Введите название задачи: ");
                    String title = scanner.nextLine();

                    System.out.print("Введите описание задачи: ");
                    String description = scanner.nextLine();

                    System.out.print("Введите категорию задачи (например: Работа, Дом, Учёба): ");
                    String category = scanner.nextLine();

                    Task newTask = new Task(title, description, category);
                    taskManager.addTask(newTask);

                    System.out.println("✅ Задача успешно добавлена.");
                    break;
                }
                case "2": {
                    System.out.println("=== Добавление срочной задачи ===");

                    System.out.print("Введите название задачи: ");
                    String title = scanner.nextLine();

                    System.out.print("Введите описание задачи: ");
                    String description = scanner.nextLine();

                    System.out.print("Введите категорию задачи (например: Работа, Дом, Учёба): ");
                    String category = scanner.nextLine();

                    System.out.print("Введите приоритет задачи (от 1 до 5): ");
                    int priorityLevel = Integer.parseInt(scanner.nextLine());


                    Task newUrgentTask = new UrgentTask(title, description, category, priorityLevel);
                    taskManager.addTask(newUrgentTask);

                    System.out.println("✅ Задача успешно добавлена.");
                    break;
            }
                // Показать все задачи

                case "3":{
                    System.out.println("=== Список всех задач ===");
                    List<Task> allTasks = taskManager.getAllTasks();
                    if (allTasks.isEmpty()) {
                        System.out.println("Задач пока нет.");
                    } else {
                        for(int i = 0 ; i < allTasks.size() ; i++) {
                            System.out.println((i + 1) + ". " + allTasks.get(i));
                        }
                    }
                    break;
                }
                case "4":
                    // Отметить задачу как выполненную
                {
                    System.out.println("=== Отметить задачу как выполненную ===");
                    List<Task> allTasks = taskManager.getAllTasks();
                    if (allTasks.isEmpty()) {
                        System.out.println("Нет задач для выполнения.");
                        break;
                    }

                    for (int i = 0; i < allTasks.size(); i++) {
                        System.out.println((i + 1) + ". " + allTasks.get(i));
                    }

                    System.out.print("Введите номер задачи, которую хотите отметить как выполненную: ");
                    try {
                        int taskNumber = Integer.parseInt(scanner.nextLine());
                        int index = taskNumber - 1;

                        taskManager.markComplete(index);
                        System.out.println("✅ Задача отмечена как выполненная.");
                    } catch (NumberFormatException e) {
                        System.out.println("Ошибка: Введите корректный номер задачи.");
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Ошибка: Номер задачи вне допустимого диапазона.");
                    }

                    break;
                }
                case "5":
                    // Удалить задачу
                {
                    System.out.println("=== Удаление задачи ===");

                    List<Task> allTasks = taskManager.getAllTasks();

                    if (allTasks.isEmpty()) {
                        System.out.println("Нет задач для удаления.");
                        break;
                    }

                    for (int i = 0; i < allTasks.size(); i++) {
                        System.out.println((i + 1) + ". " + allTasks.get(i));
                    }

                    System.out.print("Введите номер задачи, которую хотите удалить: ");
                    try {
                        int taskNumber = Integer.parseInt(scanner.nextLine());
                        int index = taskNumber - 1;

                        taskManager.removeTask(index);
                        System.out.println("Задача успешно удалена.");
                    } catch (NumberFormatException e) {
                        System.out.println("Ошибка: Введите корректный номер задачи.");
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Ошибка: Номер задачи вне допустимого диапазона.");
                    }

                    break;
                }
                case "6":
                    // Фильтр по категории
                {
                    System.out.println("=== Фильтрация по категории ===");
                    System.out.print("Введите название категории (например: Работа, Дом, Учёба): ");
                    String category = scanner.nextLine();

                    List<Task> filteredTasks = filter.filterByCategory(category);

                    if (filteredTasks.isEmpty()) {
                        System.out.println("Задачи с категорией '" + category + "' не найдены.");
                    } else {
                        System.out.println("Задачи в категории '" + category + "':");
                        for (int i = 0; i < filteredTasks.size(); i++) {
                            System.out.println((i + 1) + ". " + filteredTasks.get(i));
                        }
                    }

                    break;
                }
                case "7":
                    // Фильтр по статусу
                {
                    System.out.println("=== Фильтрация по статусу ===");
                    System.out.println("1. Показать только выполненные задачи");
                    System.out.println("2. Показать только невыполненные задачи");
                    System.out.print("Выберите вариант: ");
                    String statusChoice = scanner.nextLine();

                    boolean isCompleted;
                    if (statusChoice.equals("1")) {
                        isCompleted = true;
                    } else if (statusChoice.equals("2")) {
                        isCompleted = false;
                    } else {
                        System.out.println("Некорректный выбор. Возврат в меню.");
                        break;
                    }

                    List<Task> filtered = filter.filterByStatus(isCompleted);

                    if (filtered.isEmpty()) {
                        System.out.println("Задач с выбранным статусом не найдено.");
                    } else {
                        System.out.println("Результаты фильтрации:");
                        for (int i = 0; i < filtered.size(); i++) {
                            System.out.println((i + 1) + ". " + filtered.get(i));
                        }
                    }
                    break;
                }
                case "8":
                    // Фильтр по дате
                {
                    System.out.println("=== Фильтрация по дате создания задачи ===");
                    System.out.println("Формат ввода: yyyy-MM-dd HH:mm");

                    System.out.print("Введите начальную дату и время: ");
                    String start = scanner.nextLine();

                    System.out.print("Введите конечную дату и время: ");
                    String end = scanner.nextLine();

                    List<Task> filtered = filter.filterByDate(start, end);

                    if (filtered.isEmpty()) {
                        System.out.println("Задачи в указанном диапазоне не найдены.");
                    } else {
                        System.out.println("Найдено задач: " + filtered.size());
                        for (int i = 0; i < filtered.size(); i++) {
                            System.out.println((i + 1) + ". " + filtered.get(i));
                        }
                    }

                    break;
            }
                case "9":
                    // Поиск по ключевому слову
                {
                    System.out.println("=== Поиск задач по ключевому слову ===");
                    System.out.print("Введите слово или фразу для поиска: ");
                    String keyword = scanner.nextLine();

                    List<Task> found = taskManager.searchByKeyword(keyword);

                    if (found.isEmpty()) {
                        System.out.println("По запросу ничего не найдено.");
                    } else {
                        System.out.println("Найдено задач: " + found.size());
                        for (int i = 0; i < found.size(); i++) {
                            System.out.println((i + 1) + ". " + found.get(i));
                        }
                    }

                    break;
                }

                case "10":
                    // Показать статистику
                {
                    System.out.println("=== Статистика задач ===");
                    taskManager.printStats();
                    break;
                }


                case "11":
                    // Сохранить в файл
                {
                    System.out.println("=== Сохранение задач в файл ===");
                    System.out.print("Введите имя файла (например: tasks.json): ");
                    String filename = scanner.nextLine();

                    taskManager.saveToFile(filename);
                    System.out.println("✅ Задачи успешно сохранены в файл: " + filename);
                    break;
                }

                case "12":
                    // Загрузить из файла
                {
                    System.out.println("=== Загрузка задач из файла ===");
                    System.out.print("Введите имя файла (например: tasks.json): ");
                    String filename = scanner.nextLine();

                    taskManager.loadFromFile(filename);
                    System.out.println("✅ Задачи успешно загружены из файла: " + filename);
                    break;
                }
                case "0":
                    System.out.println("Выход...");
                    return;
                default:
                    System.out.println("Некорректный ввод. Повторите.");
            }
        }
    }

    }

