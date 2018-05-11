# Sovelluksen testaaminen

## Yksikkö- ja integraatiotestaus
#### Sovelluslogiikka
Ohjelman kaikille tärkeimmille luokille on luotu omat testiluokkansa, jotka testaavat sekä yksilö, että integraatio tasolla luokkien toimintaa.  ResourceManagerTest, HumanDistributorTest ja Human -luokat ovat yksilötestejä, jotka testaavat vastaavien luokkien perustoiminnallisuuden toimimista. MultiplierTest testaa jo hieman integraatiotasolla ohjelman toimintaa, testaten Multiplier-luokan toimintaa HumanDistributor ja ResourceManager luokkien kanssa. Kaikkein tärkein testausluokka on LogicTest, joka testaa sekä Logic-luokan toimintaa yksilönä, että varmistaa sen toiminnan myös muiden luokkien kanssa. Ja se sisältää varsinaisesti koko ohjelman toimintaa testaavat testit. Se testaa integraatiotasolla että kaikki luokat toteuttavat omat tehtävänsä ja Logic-luokka pystyy yhdistämään näiden toiminnan oikein.

#### DAO-luokka
FileOperatorTest-luokka testaa Logic-luokan ja FileOperator-luokan yhteistoiminnallisuutta, ja varmistaa sen, että tiedoston tallennus ja luku onnistuu.

## Testauskattavuudesta
<img src="https://github.com/PinguKoodi/otm-harjoitustyo/blob/master/dokumentointi/Grafiikat/Testauskattavuus.PNG"/>
Testein rivikattavuus on kokonaisuudessaan 97%, ja haaraumakattavuus on 96%. Suurin testaamatta jäänyt metodi on FileOperator-luokan createNewConfigFile(), mutta sen toiminta on testattu käyttäjätasolta. Testeissä ollaan pyritty huomioimaan myös mahdollisia outoja tai erikoisia tilanteita, jotta ohjelman käytös olisi ennustettavaa.

## Järjestelmätestaus
Ohjelmaa on testattu manuaalisesti erilaisissa tilanteissa, että se toteuttaa vaatimusmäärittelyn ja ei kaadu missään vaiheessa.

## Asennus ja konfiguroitu
Sovelluksen testaus on suoritettu Windows-ympäristössä lataamalla se ja käyttäen sitä käyttöohjeen mukaan. Testausta on suoritettu sekä ilman konfiguraatio-tiedostoa että sen kanssa. Lisäksi testausta on myös kokeiltu siten, että config-tiedostoon on kirjoitettu virheellisiä tietoja, tai se on jätetty tyhjäksi.

## Ohjelman toiminnallisuus
Ohjelma toteuttaa kaikki vaaditut toiminnallisuudet. Ohjelman käyttäjä ei pysty syöttämään virheellisiä arvoja mihinkään muuhun kuin tallenus- ja konfiguraatiotiedstoon, ja ohjelman toiminnallisuus on järkevää mikäli näin tapahtuu.

## Sovelluksen ongelmat
Testatessa ohjelma saattaa luoda "testSaveData.txt"-tiedoston kansioon, samoiten kun "config.txt" -tiedoston.
