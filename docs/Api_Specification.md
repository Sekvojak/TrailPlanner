# API Specification – TrailPlanner

## 1. Účel dokumentu

Tento dokument opisuje návrh REST API aplikácie TrailPlanner.

API zabezpečuje komunikáciu medzi frontendovou časťou aplikácie a backendom.

Dokument definuje:

* základnú štruktúru API,
* dostupné endpointy,
* HTTP metódy,
* autentifikáciu a autorizáciu,
* vstupné a výstupné DTO,
* stavové HTTP kódy,
* validačné pravidlá,
* jednotný formát chybových odpovedí.

Návrh je vytvorený pre MVP verziu aplikácie.

---

## 2. Základná adresa API

Všetky endpointy budú používať spoločný prefix:

```text
/api/v1
```

Príklady:

```http
GET /api/v1/trails
GET /api/v1/trails/{trailId}
POST /api/v1/auth/register
```

Použitie verzie `v1` umožní v budúcnosti vytvoriť novú verziu API bez okamžitého narušenia existujúcich klientov.

---

## 3. Formát komunikácie

API používa formát JSON.

Requesty, ktoré obsahujú telo požiadavky, používajú hlavičku:

```http
Content-Type: application/json
```

Odpovede API používajú:

```http
Content-Type: application/json
```

Výnimkou môžu byť odpovede bez tela, napríklad úspešné odstránenie záznamu.

---

## 4. Konvencie pomenovania

### URL adresy

URL používajú:

* malé písmená,
* množné číslo názvov zdrojov,
* slová oddelené pomlčkou iba v prípade potreby.

Príklady:

```text
/trails
/locations
/users/me/favorites
/trail-reports
```

### JSON atribúty

JSON atribúty používajú formát `camelCase`.

Príklad:

```json
{
  "distanceKm": 12.5,
  "durationMinutes": 240,
  "elevationGainM": 850
}
```

Databázové stĺpce môžu používať formát `snake_case`, ale tento spôsob pomenovania sa neprenáša priamo do verejného API.

---

## 5. Autentifikácia a autorizácia

API rozlišuje tri typy prístupu:

| Typ používateľa         | Oprávnenia                                                            |
| ----------------------- | --------------------------------------------------------------------- |
| Návštevník              | Verejné vyhľadávanie, detail trasy, registrácia, prihlásenie          |
| Registrovaný používateľ | Funkcie návštevníka, obľúbené trasy, hodnotenia, komentáre a hlásenia |
| Administrátor           | Funkcie používateľa, správa trás, hlásení a používateľov              |

Presný spôsob autentifikácie bude potvrdený v technologických rozhodnutiach.

Predpokladá sa použitie JWT tokenu alebo serverovej relácie.

Pri JWT autentifikácii klient posiela token v hlavičke:

```http
Authorization: Bearer <token>
```

Backend musí vždy overiť:

* platnosť autentifikácie,
* rolu používateľa,
* oprávnenie pracovať s daným zdrojom.

Identita používateľa sa nesmie pre používateľské operácie získavať z voľne zadaného `userId`, ale z autentifikovaného kontextu.

---

## 6. HTTP stavové kódy

API používa nasledujúce základné HTTP stavové kódy:

| Kód                         | Význam                                                 |
| --------------------------- | ------------------------------------------------------ |
| `200 OK`                    | Úspešné načítanie alebo aktualizácia                   |
| `201 Created`               | Úspešné vytvorenie nového zdroja                       |
| `204 No Content`            | Úspešná operácia bez tela odpovede                     |
| `400 Bad Request`           | Neplatný vstup alebo validačná chyba                   |
| `401 Unauthorized`          | Používateľ nie je prihlásený alebo token nie je platný |
| `403 Forbidden`             | Používateľ nemá potrebné oprávnenie                    |
| `404 Not Found`             | Požadovaný zdroj neexistuje                            |
| `409 Conflict`              | Konflikt, napríklad duplicitný e-mail                  |
| `500 Internal Server Error` | Neočakávaná chyba servera                              |

---

## 7. Jednotný formát chybovej odpovede

Všetky chyby majú používať jednotnú štruktúru.

### Príklad všeobecnej chyby

```json
{
  "timestamp": "2026-07-20T14:30:00Z",
  "status": 404,
  "error": "Not Found",
  "code": "TRAIL_NOT_FOUND",
  "message": "Turistická trasa nebola nájdená.",
  "path": "/api/v1/trails/123"
}
```

### Príklad validačnej chyby

```json
{
  "timestamp": "2026-07-20T14:30:00Z",
  "status": 400,
  "error": "Bad Request",
  "code": "VALIDATION_ERROR",
  "message": "Niektoré vstupné údaje nie sú platné.",
  "path": "/api/v1/auth/register",
  "fieldErrors": [
    {
      "field": "email",
      "message": "E-mail nemá platný formát."
    },
    {
      "field": "password",
      "message": "Heslo musí obsahovať aspoň 8 znakov."
    }
  ]
}
```

Technické detaily výnimiek, stack trace ani databázové informácie sa klientovi nesmú vracať.

---

# 8. Autentifikačné endpointy

## 8.1 Registrácia používateľa

```http
POST /api/v1/auth/register
```

### Prístup

Verejný endpoint.

### Request body

```json
{
  "name": "Dominik",
  "email": "dominik@example.com",
  "password": "bezpecneHeslo123",
  "passwordConfirmation": "bezpecneHeslo123"
}
```

### Validačné pravidlá

* `name` je povinné,
* `name` môže mať napríklad 2 až 100 znakov,
* `email` je povinný,
* `email` musí mať platný formát,
* `email` musí byť jedinečný,
* `password` je povinné,
* heslo musí spĺňať minimálne bezpečnostné pravidlá,
* `password` a `passwordConfirmation` sa musia zhodovať.

### Úspešná odpoveď

```http
201 Created
```

```json
{
  "id": "f0d71e83-7c50-48fc-9f48-1f3bd6738a91",
  "name": "Dominik",
  "email": "dominik@example.com",
  "role": "USER",
  "createdAt": "2026-07-20T14:30:00Z"
}
```

### Možné chyby

| Kód   | Situácia                                |
| ----- | --------------------------------------- |
| `400` | Neplatné vstupné údaje                  |
| `409` | Používateľ s daným e-mailom už existuje |

---

## 8.2 Prihlásenie používateľa

```http
POST /api/v1/auth/login
```

### Prístup

Verejný endpoint.

### Request body

```json
{
  "email": "dominik@example.com",
  "password": "bezpecneHeslo123"
}
```

### Úspešná odpoveď

```http
200 OK
```

Príklad pri JWT autentifikácii:

```json
{
  "accessToken": "jwt-token",
  "tokenType": "Bearer",
  "expiresIn": 3600,
  "user": {
    "id": "f0d71e83-7c50-48fc-9f48-1f3bd6738a91",
    "name": "Dominik",
    "email": "dominik@example.com",
    "role": "USER"
  }
}
```

### Možné chyby

| Kód   | Situácia                               |
| ----- | -------------------------------------- |
| `400` | Chýbajúce alebo neplatné vstupné údaje |
| `401` | Nesprávny e-mail alebo heslo           |
| `403` | Používateľský účet je deaktivovaný     |

Pri nesprávnom e-maile alebo hesle má API vrátiť rovnakú všeobecnú správu.

---

## 8.3 Odhlásenie používateľa

```http
POST /api/v1/auth/logout
```

### Prístup

Prihlásený používateľ alebo administrátor.

### Úspešná odpoveď

```http
204 No Content
```

Presné správanie závisí od zvoleného spôsobu autentifikácie.

Pri serverovej relácii sa relácia zneplatní.

Pri JWT riešení sa môže odstrániť alebo zneplatniť obnovovací token, ak ho aplikácia používa.

---

## 8.4 Zobrazenie aktuálneho používateľa

```http
GET /api/v1/users/me
```

### Prístup

Prihlásený používateľ alebo administrátor.

### Úspešná odpoveď

```http
200 OK
```

```json
{
  "id": "f0d71e83-7c50-48fc-9f48-1f3bd6738a91",
  "name": "Dominik",
  "email": "dominik@example.com",
  "role": "USER",
  "enabled": true,
  "createdAt": "2026-07-20T14:30:00Z"
}
```

### Možné chyby

| Kód   | Situácia                     |
| ----- | ---------------------------- |
| `401` | Používateľ nie je prihlásený |

---

# 9. Endpointy turistických trás

## 9.1 Vyhľadanie a zobrazenie zoznamu trás

```http
GET /api/v1/trails
```

### Prístup

Verejný endpoint.

### Query parametre

| Parameter            | Typ         | Povinný | Význam                                       |
| -------------------- | ----------- | ------- | -------------------------------------------- |
| `query`              | String      | nie     | Textové vyhľadávanie podľa názvu alebo opisu |
| `locationId`         | UUID / Long | nie     | Filtrovanie podľa lokality                   |
| `difficulty`         | String      | nie     | Náročnosť trasy                              |
| `routeType`          | String      | nie     | Typ trasy                                    |
| `maxDistanceKm`      | Decimal     | nie     | Maximálna dĺžka                              |
| `maxDurationMinutes` | Integer     | nie     | Maximálne trvanie                            |
| `maxElevationGainM`  | Integer     | nie     | Maximálne prevýšenie                         |
| `page`               | Integer     | nie     | Číslo stránky, predvolene `0`                |
| `size`               | Integer     | nie     | Počet položiek, predvolene napríklad `20`    |
| `sort`               | String      | nie     | Spôsob zoradenia                             |

### Príklad požiadavky

```http
GET /api/v1/trails?locationId=5&difficulty=MEDIUM&maxDistanceKm=15&page=0&size=20
```

### Úspešná odpoveď

```http
200 OK
```

```json
{
  "content": [
    {
      "id": "43dd0a7e-4f86-4f56-b047-28eb94cccb71",
      "name": "Výstup na Veľký Choč",
      "location": {
        "id": "08c71173-4f2e-44b6-868d-e054c296312b",
        "name": "Chočské vrchy",
        "region": "Žilinský kraj",
        "country": "Slovensko"
      },
      "distanceKm": 13.4,
      "durationMinutes": 330,
      "elevationGainM": 950,
      "difficulty": "HARD",
      "routeType": "OUT_AND_BACK",
      "averageRating": 4.7,
      "ratingCount": 35,
      "favorite": false
    }
  ],
  "page": 0,
  "size": 20,
  "totalElements": 1,
  "totalPages": 1,
  "first": true,
  "last": true
}
```

Atribút `favorite` môže byť:

* `true` alebo `false` pri prihlásenom používateľovi,
* `false` alebo `null` pri návštevníkovi.

### Možné chyby

| Kód   | Situácia                                   |
| ----- | ------------------------------------------ |
| `400` | Neplatná hodnota filtra alebo stránkovania |

---

## 9.2 Zobrazenie detailu trasy

```http
GET /api/v1/trails/{trailId}
```

### Prístup

Verejný endpoint.

### Úspešná odpoveď

```http
200 OK
```

```json
{
  "id": "43dd0a7e-4f86-4f56-b047-28eb94cccb71",
  "name": "Výstup na Veľký Choč",
  "description": "Turistická trasa vedúca na vrchol Veľkého Choča.",
  "location": {
    "id": "08c71173-4f2e-44b6-868d-e054c296312b",
    "name": "Chočské vrchy",
    "region": "Žilinský kraj",
    "country": "Slovensko"
  },
  "distanceKm": 13.4,
  "durationMinutes": 330,
  "elevationGainM": 950,
  "difficulty": "HARD",
  "routeType": "OUT_AND_BACK",
  "externalMapUrl": "https://example.com/map",
  "averageRating": 4.7,
  "ratingCount": 35,
  "favorite": true,
  "userRating": 5,
  "trailPoints": [
    {
      "latitude": 49.147215,
      "longitude": 19.353492,
      "elevationM": 670,
      "orderIndex": 0
    },
    {
      "latitude": 49.150118,
      "longitude": 19.344291,
      "elevationM": 1611,
      "orderIndex": 1
    }
  ],
  "createdAt": "2026-07-20T14:30:00Z",
  "updatedAt": "2026-07-20T14:30:00Z"
}
```

### Možné chyby

| Kód   | Situácia         |
| ----- | ---------------- |
| `404` | Trasa neexistuje |

Komentáre môžu byť načítané samostatným endpointom, aby detail trasy neobsahoval neobmedzený zoznam komentárov.

---

# 10. Endpointy lokalít

## 10.1 Zobrazenie zoznamu lokalít

```http
GET /api/v1/locations
```

### Prístup

Verejný endpoint.

### Úspešná odpoveď

```http
200 OK
```

```json
[
  {
    "id": "08c71173-4f2e-44b6-868d-e054c296312b",
    "name": "Chočské vrchy",
    "region": "Žilinský kraj",
    "country": "Slovensko",
    "description": "Pohorie na severe Slovenska."
  }
]
```

Tento endpoint môže frontend použiť na naplnenie filtrov alebo formulára trasy.

---

# 11. Endpointy obľúbených trás

## 11.1 Pridanie trasy medzi obľúbené

```http
PUT /api/v1/users/me/favorites/{trailId}
```

### Prístup

Prihlásený používateľ alebo administrátor.

### Úspešná odpoveď

```http
200 OK
```

```json
{
  "trailId": "43dd0a7e-4f86-4f56-b047-28eb94cccb71",
  "favorite": true,
  "createdAt": "2026-07-20T14:30:00Z"
}
```

Endpoint je idempotentný.

Ak už trasa medzi obľúbenými je, nevytvorí sa nový duplicitný záznam.

### Možné chyby

| Kód   | Situácia                     |
| ----- | ---------------------------- |
| `401` | Používateľ nie je prihlásený |
| `404` | Trasa neexistuje             |

---

## 11.2 Zobrazenie obľúbených trás

```http
GET /api/v1/users/me/favorites
```

### Prístup

Prihlásený používateľ alebo administrátor.

### Query parametre

| Parameter | Typ     | Povinný | Význam         |
| --------- | ------- | ------- | -------------- |
| `page`    | Integer | nie     | Číslo stránky  |
| `size`    | Integer | nie     | Počet položiek |

### Úspešná odpoveď

```http
200 OK
```

```json
{
  "content": [
    {
      "id": "43dd0a7e-4f86-4f56-b047-28eb94cccb71",
      "name": "Výstup na Veľký Choč",
      "distanceKm": 13.4,
      "durationMinutes": 330,
      "elevationGainM": 950,
      "difficulty": "HARD",
      "routeType": "OUT_AND_BACK",
      "averageRating": 4.7,
      "ratingCount": 35,
      "savedAt": "2026-07-20T14:30:00Z"
    }
  ],
  "page": 0,
  "size": 20,
  "totalElements": 1,
  "totalPages": 1
}
```

---

## 11.3 Odstránenie trasy z obľúbených

```http
DELETE /api/v1/users/me/favorites/{trailId}
```

### Prístup

Prihlásený používateľ alebo administrátor.

### Úspešná odpoveď

```http
204 No Content
```

Endpoint je idempotentný.

Ak záznam neexistuje, výsledný stav je už splnený a API môže stále vrátiť `204 No Content`.

### Možné chyby

| Kód   | Situácia                     |
| ----- | ---------------------------- |
| `401` | Používateľ nie je prihlásený |
| `404` | Trasa neexistuje             |

---

# 12. Endpointy hodnotení

## 12.1 Vytvorenie alebo zmena hodnotenia

```http
PUT /api/v1/trails/{trailId}/rating
```

### Prístup

Prihlásený používateľ alebo administrátor.

### Request body

```json
{
  "value": 5
}
```

### Validačné pravidlá

* `value` je povinné,
* hodnota musí byť celé číslo,
* povolený rozsah je od `1` do `5`.

### Úspešná odpoveď

```http
200 OK
```

```json
{
  "trailId": "43dd0a7e-4f86-4f56-b047-28eb94cccb71",
  "userRating": 5,
  "averageRating": 4.7,
  "ratingCount": 36,
  "createdAt": "2026-07-20T14:30:00Z",
  "updatedAt": "2026-07-20T14:30:00Z"
}
```

Ak hodnotenie ešte neexistovalo, môže API vrátiť `201 Created`.

Ak hodnotenie existovalo a bolo zmenené, vráti `200 OK`.

Pre jednoduchší kontrakt môže API v oboch prípadoch používať `200 OK`.

### Možné chyby

| Kód   | Situácia                        |
| ----- | ------------------------------- |
| `400` | Hodnota nie je v rozsahu 1 až 5 |
| `401` | Používateľ nie je prihlásený    |
| `404` | Trasa neexistuje                |

---

## 12.2 Odstránenie vlastného hodnotenia

```http
DELETE /api/v1/trails/{trailId}/rating
```

### Prístup

Prihlásený používateľ alebo administrátor.

### Úspešná odpoveď

```http
204 No Content
```

### Poznámka

Tento endpoint treba ponechať iba vtedy, ak má používateľ v MVP možnosť svoje hodnotenie úplne odstrániť.

Ak má mať iba možnosť hodnotenie zmeniť, endpoint nebude súčasťou MVP.

---

# 13. Endpointy komentárov

## 13.1 Zobrazenie komentárov trasy

```http
GET /api/v1/trails/{trailId}/comments
```

### Prístup

Verejný endpoint.

### Query parametre

| Parameter | Typ     | Povinný | Význam                                       |
| --------- | ------- | ------- | -------------------------------------------- |
| `page`    | Integer | nie     | Číslo stránky                                |
| `size`    | Integer | nie     | Počet komentárov                             |
| `sort`    | String  | nie     | Zoradenie, napríklad `newest` alebo `oldest` |

### Úspešná odpoveď

```http
200 OK
```

```json
{
  "content": [
    {
      "id": "5ff6262d-4370-4f87-94eb-ed51d827ef52",
      "text": "Pekná trasa, ale záver je pomerne strmý.",
      "author": {
        "id": "f0d71e83-7c50-48fc-9f48-1f3bd6738a91",
        "name": "Dominik"
      },
      "createdAt": "2026-07-20T14:30:00Z",
      "updatedAt": "2026-07-20T14:30:00Z"
    }
  ],
  "page": 0,
  "size": 20,
  "totalElements": 1,
  "totalPages": 1
}
```

### Možné chyby

| Kód   | Situácia         |
| ----- | ---------------- |
| `404` | Trasa neexistuje |

---

## 13.2 Pridanie komentára

```http
POST /api/v1/trails/{trailId}/comments
```

### Prístup

Prihlásený používateľ alebo administrátor.

### Request body

```json
{
  "text": "Pekná trasa, ale záver je pomerne strmý."
}
```

### Validačné pravidlá

* text je povinný,
* komentár nesmie byť prázdny ani pozostávať iba z medzier,
* komentár môže mať napríklad maximálne 2 000 znakov.

### Úspešná odpoveď

```http
201 Created
```

```json
{
  "id": "5ff6262d-4370-4f87-94eb-ed51d827ef52",
  "text": "Pekná trasa, ale záver je pomerne strmý.",
  "author": {
    "id": "f0d71e83-7c50-48fc-9f48-1f3bd6738a91",
    "name": "Dominik"
  },
  "trailId": "43dd0a7e-4f86-4f56-b047-28eb94cccb71",
  "createdAt": "2026-07-20T14:30:00Z",
  "updatedAt": "2026-07-20T14:30:00Z"
}
```

### Možné chyby

| Kód   | Situácia                              |
| ----- | ------------------------------------- |
| `400` | Komentár je prázdny alebo príliš dlhý |
| `401` | Používateľ nie je prihlásený          |
| `404` | Trasa neexistuje                      |

---

## 13.3 Úprava vlastného komentára

```http
PUT /api/v1/comments/{commentId}
```

### Prístup

Autor komentára alebo administrátor.

### Request body

```json
{
  "text": "Upravený text komentára."
}
```

### Úspešná odpoveď

```http
200 OK
```

```json
{
  "id": "5ff6262d-4370-4f87-94eb-ed51d827ef52",
  "text": "Upravený text komentára.",
  "author": {
    "id": "f0d71e83-7c50-48fc-9f48-1f3bd6738a91",
    "name": "Dominik"
  },
  "createdAt": "2026-07-20T14:30:00Z",
  "updatedAt": "2026-07-20T15:10:00Z"
}
```

### Možné chyby

| Kód   | Situácia                            |
| ----- | ----------------------------------- |
| `400` | Neplatný text komentára             |
| `401` | Používateľ nie je prihlásený        |
| `403` | Používateľ nie je autorom komentára |
| `404` | Komentár neexistuje                 |

### Poznámka

Úpravu komentárov možno presunúť mimo MVP, ak nie je pokrytá use casmi.

---

## 13.4 Odstránenie komentára

```http
DELETE /api/v1/comments/{commentId}
```

### Prístup

Autor komentára alebo administrátor.

### Úspešná odpoveď

```http
204 No Content
```

### Možné chyby

| Kód   | Situácia                                      |
| ----- | --------------------------------------------- |
| `401` | Používateľ nie je prihlásený                  |
| `403` | Používateľ nemá oprávnenie komentár odstrániť |
| `404` | Komentár neexistuje                           |

### Poznámka

Endpoint patrí do MVP iba vtedy, ak sa rozhodneme podporovať mazanie komentárov.

---

# 14. Endpointy hlásení trás

## 14.1 Vytvorenie hlásenia

```http
POST /api/v1/trails/{trailId}/reports
```

### Prístup

Prihlásený používateľ alebo administrátor.

### Request body

```json
{
  "description": "Uvedené prevýšenie pravdepodobne nie je správne."
}
```

### Validačné pravidlá

* opis je povinný,
* opis nesmie byť prázdny,
* opis môže mať maximálne napríklad 2 000 znakov.

### Úspešná odpoveď

```http
201 Created
```

```json
{
  "id": "88e7bd72-f692-4712-8cc9-92cbb5dfbb10",
  "trailId": "43dd0a7e-4f86-4f56-b047-28eb94cccb71",
  "description": "Uvedené prevýšenie pravdepodobne nie je správne.",
  "status": "OPEN",
  "createdAt": "2026-07-20T14:30:00Z"
}
```

### Možné chyby

| Kód   | Situácia                     |
| ----- | ---------------------------- |
| `400` | Neplatný opis                |
| `401` | Používateľ nie je prihlásený |
| `404` | Trasa neexistuje             |

---

# 15. Administrátorské endpointy trás

Všetky endpointy v tejto sekcii vyžadujú rolu `ADMIN`.

## 15.1 Vytvorenie trasy

```http
POST /api/v1/admin/trails
```

### Request body

```json
{
  "name": "Výstup na Veľký Choč",
  "description": "Turistická trasa vedúca na vrchol Veľkého Choča.",
  "locationId": "08c71173-4f2e-44b6-868d-e054c296312b",
  "distanceKm": 13.4,
  "durationMinutes": 330,
  "elevationGainM": 950,
  "difficulty": "HARD",
  "routeType": "OUT_AND_BACK",
  "externalMapUrl": "https://example.com/map",
  "trailPoints": [
    {
      "latitude": 49.147215,
      "longitude": 19.353492,
      "elevationM": 670,
      "orderIndex": 0
    },
    {
      "latitude": 49.150118,
      "longitude": 19.344291,
      "elevationM": 1611,
      "orderIndex": 1
    }
  ]
}
```

### Validačné pravidlá

* názov je povinný,
* opis je povinný,
* lokalita musí existovať,
* vzdialenosť musí byť väčšia ako nula,
* trvanie musí byť väčšie ako nula,
* prevýšenie nesmie byť záporné,
* náročnosť musí byť platná hodnota,
* typ trasy musí byť platná hodnota,
* GPS súradnice musia byť v povolenom rozsahu,
* poradie GPS bodov musí byť jedinečné,
* trasa musí obsahovať minimálny počet bodov podľa finálneho rozhodnutia.

### Úspešná odpoveď

```http
201 Created
```

Odpoveď obsahuje detail vytvorenej trasy.

### Možné chyby

| Kód   | Situácia                                |
| ----- | --------------------------------------- |
| `400` | Neplatné údaje                          |
| `401` | Používateľ nie je prihlásený            |
| `403` | Používateľ nemá rolu `ADMIN`            |
| `404` | Lokalita neexistuje                     |
| `409` | Trasa s konfliktnými údajmi už existuje |

---

## 15.2 Úprava trasy

```http
PUT /api/v1/admin/trails/{trailId}
```

### Prístup

Administrátor.

### Request body

Request používa rovnakú alebo podobnú štruktúru ako vytvorenie trasy.

```json
{
  "name": "Výstup na Veľký Choč",
  "description": "Aktualizovaný opis turistickej trasy.",
  "locationId": "08c71173-4f2e-44b6-868d-e054c296312b",
  "distanceKm": 13.6,
  "durationMinutes": 340,
  "elevationGainM": 970,
  "difficulty": "HARD",
  "routeType": "OUT_AND_BACK",
  "externalMapUrl": "https://example.com/map",
  "trailPoints": [
    {
      "latitude": 49.147215,
      "longitude": 19.353492,
      "elevationM": 670,
      "orderIndex": 0
    },
    {
      "latitude": 49.150118,
      "longitude": 19.344291,
      "elevationM": 1611,
      "orderIndex": 1
    }
  ]
}
```

### Úspešná odpoveď

```http
200 OK
```

Odpoveď obsahuje aktualizovaný detail trasy.

### Možné chyby

| Kód   | Situácia                        |
| ----- | ------------------------------- |
| `400` | Neplatné údaje                  |
| `401` | Používateľ nie je prihlásený    |
| `403` | Používateľ nemá rolu `ADMIN`    |
| `404` | Trasa alebo lokalita neexistuje |
| `409` | Konflikt pri súbežnej úprave    |

---

## 15.3 Deaktivácia alebo odstránenie trasy

Možnosť A – deaktivácia:

```http
PATCH /api/v1/admin/trails/{trailId}/status
```

Request:

```json
{
  "active": false
}
```

Možnosť B – fyzické odstránenie:

```http
DELETE /api/v1/admin/trails/{trailId}
```

### Poznámka

Pred implementáciou treba rozhodnúť, či MVP:

* umožní fyzické odstránenie trasy,
* umožní iba deaktiváciu,
* alebo nebude obsahovať ani jednu z týchto operácií.

Odporúčané riešenie je deaktivácia trasy, pretože zachová súvisiace hodnotenia, komentáre a ďalšie historické údaje.

---

# 16. Administrátorské endpointy hlásení

## 16.1 Zobrazenie hlásení

```http
GET /api/v1/admin/reports
```

### Prístup

Administrátor.

### Query parametre

| Parameter | Typ     | Povinný | Význam                  |
| --------- | ------- | ------- | ----------------------- |
| `status`  | String  | nie     | Filtrovanie podľa stavu |
| `page`    | Integer | nie     | Číslo stránky           |
| `size`    | Integer | nie     | Počet položiek          |

### Úspešná odpoveď

```http
200 OK
```

```json
{
  "content": [
    {
      "id": "88e7bd72-f692-4712-8cc9-92cbb5dfbb10",
      "trail": {
        "id": "43dd0a7e-4f86-4f56-b047-28eb94cccb71",
        "name": "Výstup na Veľký Choč"
      },
      "author": {
        "id": "f0d71e83-7c50-48fc-9f48-1f3bd6738a91",
        "name": "Dominik"
      },
      "description": "Uvedené prevýšenie pravdepodobne nie je správne.",
      "status": "OPEN",
      "adminNote": null,
      "createdAt": "2026-07-20T14:30:00Z",
      "resolvedAt": null
    }
  ],
  "page": 0,
  "size": 20,
  "totalElements": 1,
  "totalPages": 1
}
```

---

## 16.2 Zmena stavu hlásenia

```http
PATCH /api/v1/admin/reports/{reportId}
```

### Prístup

Administrátor.

### Request body

```json
{
  "status": "RESOLVED",
  "adminNote": "Prevýšenie bolo opravené."
}
```

### Povolené hodnoty stavu

```text
OPEN
IN_PROGRESS
RESOLVED
REJECTED
```

### Úspešná odpoveď

```http
200 OK
```

```json
{
  "id": "88e7bd72-f692-4712-8cc9-92cbb5dfbb10",
  "status": "RESOLVED",
  "adminNote": "Prevýšenie bolo opravené.",
  "resolvedAt": "2026-07-20T16:00:00Z"
}
```

---

# 17. Administrátorské endpointy používateľov

Táto časť sa použije iba vtedy, ak správa používateľov zostáva súčasťou MVP.

## 17.1 Zobrazenie používateľov

```http
GET /api/v1/admin/users
```

### Prístup

Administrátor.

### Query parametre

| Parameter | Typ     | Povinný | Význam                                |
| --------- | ------- | ------- | ------------------------------------- |
| `query`   | String  | nie     | Vyhľadávanie podľa mena alebo e-mailu |
| `role`    | String  | nie     | Filtrovanie podľa roly                |
| `enabled` | Boolean | nie     | Filtrovanie podľa stavu účtu          |
| `page`    | Integer | nie     | Číslo stránky                         |
| `size`    | Integer | nie     | Počet položiek                        |

---

## 17.2 Zmena stavu používateľského účtu

```http
PATCH /api/v1/admin/users/{userId}/status
```

### Prístup

Administrátor.

### Request body

```json
{
  "enabled": false
}
```

### Úspešná odpoveď

```http
200 OK
```

```json
{
  "id": "f0d71e83-7c50-48fc-9f48-1f3bd6738a91",
  "name": "Dominik",
  "email": "dominik@example.com",
  "role": "USER",
  "enabled": false
}
```

Administrátor nesmie deaktivovať vlastný účet, ak by tým v systéme nezostal žiadny aktívny administrátor.

---

# 18. Prehľad endpointov

## Verejné endpointy

| Metóda | Endpoint                     | Účel            |
| ------ | ---------------------------- | --------------- |
| `POST` | `/auth/register`             | Registrácia     |
| `POST` | `/auth/login`                | Prihlásenie     |
| `GET`  | `/trails`                    | Vyhľadanie trás |
| `GET`  | `/trails/{trailId}`          | Detail trasy    |
| `GET`  | `/trails/{trailId}/comments` | Komentáre trasy |
| `GET`  | `/locations`                 | Zoznam lokalít  |

## Endpointy prihláseného používateľa

| Metóda   | Endpoint                        | Účel                            |
| -------- | ------------------------------- | ------------------------------- |
| `POST`   | `/auth/logout`                  | Odhlásenie                      |
| `GET`    | `/users/me`                     | Aktuálny používateľ             |
| `PUT`    | `/users/me/favorites/{trailId}` | Pridanie obľúbenej trasy        |
| `GET`    | `/users/me/favorites`           | Zoznam obľúbených trás          |
| `DELETE` | `/users/me/favorites/{trailId}` | Odstránenie z obľúbených        |
| `PUT`    | `/trails/{trailId}/rating`      | Pridanie alebo zmena hodnotenia |
| `DELETE` | `/trails/{trailId}/rating`      | Odstránenie hodnotenia          |
| `POST`   | `/trails/{trailId}/comments`    | Pridanie komentára              |
| `PUT`    | `/comments/{commentId}`         | Úprava komentára                |
| `DELETE` | `/comments/{commentId}`         | Odstránenie komentára           |
| `POST`   | `/trails/{trailId}/reports`     | Nahlásenie problému             |

## Administrátorské endpointy

| Metóda   | Endpoint                         | Účel                              |
| -------- | -------------------------------- | --------------------------------- |
| `POST`   | `/admin/trails`                  | Vytvorenie trasy                  |
| `PUT`    | `/admin/trails/{trailId}`        | Úprava trasy                      |
| `PATCH`  | `/admin/trails/{trailId}/status` | Aktivácia alebo deaktivácia trasy |
| `DELETE` | `/admin/trails/{trailId}`        | Odstránenie trasy                 |
| `GET`    | `/admin/reports`                 | Zobrazenie hlásení                |
| `PATCH`  | `/admin/reports/{reportId}`      | Spracovanie hlásenia              |
| `GET`    | `/admin/users`                   | Zobrazenie používateľov           |
| `PATCH`  | `/admin/users/{userId}/status`   | Aktivácia alebo deaktivácia účtu  |

Všetky cesty v tabuľke používajú prefix:

```text
/api/v1
```

---

# 19. DTO objekty

Backend nebude vracať databázové alebo JPA entity priamo.

Použijú sa samostatné DTO objekty.

## Príklady vstupných DTO

```text
RegisterRequest
LoginRequest
TrailSearchRequest
CreateTrailRequest
UpdateTrailRequest
RatingRequest
CreateCommentRequest
CreateTrailReportRequest
UpdateTrailReportRequest
```

## Príklady výstupných DTO

```text
UserResponse
AuthResponse
TrailSummaryResponse
TrailDetailResponse
LocationResponse
FavoriteTrailResponse
RatingResponse
CommentResponse
TrailReportResponse
PagedResponse<T>
ApiErrorResponse
```

Použitie DTO zabraňuje:

* nechcenému zverejneniu interných atribútov,
* priamemu prepojeniu verejného API s databázovým modelom,
* problémom s nekonečnými cyklami pri serializácii vzťahov,
* komplikovaným zmenám API pri úprave databázy.

---

# 20. Stránkovanie

Všetky endpointy, ktoré môžu vracať väčší počet položiek, budú stránkované.

Použijú parametre:

```text
page
size
sort
```

Príklad:

```http
GET /api/v1/trails?page=0&size=20&sort=name,asc
```

Všeobecná odpoveď:

```json
{
  "content": [],
  "page": 0,
  "size": 20,
  "totalElements": 0,
  "totalPages": 0,
  "first": true,
  "last": true
}
```

Server má nastaviť maximálnu povolenú hodnotu `size`, napríklad `100`, aby klient nemohol jednou požiadavkou načítať neobmedzený počet záznamov.

---

# 21. Bezpečnostné zásady API

API musí dodržiavať tieto pravidlá:

* heslá sa nikdy nevracajú v odpovedi,
* `passwordHash` sa nikdy nenachádza v DTO,
* používateľská identita sa získava z autentifikácie,
* používateľ nemôže meniť obľúbené trasy iného používateľa,
* používateľ môže upraviť iba vlastný komentár,
* administrátorské operácie vyžadujú rolu `ADMIN`,
* vstupy sa validujú pred spracovaním,
* SQL a JPA chyby sa nevracajú priamo klientovi,
* chybové správy nesmú odhaľovať citlivé interné informácie,
* prihlasovanie má byť chránené proti neobmedzenému počtu pokusov v budúcej produkčnej verzii.

---

# 22. Otvorené rozhodnutia

Pred implementáciou je potrebné potvrdiť:

1. Či sa použije JWT alebo serverová relácia.
2. Či bude použitý obnovovací token.
3. Či môže používateľ odstrániť vlastné hodnotenie.
4. Či môže používateľ upraviť a odstrániť komentár.
5. Či ostáva nahlasovanie problémov súčasťou MVP.
6. Či ostáva administrátorská správa používateľov súčasťou MVP.
7. Či sa trasy budú fyzicky mazať alebo iba deaktivovať.
8. Či sa GPS body budú vracať vo verejnom detaile trasy už v MVP.
9. Či má byť `routeType` povinný.
10. Aké presné validačné limity budú platiť pre textové polia.
11. Či vyhľadávanie použije iba klasické filtrovanie alebo aj skóre zhody.
12. Či bude dokumentácia API generovaná pomocou OpenAPI a Swagger UI.

---

# 23. Odporúčanie pre implementáciu

API by sa malo implementovať postupne podľa funkčných oblastí.

Odporúčané poradie:

1. lokality a verejné trasy,
2. detail trasy,
3. registrácia a prihlásenie,
4. aktuálny používateľ,
5. obľúbené trasy,
6. hodnotenia,
7. komentáre,
8. administrátorská správa trás,
9. hlásenia,
10. správa používateľov.

Ako prvý vertikálny rez je vhodné implementovať:

```text
GET /api/v1/trails
GET /api/v1/trails/{trailId}
GET /api/v1/locations
```

Tento krok umožní overiť:

* databázový model,
* JPA entity,
* repository vrstvu,
* service vrstvu,
* controllery,
* DTO mapovanie,
* validáciu,
* stránkovanie,
* základnú komunikáciu s frontendom.

---

# 24. Zhrnutie

REST API aplikácie TrailPlanner používa:

* spoločný prefix `/api/v1`,
* JSON formát,
* štandardné HTTP metódy,
* konzistentné stavové kódy,
* DTO objekty,
* jednotný formát chýb,
* stránkovanie zoznamov,
* oddelenie verejných, používateľských a administrátorských operácií.

Návrh pokrýva:

* autentifikáciu,
* vyhľadávanie a detail trás,
* lokality,
* obľúbené trasy,
* hodnotenia,
* komentáre,
* hlásenia problémov,
* administrátorskú správu trás a používateľov.

Pred implementáciou sa dokument upraví podľa finálne potvrdeného rozsahu MVP a zvoleného spôsobu autentifikácie.
