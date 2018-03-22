/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.unicafe;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author pyylauri
 */
public class KassapaateTest {
    
    Kassapaate paate;
    
    @Before
    public void setUp() {
        this.paate = new Kassapaate();
    }
    
    @Test
    public void luotuKassapaateOlemassa() {
        assertTrue(this.paate != null);
    }
    @Test
    public void kassapaatteellaOikeaMaaraRahaaAlussa() {
        assertEquals(100000, this.paate.kassassaRahaa());
    }
    @Test
    public void kassassaOikeaMaaraLounaitaMyytyAlussa() {
        assertEquals(0, this.paate.maukkaitaLounaitaMyyty());
        assertEquals(0, this.paate.edullisiaLounaitaMyyty());
    }
    @Test
    public void edullisenLounaanMyyntiOnnistuu () {
        int vaihtoraha = this.paate.syoEdullisesti(300);
        assertEquals(100240, this.paate.kassassaRahaa());
        assertEquals(60, vaihtoraha);
    }
    @Test
    public void maukkaanLounaanMyyntiOnnistuu () {
        int vaihtoraha = this.paate.syoMaukkaasti(500);
        assertEquals(100400, this.paate.kassassaRahaa());
        assertEquals(100, vaihtoraha);
    }
    @Test
    public void myytyjenEdullistenLounaidenMaaraKasvaaOikein () {
        this.paate.syoEdullisesti(500);
        this.paate.syoEdullisesti(500);
        assertEquals(2, this.paate.edullisiaLounaitaMyyty());
    }
    @Test
    public void myytyjenMaukkaidenLounaidenMaaraKasvaaOikein () {
        this.paate.syoMaukkaasti(500);
        this.paate.syoMaukkaasti(500);
        assertEquals(2, this.paate.maukkaitaLounaitaMyyty());
    }
    @Test
    public void myytyjenLounaidenMaaraEiKasvaJosRahaEiRiita () {
        this.paate.syoMaukkaasti(100);
        this.paate.syoEdullisesti(100);
        assertEquals(0, this.paate.maukkaitaLounaitaMyyty());
        assertEquals(0, this.paate.edullisiaLounaitaMyyty());
    }
    @Test
    public void vaihtorahanPalautusOnnistuuJosRahatEiRiita() {
        int vaihtoraha = this.paate.syoMaukkaasti(100);
        assertEquals(100, vaihtoraha);
        vaihtoraha = this.paate.syoEdullisesti(100);
        assertEquals(100, vaihtoraha);
    }
    @Test
    public void kassanRahamaaraSailyyJosMaksuEiRiita () {
        this.paate.syoMaukkaasti(100);
        assertEquals(100000, this.paate.kassassaRahaa());
        this.paate.syoEdullisesti(100);
        assertEquals(100000, this.paate.kassassaRahaa());
    }
    @Test
    public void kortillaVoiOstaaEdullisiaLounaita() {
        Maksukortti kortti = new Maksukortti(500);
        assertTrue(this.paate.syoEdullisesti(kortti));

        
    }
    @Test
    public void kortillaOstaessaEdullinenLounasSaldotMuuttuvatOikein () {
        Maksukortti kortti = new Maksukortti(500);
        this.paate.syoEdullisesti(kortti);
        assertEquals(100000, this.paate.kassassaRahaa());
        assertEquals(kortti.saldo(), 260);
    }
    @Test
    public void kortillaOstaessaEdullinenLounasMyydytLounaatKasvavat () {
        Maksukortti kortti = new Maksukortti(500);
        this.paate.syoEdullisesti(kortti);
        assertEquals(1, this.paate.edullisiaLounaitaMyyty());
    }
    @Test
    public void josKortillaEiOleTarpeeksiRahaaEdulliseenLounaaseenPalauttaaFalse () {
        Maksukortti kortti = new Maksukortti(200);
        assertTrue(!this.paate.syoEdullisesti(kortti));
    }
    @Test
    public void josKortillaEiOleTarpeeksiRahaaSaldotEiMuutu () {
        Maksukortti kortti = new Maksukortti(200);
        this.paate.syoEdullisesti(kortti);
        assertEquals(200, kortti.saldo());
        assertEquals(100000, this.paate.kassassaRahaa());
    }
    @Test
    public void josKortillaEiOleTarpeeksiRahaaMyydytLounaatEiKasva () {
        Maksukortti kortti = new Maksukortti(200);
        this.paate.syoEdullisesti(kortti);
        assertEquals(0, this.paate.edullisiaLounaitaMyyty());
    }
    @Test
    public void kortillaVoiOstaaMaukkaitaLounaita() {
        Maksukortti kortti = new Maksukortti(500);
        assertTrue(this.paate.syoMaukkaasti(kortti));

        
    }
    @Test
    public void kortillaOstaessaMaukasLounasSaldotMuuttuvatOikein () {
        Maksukortti kortti = new Maksukortti(500);
        this.paate.syoMaukkaasti(kortti);
        assertEquals(100000, this.paate.kassassaRahaa());
        assertEquals(kortti.saldo(), 100);
    }
    @Test
    public void kortillaOstaessaMaukasLounasMyydytLounaatKasvavat () {
        Maksukortti kortti = new Maksukortti(500);
        this.paate.syoMaukkaasti(kortti);
        assertEquals(1, this.paate.maukkaitaLounaitaMyyty());
    }
    @Test
    public void josKortillaEiOleTarpeeksiRahaaMaukkaaseenLounaaseenPalauttaaFalse () {
        Maksukortti kortti = new Maksukortti(200);
        assertTrue(!this.paate.syoMaukkaasti(kortti));
    }
    @Test
    public void josKortillaEiOleTarpeeksiRahaaMaukkaaseenSaldotEiMuutu () {
        Maksukortti kortti = new Maksukortti(200);
        this.paate.syoMaukkaasti(kortti);
        assertEquals(200, kortti.saldo());
        assertEquals(100000, this.paate.kassassaRahaa());
    }
    @Test
    public void josKortillaEiOleTarpeeksiRahaaMaukkaaeenMyydytLounaatEiKasva () {
        Maksukortti kortti = new Maksukortti(200);
        this.paate.syoMaukkaasti(kortti);
        assertEquals(0, this.paate.maukkaitaLounaitaMyyty());
    }
    @Test
    public void kortillaRahanLataaminenOnnistuuJosSummaPositiivinen () {
        Maksukortti kortti = new Maksukortti(0);
        this.paate.lataaRahaaKortille(kortti, 1000);
        assertEquals(1000, kortti.saldo());
        assertEquals(101000, this.paate.kassassaRahaa());
    }
    @Test
    public void kortilleEiLadataRahaaJosSummaNegatiivinen () {
        Maksukortti kortti = new Maksukortti(0);
        this.paate.lataaRahaaKortille(kortti, -1000);
        assertEquals(0, kortti.saldo());
        assertEquals(100000, this.paate.kassassaRahaa());
    }
            
    
    
    
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
