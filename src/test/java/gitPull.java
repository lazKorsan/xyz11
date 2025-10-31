public class gitPull {
    public static void main(String[] args) {
        /*
        Bu dosya, bir projedeki günlük Git iş akışını açıklayan bir rehber niteliğindedir.
        'ahmet' adında bir branch üzerinde çalışan bir tester veya developer'ın
        güne başlarken projeyi en güncel haline nasıl getireceğini adım adım gösterir.
        */

        // ===================================================================================
        // TESTER İÇİN GÜNLÜK GIT AKIŞI (İŞ BAŞLANGICI)
        // ===================================================================================

        // Güne başlarken ilk yapılması gereken, projenin merkezi sunucudaki (remote)
        // en son sürümünü kendi bilgisayarına indirmektir. Bu işleme "pull" denir.
        // Böylece takım arkadaşlarının yaptığı değişiklikler senin projene de eklenir.

        // --- ADIM 1: Doğru Branch'te Olduğundan Emin Ol ---
        // Genellikle kendi görevlerini yürüttüğün özel bir branch'te çalışırsın.
        // 'ahmet' branch'ine geçiş yapalım:
        System.out.println("git checkout ahmet");

        // --- ADIM 2: Projenin Son Halini Sunucudan Çek (Pull) ---
        // 'origin' olarak adlandırılan merkezi sunucudan 'ahmet' branch'inin en güncel halini çek.
        // Bu komut, sunucudaki yeni değişiklikleri yerel bilgisayarına indirir ve mevcut kodunla birleştirir.
        System.out.println("git pull origin ahmet");

        System.out.println("\nArtık projenin en güncel hali bilgisayarında. Çalışmaya başlayabilirsin!");
    }
}
