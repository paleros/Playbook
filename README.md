# Játékgyűjtemény

- Budapesti Műszaki és Gazdaságtudományi Egyetem - BME
- Villamosmérnöki és Informatikai Kar - VIK
- Automatizálási és Alkalmazott Informatikai Tanszék - AUT
- MSc Mérnök informatikus szak
- Önálló laboratórium 2

## Rövid leírás
Ez egy könnyen kezelhető mobil és PC alkalmazás tervezet gyerekeknek és fiataloknak szervezett programokhoz. 
Az alkalmazás célja, hogy gyorsan és egyszerűen hozzáférhető, szűrhető és frissíthető játékgyűjteményt biztosítson szervezők számára.

## Mire készült
- Gyors ötletadás játékszervezéshez (táborok, összejövetelek, programok).
- Szűrés korosztály, játékidő, helyszín (beltéri/kültéri) és eszközigény szerint.
- Egygombos véletlenszerű játékválasztás a döntés segítésére.
- Központi adatbázisról (Firebase) történő frissítés: a játékokat nem a felhasználók adják hozzá, hanem egy külön program tölti fel/karbantartja az adatbázist.

## Főbb funkciók
- Játéklista böngészése és részletes megtekintése (név, rövid/hosszú leírás, ajánlott korosztály, időtartam, hely, szükséges eszközök).
- Keresés és többparaméteres szűrés.
- Random játék generálása a beállított paraméterek alapján.
- Offline cache és frissítés Firebase-ről.

## Műszaki részletek
- Platform: Kotlin Multiplatform (Android és PC alkalmazás).
- Backend/adat: Firebase (FireStore , a feltöltő eszköz a játékok karbantartásához).
- Nyelvek és eszközök: Kotlin, Gradle, AndroidStudio.
- Cél: egyszerű, reszponzív és modern UI.

## Telepítés és futtatás (rövid)
1. Klónozd a repót: `git clone https://github.com/paleros/Playbook.git`
2. Nyisd meg Android Studio / IntelliJ-ben.
3. Állítsd be a `google-services.json` fájlt a megfelelő Firebase projekt adataival.
4. Futtasd a kívánt modul build konfigurációját (Android vagy JVM).

## Adatkezelés
- A játékok központilag kerülnek feltöltésre egy szerveres/asztali feltöltő programmal a Firebase adatbázisba.
- A mobil/PC kliens lekéri és cache-eli az adatokat, a felhasználó csak böngészi és szűri a listát.

## Fejlesztési megjegyzések
- A projekt célja a Kotlin Multiplatform megismerése és alkalmazása, valamint Firebase integráció gyakorlása.
- Fontos részek: offline támogatás, gyors szűrés és letisztult modern felhasználói felület.