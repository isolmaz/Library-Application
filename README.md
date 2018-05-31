# Library-Application
Library Application (JSF &amp; JPA &amp; MAVEN &amp; MySQL)


# 1.0 UYGULAMA KURULUMU
---

### 1.1 Library-Application Uygulamasının Github Üzerinden İndirilmesi
Git uygulamasının bilgisayarınızda kurulu olduğunu varsayarak;
* `Git Bash` uygulamasını çalıştırın.
* `cd {uygulamayı indirmek istediğiniz konum}` satırını çalıştırın.
* `$ git clone https://github.com/mhmtnasif/Library-Application.git` satırını çalıştırın.

Yukarıdaki adımları tamamladıktan sonra, uygulama `{uygulamayı indirmek istediğiniz konum}` konumuna indirilmiş olmalıdır.

### 1.2 MySQL Veri tabanı Yönetim Sisteminin Yapılandırılması
MySQL veri tabanı yönetim sisteminin bilgisayarınızda kurulu olduğunu varsayarak; uygulamanın veritabanına erişebilmesi için gerekli olan bilgiler aşağıdaki gibi olmalıdır.

```sh
Username : root
password : root
```
MySQL veri tabanı erişim bilgilerini güncellemek için;
* `MySQL Command Line Client` uygulamasını çalıştırın.
* Mevcut root şifrenizi girin.
* `ALTER USER 'root'@'localhost' IDENTIFIED BY 'root';` kod satırını çalıştırın.

Yukarıdaki işlemleri başarılı bir şekilde gerçekleştirdikten sonra, MySQL veri tabanı yönetim sisteminin erişim bilgilerini, uygulamamızın ihtiyaçları doğrultusunda güncellemiş olmaktayız.

Uygulamanın çalışabilmesi için veri tabanı adı `library` olmalıdır.
Veri tabanı kurmak için;
* `MySQL Command Line Client` uygulamasını çalıştırın.
* Mevcut root şifrenizi girin.
* `create database library; ` kod satırını çalıştırın.
* `use library; ` kod satırını çalıştırın.

Yukarıdaki işlemleri başarılı bir şekilde gerçekleştirdikten sonra, uygulamamızın ihtiyaç duyduğu veritabanı işlemlerini gerçekleştirmiş olmaktayız.

### 1.3 Uygulamanın Tomcat Server'a Yüklenmesi
Tomcat Server'ın bilgisayarınızda kurulu olduğunu varsayarak;
* `{Tomcat Server'ın kurulu olduğu dizin}/bin/startup.bat` dosyasını çalıştırın.
* http://localhost:8080/manager/html adresine gidin.
* Tomcat Server'ın arayüz erişim bilgilerini`(kullanıcı adı,şifre)` girin.
* Tomcat Server tarafından yetkilendirildikten  sonra `Deploy` bölümünü bulun.
* `Deploy` bölümünün altında olan `WAR file to deploy` bölümündeki `Dosya Seç` butonuna tıklayın.
* Açılan pencerede `{Library-Application dizini}\out\artifacts\Library_Application_war` klasörü içerisinde bulunan `Library-Application_war.war` dosyasını şeçin ve `Deploy` butonuna basın.

Yukarıdaki adımları tamamladıktan sonra, `Library-Application` uygulamasının kurulumunu tamamlamış olmaktayız.

### 1.4 Library-Application Uygulamasının Çalıştırılması
`Başlık 2.1,Başlık 2.2 ve Başlık 2.3`' teki işlemlerin başarılı bir şekilde gerçekleştirildiğini varsayarak;
* http://localhost:8080/Library-Application_war adresine gidin.
* Açılan sayfada `Sign Up` butonuna tıkladıktan sonra açılan sayfada `Kullanıcı adı, şifre` bilgisi girerek kayıt olun.
* `MySQL Command Line Client` uygulamasını çalıştırın.
* Mevcut root şifrenizi girin.
* `use library;` kod satırını çalıştırın.
* `UPDATE users SET ISADMIN=1 WHERE username={kayıt olduğunuz kullanıcı adı};` kod satırını çalıştırın.
##  ` ***TOMCAT SERVERI RESTART EDIN `
Yukarıdaki işlemleri başarılı bir şekilde gerçekleştirdikten sonra, `{kayıt olduğunuz kullanıcı adı,şifre};` bilgileri ile `login.xhtml` sayfasından `yönetici ` olarak giriş yapabilirsiniz.

### 1.4.1 Yönetici Rolleri
Uygulamaya `yönetici` olarak giriş yaptığınızda;
* `Home` sayfasını kullanarak arama yapabilirsiniz.Arama sonucundaki verileri görüntüleyebilirsiniz.
* `Add Author` sayfasını kullanarak Yazar ekleyebilirsiniz,
* `Add Publisher` sayfasını kullanarak Yayınevi ekleyebilirsiniz.
* `Add Book` sayfasını kullanarak Kitap ekleyebillirsiniz.
* `Edit/Delete/Search Author` sayfasını kullanarak sistemde olan tüm yazarları arayabilir,güncelleyebilir veya silebilirsiniz.
* `Edit/Delete/Search Publisher` sayfasını kullanarak sistemde olan tüm yayınevlerini arayabilir,güncelleyebilir veya silebilirsiniz.
* `Edit/Delete/Search Books` sayfasını kullanarak sistemde olan tüm kitapları arayabilir,güncelleyebilir veya silebilirsiniz.
 * `logout` sayfasını kullanarak  sistemden çıkış yapabilirsiniz.
 
### 1.4.2 Standart Kullanıcı Rolleri

Standart kullanıcılar;
 * `login.xhtml` sayfasınındaki `Login With Linkedin` butonuna tıklayarak Linkedin hesapları ile sisteme giriş yapabilirler
* `register.xhtml` sayfasında `Kullanıcı Adı ve Şifre` bilgilerini girerek sisteme kayıt olabilirler
* `login.xhtml` sayfasında kayıt oldukları bilgileri kullanarak sisteme giriş yapabilirler.

 Uygulamaya `Standart kullanıcı` olarak giriş yaptığınızda;

 * `Home` sayfasını kullanarak arama yapabilirsiniz.Arama sonucundaki verileri görüntüleyebilirsiniz.
* `Add Book` sayfasını kullanarak kitap ekleyebillirsiniz.
* `My Books` sayfasını kullanarak sadece sisteme eklediğiniz kitapları görüntüleyebilirsiniz.
 * `logout` sayfasını kullanarak  sistemden çıkış yapabilirsiniz.


---

# MAHMUT NASİFOĞLU


