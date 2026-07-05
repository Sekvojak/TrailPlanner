# Domain Model – TrailPlanner

## 1. Účel doménového modelu

Tento dokument opisuje hlavné doménové entity systému TrailPlanner, ich zodpovednosti a vzťahy medzi nimi.

TrailPlanner je webová aplikácia na vyhľadávanie a odporúčanie turistických trás podľa preferencií používateľa.

---

## 2. Hlavné entity

### User

Predstavuje používateľa systému.

**Zodpovednosti:**

* registrácia a prihlásenie do systému,
* správa vlastného účtu,
* pridávanie trás medzi obľúbené,
* hodnotenie trás,
* pridávanie komentárov.

**Atribúty:**

* id
* name
* email
* passwordHash
* role
* createdAt

---

### Role

Predstavuje oprávnenie používateľa v systéme.

**Typy rolí:**

* USER
* ADMIN

**Zodpovednosti:**

* určuje, čo môže používateľ v systéme vykonávať,
* odlišuje bežného používateľa od administrátora.

---

### Trail

Predstavuje turistickú trasu.

**Zodpovednosti:**

* uchováva základné informácie o trase,
* poskytuje údaje potrebné na vyhľadávanie a odporúčanie,
* obsahuje hodnotenia, komentáre a ďalšie detaily.
* môže obsahovať geometriu trasy vo forme GPS bodov, 
* je pripravená na budúce zobrazenie na mape.

**Atribúty:**

* id
* name
* description
* location
* distance
* duration
* elevationGain
* difficulty
* routeType
* createdAt
* updatedAt
* startLatitude 
* startLongitude 
* endLatitude 
* endLongitude

---

### Difficulty

Predstavuje náročnosť turistickej trasy.

**Príklady hodnôt:**

* EASY
* MEDIUM
* HARD

**Zodpovednosti:**

* umožňuje filtrovanie trás podľa náročnosti,
* pomáha pri odporúčaní vhodných trás.

---

### RouteType

Predstavuje typ turistickej trasy.

**Príklady hodnôt:**

* LOOP
* OUT_AND_BACK
* POINT_TO_POINT

**Zodpovednosti:**

* opisuje charakter trasy,
* umožňuje používateľovi lepšie pochopiť priebeh trasy.

---

### Location

Predstavuje geografické umiestnenie trasy.

**Zodpovednosti:**

* uchováva informácie o oblasti alebo mieste,
* môže byť použitá pri vyhľadávaní podľa regiónu,
* môže byť neskôr rozšírená o GPS súradnice.

**Atribúty:**

* id
* name
* region
* country
* latitude
* longitude

---

### TrailPoint

Predstavuje jeden GPS bod na trase.

**Zodpovednosti:**
- uchováva presnú polohu bodu na trase,
- umožňuje zoradiť body podľa poradia,
- môže byť použitý na budúce vykreslenie trasy na mape,
- môže slúžiť na výpočet vzdialenosti a prevýšenia.

**Atribúty:**
- id
- trailId
- latitude
- longitude
- elevation
- orderIndex

---

### UserPreference

Predstavuje preferencie používateľa pri hľadaní alebo odporúčaní trás.

**Zodpovednosti:**

* uchováva kritériá, podľa ktorých chce používateľ nájsť trasu,
* slúži ako vstup pre odporúčací mechanizmus.

**Atribúty:**

* preferredDifficulty
* maxDistance
* maxDuration
* preferredLocation
* preferredRouteType

---

### FavoriteTrail

Predstavuje vzťah medzi používateľom a trasou, ktorú si používateľ označil ako obľúbenú.

**Zodpovednosti:**

* eviduje obľúbené trasy používateľa,
* umožňuje používateľovi uložiť si trasu na neskôr.

**Atribúty:**

* id
* userId
* trailId
* createdAt

---

### Rating

Predstavuje hodnotenie trasy používateľom.

**Zodpovednosti:**

* umožňuje používateľovi ohodnotiť trasu,
* slúži na výpočet priemerného hodnotenia trasy.

**Atribúty:**

* id
* value
* userId
* trailId
* createdAt

---

### Comment

Predstavuje komentár používateľa k trase.

**Zodpovednosti:**

* umožňuje používateľovi zdieľať skúsenosť s trasou,
* zobrazuje spätnú väzbu ostatným používateľom.

**Atribúty:**

* id
* text
* userId
* trailId
* createdAt
* updatedAt

---

### TrailRecommendation

Predstavuje odporúčanie trasy používateľovi.

**Zodpovednosti:**

* prepája používateľské preferencie s vhodnými trasami,
* reprezentuje výsledok odporúčacieho procesu.

**Atribúty:**

* id
* userId
* trailId
* reason
* score
* createdAt

---

### AdminAction

Predstavuje administrátorský zásah v systéme.

**Zodpovednosti:**

* eviduje správu trás administrátorom,
* môže slúžiť ako jednoduchý audit zmien.

**Atribúty:**

* id
* adminId
* actionType
* targetEntity
* targetEntityId
* createdAt

---

## 3. Vzťahy medzi entitami

### User – FavoriteTrail – Trail

* Jeden používateľ môže mať viacero obľúbených trás.
* Jedna trasa môže byť obľúbená u viacerých používateľov.
* Vzťah medzi používateľom a trasou je typu many-to-many, riešený cez entitu FavoriteTrail.

---

### User – Rating – Trail

* Jeden používateľ môže hodnotiť viacero trás.
* Jedna trasa môže mať viacero hodnotení.
* Používateľ by mal mať maximálne jedno hodnotenie pre jednu konkrétnu trasu.

---

### User – Comment – Trail

* Jeden používateľ môže pridať viacero komentárov.
* Jedna trasa môže mať viacero komentárov.
* Každý komentár patrí práve jednému používateľovi a jednej trase.

---

### Trail – Location

- Jedna trasa patrí k jednej hlavnej lokalite.
- Jedna lokalita môže obsahovať viacero trás.
- Location opisuje oblasť trasy, nie presný priebeh trasy.
---

### Trail – Difficulty

* Každá trasa má jednu úroveň náročnosti.
* Jedna úroveň náročnosti môže byť priradená viacerým trasám.

---

### Trail – TrailPoint

- Jedna trasa môže mať viacero GPS bodov.
- Jeden GPS bod patrí práve jednej trase.
- Body sú zoradené podľa atribútu orderIndex.
- Tento vzťah umožňuje budúce zobrazenie trasy na mape.

---

### Trail – RouteType

* Každá trasa má jeden typ trasy.
* Jeden typ trasy môže byť použitý pri viacerých trasách.

---

### User – UserPreference

* Používateľ môže mať uložené preferencie.
* Preferencie sa používajú pri odporúčaní trás.

---

### UserPreference – TrailRecommendation – Trail

* Preferencie používateľa slúžia ako vstup pre odporúčanie trás.
* Odporúčanie prepája používateľa s konkrétnou trasou.
* Jedno odporúčanie patrí jednému používateľovi a jednej trase.

---

### Admin – Trail

* Administrátor môže vytvárať, upravovať a mazať trasy.
* Administrátorské akcie môžu byť evidované cez entitu AdminAction.

---

## 4. Zjednodušený textový diagram

```text
User
 ├── has Role
 ├── has UserPreference
 ├── creates Comment
 ├── creates Rating
 ├── saves FavoriteTrail
 └── receives TrailRecommendation

Trail
 ├── has Difficulty
 ├── has RouteType
 ├── belongs to Location
 ├── has TrailPoints
 ├── has Comments
 ├── has Ratings
 └── can be saved as FavoriteTrail

TrailPoint
 └── belongs to Trail

FavoriteTrail
 ├── belongs to User
 └── belongs to Trail

Rating
 ├── belongs to User
 └── belongs to Trail

Comment
 ├── belongs to User
 └── belongs to Trail

TrailRecommendation
 ├── belongs to User
 └── references Trail

AdminAction
 ├── performed by User with ADMIN role
 └── affects Trail or other managed entity
```

---

## 5. Poznámky k MVP

Pre MVP sú najdôležitejšie tieto entity:

* User
* Trail
* Location
* FavoriteTrail
* Rating
* Comment
* UserPreference
* TrailRecommendation

Entity ako AdminAction môžu byť implementované neskôr, ak bude potrebné evidovať administrátorské zásahy.

---

## 6. Možné rozšírenia do budúcna

V ďalších verziách systému je možné pridať:

* TrailImage – obrázky turistických trás,
* TrailPoint – body na trase alebo GPS body,
* Tag – značky ako výhľad, les, vodopád, rodinná trasa,
* WeatherInfo – počasie pre danú lokalitu,
* ExternalMapData – integrácia s externými mapovými službami,
* CompletedTrail – evidencia trás, ktoré používateľ absolvoval.
