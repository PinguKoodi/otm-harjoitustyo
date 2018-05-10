# Käyttöohje
Lataa Society.jar tiedosto
## Käynnistys
Käynnistä sovellus joko tuplaklikkauksella tai komentoriviltä koodilla 
```java -jar Society.jar```

Mikäli haluat jo ennen sovelluksen käynnistystä tehdä konfiguraatioita, tee "config.txt"-tiedosto kansioon, jossa .jar-tiedosto on. Siitä luetaan seuraavat tiedot: data, startingHumans, difficulty. Data määrittelee mihin tiedostoon tallennettu peli tallennetaan, jos tiedostoa ei ole, se luodaan. StartingHumans muuttuja määrittelee uuden pelin aloittavat ihmiset, oletusmäärä on 17, mutta pelin kulkua voi muuttaa vaihtamalla sitä. Lisäksi on vielä difficulty. Sen oletusarvo on 1.0, jolloin tuotanto on normaalia. Vaikeustaso toimii siten, että sillä kerrotaan kaikki tuotanto, eli mitä alhaisempi se on, sen vaikeampi peli. Esimerkiksi 0.7 on lähes mahdoton, kun taas 1.3 on jo aika helppo.


Config.txt -tiedostoa ei kuitenkaan ole välttämätöntä luoda, sillä peli luo sen itse mikäli sitä ei ole olemassa.

## Pelaaminen
Pelin aloitusikkunasta voit valita haluatko aloittaa uuden pelin, ladata jo olemassa olevan tallennustiedoston, lukea peliohjeen vai lopettaa pelin. Napit toimivat hiiren painamisella.
Jos valitsit peliohjeen, saat luettavaksesi lyhyen kuvauksen pelistä ja ohjeita pelin pelaamiseen.

Varsinaiseen peliin pääsee valitsemalla joko "Start new game" tai "Load game". Nyt olet peli-ikkunassa, jossa sinulla on näkyvissäsi yhteiskuntasi tiedot ja useita eri painikkeita. Oikeassa yläkulmassa on "Game guide" -painike joka avaa saman peliohjeen kuin päävalikossa. Siellä on kanssa "Human Overview"-nappula, josta avautuu uuteen ikkunaan lista yhteiskuntasi ihmisistä. Listan voi sulkea rastista. Varsinainen pelaaminen tapahtuu painamalla "Assign to" -nappuloita, joita on neljä kappaletta: Farm, Factory, Laboratory ja Army. Jos sinulla on työttömiä kansalaisia, niin peli ohjaa yhden heistä nappulan mukaiseen työpaikkaan. Kun haluat siirtyä vuodessa eteenpäin, paina "End turn"-painiketta, jolloin ohjelma laskee vuoden aikana tapahtuvat tuotannot ja ruuan sekä työkalujen kulutuksen. Lisäksi vuosimittari nousee yhdellä ja mahdollisesti uusia lapsia syntyy.

Näet pelin alalaidassa työttömien lukumäärän, näitä ei kannata olla vuoron lopussa yhtään sillä ne vain syövät ruokaa, eivätkä tuota mitään. Lapset sen sijaan ovat ihmisiä, jotka eivät ole vielä tarpeeksi vanhoja työelämään, mutta he kuitenkin myös kuluttavat ruokaa.

Peli-ikkunassa on paljon erilaisia lukuja. Oikealla olevat Food, Tools, Science ja Guns numerot kertovat kuinka paljon sinulla on kutakin resurssia. Näistä vielä oikealle on listattuna kunkin resurssin tuotanto ensi vuonna. Ruuan tuotanto näkyy punaisella mikäli tuotat vähemmän kuin mitä kansalaisesi syö. Lisäksi sotilaiden määrä on myös punaisella, jos sinulla on niitä liian vähän suhteessa muihin kansalaisiin, jolloin resurssien tuotanto kärsii. Sinulta saattaa myös kadota resursseja mikäli sinulla on liian vähän sotilaita.
Peli-ikkunassa voit lisäksi tallentaa pelisi "Save game"-painikkeella, joka ilmoittaa myös onnistuiko tallennus. Voit myös poistua peli-ikkunasta takaisin aloitusvalikkoon painamalla "Main menu"-painiketta.
