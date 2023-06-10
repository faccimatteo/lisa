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

	private final StringGraph simpleSon;
	private final StringGraph concatSon;
	private final StringGraph maxSon;

	private final StringGraph emptySon;

	public BaseStringGraphTest() {
		this.simpleSon = new StringGraph(SIMPLE, new ArrayList<>(), a);
		this.concatSon = new StringGraph(CONCAT, List.of(simpleSon), null);
		this.maxSon = new StringGraph(MAX);
		this.emptySon = new StringGraph(EMPTY);

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

	protected StringGraph getSimpleSon() {
		return simpleSon;
	}

	protected StringGraph getConcatSon() {
		return concatSon;
	}

	protected StringGraph getMaxSon() {
		return maxSon;
	}

	protected StringGraph getEmptySon() {
		return emptySon;
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
