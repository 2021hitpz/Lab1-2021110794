import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FindBridegwordsTest {
    public TuPojo tuPojo;
    public  DotGraphBuilder dotGraphBuilder=new DotGraphBuilder();


    @BeforeEach
    public void beforeAll() {
        String Path="data.txt";
        readMytxt readmytxt = new readMytxt(Path);
        tuPojo = new TuPojo();
        tuPojo.buildGraphFromString(readmytxt.s);
    }

    @Test
    void find() {
        FindBridegwords findBridegwords = new FindBridegwords();
        String word1 = new String("new22");
        String word2 = new String();

        //я╟урге╫с╢й

        List<String> BrigeWords = findBridegwords.find(tuPojo,word1,word2);

    }
}