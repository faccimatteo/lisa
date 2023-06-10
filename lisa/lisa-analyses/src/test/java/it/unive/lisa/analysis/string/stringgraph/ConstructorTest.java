package it.unive.lisa.analysis.string.stringgraph;

import static it.unive.lisa.analysis.string.stringgraph.StringGraph.CHARACTER.*;
import static it.unive.lisa.analysis.string.stringgraph.StringGraph.NodeType.*;
import static org.junit.Assert.*;

import it.unive.lisa.analysis.string.stringgraph.exception.WrongBuildStringGraphException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Test;

public class ConstructorTest extends BaseStringGraphTest {

	public ConstructorTest() {
		super();
	}

	/**
	 * SIMPLE NODE
	 */

	@Test
	public void whenSimpleNodeAreCreated_thenChecksNoSonsArePresentAndCharacterIsPresent() {
		StringGraph stringGraph;
		for (StringGraph.CHARACTER c : this.getAvailableCharacters()) {
			stringGraph = new StringGraph(SIMPLE, new ArrayList<>(), c);
			assertStringGraph(stringGraph, List.of(), 0, List.of(), 0, SIMPLE, true, null);
		}
	}

	@Test(expected = WrongBuildStringGraphException.class)
	public void whenSimpleNodeWithSonsIsCreated_thenThrowWrongBuildStringGraphException() {
		new StringGraph(SIMPLE, List.of(this.getSimpleSon()), a);
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
		List<StringGraph> sonsList = List.of(this.getSimpleSon(), this.getConcatSon(), this.getMaxSon());
		StringGraph stringGraph = new StringGraph(CONCAT,
				sonsList,
				null);
		assertStringGraph(stringGraph, sonsList, 3, List.of(), 0, CONCAT, false, null);

		assertTrue(this.getSimpleSon().getFathers().contains(stringGraph));
		assertTrue(this.getConcatSon().getFathers().contains(stringGraph));
		assertTrue(this.getMaxSon().getFathers().contains(stringGraph));

	}

	@Test
	public void whenConcatNodeWithOneSonIsCreated_thenChecksForCorrectness() {

		List<StringGraph> sonsList = List.of(this.getMaxSon());
		StringGraph stringGraph = new StringGraph(CONCAT, sonsList, null);
		assertStringGraph(stringGraph, sonsList, 1, List.of(), 0, CONCAT, false, null);
		assertTrue(this.getMaxSon().getFathers().contains(stringGraph));

	}

	@Test
	public void whenConcatNodeWithMultipleSonsIsCreated_thenChecksForCorrectness() {

		List<StringGraph> stringGraphSons = new ArrayList<>();
		List<StringGraph> sons = new ArrayList<>();
		StringGraph son;
		for (StringGraph.CHARACTER c : this.getAvailableCharacters()) {
			son = new StringGraph(SIMPLE, new ArrayList<>(), c);
			sons.add(son);
			stringGraphSons.add(son);
		}

		StringGraph stringGraph = new StringGraph(CONCAT, stringGraphSons, null);
		assertStringGraph(stringGraph, sons, this.getAvailableCharacters().size(), List.of(), 0, CONCAT, true, null);

		for(StringGraph s : sons) {
			assertTrue(s.getFathers().contains(stringGraph));
		}

	}

	@Test(expected = WrongBuildStringGraphException.class)
	public void whenConcatNodeWithoutSonsIsCreated_thenThrowWrongBuildStringGraphException() {
		new StringGraph(CONCAT, List.of(), null);
	}

	@Test(expected = WrongBuildStringGraphException.class)
	public void whenConcatNodeWithCharacterIsCreated_thenThrowWrongBuildStringGraphException() {
		new StringGraph(CONCAT, List.of(this.getSimpleSon()), a);
	}

	/**
	 * MAX NODE
	 */
	@Test
	public void whenMaxNodeIsCreated_thenChecksNoSonsNoSonsArePresent() {

		StringGraph stringGraph = new StringGraph(MAX, List.of(), null);
		assertStringGraph(stringGraph, List.of(), 0, List.of(), 0, MAX, true, null);

	}

	@Test(expected = WrongBuildStringGraphException.class)
	public void whenMaxNodeWithSonsIsCreated_thenThrowWrongBuildStringGraphException() {
		new StringGraph(MAX,
				List.of(this.getSimpleSon(), this.getConcatSon(), this.getMaxSon()),
				a);
	}

	/**
	 * CONSTRUCTOR GIVEN A STRING
	 */
	@Test
	public void whenStringGraphFromASimpleStringIsCreated_thenChecksStringGraphIsConcatNodeAndEverySonIsSimpleNode() {

		String word = "supercalifragilistichespiralidoso";
		StringGraph stringGraph = new StringGraph(word);
		List<StringGraph> sons = stringGraph.getSons();
		assertStringGraph(stringGraph, List.of(), 33, List.of(), 0, CONCAT, true, null);

		for (int i = 0; i < sons.size(); ++i) {
			assertEquals(sons.get(i).getLabel(), SIMPLE);
			assertTrue(sons.get(i).getSons().isEmpty());
			assertEquals(sons.get(i).getCharacter(), StringGraph.map(word.charAt(i)));
			assertTrue(sons.get(i).getFathers().contains(stringGraph));
		}

	}

	@Test
	public void whenStringGraphWithUnknownCharIsCreated_thenChecksMAXStringGraphIsCreated() {

		String word = "supercalifragilis<tichespiralidoso";
		StringGraph stringGraph = new StringGraph(word);
		assertStringGraph(stringGraph, List.of(), 0, List.of(), 0, MAX, true, null);

	}

	@Test
	public void whenStringGraphFromEmptyStringIsCreated_thenChecksForEmptyLabel() {

		StringGraph stringGraph = new StringGraph("");
		assertStringGraph(stringGraph, List.of(), 0, List.of(), 0, EMPTY, true, null);

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
			assertStringGraph(stringGraph, List.of(), 0, List.of(), 0, SIMPLE, true, null);
		}

	}

	@Test(expected = WrongBuildStringGraphException.class)
	public void whenStringGraphFromNullStringIsCreated_thenThrowWrongBuildStringGraphException() {
		new StringGraph((String) null);
	}
}
