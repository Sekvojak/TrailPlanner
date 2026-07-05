# Use Cases

# TrailPlanner

## UC-01 Vyhľadanie turistickej trasy

**Aktér:** Návštevník alebo registrovaný používateľ

**Cieľ:** Používateľ chce nájsť vhodnú turistickú trasu podľa svojich preferencií.

**Predpoklady:**
- Aplikácia obsahuje databázu turistických trás.
- Používateľ otvoril stránku vyhľadávania trás.

**Hlavný scenár:**
1. Používateľ otvorí stránku s vyhľadávaním trás.
2. Používateľ zadá preferencie, napríklad lokalitu, dĺžku, prevýšenie alebo náročnosť.
3. Používateľ spustí vyhľadávanie.
4. Systém overí zadané filtre.
5. Systém vyhľadá trasy zodpovedajúce zadaným kritériám.
6. Systém zoradí trasy podľa vhodnosti.
7. Systém zobrazí zoznam odporúčaných trás.

**Alternatívne scenáre:**
- Ak používateľ nezadá žiadne filtre, systém zobrazí všeobecne odporúčané alebo najlepšie hodnotené trasy.
- Ak systém nenájde žiadnu trasu, zobrazí informáciu, že neboli nájdené žiadne výsledky.
- Ak sú zadané filtre neplatné, systém zobrazí validačnú chybu.

**Výsledok:**
Používateľ vidí zoznam trás, ktoré zodpovedajú jeho požiadavkám.


## UC-02 Zobrazenie detailu trasy

**Aktér:** Návštevník alebo registrovaný používateľ

**Cieľ:** Používateľ chce zobraziť detaily vybranej turistickej trasy

**Predpoklady:** 
- Vybraná turistická trasa existuje
- Používateľ sa nachádza v zozname trás alebo má priamy odkaz na detail trasy.

**Hlavný scenár:**
1. Používateľ vyberie turistickú trasu zo zoznamu výsledkov.
2. Používateľ otvorí detail vybranej trasy.
3. Systém načíta údaje o vybranej trase.
4. Systém zobrazí detail trasy.
5. Systém zobrazí základné informácie, ako názov, lokalitu, dĺžku, prevýšenie, odhadovaný čas, náročnosť a popis.
6. Systém zobrazí doplnkové informácie, ako hodnotenie, komentáre, fotografie a upozornenia k trase.

**Alternatívne scenáre:**
- Ak vybraná trasa neexistuje, systém zobrazí informáciu, že trasa nebola nájdená.
- Ak sa nepodarí načítať niektoré doplnkové údaje, systém zobrazí aspoň základné informácie o trase.

**Výsledok:**  
Používateľ vidí detail vybranej turistickej trasy.

## UC-03 Registrácia používateľa

**Aktér:** Návštevník

**Cieľ:**  
Návštevník si chce vytvoriť používateľský účet.

**Predpoklady:**
- Návštevník nie je prihlásený.

**Hlavný scenár:**
1. Návštevník otvorí registračný formulár.
2. Zadá e-mail, používateľské meno a heslo.
3. Systém overí zadané údaje.
4. Systém vytvorí nový používateľský účet.
5. Systém informuje používateľa o úspešnej registrácii.

**Alternatívne scenáre:**
- Ak je e-mail už použitý, systém zobrazí validačnú chybu.
- Ak heslo nespĺňa požiadavky, systém zobrazí validačnú chybu.

**Výsledok:**  
Používateľ má vytvorený účet.

## UC-04 Prihlásenie používateľa

**Aktér:** Registrovaný používateľ

**Cieľ:**  
Používateľ sa chce prihlásiť do svojho účtu.

**Predpoklady:**
- Používateľ má vytvorený účet.
- Používateľ nie je prihlásený.

**Hlavný scenár:**
1. Používateľ otvorí prihlasovací formulár.
2. Zadá e-mail a heslo.
3. Systém overí prihlasovacie údaje.
4. Systém prihlási používateľa.
5. Systém presmeruje používateľa do aplikácie.

**Alternatívne scenáre:**
- Ak sú prihlasovacie údaje nesprávne, systém zobrazí chybovú správu.
- Ak účet neexistuje, systém zobrazí chybovú správu.

**Výsledok:**  
Používateľ je prihlásený.

## UC-05 Odhlásenie používateľa

**Aktér:** Registrovaný používateľ

**Cieľ:**  
Používateľ sa chce bezpečne odhlásiť zo svojho účtu.

**Predpoklady:**
- Používateľ je prihlásený.

**Hlavný scenár:**
1. Používateľ zvolí možnosť odhlásenia.
2. Systém ukončí používateľskú reláciu.
3. Systém presmeruje používateľa na verejnú časť aplikácie.

**Výsledok:**  
Používateľ je odhlásený.

## UC-06 Uloženie trasy medzi obľúbené

**Aktér:** Registrovaný používateľ

**Cieľ:**  
Používateľ si chce uložiť turistickú trasu medzi obľúbené.

**Predpoklady:**
- Používateľ je prihlásený.
- Vybraná turistická trasa existuje.

**Hlavný scenár:**
1. Používateľ otvorí detail turistickej trasy.
2. Používateľ zvolí možnosť uložiť trasu medzi obľúbené.
3. Systém overí, či trasa ešte nie je uložená medzi obľúbenými.
4. Systém uloží trasu do zoznamu obľúbených trás používateľa.
5. Systém zobrazí potvrdenie o úspešnom uložení.

**Alternatívne scenáre:**
- Ak je trasa už uložená medzi obľúbenými, systém informuje používateľa.
- Ak používateľ nie je prihlásený, systém ho vyzve na prihlásenie.

**Výsledok:**  
Trasa je uložená medzi obľúbenými trasami používateľa.

## UC-07 Zobrazenie obľúbených trás

**Aktér:** Registrovaný používateľ

**Cieľ:**  
Používateľ chce zobraziť zoznam svojich obľúbených trás.

**Predpoklady:**
- Používateľ je prihlásený.

**Hlavný scenár:**
1. Používateľ otvorí sekciu obľúbených trás.
2. Systém načíta obľúbené trasy používateľa.
3. Systém zobrazí zoznam obľúbených trás.

**Alternatívne scenáre:**
- Ak používateľ nemá žiadne obľúbené trasy, systém zobrazí prázdny stav s príslušnou informáciou.

**Výsledok:**  
Používateľ vidí svoje obľúbené trasy.

## UC-08 Odstránenie trasy z obľúbených

**Aktér:** Registrovaný používateľ

**Cieľ:**  
Používateľ chce odstrániť trasu zo svojho zoznamu obľúbených trás.

**Predpoklady:**
- Používateľ je prihlásený.
- Trasa je uložená medzi jeho obľúbenými trasami.

**Hlavný scenár:**
1. Používateľ otvorí zoznam obľúbených trás alebo detail trasy.
2. Používateľ zvolí možnosť odstrániť trasu z obľúbených.
3. Systém odstráni trasu zo zoznamu obľúbených trás používateľa.
4. Systém zobrazí potvrdenie o odstránení.

**Výsledok:**  
Trasa už nie je uložená medzi obľúbenými trasami používateľa.

## UC-09 Pridanie hodnotenia trasy

**Aktér:** Registrovaný používateľ

**Cieľ:**  
Používateľ chce ohodnotiť turistickú trasu.

**Predpoklady:**
- Používateľ je prihlásený.
- Turistická trasa existuje.

**Hlavný scenár:**
1. Používateľ otvorí detail turistickej trasy.
2. Používateľ zvolí možnosť pridať hodnotenie.
3. Používateľ zadá hodnotenie.
4. Systém overí zadané hodnotenie.
5. Systém uloží hodnotenie k trase.
6. Systém aktualizuje priemerné hodnotenie trasy.

**Alternatívne scenáre:**
- Ak používateľ zadá neplatné hodnotenie, systém zobrazí validačnú chybu.
- Ak používateľ už trasu hodnotil, systém môže pôvodné hodnotenie aktualizovať alebo ho upozorniť, že hodnotenie už existuje.

**Výsledok:**  
Hodnotenie je uložené a zohľadnené v priemernom hodnotení trasy.

## UC-10 Pridanie komentára k trase

**Aktér:** Registrovaný používateľ

**Cieľ:**  
Používateľ chce pridať komentár k turistickej trase.

**Predpoklady:**
- Používateľ je prihlásený.
- Turistická trasa existuje.

**Hlavný scenár:**
1. Používateľ otvorí detail turistickej trasy.
2. Používateľ zadá text komentára.
3. Systém overí obsah komentára.
4. Systém uloží komentár k trase.
5. Systém zobrazí komentár pri detaile trasy.

**Alternatívne scenáre:**
- Ak je komentár prázdny alebo príliš dlhý, systém zobrazí validačnú chybu.

**Výsledok:**  
Komentár je uložený a zobrazený pri turistickej trase.

## UC-11 Vytvorenie novej trasy administrátorom

**Aktér:** Administrátor

**Cieľ:**  
Administrátor chce pridať novú turistickú trasu do systému.

**Predpoklady:**
- Administrátor je prihlásený.
- Administrátor má oprávnenie spravovať turistické trasy.

**Hlavný scenár:**
1. Administrátor otvorí formulár na vytvorenie novej trasy.
2. Administrátor zadá údaje o trase.
3. Systém overí zadané údaje.
4. Systém vytvorí novú turistickú trasu.
5. Systém zobrazí potvrdenie o úspešnom vytvorení trasy.

**Alternatívne scenáre:**
- Ak sú zadané údaje neúplné alebo neplatné, systém zobrazí validačné chyby.
- Ak podobná trasa už existuje, systém môže administrátora upozorniť na možnú duplicitu.

**Výsledok:**  
Nová turistická trasa je dostupná v systéme.

## UC-12 Úprava existujúcej trasy administrátorom

**Aktér:** Administrátor

**Cieľ:**  
Administrátor chce upraviť údaje existujúcej turistickej trasy.

**Predpoklady:**
- Administrátor je prihlásený.
- Administrátor má oprávnenie spravovať turistické trasy.
- Upravovaná turistická trasa existuje.

**Hlavný scenár:**
1. Administrátor otvorí detail alebo administračný zoznam trás.
2. Administrátor vyberie trasu, ktorú chce upraviť.
3. Administrátor upraví vybrané údaje o trase.
4. Systém overí upravené údaje.
5. Systém uloží zmeny.
6. Systém zobrazí potvrdenie o úspešnej úprave.

**Alternatívne scenáre:**
- Ak sú upravené údaje neplatné, systém zobrazí validačné chyby.
- Ak trasa už neexistuje, systém zobrazí informáciu, že trasa nebola nájdená.

**Výsledok:**  
Údaje turistickej trasy sú aktualizované.