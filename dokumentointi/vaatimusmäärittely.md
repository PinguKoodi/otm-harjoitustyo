# Sovelluksen vaatimusmäärittely

## Sovelluksen yleiskuvaus
Tarkoituksena on tehdä yksinkertainen vuoropohjainen strategiapeli, jossa pelaaja kontrolloi omaa yhteiskuntaansa. Pelaaja saa sijoittaa kansalaiset itse valitsemallaan tavalla maatilan, tehtaan, laboratorion ja armeijan välillä. Tarkoituksena pelissä on kerätä mahdollisimman paljon tiedepisteitä laboratoriosta, samalla kuitenkin huolehtien kansalaisten ruuan, viihtyvyyden ja turvallisuuden riittävyydestä maatilan, tehtaan ja armeijan avulla.

## Käyttöliittymästä
Yksinkertaistettu kuva käyttöliittymästä

![kuva](https://github.com/PinguKoodi/otm-harjoitustyo/blob/master/dokumentointi/Grafiikat/Kayttoliittyma.png)

Pelissä on yhteensä neljä erilaista näkymää: aloitusnäkymä, varsinanen pelinäkymä, ohjeikkuna ja lista ihmisistä -ikkuna. Itse pelaaminen tapahtuu pelinäkymässä, ja muut näkymät vain tukevat sitä. Ensimmäisenä aukeaa aloitusnäkymä, josta pelaaja etenee valintansa mukaan peliin, ohjeeseen tai sulkee pelin.

## Pelin toiminnallisuus
Kun käyttäjä käynnistää ohjelman, hänelle avautuu aloitusikkuna, josta hän voi valita joko uuden pelin, tai ladata jo olemassa olevan tallennetun pelin. Aloitusikkunassa on myös mahdollisuus lukea lyhyt ohje pelin pelaamiseen, tai poistua pleistä

Aloitettuaan pelin, pelaaja voi laittaa työttömät kansalaiset valitsemaan tuotantopaikkaan, jossa he pysyvät kuolemaansa (80-100v) saakka. Sitten pelaaja painaa lopeta vuoro näppäintä, joka siirtää pelikelloa vuoden eteenpäin, ja kaikki laitokset tuottavat hyödykkeitä. Tarkoituksena on, että pelaaja joutuu valitsemaan sen välillä, että keskittyykö hän ruuantuotannon varmistamiseen, mikä vähentää tieteen tuotantoa, vai yrittääkö hän maksimoida tieteilijöiden määrän. Eri tuotantolaitoksilla on myös tarkoitus olla riippuvuuksia toisistaan siten, että:
1. Tiedepisteet parantavat tehtaan tuotantoa selvästi, ja hieman maatilan
2. Tehtaan tuotteet parantavat maatilan tuotantoa selvästi
3. Maatilalla tuotettava ruoka nostaa tyytyväisyystasoa, joka parantaa kaiken tuottamista
4. Armeijaa tarvitaan turvaamaan yleinen turvallisuus, jos ei ole tarpeeksi niin resursseja katoaa ja tuotanto pienenee.

Jokaisen vuoron aikana myös syntyy uusia ihmisiä, jotka voivat täysi-ikäisinä osallistua yhteiskunnan toimintaan ja tuottaa resursseja. Jokaisella ihmisellä on myös oma kokemustaso, joka vaikuttaa hänen tuottamansa resurssien määrään. Mitä kauemmin ihminen on tehnyt töitä, sen taitavampi hänestä tulee.
Lisäksi jos pelaajalta loppuu ruoka, hänen kansalaisensa alkavat kuolemaan ennenaikaisesti, ja jos kaikki kansalaiset kuolevat, pelaaja häviää pelin.

## Jatkokehitys mahdollisuuksia
- Erilaisia kertoimia ja asioiden vaikutusta toisiinsa, esimerkiksi tyytyväisyyden suurempi vaikutus resurssien tuotantoon
- Tieteen vaikutus ihmisten elinikään
- Satunnaisvaihteluja, kuten että ruuantuotanto on jonain vuonna tavallista pienempää/suurempaa, esim. hurrikaanin takia
- Moninpeli tietokonetta vastaan (kilpailu tiedepisteistä)
  - Moninpelissä mahdollisuus hyökätä toisen pelaajan kimppuun
- Erilaisia työtehtäviä
  - Opettaja, joka nopeuttaa lasten siirtymistä työvoimaan
  - Pomo, joka ei itse tuota mutta parantaa muiden tuotantoa
  - Synnytysvalmentaja joka kasvattaa syntyvyyttä
  - Lääkäri, joka tekee ihmisistä pidempi-ikäisiä
- Uusia oliolajeja, esimerkiksi robotit, jotka eivät saa kokemusta mutta kestävät pitkään eivätkä tarvitse paljoa ruokaa.
- Pelin vaikeutuminen ajan kuluessa
