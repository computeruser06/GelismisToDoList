import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TaskManager {
    private List<Task> tasks;
    private final String FILE_NAME = "tasks_advanced.txt";

    public TaskManager() {
        tasks = new ArrayList<>();
        loadFromFile();
    }

    public void addTask(String title, String description, Task.Priority priority) {
        int id = tasks.isEmpty() ? 1 : tasks.get(tasks.size() - 1).getId() + 1;
        Task newTask = new Task(id, title, description, priority);
        tasks.add(newTask);
        saveToFile();
        System.out.println("✨ Görev başarıyla eklendi!");
    }

    public void listTasks() {
        if (tasks.isEmpty()) {
            System.out.println("\n📭 Henüz kayıtlı bir görev yok.");
            return;
        }
        System.out.println("\n================ GÖREVLER LİSTESİ ================");
        for (Task task : tasks) {
            System.out.println(task);
            System.out.println("--------------------------------------------------");
        }
    }

    // Yeni Özellik: Önceliğe göre sıralı listeleme (Yüksekten düşüğe)
    public void listTasksByPriority() {
        if (tasks.isEmpty()) {
            System.out.println("\n📭 Henüz kayıtlı bir görev yok.");
            return;
        }

        List<Task> sortedList = new ArrayList<>(tasks);
        sortedList.sort(Comparator.comparing(Task::getPriority, (p1, p2) -> {
            return Integer.compare(getPriorityWeight(p2), getPriorityWeight(p1));
        }));

        System.out.println("\n========== ÖNCELİK SIRALI GÖREVLERİ ==========");
        for (Task task : sortedList) {
            System.out.println(task);
            System.out.println("--------------------------------------------------");
        }
    }

    private int getPriorityWeight(Task.Priority p) {
        switch (p) {
            case YUKSEK: return 3;
            case NORMAL: return 2;
            case DUSUK: return 1;
            default: return 0;
        }
    }

    public void completeTask(int id) {
        Task task = findTaskById(id);
        if (task != null) {
            task.setCompleted(true);
            saveToFile();
            System.out.println("🎉 Görev tamamlandı olarak işaretlendi!");
        } else {
            System.out.println("❌ Bu ID'ye sahip bir görev bulunamadı.");
        }
    }

    public void deleteTask(int id) {
        Task task = findTaskById(id);
        if (task != null) {
            tasks.remove(task);
            reassignIds();
            saveToFile();
            System.out.println("🗑️ Görev başarıyla silindi!");
        } else {
            System.out.println("❌ Bu ID'ye sahip bir görev bulunamadı.");
        }
    }

    private Task findTaskById(int id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                return task;
            }
        }
        return null;
    }

    private void reassignIds() {
        for (int i = 0; i < tasks.size(); i++) {
            tasks.get(i).setId(i + 1);
        }
    }

    public void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Task task : tasks) {
                writer.println(task.toFileString());
            }
        } catch (IOException e) {
            System.out.println("⚠️ Dosya kaydedilirken hata oluştu: " + e.getMessage());
        }
    }

    public void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            tasks.clear();
            while ((line = reader.readLine()) != null) {
                Task task = Task.fromFileString(line);
                if (task != null) {
                    tasks.add(task);
                }
            }
        } catch (IOException e) {
            System.out.println("⚠️ Dosya okunurken hata oluştu: " + e.getMessage());
        }
    }
}