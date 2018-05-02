# Arkkitehtuurikuvaus

## Rakenne
Ohjelmassa on kolmitasoinen kerrosarkkitehtuuri, jossa pakkaus society.ui sisältää käyttöliittymän ja ohjelman käynnistävän Main-luokan, society.domain varsinaisen sovelluslogiikan ja society.data pakkaus sisältää tiedostoja käsittelevän luokan.

## Luokkakaavio
Luokkakaavio josta selviää tärkeimmät riippuvuussuhteet
<img src="https://github.com/PinguKoodi/otm-harjoitustyo/blob/master/dokumentointi/Grafiikat/SocietyClassDiagramV2.jpg"/>
Uudistettu pakkausrakennetta kuvaavaa kaavio:

<img src="https://github.com/PinguKoodi/otm-harjoitustyo/blob/master/dokumentointi/Grafiikat/SocietyV3.png"/>

## Sovelluslogiikka
Sovelluksen toiminnan keskuksena on luokka Logic, joka käsittelee käyttöliittymän, eli luokan Main sille lähettämiä pyyntöjä ja käskyjä. 
Logic-olio tuntee Distributor-olion, Multiplier-olion, ResourceManager-olion ja SaveOperator-olion. 
Distributor-luokka tuntee vain WorkerUnit-rajapinnan toteuttavia olioita. 
Multiplier-olio tuntee ResourceManager-olion ja Distributor-olion.
ResourceManager ei tunne mitään muita olioita.
Main-olio tuntee Logic-olion ja Distributor-olion.
Main-olio luo heti alussa yhden Distributor-olion ja yhden Logic-olion, jolloin Logic luo vielä yhden Multiplier-olion ja ResourceManager-olion. Nämä oliot pysyvät samoina koko sovelluksen suorituksen ajan.
Pelin alussa pelaaja valitsee haluaako aloittaa uuden pelin ja ladata vanhan. Logiikka-luokka määrittelee sen mukaan pelin lähtötilanteen. Varsinaisessa peli-ikkunassa eri painikkeet lähettävät logiikkaluokalle eri metodikutsuja. "Assign to Farm" -painike lähettää Logic-luokalle kutsun assignWorker(Factories.FARM), jolloin ohjelma tarkistaa Distributor-rajapinnan toteuttavalta luokalta, onko siellä työttömiä, ja mikäli on, niin asettaa jollekin näistä työpaikaksi maatilan. Muuten painike ei tee mitään. Vastaavat painikkeet on myös muillekin työpaikoille.
Pelin tärkein painike on kuitenkin "End Turn" -painike, joka aiheuttaa metodin endTurn kutsumisen. Tämä metodi edistää pelin sisäistä kelloa, eli Logic-luokan oliomuuttujaa "year". Samassa metodissa myös lasketaan tuotanto getProduction-metodilla, joka pyytää Multiplier-luokalta kertoimet jotka määrittelevät kuinka paljon yksittäinen työntekijä tuottaa. Lisäksi kaikki työntekijät vanhenevat vuoden, kun kutsutaan Distributor:lta metodi makeOneYearOlder. Tämän lisäksi lasketaan myös ihmisten onnellisuus, ja mahdollisesti synnytetään uusia ihmisiä Distributorin metodin makeBabies-metodilla. Toisaalta, jos pelaajan ruoka on loppunut, eikä hän saa tehtyä sitä tarpeeksi lisää, Distributor luokka tappaa työntekijöitä kunnes ruokaa on riittävästi. Jos käy niin, että kaikki työntekijät kuolevat, niin peli päättyy ja metodi endTurn palauttaa arvon true. Tämä aiheuttaa Main-luokassa häviönäytön ilmestymisen ruudulle.
Kun endTurn metodi on tehnyt kaikki laskunsa, pelaaja voi jatkaa pelaamista ja asettaa työntekijöitä työpaikkoihin tai painaa uudelleen endTurn -painiketta.

## Sekvenvssikaavio
Sekvenssikaavio tilanteesta, jossa peli on jo käynnistetty, ja pelaaja painaa Start Game nappia. Sen jälkeen pelaaja painaa kerran "Assign to Farm" -nappia, ja lopuksi "End turn" -nappia. Sekvenssikaavioon on kuvattu vain varsinaisen sovelluslogiikan toiminta, ja jo olemassa olevat Human-oliot on niputettu yhdeksi olioksi, sillä niiden yksilöllinen tunnistaminen ei ole mielekästä.
<img src="https://github.com/PinguKoodi/otm-harjoitustyo/blob/master/dokumentointi/Grafiikat/Society.png"/>
