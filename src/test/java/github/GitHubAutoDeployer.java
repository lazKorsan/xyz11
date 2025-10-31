package github;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GitHubAutoDeployer {
    private String projectPath ;
    private String remoteUrl ;
    private String branchName ;
    public GitHubAutoDeployer(String projectPath, String remoteUrl, String branchName) {
        this.projectPath = projectPath;
        this.remoteUrl = remoteUrl;
        this.branchName = branchName;
    }

    public boolean deployToGitHub() {
        System.out.println("🚀 GitHub'a otomatik deploy başlıyor...");

        try {
            // 1. Git durumunu kontrol et
            if (!isGitRepository()) {
                System.out.println("❌ Bu dizin bir Git repository'si değil!");
                initializeGitRepository();
            }

            // 2. Remote repository'yi kontrol et ve ayarla
            setupRemoteRepository();

            // 3. Branch işlemleri
            setupBranch();

            // 4. Sunucudaki değişiklikleri çek (Pull)
            pullChanges();

            // 5. Değişiklikleri sahneye ekle (Add)
            addChanges();

            // 6. Değişiklikleri kaydet (Commit)
            // Eğer sahnelenecek değişiklik yoksa commit atmayı dene, hata verirse devam et.
            if (!commitChanges()) {
                System.out.println("ℹ️ Kaydedilecek yeni değişiklik bulunamadı. Yine de sunucuyla eşitleme denenecek.");
            }

            // 7. Değişiklikleri sunucuya gönder (Push)
            pushToGitHub();

            System.out.println("✅ GitHub'a başarıyla deploy edildi!");
            return true;

        } catch (Exception e) {
            System.out.println("❌ Deploy işlemi başarısız: " + e.getMessage());
            return false;
        }
    }

    private boolean isGitRepository() throws IOException, InterruptedException {
        Process process = executeCommand("git", "status");
        return process.waitFor() == 0;
    }

    private void initializeGitRepository() throws IOException, InterruptedException {
        System.out.println("🔄 Git repository'si başlatılıyor...");
        executeCommand("git", "init");
        executeCommand("git", "branch", "-M", "main");
    }

    private void setupRemoteRepository() throws IOException, InterruptedException {
        System.out.println("🌐 Remote repository ayarlanıyor...");

        // Mevcut remote'ları kontrol et
        Process process = executeCommand("git", "remote -v");
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
        String line;
        boolean hasRemote = false;

        while ((line = reader.readLine()) != null) {
            if (line.contains("origin")) {
                hasRemote = true;
                break;
            }
        }

        if (!hasRemote) {
            executeCommand("git", "remote", "add", "origin", remoteUrl);
            System.out.println("✅ Remote repository eklendi: " + remoteUrl);
        } else {
            executeCommand("git", "remote", "set-url", "origin", remoteUrl);
            System.out.println("✅ Remote repository güncellendi: " + remoteUrl);
        }
    }

    private void setupBranch() throws IOException, InterruptedException {
        System.out.println("🌿 Branch işlemleri yapılıyor...");

        // Mevcut branch'leri kontrol et
        Process process = executeCommand("git", "branch");
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
        String line;
        boolean branchExists = false;

        while ((line = reader.readLine()) != null) {
            if (line.contains(branchName)) {
                branchExists = true;
                break;
            }
        }

        if (branchExists) {
            executeCommand("git", "checkout", branchName);
            System.out.println("✅ " + branchName + " branch'ine geçildi");
        } else {
            executeCommand("git", "checkout", "-b", branchName);
            System.out.println("✅ Yeni branch oluşturuldu: " + branchName);
        }
    }

    private void pullChanges() throws IOException, InterruptedException {
        System.out.println("🔄 Sunucudaki son değişiklikler çekiliyor (pull)...");
        executeCommand("git", "pull", "origin", branchName);
    }

    private void addChanges() throws IOException, InterruptedException {
        System.out.println("📦 Değişiklikler ekleniyor...");
        executeCommand("git", "add", ".");
        System.out.println("✅ Tüm değişiklikler staged area'ya eklendi");
    }

    private boolean commitChanges() throws IOException, InterruptedException {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String commitMessage = "Auto-deploy: Evening work completed at " + timestamp;

        System.out.println("💾 Commit yapılıyor: " + commitMessage);
        Process process = executeCommand("git", "commit", "-m", commitMessage);
        if (process.waitFor() == 0) {
            System.out.println("✅ Commit tamamlandı");
            return true;
        }
        return false;
    }

    private void pushToGitHub() throws IOException, InterruptedException {
        System.out.println("🚀 GitHub'a push işlemi başlıyor...");

        // Önce mevcut branch'i push et
        System.out.println("BRANCH: " + branchName);
        executeCommand("git", "push", "-u", "origin", branchName);
        System.out.println("✅ " + branchName + " branch'i başarıyla push edildi.");

        // Ana branch'i (main) güncelle
        System.out.println("🔄 Main branch güncelleniyor...");
        executeCommand("git", "checkout", "main");
        executeCommand("git", "pull", "origin", "main"); // Önce main'i güncelle
        executeCommand("git", "merge", branchName, "--no-ff", "-m", "Merge branch '" + branchName + "'");
        executeCommand("git", "push", "origin", "main");
        System.out.println("✅ Main branch başarıyla güncellendi ve push edildi.");

        // Tekrar tester'ın çalışma branch'ine geri dön
        executeCommand("git", "checkout", branchName);
        System.out.println("✅ Tekrar " + branchName + " branch'ine geçildi.");
    }

    private Process executeCommand(String... command) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.directory(new File(projectPath));
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();

        // Çıktıyı oku ve göster
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println("   → " + line);
        }

        process.waitFor();
        return process;
    }

    public void showGitStatus() throws IOException, InterruptedException {
        System.out.println("\n=== GIT DURUMU ===");
        executeCommand("git", "status", "-s"); // -s for short status
        System.out.println("\n=== BRANCH'LER ===");
        executeCommand("git", "branch", "-a");
    }
}