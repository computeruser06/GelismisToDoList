import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();
        Scanner scanner = new Scanner(System.in);
        int choice;

        System.out.println("🎯 Gelişmiş Konsol To-Do List Uygulamasına Hoş Geldiniz!");

        do {
            printMenu();
            System.out.print("Seçiminiz (1-6): ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                choice = -1;
            }

            switch (choice) {
                case 1:
                    manager.listTasks();
                    break;
                case 2:
                    System.out.print("Görev Başlığı: ");
                    String title = scanner.nextLine();
                    System.out.print("Görev Açıklaması: ");
                    String desc = scanner.nextLine();

                    System.out.println("\nÖncelik Seçin:");
                    System.out.println("1. Düşük (🟢)");
                    System.out.println("2. Normal (🟡)");
                    System.out.println("3. Yüksek (🔴)");
                    System.out.print("Seçiminiz (1-3): ");

                    int pChoice;
                    try {
                        pChoice = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        pChoice = 2;
                    }

                    Task.Priority priority = Task.Priority.NORMAL;
                    if (pChoice == 1) priority = Task.Priority.DUSUK;
                    else if (pChoice == 3) priority = Task.Priority.YUKSEK;

                    manager.addTask(title, desc, priority);
                    break;
                case 3:
                    manager.listTasksByPriority();
                    break;
                case 4:
                    manager.listTasks();
                    System.out.print("Tamamlandı olarak işaretlenecek Görev ID: ");
                    try {
                        int completeId = Integer.parseInt(scanner.nextLine());
                        manager.completeTask(completeId);
                    } catch (NumberFormatException e) {
                        System.out.println("❌ Geçersiz ID formatı.");
                    }
                    break;
                case 5:
                    manager.listTasks();
                    System.out.print("Silinecek Görev ID: ");
                    try {
                        int deleteId = Integer.parseInt(scanner.nextLine());
                        manager.deleteTask(deleteId);
                    } catch (NumberFormatException e) {
                        System.out.println("❌ Geçersiz ID formatı.");
                    }
                    break;
                case 6:
                    System.out.println("👋 Programdan çıkılıyor. İyi günler!");
                    break;
                default:
                    System.out.println("❌ Geçersiz seçim! Lütfen 1-6 arasında bir sayı girin.");
            }
            System.out.println();
        } while (choice != 6);

        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n--- İŞLEM MENÜSÜ ---");
        System.out.println("1. Görevleri Listele");
        System.out.println("2. Yeni Görev Ekle (Öncelik Seçmeli)");
        System.out.println("3. Önceliğe Göre Sıralı Listele");
        System.out.println("4. Görevi Tamamla");
        System.out.println("5. Görev Sil");
        System.out.println("6. Çıkış");
    }
}