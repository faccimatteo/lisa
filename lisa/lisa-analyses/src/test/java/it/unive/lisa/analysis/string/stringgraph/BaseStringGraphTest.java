package it.unive.lisa.analysis.string.stringgraph;

import static it.unive.lisa.analysis.string.stringgraph.StringGraph.CHARACTER.a;
import static it.unive.lisa.analysis.string.stringgraph.StringGraph.NodeType.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

public class BaseStringGraphTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();
	private final List<StringGraph.CHARACTER> availableCharacters;

	private final StringGraph firstSonStringGraph;
	private final StringGraph secondSonStringGraph;
	private final StringGraph thirdSonStringGraph;

	public BaseStringGraphTest() {
		this.firstSonStringGraph = new StringGraph(SIMPLE, new ArrayList<>(), a);
		this.secondSonStringGraph = new StringGraph(CONCAT, List.of(firstSonStringGraph), null);
		this.thirdSonStringGraph = new StringGraph(MAX, new ArrayList<>(), null);

		this.availableCharacters = new ArrayList<>(
				List.of(
						StringGraph.CHARACTER.a,
						StringGraph.CHARACTER.b,
						StringGraph.CHARACTER.c,
						StringGraph.CHARACTER.d,
						StringGraph.CHARACTER.e,
						StringGraph.CHARACTER.f,
						StringGraph.CHARACTER.g,
						StringGraph.CHARACTER.h,
						StringGraph.CHARACTER.i,
						StringGraph.CHARACTER.j,
						StringGraph.CHARACTER.k,
						StringGraph.CHARACTER.l,
						StringGraph.CHARACTER.m,
						StringGraph.CHARACTER.n,
						StringGraph.CHARACTER.o,
						StringGraph.CHARACTER.p,
						StringGraph.CHARACTER.q,
						StringGraph.CHARACTER.r,
						StringGraph.CHARACTER.s,
						StringGraph.CHARACTER.t,
						StringGraph.CHARACTER.u,
						StringGraph.CHARACTER.v,
						StringGraph.CHARACTER.w,
						StringGraph.CHARACTER.x,
						StringGraph.CHARACTER.y,
						StringGraph.CHARACTER.z));
	}

	protected List<StringGraph.CHARACTER> getAvailableCharacters() {
		return availableCharacters;
	}

	protected ExpectedException getException() {
		return exception;
	}

	protected StringGraph getFirstSonStringGraph() {
		return firstSonStringGraph;
	}

	protected StringGraph getSecondSonStringGraph() {
		return secondSonStringGraph;
	}

	protected StringGraph getThirdSonStringGraph() {
		return thirdSonStringGraph;
	}

	protected static void assertStringGraph(
			StringGraph s,
			List<StringGraph> expectedSons,
			int expectedSonsNumber,
			List<StringGraph> expectedFathers,
			int expectedFathersNumber,
			StringGraph.NodeType expectedNodeType,
			boolean expectedIsNormalized,
			Integer expectedBound
	) {
		assertEquals(expectedNodeType, s.getLabel());
		assertEquals(expectedIsNormalized, s.isNormalized());

		if (expectedSons.isEmpty()) assertEquals(expectedSonsNumber, s.getSons().size());
		else assertEquals(expectedSons, s.getSons());

		if (expectedFathers.isEmpty()) assertEquals(expectedFathersNumber, s.getFathers().size());
		else assertEquals(expectedFathers, s.getFathers());

		assertEquals(expectedBound, s.getBound());
	}
}
