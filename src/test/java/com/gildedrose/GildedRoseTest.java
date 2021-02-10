package com.gildedrose;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    @Test
    void ordinaryQualityCheckBeforeExpire() {
        Item[] items = new Item[] { new Item("ordinary", 10, 20) };
        GildedRose app = new GildedRose(items);
        app.nDaysPassed(app, 7);
        assertEquals(13, items[0].quality);
    }

    @Test
    void vestQualityCheckAfterExpire() {
        Item[] items = new Item[] { new Item("ordinary", 10, 20) };
        GildedRose app = new GildedRose(items);
        app.nDaysPassed(app, 12);
        assertEquals(6, items[0].quality);
    }

    @ParameterizedTest
    @ValueSource(ints = {191,288, 2325, 23546, 21315, 448498, 454454, 797, 9818, 561874156})
    void ordinaryQualityCheckAfterExpire(int n) {
        Item[] items = new Item[] { new Item("ordinary", 10, 20) };
        GildedRose app = new GildedRose(items);
        app.nDaysPassed(app, n);
        assertEquals(0, items[0].quality);
    }

    @Test
    void abQualityCheckAfter5Days() {
        Item[] items = new Item[] { new Item("Aged Brie",5, 0) };
        GildedRose app = new GildedRose(items);
        app.nDaysPassed(app, 3);
        assertEquals(3, items[0].quality);
    }

    @ParameterizedTest
    @ValueSource(ints = {191,288, 2325, 23546, 21315, 448498, 454454, 797, 9818, 561874156})
    void abQualityCheckAfter50Days(int n) {
        Item[] items = new Item[]{new Item("Aged Brie", 5, 0)};
        GildedRose app = new GildedRose(items);
        app.nDaysPassed(app, n);
        assertEquals(50, items[0].quality);
    }

    @Test
    void abQualityCheckAfter0Days() {
        Item[] items = new Item[] { new Item("Aged Brie", 0, 0) };
        GildedRose app = new GildedRose(items);
        app.nDaysPassed(app, 3);
        assertEquals(6, items[0].quality);
    }

    @Test
    void backstageQualityCheckBefore5Days() {
        Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert",13, 29) };
        GildedRose app = new GildedRose(items);
        app.nDaysPassed(app, 12);
        assertEquals(50, items[0].quality);
    }

    @Test
    void backstageQualityCheckBefore10Days() {
        Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 13, 29) };
        GildedRose app = new GildedRose(items);
        app.nDaysPassed(app, 14);
        assertEquals(0, items[0].quality);
    }

    @Test
    void legendaryQualityCheck(){
        Item[] items = new Item[] { new Item("Sulfuras, Hand of Ragnaros", 5, 80) };
        GildedRose app = new GildedRose(items);
        app.nDaysPassed(app, 8);
        assertEquals(80, app.items[0].quality);
    }

    @ParameterizedTest
    @ValueSource(ints = {1,8,15,23,89,104,26,17,191,288})
    void legendaryQualityCheck(int n){
        Item[] items = new Item[] {new Item("Sulfuras, Hand of Ragnaros", 5, 80)};
        GildedRose app = new GildedRose(items);
        app.nDaysPassed(app, n);
        assertEquals(80, items[0].quality);
    }

}
// Ordinary items: lose 1 quality until they expire, than they lose 2 quality. Min 0.
//Aged Brie: gain 1 quality until they expire, then they gain 2 quality. Max 50.
//Sulfuras: quality is always 80.
//Backstage pass: if 11 > sellIn > 5 increases by 2 a day.
//                if 6 > sellIn > -1 increases by 3 a day.
//                if sellIn < 0 quality is 0.
