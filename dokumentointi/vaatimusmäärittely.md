# Sovelluksen vaatimusmäärittely

## Sovelluksen yleiskuvaus
Tarkoituksena on tehdä yksinkertainen vuoropohjainen strategiapeli, jossa pelaaja kontrolloi omaa yhteiskuntaansa. Pelaaja saa sijoittaa kansalaiset itse valitsemallaan tavalla maatilan, tehtaan, laboratorion ja armeijan välillä. Tarkoituksena pelissä on kerätä mahdollisimman paljon tiedepisteitä laboratoriosta, samalla kuitenkin huolehtien kansalaisten ruuan, viihtyvyyden ja turvallisuuden riittävyydestä maatilan, tehtaan ja armeijan avulla.

## Luonnos käyttöliittymästä
Lopullinen peli tulee olemaan englanniksi

![kuva](https://github.com/PinguKoodi/otm-harjoitustyo/blob/master/dokumentointi/Grafiikat/Yhteiskunta_UI.png)

Pelissä tulee myös olemaan aloitusikkuna, jossa on mahdollisuus ladata pelitiedosto, tai aloittaa uusi peli

## Alustava toiminnallisuus
Kun käyttäjä käynnistää ohjelman, hänelle avautuu aloitusikkuna, josta hän voi valita joko uuden pelin, tai ladata jo olemassa olevan tallennetun pelin. Aloitusikkunassa on myös mahdollisuus lukea lyhyt ohje pelin pelaamiseen.

Aloitettuaan pelin, pelaaja voi laittaa työttömät kansalaiset valitsemaan tuotantopaikkaan, jossa he pysyvät kuolemaansa (70-80v) saakka. Sitten pelaaja painaa lopeta vuoro näppäintä, joka siirtää pelikelloa vuoden eteenpäin, ja kaikki laitokset tuottavat hyödykkeitä. Tarkoituksena on, että pelaaja joutuu valitsemaan sen välillä, että keskittyykö hän ruuantuotannon varmistamiseen, mikä vähentää tieteen tuotantoa, vai yrittääkö hän maksimoida tieteilijöiden määrän. Eri tuotantolaitoksilla on myös tarkoitus olla riippuvuuksia toisistaan siten, että:
1. Tiedepisteet parantavat tehtaan tuotantoa selvästi, ja hieman maatilan
2. Tehtaan tuotteet parantavat maatilan tuotantoa selvästi
3. Maatilalla tuotettava ruoka nostaa tyytyväisyystasoa, joka parantaa kaiken tuottamista

Lisäksi jos pelaajalta loppuu ruoka, hänen kansalaisensa alkavat kuolemaan ennenaikaisesti.

## Jatkokehitys mahdollisuuksia
- Erilaisia kertoimia, kuten että liian suuri armeija vähentää tyytyväisyyttä, kun taas pienempi aiheuttaa ulkoisia uhkia
- Tieteen ja tehtaan tuotteiden vaikutus yleistyytyväisyyteen
- Satunnaisvaihteluja, kuten että ruuantuotanto on jonain vuonna tavallista pienempää/suurempaa, esim. hurrikaanin takia
- Moninpeli tietokonetta vastaan (kilpailu tiedepisteistä)
- Jokaiselle ihmiselle yksilöllinen taitovaikutus, joka vaikuttaa tehtaan tuotantoon. Esim. jos työntekijä ollut 20 vuotta maatilalla, hän tuottaa enemmän kuin uusi työntekijä
- Erilaisia työtehtäviä, esim. opettaja, joka nopeuttaa lasten siirtymistä työvoimaan
