package github;

import java.util.Scanner;

public class EveningTesterDeploySystem {
    private GitHubAutoDeployer deployer;
    private String projectPath;
    private String githubUrl;

    public EveningTesterDeploySystem(String projectPath, String githubUrl) {
        this.projectPath = projectPath;
        this.githubUrl = githubUrl;
        this.deployer = new GitHubAutoDeployer(projectPath, githubUrl, "ahmet");
    }

    public void startEveningDeployment() {
        System.out.println("🌙 AKŞAM DEPLOY SİSTEMİ");
        System.out.println("========================");
        System.out.println("Proje Yolu: " + projectPath);
        System.out.println("GitHub URL: " + githubUrl);
        System.out.println("Çalışma Branch'i: ahmet");

        Scanner scanner = new Scanner(System.in);

        // Mevcut durumu göster
        try {
            deployer.showGitStatus();
        } catch (Exception e) {
            System.out.println("❌ Git durumu gösterilemedi: " + e.getMessage());
        }

        // Onay iste
        System.out.print("\n🚀 GitHub'a deploy etmek istiyor musunuz? (evet/hayır): ");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("evet") || confirmation.equalsIgnoreCase("e")) {
            System.out.println("\n⏳ Deploy işlemi başlatılıyor...");

            boolean success = deployer.deployToGitHub();

            if (success) {
                System.out.println("\n🎉 DEPLOY BAŞARILI!");
                System.out.println("📧 Tester evine gidebilir, proje GitHub'da!");
                System.out.println("🔗 Repository: " + githubUrl);
            } else {
                System.out.println("\n❌ DEPLOY BAŞARISIZ!");
                System.out.println("⚠️  Lütfen manuel kontrol yapın!");
            }
        } else {
            System.out.println("❌ Deploy iptal edildi.");
        }

        scanner.close();
    }

    public static void main(String[] args) {
        // Bu değerleri kendi projenize göre değiştirin
        String projectPath = "C:\\Users\\user\\IdeaProjects\\xyz11";
        String githubUrl = "https://github.com/lazKorsan/xyz11.git";

        EveningTesterDeploySystem deploySystem = new EveningTesterDeploySystem(projectPath, githubUrl);
        deploySystem.startEveningDeployment();
    }
}