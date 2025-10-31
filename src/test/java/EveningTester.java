import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class EveningTester {
    private ProjectManager projectManager;
    private List<String> completedTests;
    private boolean workCompleted;

    public EveningTester() {
        this.projectManager = new ProjectManager();
        this.completedTests = new ArrayList<>();
        this.workCompleted = false;
    }

    public void startEveningWork() {
        System.out.println("🌙 Akşam çalışması başlıyor...");
        System.out.println("Saat: " + LocalTime.now());

        // Ahmet branch'ine geç
        projectManager.switchBranch("ahmet");

        // Testleri çalıştır
        runTests();

        // Eğer tüm testler başarılıysa projeyi yükle
        if (workCompleted) {
            deployProject();
        }
    }

    private void runTests() {
        System.out.println("\n🧪 Testler çalıştırılıyor...");

        // Örnek test senaryoları
        String[] testScenarios = {
                "Unit Test - UserService",
                "Integration Test - Database",
                "UI Test - Login Page",
                "Performance Test - API",
                "Security Test - Authentication"
        };

        for (String test : testScenarios) {
            System.out.println("▶️  Çalıştırılıyor: " + test);
            // Simüle edilmiş test süresi
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            completedTests.add(test);
            System.out.println("✅ Tamamlandı: " + test);
        }

        workCompleted = true;
        System.out.println("\n🎉 Tüm testler başarıyla tamamlandı!");
        System.out.println("Tamamlanan test sayısı: " + completedTests.size());
    }

    private void deployProject() {
        System.out.println("\n🚀 Proje yükleniyor...");

        // Son commit'i yap
        projectManager.commitChanges("Evening tests completed - " + LocalTime.now());

        // Projeyi push et
        projectManager.pushToRemote();

        System.out.println("✅ Proje başarıyla yüklendi!");
        System.out.println("🏁 Akşam çalışması tamamlandı!");
    }

    public void showWorkSummary() {
        System.out.println("\n=== AKŞAM ÇALIŞMASI ÖZETİ ===");
        System.out.println("Çalışma durumu: " + (workCompleted ? "Tamamlandı" : "Devam Ediyor"));
        System.out.println("Tamamlanan testler:");
        completedTests.forEach(test -> System.out.println("   ✓ " + test));
        projectManager.showStatus();
    }

    public static void main(String[] args) {
        EveningTester tester = new EveningTester();

        // Projeyi başlat
        tester.projectManager.initializeProject();

        // Akşam çalışmasını başlat
        tester.startEveningWork();

        // Özeti göster
        tester.showWorkSummary();
    }
}