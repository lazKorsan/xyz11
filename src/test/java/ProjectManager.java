import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ProjectManager {
    private Map<String, Branch> branches;
    private String currentBranch;
    private boolean initialCommitsDone;

    public ProjectManager() {
        this.branches = new HashMap<>();
        this.currentBranch = "main";
        this.initialCommitsDone = false;
        initializeBranches();
    }

    private void initializeBranches() {
        branches.put("main", new Branch("main", "stable"));
        branches.put("ahmet", new Branch("ahmet", "development"));
    }

    public void initializeProject() {
        System.out.println("🔄 Proje başlatılıyor...");
        createInitialCommits();
        initialCommitsDone = true;
        System.out.println("✅ İlk yüklemeler tamamlandı!");
    }

    private void createInitialCommits() {
        branches.get("main").setLastCommit("initial-commit-main");
        branches.get("ahmet").setLastCommit("initial-commit-ahmet");
    }

    public boolean switchBranch(String branchName) {
        if (branches.containsKey(branchName)) {
            this.currentBranch = branchName;
            System.out.println("🔀 " + branchName + " branch'ine geçildi");
            return true;
        } else {
            System.out.println("❌ " + branchName + " branch'i bulunamadı");
            return false;
        }
    }

    public void commitChanges(String commitMessage) {
        Branch current = branches.get(currentBranch);
        current.setLastCommit(commitMessage);
        current.setLastCommitTime(LocalDateTime.now());
        System.out.println("💾 Commit yapıldı: " + commitMessage);
    }

    public void pushToRemote() {
        Branch current = branches.get(currentBranch);
        System.out.println("🚀 " + currentBranch + " branch'ine push yapılıyor...");
        System.out.println("✅ Son commit: " + current.getLastCommit());
        System.out.println("⏰ Commit zamanı: " + current.getLastCommitTime());
    }

    public void showStatus() {
        System.out.println("\n=== PROJE DURUMU ===");
        System.out.println("📍 Mevcut branch: " + currentBranch);
        System.out.println("📋 Branch'ler:");
        branches.forEach((name, branch) -> {
            System.out.println("   - " + name + " (" + branch.getStatus() + ")");
            System.out.println("     Son commit: " + branch.getLastCommit());
        });
    }

    // Getter ve Setter metodları
    public String getCurrentBranch() {
        return currentBranch;
    }

    public boolean isInitialCommitsDone() {
        return initialCommitsDone;
    }
}

class Branch {
    private String name;
    private String status;
    private String lastCommit;
    private LocalDateTime lastCommitTime;

    public Branch(String name, String status) {
        this.name = name;
        this.status = status;
        this.lastCommit = "Henüz commit yapılmadı";
    }

    // Getter ve Setter metodları
    public String getName() { return name; }
    public String getStatus() { return status; }
    public String getLastCommit() { return lastCommit; }
    public LocalDateTime getLastCommitTime() { return lastCommitTime; }

    public void setLastCommit(String lastCommit) { this.lastCommit = lastCommit; }
    public void setLastCommitTime(LocalDateTime lastCommitTime) { this.lastCommitTime = lastCommitTime; }
}