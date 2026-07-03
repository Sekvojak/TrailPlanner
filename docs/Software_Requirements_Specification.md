# TrailPlanner

Webová aplikácia na plánovanie turistických trás podľa preferencií používateľov.

## Účel dokumentu
Tento dokument slúži ako špecifikácia požiadaviek pre webovú aplikáciu TrailPlanner. Jeho cieľom je definovať funkcionalitu systému, požiadavky na jeho správanie a základné pravidlá fungovania aplikácie. Dokument predstavuje podklad pre návrh architektúry, databázového modelu, implementáciu jednotlivých funkcionalít a následné testovanie systému

## Predstavenie projektu
TrailPlanner je webová aplikácia určená na plánovanie turistických výletov podľa individuálnych preferencií používateľa. Umožňuje vyhľadávať turistické trasy na základe zvolených kritérií, ako sú dĺžka trasy, prevýšenie, náročnosť alebo lokalita, a poskytuje používateľovi odporúčania vhodných trás.

Cieľom aplikácie nie je nahradiť klasické mapové aplikácie alebo navigáciu, ale zjednodušiť proces výberu vhodnej turistickej trasy. Používateľ tak nemusí manuálne porovnávať desiatky možností z rôznych zdrojov, ale dostane prehľad trás, ktoré najlepšie zodpovedajú jeho požiadavkám.

## Problém, ktorý aplikácia rieši
Pri plánovaní turistického výletu je často potrebné vyhľadávať informácie z viacerých zdrojov. Používateľ musí samostatne hľadať vhodné trasy, overovať ich náročnosť, dĺžku, prevýšenie, prípadne zisťovať aktuálne podmienky alebo hodnotenia od ostatných turistov.

Hoci existuje viacero kvalitných mapových a turistických aplikácií, výber vhodnej trasy je vo veľkej miere ponechaný na samotného používateľa. Ten musí jednotlivé možnosti manuálne filtrovať a porovnávať.

TrailPlanner sa zameriava na zjednodušenie tohto procesu tým, že na základe zadaných preferencií vyberie a odporučí trasy, ktoré najlepšie spĺňajú požadované kritériá.

## Cieľ aplikácie
Cieľom projektu je vytvoriť webovú aplikáciu, ktorá používateľom umožní jednoducho plánovať turistické výlety podľa ich individuálnych preferencií.

Aplikácia bude poskytovať možnosť filtrovania turistických trás podľa rôznych parametrov, zobrazovania detailných informácií o jednotlivých trasách, ukladania odporúčanie trás na základe zadaných preferencií používateľa.

Projekt zároveň slúži ako praktická implementácia princípov vývoja backendových aplikácií s dôrazom na kvalitný návrh architektúry, bezpečnosť, testovanie a škálovateľnosť systému.


# Rozsah projektu

## Funkcionalita aplikácie

TrailPlanner bude webová aplikácia zameraná na odporúčanie turistických trás používateľom, ktorí nevedia, kam by sa chceli vybrať. Na rozdiel od klasických mapových aplikácií nebude používateľ povinný poznať konkrétny cieľ trasy. Aplikácia mu na základe zadaných preferencií ponúkne vhodné možnosti.

Používateľ bude môcť vyhľadávať turistické trasy bez registrácie. Vyhľadávanie bude založené na filtrovaní a odporúčaní podľa parametrov, ako sú lokalita, dĺžka trasy, prevýšenie, náročnosť, odhadovaný čas a typ trasy.

Registrovaný používateľ bude môcť ukladať obľúbené trasy, hodnotiť absolvované trasy, pridávať komentáre a prípadne fotografie k trasám.

Aplikácia bude obsahovať vlastnú databázu turistických trás. Tá nebude predstavovať kompletný mapový systém, ale kolekciu vybraných trás s potrebnými údajmi pre odporúčanie a zobrazenie detailu trasy.

Administrátor bude môcť spravovať údaje o turistických trasách, upravovať ich parametre a dopĺňať nové trasy do systému.

## Funkcionalita mimo rozsahu projektu

Prvá verzia aplikácie nebude slúžiť ako plnohodnotná mapová alebo navigačná aplikácia. Nebude poskytovať navigáciu počas turistiky, offline mapy ani sledovanie GPS polohy používateľa v reálnom čase.

Aplikácia nebude v prvej verzii automaticky získavať všetky turistické trasy z externých mapových služieb. Údaje o trasách budú spravované vo vlastnej databáze aplikácie.

Používatelia nebudú v prvej verzii vytvárať nové verejné trasy bez kontroly administrátora. Cieľom prvej verzie je sústrediť sa najmä na kvalitné vyhľadávanie, filtrovanie, odporúčanie a správu existujúcich trás.

Mobilná aplikácia nebude súčasťou prvej verzie projektu.

## Budúce rozšírenia

V budúcnosti môže byť aplikácia rozšírená o import GPX súborov, integráciu s externými mapovými službami, zobrazenie trás na interaktívnej mape, odporúčania podľa aktuálneho počasia, pokročilejší odporúčací algoritmus, AI asistenta pre plánovanie výletov a mobilnú aplikáciu vytvorenú v Kotlin/Jetpack Compose.


# Používateľské roly

## Návštevník (Guest)

Návštevník predstavuje používateľa, ktorý pristupuje do aplikácie bez registrácie alebo prihlásenia. Má prístup k základným funkciám aplikácie, ktorých cieľom je umožniť vyhľadávanie a prehliadanie turistických trás.

Návštevník môže:
- vyhľadávať turistické trasy,
- filtrovať trasy podľa dostupných kritérií,
- zobrazovať detail turistickej trasy,
- prezerať hodnotenia a komentáre ostatných používateľov.

Návštevník nemôže:
- ukladať obľúbené trasy,
- pridávať hodnotenia alebo komentáre,
- nahrávať fotografie,
- upravovať údaje v aplikácii.

---

## Registrovaný používateľ (Registered User)

Registrovaný používateľ je používateľ s vytvoreným účtom, ktorý má prístup ku všetkým funkciám určeným pre bežných používateľov aplikácie. Okrem možností dostupných návštevníkovi môže využívať personalizované funkcie systému.

Registrovaný používateľ môže:
- vykonávať všetky činnosti dostupné návštevníkovi,
- ukladať trasy medzi obľúbené,
- pridávať hodnotenia a komentáre k trasám,
- nahrávať fotografie z absolvovaných trás,
- spravovať svoj používateľský profil.

---

## Administrátor (Administrator)

Administrátor je používateľ zodpovedný za správu obsahu aplikácie. Jeho úlohou je zabezpečiť aktuálnosť a správnosť údajov uložených v systéme.

Administrátor môže:
- vykonávať všetky činnosti registrovaného používateľa,
- pridávať nové turistické trasy,
- upravovať údaje existujúcich trás,
- odstraňovať neplatné alebo duplicitné trasy,
- spravovať používateľské hodnotenia, komentáre a fotografie,
- spravovať používateľské účty.


# 4. Funkčné požiadavky

Funkčné požiadavky definujú funkcionalitu, ktorú musí systém poskytovať jednotlivým používateľom.

## 4.1 Správa používateľov

Systém musí umožniť registráciu nového používateľa pomocou e-mailovej adresy a hesla.

Systém musí umožniť prihlásenie registrovaného používateľa.

Systém musí umožniť bezpečné odhlásenie používateľa.

Registrovaný používateľ musí mať možnosť upravovať údaje svojho používateľského profilu.

Systém musí umožniť zmenu hesla registrovaného používateľa.

---

## 4.2 Vyhľadávanie turistických trás

Systém musí umožniť vyhľadávanie turistických trás bez nutnosti registrácie.

Systém musí umožniť filtrovanie trás podľa lokality.

Systém musí umožniť filtrovanie podľa dĺžky trasy.

Systém musí umožniť filtrovanie podľa prevýšenia.

Systém musí umožniť filtrovanie podľa náročnosti.

Systém musí umožniť filtrovanie podľa typu trasy.

Systém musí umožniť kombináciu viacerých filtrov súčasne.

---

## 4.3 Detail turistickej trasy

Systém musí zobrazovať detail turistickej trasy.

Detail trasy musí obsahovať minimálne:

- názov trasy,
- lokalitu,
- dĺžku,
- prevýšenie,
- odhadovaný čas absolvovania,
- náročnosť,
- stručný popis,
- fotografie,
- hodnotenie používateľov.

---

## 4.4 Obľúbené trasy

Registrovaný používateľ musí mať možnosť uložiť turistickú trasu medzi obľúbené.

Registrovaný používateľ musí mať možnosť odstrániť trasu zo zoznamu obľúbených.

Registrovaný používateľ musí mať možnosť zobraziť zoznam svojich obľúbených trás.

---

## 4.5 Hodnotenia a komentáre

Registrovaný používateľ musí mať možnosť ohodnotiť absolvovanú turistickú trasu.

Registrovaný používateľ musí mať možnosť pridať textový komentár.

Registrovaný používateľ musí mať možnosť nahrať fotografie k absolvovanej trase.

Systém musí zobrazovať priemerné hodnotenie každej trasy.

---

## 4.6 Odporúčanie trás

Systém musí odporučiť turistické trasy na základe preferencií zadaných používateľom.

Pri odporúčaní trás systém zohľadní najmä:

- lokalitu,
- dĺžku trasy,
- prevýšenie,
- náročnosť,
- typ trasy.

Systém musí zoradiť výsledky podľa ich vhodnosti.

---

## 4.7 Administrácia

Administrátor musí mať možnosť vytvárať nové turistické trasy.

Administrátor musí mať možnosť upravovať existujúce turistické trasy.

Administrátor musí mať možnosť odstraňovať turistické trasy.

Administrátor musí mať možnosť spravovať fotografie, komentáre a hodnotenia používateľov.



# 5. Nefunkčné požiadavky

Nefunkčné požiadavky definujú vlastnosti systému, ktoré nepopisujú jeho funkcionalitu, ale určujú spôsob jeho prevádzky, bezpečnosti a kvality.

## 5.1 Použiteľnosť

Aplikácia bude dostupná prostredníctvom moderných webových prehliadačov.

Používateľské rozhranie bude navrhnuté s dôrazom na jednoduchosť a prehľadnosť.

---

## 5.2 Bezpečnosť

Používateľské heslá nesmú byť ukladané v čitateľnej podobe.

Systém musí zabezpečiť autentifikáciu registrovaných používateľov.

Každý používateľ bude mať prístup iba k údajom, na ktoré má oprávnenie.

---

## 5.3 Výkon

Systém by mal poskytovať výsledky vyhľadávania v čo najkratšom čase.

Aplikácia bude navrhnutá tak, aby bolo možné rozširovať databázu turistických trás bez výrazného zníženia výkonu.

---

## 5.4 Spoľahlivosť

Systém musí zabezpečiť konzistentné ukladanie údajov.

V prípade chyby musí používateľ dostať zrozumiteľnú chybovú správu.

---

## 5.5 Rozšíriteľnosť

Architektúra aplikácie bude navrhnutá tak, aby bolo možné jednoducho pridávať nové funkcionality.

Aplikácia bude pripravená na budúcu integráciu s externými službami, ako sú mapové alebo meteorologické API.

---

## 5.6 Prenositeľnosť

Aplikácia bude navrhnutá ako webová aplikácia nezávislá od operačného systému používateľa.

Backend a frontend budú oddelené, aby bolo možné v budúcnosti vytvoriť mobilnú aplikáciu bez zásadných zmien backendu.