# Tubes_PBW : RanTreker

Anggota kelompok :
- Axel Darmaputra Triyudha
- Jason Kelvin Agung
- Nazaren Ben
- Naia Azzahra

# Fitur Admin :
- Mengelola data User
- Mengelola race (pertandingan)
- Membuat pertandingan baru

# Fitur Pengguna :
- Menambahkan activity baru
- Melihat dashboard 
- Daftar race

# Cara menjalankan program :
- Git clone https://github.com/NaiaAzzahraSugandi/Tubes_PBW.git
- Pindah ke direktori Tubes_PBW
- Masuk ke dalam PostgreSQL menggunakan terminal atau pgAdmin
- Membuat database dengan nama TubesPBW
- Run Tabel dan Dummy Data yang sudah tersedia pada folder Database. Lakukan proses run file DDL_Database.sql saja.
- Buka file src/main/resources/application.properties dan sesuaikan dengan pengaturan database Anda.
    spring.datasource.url=jdbc:postgresql://localhost:5432/Tubes_PBW
    spring.datasource.username=(username postgresql Anda)
    spring.datasource.password=(password postgresql Anda)
- Dalam terminal (pastikan sudah masuk direktori yang sesuai) jalankan perintah dibawah ini
    1. ./gradlew clean build (tunggu hingga selesai dan muncul BUILD SUCCESSFUL)
    2. ./gradlew bootRun
- Aplikasi akan berjalan dan bisa akses melalui browser pada alamat http://localhost:8080/. 
- Anda akan langsung diarahakan pada halaman landing page dari aplikasi RanTreker. Pastikan membuat akun (register) jika belum memiliki akun.
