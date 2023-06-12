package it.unive.lisa.analysis.string.stringgrapdomain;

import it.unive.lisa.analysis.SemanticException;
import it.unive.lisa.analysis.string.stringgraph.StringGraph;
import it.unive.lisa.analysis.string.stringgraph.StringGraphDomain;
import it.unive.lisa.program.SourceCodeLocation;
import it.unive.lisa.program.SyntheticLocation;
import it.unive.lisa.program.type.StringType;
import it.unive.lisa.symbolic.value.Constant;
import org.junit.Test;

import java.util.List;

import static it.unive.lisa.analysis.string.stringgraph.StringGraph.NodeType.*;
import static org.junit.Assert.assertEquals;

public class EvalNonNullConstantTest {

    @Test
    public void whenConstantIsString_thenEvaluateItCorrectly() throws SemanticException {
        StringGraphDomain s = new StringGraphDomain(new StringGraph(SIMPLE, List.of(), StringGraph.CHARACTER.a));
        Constant c = new Constant(StringType.INSTANCE,"\"constant\"", SyntheticLocation.INSTANCE);
        s = s.evalNonNullConstant(c, null);
        assertEquals(CONCAT, s.getStringGraph().getLabel());
        assertEquals("CONCAT[ c o n s t a n t]", s.getStringGraph().toString());

    }
}
