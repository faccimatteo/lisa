//package it.unive.lisa.analysis.string.stringgraph;
//
//import org.junit.Test;
//
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;
//
//public class SonsAndFathersTest extends BaseStringGraphTest {
//
//    @Test
//    public void whenAddSonIsExecuted_thenChecksSonAndFathersAreAdded() {
//        StringGraph s = new StringGraph("cia");
//        StringGraph o = new StringGraph("o");
//        s.addSon(o);
//        assertTrue(s.getSons().contains(o));
//        assertTrue(o.getFathers().contains(s));
//    }
//
//    @Test
//    public void whenRemoveSonIsExecuted_thenChecksSonAndFathersAreRemoved() {
//        StringGraph s = new StringGraph("cia");
//        StringGraph o = new StringGraph("o");
//        s.addSon(o);
//        assertTrue(s.getSons().contains(o));
//        assertTrue(o.getFathers().contains(s));
//
//        s.removeSon(o);
//        assertFalse(s.getSons().contains(o));
//        assertFalse(o.getFathers().contains(s));
//    }
//
//    @Test
//    public void whenAddAllSonsIsExecuted_thenChecksSonsAndFatherAreAdded() {
//        StringGraph s = new StringGraph("ciao");
//
//        s.addSon(this.getFirstSonStringGraph());
//        s.addSon(this.getSecondSonStringGraph());
//        s.addSon(this.getThirdSonStringGraph());
//        assertTrue(s.getSons().contains(this.getFirstSonStringGraph()));
//        assertTrue(s.getSons().contains(this.getSecondSonStringGraph()));
//        assertTrue(s.getSons().contains(this.getThirdSonStringGraph()));
//        assertTrue(this.getFirstSonStringGraph().getFathers().contains(s));
//        assertTrue(this.getSecondSonStringGraph().getFathers().contains(s));
//        assertTrue(this.getThirdSonStringGraph().getFathers().contains(s));
//    }
//
//    @Test
//    public void whenRemoveAllSons_thenChecksSonsAndFatherAreRemoved() {
//        StringGraph s = new StringGraph("test");
//        s.addSon(this.getFirstSonStringGraph());
//        s.addSon(this.getSecondSonStringGraph());
//        s.addSon(this.getThirdSonStringGraph());
//        assertTrue(s.getSons().contains(this.getFirstSonStringGraph()));
//        assertTrue(s.getSons().contains(this.getSecondSonStringGraph()));
//        assertTrue(s.getSons().contains(this.getThirdSonStringGraph()));
//        assertTrue(this.getFirstSonStringGraph().getFathers().contains(s));
//        assertTrue(this.getSecondSonStringGraph().getFathers().contains(s));
//        assertTrue(this.getThirdSonStringGraph().getFathers().contains(s));
//
//        s.removeAllSons();
//
//        assertFalse(s.getSons().contains(this.getFirstSonStringGraph()));
//        assertFalse(s.getSons().contains(this.getSecondSonStringGraph()));
//        assertFalse(s.getSons().contains(this.getThirdSonStringGraph()));
//        assertFalse(this.getFirstSonStringGraph().getFathers().contains(s));
//        assertFalse(this.getSecondSonStringGraph().getFathers().contains(s));
//        assertFalse(this.getThirdSonStringGraph().getFathers().contains(s));
//
//    }
//
//}
