package it.unive.lisa.analysis.string.stringgraph;

import it.unive.lisa.analysis.string.stringgraph.exception.WrongBuildStringGraphException;
import org.junit.Test;

import static it.unive.lisa.analysis.string.stringgraph.StringGraph.CHARACTER.*;
import static it.unive.lisa.analysis.string.stringgraph.StringGraph.NodeType.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ConstructorTest extends BaseTest{

    public ConstructorTest () {
        super();
    }

    /**
     * SIMPLE NODE
     */

    @Test
    public void whenSimpleNodeAreCreated_thenChecksNoSonsArePresentAndCharacterIsPresent() {
        StringGraph stringGraph;
        for(StringGraph.CHARACTER c : this.getAvailableCharacters()) {
            stringGraph = new StringGraph(SIMPLE, new ArrayList<>(), c);
            assertTrue(stringGraph.getSons().isEmpty());
            assertEquals(stringGraph.getLabel(), SIMPLE);
            assertTrue(stringGraph.isNormalized());
            assertTrue(stringGraph.getFathers().isEmpty());
            assertNull(stringGraph.getBound());
        }
    }

    @Test(expected = WrongBuildStringGraphException.class)
    public void whenSimpleNodeWithSonsIsCreated_thenThrowWrongBuildStringGraphException() {
        new StringGraph(SIMPLE, List.of(this.getFirstSonStringGraph()), a);
    }

    @Test(expected = WrongBuildStringGraphException.class)
    public void whenSimpleNodeWithNoCharacterIsCreated_thenThrowWrongBuildStringGraphException() {
        new StringGraph(SIMPLE, new ArrayList<>(), null);
    }

    /**
     * CONCAT NODE
     */
    @Test
    public void whenConcatNodeWithMixedSonsIsCreated_thenChecksForCorrectness() {

        StringGraph stringGraph = new StringGraph(CONCAT, List.of(this.getFirstSonStringGraph(), this.getSecondSonStringGraph(), this.getThirdSonStringGraph()), null);
        assertEquals(stringGraph.getLabel(), CONCAT);
        assertFalse(stringGraph.isNormalized());
        assertEquals(stringGraph.getSons(), List.of(this.getFirstSonStringGraph(), this.getSecondSonStringGraph(), this.getThirdSonStringGraph()));
        assertTrue(stringGraph.getFathers().isEmpty());
        assertNull(stringGraph.getBound());

    }

    @Test
    public void whenConcatNodeWithOneSonIsCreated_thenChecksForCorrectness() {

        StringGraph stringGraph = new StringGraph(CONCAT, List.of(this.getThirdSonStringGraph()), null);
        assertEquals(stringGraph.getLabel(), CONCAT);
        assertFalse(stringGraph.isNormalized());
        assertEquals(stringGraph.getSons(), List.of(this.getThirdSonStringGraph()));
        assertTrue(stringGraph.getFathers().isEmpty());
        assertNull(stringGraph.getBound());

    }

    @Test
    public void whenConcatNodeWithMultipleSonsIsCreated_thenChecksForCorrectness() {

        List<StringGraph> stringGraphSons = new ArrayList<>();

        for(StringGraph.CHARACTER c : this.getAvailableCharacters()) {
            stringGraphSons.add(new StringGraph(SIMPLE, new ArrayList<>(), c));
        }

        StringGraph stringGraph = new StringGraph(CONCAT, stringGraphSons, null);
        assertEquals(stringGraph.getLabel(), CONCAT);
        assertTrue(stringGraph.isNormalized());
        assertEquals(stringGraph.getSons(), stringGraphSons);
        assertTrue(stringGraph.getFathers().isEmpty());
        assertNull(stringGraph.getBound());

    }


    @Test(expected = WrongBuildStringGraphException.class)
    public void whenConcatNodeWithoutSonsIsCreated_thenThrowWrongBuildStringGraphException() {
        new StringGraph(CONCAT, List.of(), null);
    }

    @Test(expected = WrongBuildStringGraphException.class)
    public void whenConcatNodeWithCharacterIsCreated_thenThrowWrongBuildStringGraphException() {
        new StringGraph(CONCAT, List.of(this.getFirstSonStringGraph()), a);
    }

    /**
     * MAX NODE
     */
    @Test
    public void whenMaxNodeIsCreated_thenChecksNoSonsNoSonsArePresent() {

        StringGraph stringGraph = new StringGraph(MAX, List.of(), null);
        assertEquals(stringGraph.getLabel(), MAX);
        assertTrue(stringGraph.getSons().isEmpty());
        assertTrue(stringGraph.getFathers().isEmpty());
        assertNull(stringGraph.getBound());

    }

    @Test(expected = WrongBuildStringGraphException.class)
    public void whenMaxNodeWithSonsIsCreated_thenThrowWrongBuildStringGraphException() {
        new StringGraph(MAX, List.of(this.getFirstSonStringGraph(), this.getSecondSonStringGraph(), this.getThirdSonStringGraph()), a);
    }

    /**
     * CONSTRUCTOR GIVEN A STRING
     */
    @Test
    public void whenStringGraphFromASimpleStringIsCreated_thenChecksStringGraphIsConcatNodeAndEverySonIsSimpleNode() {

        String word = "supercalifragilistichespiralidoso";
        StringGraph stringGraph = new StringGraph(word);
        List<StringGraph> sons = stringGraph.getSons();
        assertEquals(sons.size(), 33);
        assertTrue(stringGraph.isNormalized());
        assertEquals(stringGraph.getLabel(), CONCAT);
        for (int i = 0; i < sons.size(); ++i) {
            assertEquals(sons.get(i).getLabel(), SIMPLE);
            assertTrue(sons.get(i).getSons().isEmpty());
            assertEquals(sons.get(i).getCharacter(), StringGraph.map(word.charAt(i)));
            assertTrue(stringGraph.getFathers().isEmpty());
            assertNull(stringGraph.getBound());
        }

    }

    @Test
    public void whenStringGraphFromEmptyStringIsCreated_thenChecksForEmptyLabel() {

        StringGraph stringGraph = new StringGraph("");
        assertEquals(stringGraph.getLabel(), EMPTY);
        assertTrue(stringGraph.getSons().isEmpty());
        assertTrue(stringGraph.isNormalized());
        assertTrue(stringGraph.getFathers().isEmpty());
        assertNull(stringGraph.getBound());

    }

    @Test
    public void whenStringGraphFromSingleCharacterStringIsCreated_thenChecksForEmptyLabel() {

        List<String> characters = this.getAvailableCharacters()
                .stream()
                .map(Enum::toString)
                .collect(Collectors.toList());
        StringGraph stringGraph;

        for (String s : characters) {
            stringGraph = new StringGraph(s);
            assertTrue(stringGraph.isNormalized());
            assertEquals(stringGraph.getLabel(), SIMPLE);
            assertTrue(stringGraph.getSons().isEmpty());
            assertEquals(stringGraph.getCharacter(), StringGraph.map(s.charAt(0)));
            assertTrue(stringGraph.getFathers().isEmpty());
            assertNull(stringGraph.getBound());
        }

    }

    @Test(expected = WrongBuildStringGraphException.class)
    public void whenStringGraphFromNullStringIsCreated_thenThrowWrongBuildStringGraphException() {
        new StringGraph((String) null);
    }
}
