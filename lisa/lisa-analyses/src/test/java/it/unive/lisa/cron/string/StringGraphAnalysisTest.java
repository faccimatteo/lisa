package it.unive.lisa.cron.string;

import it.unive.lisa.AnalysisSetupException;
import it.unive.lisa.AnalysisTestExecutor;
import it.unive.lisa.LiSAConfiguration;
import it.unive.lisa.analysis.AbstractState;
import it.unive.lisa.analysis.heap.HeapDomain;
import it.unive.lisa.analysis.nonrelational.value.TypeEnvironment;
import it.unive.lisa.analysis.string.stringgraph.StringGraph;
import it.unive.lisa.analysis.string.stringgraph.StringGraphDomain;
import it.unive.lisa.analysis.types.InferredTypes;
import org.junit.Test;

import static it.unive.lisa.LiSAFactory.getDefaultFor;

public class StringGraphAnalysisTest extends AnalysisTestExecutor {

    @Test
    public void testFSA() throws AnalysisSetupException {
        LiSAConfiguration conf = new LiSAConfiguration();
        conf.serializeResults = true;
        conf.abstractState = getDefaultFor(AbstractState.class, getDefaultFor(HeapDomain.class), new StringGraphDomain(new StringGraph("fabolousstring")),
                new TypeEnvironment<>(new InferredTypes()));
        perform("stringgraph", "program.imp", conf);
    }

}
