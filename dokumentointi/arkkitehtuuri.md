# Arkkitehtuurikuvaus

## Rakenne
Ohjelmassa on kolmitasoinen kerrosarkkitehtuuri, jossa pakkaus society.ui sisältää käyttöliittymän ja ohjelman käynnistävän Main-luokan, society.domain varsinaisen sovelluslogiikan ja society.data pakkaus sisältää tiedostoja käsittelevän luokan.

## Luokkakaavio
Luokkakaavio josta selviää tärkeimmät riippuvuussuhteet
<img src="https://github.com/PinguKoodi/otm-harjoitustyo/blob/master/dokumentointi/Grafiikat/SocietyClassDiagramV2.jpg"/>

## Sekvenvssikaavio
Sekvenssikaavio tilanteesta, jossa peli on jo käynnistetty, ja pelaaja painaa Start Game nappia. Sen jälkeen pelaaja painaa kerran "Assign to Farm" -nappia, ja lopuksi "End turn" -nappia. Sekvenssikaavioon on kuvattu vain varsinaisen sovelluslogiikan toiminta, ja jo olemassa olevat Human-oliot on niputettu yhdeksi olioksi, sillä niiden yksilöllinen tunnistaminen ei ole mielekästä.
<img src="https://github.com/PinguKoodi/otm-harjoitustyo/blob/master/dokumentointi/Grafiikat/Society.png"/>
