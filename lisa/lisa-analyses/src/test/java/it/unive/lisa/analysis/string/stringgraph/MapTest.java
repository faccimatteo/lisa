package it.unive.lisa.analysis.string.stringgraph;

import it.unive.lisa.analysis.string.stringgraph.exception.WrongBuildStringGraphException;
import org.junit.Test;

import java.util.Random;

public class MapTest extends BaseStringGraphTest {

    @Test
    public void whenMapIsExecutedOnAvailableCharacters_thenChecksMapHappensCorrectly(){
        for (StringGraph.CHARACTER c : this.getAvailableCharacters()) {
            char convertedC = c.name().charAt(0);
            StringGraph.map(convertedC);
        }
    }

    @Test(expected = WrongBuildStringGraphException.class)
    public void whenMapIsExecutedOnBadCharacters_thenThrowWrongBuildStringGraphException() {
        String badCharacters = "_?ì+ùà°ò°:,-!\"£$%&/()=?^§";
        Random r = new Random();
        char badCharacter = badCharacters.charAt(r.nextInt(badCharacters.length()));
        StringGraph.map(badCharacter);
    }

}
