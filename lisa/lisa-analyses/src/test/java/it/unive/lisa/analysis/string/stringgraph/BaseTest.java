package it.unive.lisa.analysis.string.stringgraph;

import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

import static it.unive.lisa.analysis.string.stringgraph.StringGraph.CHARACTER.a;
import static it.unive.lisa.analysis.string.stringgraph.StringGraph.NodeType.*;

public class BaseTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();
    private final List<StringGraph.CHARACTER> availableCharacters;

    private final StringGraph firstSonStringGraph;
    private final StringGraph secondSonStringGraph;
    private final StringGraph thirdSonStringGraph;

    public BaseTest() {
        this.firstSonStringGraph = new StringGraph(SIMPLE, new ArrayList<>(), a);
        this.secondSonStringGraph = new StringGraph(CONCAT,List.of(firstSonStringGraph), null);
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
                    StringGraph.CHARACTER.z
                )
        );
    }

    public List<StringGraph.CHARACTER> getAvailableCharacters() {
        return availableCharacters;
    }

    public ExpectedException getException() {
        return exception;
    }

    public StringGraph getFirstSonStringGraph() {
        return firstSonStringGraph;
    }

    public StringGraph getSecondSonStringGraph() {
        return secondSonStringGraph;
    }

    public StringGraph getThirdSonStringGraph() {
        return thirdSonStringGraph;
    }
}
