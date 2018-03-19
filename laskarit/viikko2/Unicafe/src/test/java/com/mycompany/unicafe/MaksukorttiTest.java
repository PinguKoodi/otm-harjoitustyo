package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    @Test
    public void kortinSaldoTulostuuOikein() {
        assertEquals("saldo: 0.10", kortti.toString());
    }
    @Test
    public void kortinSaldoAlussaOikein() {
        assertEquals(10, kortti.saldo());
    }
    @Test
    public void rahanLataaminenKasvattaaSaldoaOikein() {
        kortti.lataaRahaa(2000);
        assertEquals("saldo: 20.10", kortti.toString());
    }
    @Test
    public void rahanOttaminenToimii () {
        kortti.lataaRahaa(2000);
        kortti.otaRahaa(1010);
        assertEquals("saldo: 10.0", kortti.toString());
    }
    @Test
    public void saldoEiVaheneJosEiOleRiittavastaRahaa () {
        kortti.otaRahaa(100);
        assertEquals("saldo: 0.10", kortti.toString());
    }
    @Test
    public void metodiPalauttaaFalseArvonJosRahatEiRiita () {
        assertEquals(false, kortti.otaRahaa(100));
    }
    @Test
    public void metodiPalauttaaTruenJosRahatRiittaa () {
        kortti.lataaRahaa(2000);
        assertEquals(true, kortti.otaRahaa(100));
    }
}
