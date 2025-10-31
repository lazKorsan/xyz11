package github;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class MorningTesterSyncSystem {
    private final String projectPath;
    private final String branchName;

    public MorningTesterSyncSystem(String projectPath, String branchName) {
        this.projectPath = projectPath;
        this.branchName = branchName;
    }

    /**
     * Sabah senkronizasyon işlemini başlatır:
     * 1. Belirtilen branch'e geçer.
     * 2. Sunucudaki en son değişiklikleri çeker.
     */
    public void startMorningSync() {
        System.out.println("☀️ GÜNAYDIN EKİP! SABAH SENKRONİZASYON SİSTEMİ BAŞLATILIYOR ☀️");
        System.out.println("==========================================================");
        System.out.println("TeamLead Notu: Projeyi en güncel hale getirerek güne başlıyoruz.");
        System.out.println("Proje Yolu: " + projectPath);
        System.out.println("Çalışma Branch'i: " + branchName);

        try {
            // --- ADIM 1: Ana Branch'i Güncelle ---
            System.out.println("\n--- ADIM 1: Ana Branch (main) Güncelleniyor ---");
            System.out.println("   → Önce projenin temelini sağlamlaştırıyoruz.");
            Thread.sleep(1000); // Logların okunması için kısa bir bekleme
            executeCommand("git", "checkout", "main");
            executeCommand("git", "pull", "origin", "main");
            System.out.println("✅ Ana branch (main) başarıyla güncellendi.");
            System.out.println("-------------------------------------------------");

            Thread.sleep(1500); // Logların okunması için kısa bir bekleme

            // --- ADIM 2: Çalışma Branch'ine Geri Dön ---
            System.out.println("\n--- ADIM 2: Çalışma Branch'ine Geçiliyor ---");
            System.out.println("   → Şimdi " + branchName + " branch'i üzerinde çalışmaya hazırlanıyoruz.");
            Thread.sleep(1000);
            executeCommand("git", "checkout", branchName);
            System.out.println("✅ Başarıyla '" + branchName + "' branch'ine geçildi.");
            System.out.println("-------------------------------------------------");

            Thread.sleep(1500);

            // --- ADIM 3: Çalışma Branch'ini Güncelle ---
            System.out.println("\n--- ADIM 3: Çalışma Branch'i Sunucuyla Eşitleniyor ---");
            System.out.println("   → Takım arkadaşlarının yaptığı son değişiklikler projeye ekleniyor.");
            Thread.sleep(1000);
            executeCommand("git", "pull", "origin", branchName);
            System.out.println("✅ '" + branchName + "' branch'i başarıyla güncellendi.");
            System.out.println("-------------------------------------------------");

            System.out.println("\n🎉 HARİKA! Senkronizasyon tamamlandı. Proje artık en güncel haliyle çalışmaya hazır.");
            System.out.println("İyi çalışmalar dilerim!");

        } catch (IOException | InterruptedException e) {
            System.out.println("\n❌ HATA: Senkronizasyon işlemi başarısız oldu!");
            System.out.println("Lütfen hata mesajını kontrol edin: " + e.getMessage());
            // Hata durumunda iş parçacığının kesintiye uğradığını belirt
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Belirtilen komutu proje dizininde çalıştırır ve çıktısını konsola yazdırır.
     */
    private void executeCommand(String... command) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.directory(new File(projectPath));
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();

        // Komut çıktısını anlık olarak oku
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("   → " + line);
            }
        }

        process.waitFor();
    }

    public static void main(String[] args) {
        // Bu değerleri kendi projenize göre ayarlayın
        String projectPath = "C:\\Users\\user\\IdeaProjects\\xyz11";
        // Tester'ın çalışacağı branch
        String testerBranch = "ahmet";

        MorningTesterSyncSystem syncSystem = new MorningTesterSyncSystem(projectPath, testerBranch);
        syncSystem.startMorningSync();
    }
}