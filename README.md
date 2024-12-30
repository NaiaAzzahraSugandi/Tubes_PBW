# Tugas Akhir Pemrograman Berbasis Web - Aplikasi Web RanTreker
Anggota kelompok :
- Axel Darmaputra Triyudha
- Jason Kelvin Agung
- Nazaren Ben
- Naia Azzahra

# Deskripsi Fitur
RanTreker adalah aplikasi berbasis web yang dapat digunakan untuk mencatat aktivitas lari dari seseorang.
## Fitur Admin :
- Mengelola data user
- Mengelola race (pertandingan)
- Membuat pertandingan baru

## Fitur Pengguna :
- Menambahkan record lari baru
- Melihat dashboard 
- Mengikuti race
- Mendapatkan notifikasi

# Spesifikasi Sistem
- **Gradle** - build tool
- **Java** - bahasa pemrograman untuk backend
- **PostgreSQL** - database untuk menyimpan data 
- **Spring Boot** - web framework

# Persyaratan Sistem 
1. Java versi 17 
2. Gradle versi 8.0 atau versi terbaru
3. Database PostgreSQL

# Konfigurasi Awal
## 1. Clone Repository
1. Clone repository ini dengan menjalankan perintah di bawah ini dalam terminal, command prompt atau powershell.
  ```
    git clone https://github.com/NaiaAzzahraSugandi/Tubes_PBW.git
  ```
## 2. Persiapan Database
1. Masuk ke dalam PostgreSQL melalui terminal atau pgAdmin.
2. Buatlah database baru dengan nama `TubesPBW`
3. Jalankan file `DDL_Database.sql`, kemudian `Dummy_Data.sql` pada folder `Database`. Pastikan kedua file ini dijalankan dalam database yang baru saja dibuat.
## 3. Konfigurasi Aplikasi
Buka file `src/main/resources/application.properties` dan sesuaikan dengan pengaturan database Anda.
   ```
    spring.datasource.url=jdbc:postgresql://localhost:5432/TubesPBW
    spring.datasource.username=(username postgresql Anda)
    spring.datasource.password=(password postgresql Anda)
   ```
## 4. Proses Build dan Run Aplikasi
1. Pindahlah ke dalam direktori aplikasi.
2. Build aplikasi dengan menggunakan perintah berikut ini :
    - Untuk command prompt :
      `gradlew clean build`
    - Untuk powershell :
      `./gradlew clean build`
3. Jalankan aplikasi menggunakan perintah berikut ini :
    - Untuk command prompt :
      `gradlew bootRun`
    - Untuk powershell :
      `./gradlew bootRun`
4. Aplikasi akan berjalan dan bisa diakses melalui browser pada alamat http://localhost:8080/, Anda akan langsung diarahakan pada halaman landing page dari aplikasi RanTreker.
