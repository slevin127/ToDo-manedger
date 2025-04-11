/*
 * это служебный класс, который управляет списком задач: добавляет, удаляет,
 * помечает как выполненные, фильтрует, сохраняет и загружает из файла.
 */


package org.example.service;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.interfaces.Storable;
import org.example.model.Task;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TaskManager implements Storable {

    // Список tasks будет основным хранилищем всех задач
    private List<Task> tasks = new ArrayList<Task>();

    public class TaskFilter {

        // Метод возвращает задачи по заданной категории
        // фильтрует задачи по категориям
        public List<Task> filterByCategory(String category) {
            List<Task> result = new ArrayList<>();
            for (Task task : tasks) {
                if (task.getCategory().equals(category)) {
                    result.add(task);
                }
            }
            return result;
        }

        // Метод возвращает задачи по статусу (true = выполненные, false = невыполненные)
        // фильтрует задачи по - выполнено не выполнено
        public List<Task> filterByStatus(boolean isCompleted) {
            List<Task> result = new ArrayList<>();
            for (Task task : tasks) {
                if (task.isCompleted() == isCompleted) {
                    result.add(task);
                }
            }
            return result;
        }

        // Метод фильтрует задачи по дате создания
        // принимает диапазон дат в виде строки: "yyyy-MM-dd HH:mm"

        public List<Task> filterByDate(String startDate, String endDate) {
            List<Task> result = new ArrayList<>();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");//нужен, чтобы превратить строку в LocalDateTime.
            try {
                LocalDateTime start = LocalDateTime.parse(startDate, formatter);
                LocalDateTime end = LocalDateTime.parse(endDate, formatter);

                for (Task task : tasks) {
                    if (!task.getCreatedAt().isBefore(start) && !task.getCreatedAt().isAfter(end)) {
                        result.add(task);
                    }
                }
            } catch (Exception e) {
                System.out.println("Ошибка при парсинге дат: " + e.getMessage());
            }

            return result;
        }
    }

    // Этот метод добавляет новую задачу в список
    public void addTask(Task task) {
        tasks.add(task);
    }

    // Этот метод возвращает весь список задач
    // Используется для отображения всех задач в интерфейсе
    public List<Task> getAllTasks() {
        return tasks;
    }

    //Позволяет отметить задачу по номеру как выполненную
    public void markComplete(int index) {
        if (index >= 0 && index < tasks.size())
            tasks.get(index).markCompleted();
    }

    //Позволяет удалить задачу по её индексу из списка.
    public void removeTask(int index) {
        if (index >= 0 && index < tasks.size())
            tasks.remove(index);

    }

    // Сохраняем в файл
    @Override
    public void saveToFile(String filename) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        try {
            // 👇 используем TypeReference, чтобы сохранить информацию о типах
            mapper.writerWithDefaultPrettyPrinter()
                    .forType(new TypeReference<List<Task>>() {})
                    .writeValue(new File(filename), tasks);
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении: " + e.getMessage());
        }
    }


    // Загружаем из файла
    @Override
    public void loadFromFile(String filename) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        try {
            Task[] loadedTasks = mapper.readValue(new File(filename), Task[].class);
            tasks = new ArrayList<>(Arrays.asList(loadedTasks));
        } catch (IOException e) {
            System.out.println("Ошибка при загрузке: " + e.getMessage());
        }
    }

    // Метод возвращает статистику задач: всего, выполнено, не выполнено
    public void printStats() {
        int total = tasks.size();
        int completed = 0;
        int notCompleted = 0;

        for (Task task : tasks) {
            if (task.isCompleted()) {
                completed++;
            } else {
                notCompleted++;
            }
        }

        System.out.println("📊 Статистика задач:");
        System.out.println("Всего задач: " + total);
        System.out.println("Выполнено: " + completed);
        System.out.println("Не выполнено: " + notCompleted);
    }
    // Метод ищет задачи по ключевому слову в названии или описании
    public List<Task> searchByKeyword(String keyword) {
        List<Task> result = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase();

        for (Task task : tasks) {
            if (task.getTitle().toLowerCase().contains(lowerKeyword)
                    || task.getDescription().toLowerCase().contains(lowerKeyword)) {
                result.add(task);
            }
        }

        return result;
    }

}
