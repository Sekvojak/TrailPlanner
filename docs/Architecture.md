# Architecture – TrailPlanner

## 1. Účel dokumentu

Tento dokument opisuje architektúru aplikácie TrailPlanner.

Jeho cieľom je definovať základnú štruktúru systému, jednotlivé komponenty aplikácie, ich zodpovednosti a spôsob vzájomnej komunikácie.

Architektúra je navrhnutá pre prvú verziu aplikácie (MVP), pričom umožňuje jednoduché rozširovanie o nové funkcionality v budúcnosti.

---

# 2. Prehľad architektúry

TrailPlanner je webová aplikácia využívajúca architektúru **Client–Server**.

Systém je rozdelený na štyri hlavné časti:

* Frontend
* Backend
* Databáza
* Externé služby (budúce rozšírenia)

Komunikácia medzi jednotlivými časťami prebieha nasledovne:

```text
Používateľ
      │
      ▼
Frontend
      │
 HTTP / REST API
      │
      ▼
Backend
      │
SQL / JPA
      │
      ▼
Databáza
```

---

# 3. Komponenty systému

## Frontend

Frontend predstavuje používateľské rozhranie aplikácie.

Jeho hlavnou úlohou je zobrazovať údaje používateľovi a odosielať požiadavky na backend.

### Zodpovednosti

* registrácia používateľa,
* prihlásenie a odhlásenie,
* vyhľadávanie turistických trás,
* filtrovanie trás,
* zobrazenie detailu trasy,
* správa obľúbených trás,
* pridávanie hodnotení,
* pridávanie komentárov,
* administrátorské rozhranie.

Frontend nebude obsahovať významnú biznis logiku. Väčšina spracovania údajov bude prebiehať na strane backendu.

---

## Backend

Backend predstavuje hlavnú časť systému.

Obsahuje biznis logiku aplikácie a zabezpečuje komunikáciu s databázou.

### Zodpovednosti

* autentifikácia používateľov,
* autorizácia používateľov,
* správa turistických trás,
* vyhľadávanie trás,
* odporúčanie trás,
* správa obľúbených trás,
* správa komentárov,
* správa hodnotení,
* administrácia systému.

Backend zároveň zabezpečuje validáciu vstupných údajov a kontrolu oprávnení používateľov.

---

## Databáza

Databáza uchováva všetky trvalé údaje aplikácie.

Konkrétny databázový model bude definovaný v samostatnom dokumente **Database Design**.

---

## Externé služby

Prvá verzia aplikácie nebude závislá od externých služieb.

Architektúra je však navrhnutá tak, aby bolo možné v budúcnosti jednoducho integrovať napríklad:

* mapové služby,
* import GPX trás,
* import GeoJSON,
* predpoveď počasia,
* AI odporúčanie trás.

---

# 4. Architektúra backendu

Backend bude využívať viacvrstvovú architektúru.

```text
Controller
      │
      ▼
Service
      │
      ▼
Repository
      │
      ▼
Databáza
```

## Controller

Vrstva Controller prijíma HTTP požiadavky od frontendu.

Je zodpovedná za:

* prijatie požiadavky,
* základnú validáciu,
* odoslanie odpovede klientovi.

Controller nebude obsahovať biznis logiku.

---

## Service

Vrstva Service obsahuje hlavnú biznis logiku systému.

Je zodpovedná za:

* spracovanie požiadaviek,
* aplikovanie pravidiel systému,
* komunikáciu medzi jednotlivými časťami aplikácie.

Táto vrstva bude predstavovať jadro celej aplikácie.

---

## Repository

Vrstva Repository zabezpečuje komunikáciu s databázou.

Je zodpovedná za:

* vytváranie,
* načítanie,
* úpravu,
* odstránenie údajov.

---

## Databázová vrstva

Databázová vrstva zabezpečuje trvalé uloženie údajov.

Nebude obsahovať žiadnu biznis logiku.

---

# 5. Komunikácia medzi komponentmi

Frontend komunikuje s backendom prostredníctvom REST API.

Backend spracuje požiadavku, vykoná potrebnú biznis logiku a následne komunikuje s databázou.

Výsledok spracovania je následne odoslaný späť frontendu vo forme HTTP odpovede.

---

# 6. Autentifikácia a autorizácia

Systém rozlišuje tri typy používateľov:

* návštevník,
* registrovaný používateľ,
* administrátor.

Návštevník môže prezerať turistické trasy bez prihlásenia.

Registrovaný používateľ môže navyše:

* ukladať obľúbené trasy,
* hodnotiť trasy,
* pridávať komentáre.

Administrátor má rozšírené oprávnenia na správu turistických trás.

---

# 7. Pripravenosť na budúce rozšírenia

Architektúra je navrhnutá modulárne.

V budúcnosti bude možné doplniť napríklad:

* zobrazenie trás na mape,
* GPS navigáciu,
* import trás z GPX súborov,
* AI odporúčanie trás,
* predpoveď počasia,
* mobilnú aplikáciu,
* fotografie trás,
* plánovanie vlastných trás.

Väčšina nových funkcionalít bude môcť byť implementovaná bez výrazných zmien existujúcej architektúry.

---

# 8. Bezpečnosť

Pri návrhu systému budú dodržané základné bezpečnostné princípy:

* heslá používateľov nebudú ukladané v čitateľnej podobe,
* chránené operácie budú dostupné iba prihláseným používateľom,
* administrátorské operácie budú dostupné iba používateľom s rolou administrátora,
* vstupné údaje budú validované pred ich spracovaním.

---

# 9. Zhrnutie

TrailPlanner využíva viacvrstvovú architektúru typu Client–Server.

Oddelenie používateľského rozhrania, biznis logiky a databázovej vrstvy zabezpečuje lepšiu prehľadnosť systému, jednoduchšiu údržbu a jednoduchšie budúce rozširovanie aplikácie.

Navrhnutá architektúra poskytuje dostatočný základ pre implementáciu MVP a zároveň vytvára priestor na budúce rozšírenia projektu.
