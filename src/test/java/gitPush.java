public class gitPush {
    public static void main(String[] args) {
        /*
        Bu dosya, bir projedeki günlük Git iş akışını açıklayan bir rehber niteliğindedir.
        'ahmet' adında bir branch üzerinde çalışan bir tester veya developer'ın
        gün sonu çalışmalarını merkezi sunucuya (remote repository) nasıl göndereceğini adım adım gösterir.
        */

        // ===================================================================================
        // TESTER İÇİN GÜNLÜK GIT AKIŞI (İŞ BİTİMİ)
        // ===================================================================================

        // Varsayılan olarak 'ahmet' branch'inde çalıştığını varsayalım.
        // Güncel branch'i kontrol etmek için:
        // git branch

        // --- ADIM 1: Projenin Son Halini Çek (Pull) ---
        // Takım arkadaşlarının yaptığı ve merkezi sunucuya gönderdiği değişiklikleri kendi bilgisayarına çek.
        // Bu adım, olası çakışmaları (conflict) en aza indirmek için ÇOK ÖNEMLİDİR.
        // 'ahmet' branch'inin en güncel halini sunucudan (origin) çek:
        System.out.println("git pull origin ahmet");


        // --- ADIM 2: Kendi Değişikliklerini Ekle (Add) ---
        // Yaptığın tüm değişiklikleri Git'in takip etmesi için hazırlık alanına (staging area) ekle.
        // Projedeki tüm yeni veya değiştirilmiş dosyaları eklemek için:
        System.out.println("git add .");


        // --- ADIM 3: Değişikliklerini Kaydet (Commit) ---
        // Hazırlık alanındaki değişiklikleri açıklayıcı bir mesaj ile yerel bilgisayarındaki Git deposuna kaydet.
        // İyi bir commit mesajı, ne yaptığını kısaca özetler.
        System.out.println("git commit -m \"Günün görevleri tamamlandı: Kullanıcı girişi testi eklendi.\"");


        // --- ADIM 4: Değişikliklerini Sunucuya Gönder (Push) ---
        // Yerel depodaki kayıtlı değişikliklerini, takım arkadaşlarının da görebilmesi için merkezi sunucuya gönder.
        System.out.println("git push origin ahmet");
    }
}
