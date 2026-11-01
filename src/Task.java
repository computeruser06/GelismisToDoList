import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum Priority {
        DUSUK("🟢 Düşük"),
        NORMAL("🟡 Normal"),
        YUKSEK("🔴 Yüksek");

        private final String label;
        Priority(String label) { this.label = label; }
        public String getLabel() { return label; }
    }

    private int id;
    private String title;
    private String description;
    private Priority priority;
    private boolean isCompleted;
    private String createdAt;

    public Task(int id, String title, String description, Priority priority) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.isCompleted = false;

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.createdAt = dtf.format(LocalDateTime.now());
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }

    public boolean isCompleted() { return isCompleted; }
    public void setCompleted(boolean completed) { isCompleted = completed; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        String status = isCompleted ? "✅ [Tamamlandı]" : "⏳ [Bekliyor]";
        return String.format("[%d] %s | Öncelik: %s - %s\n    Açıklama: %s\n    Oluşturulma: %s",
                id, status, priority.getLabel(), title, description, createdAt);
    }

    // Dosyaya yazmak için format (Öncelik bilgisi eklendi)
    public String toFileString() {
        return id + ";" + title + ";" + description + ";" + priority.name() + ";" + isCompleted + ";" + createdAt;
    }

    // Dosyadan okuma
    public static Task fromFileString(String line) {
        String[] parts = line.split(";", 6);
        if (parts.length < 6) return null;

        try {
            Task task = new Task(Integer.parseInt(parts[0]), parts[1], parts[2], Priority.valueOf(parts[3]));
            task.setCompleted(Boolean.parseBoolean(parts[4]));
            task.setCreatedAt(parts[5]);
            return task;
        } catch (Exception e) {
            return null;
        }
    }
}