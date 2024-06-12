import java.io.File;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MAIN {

    public TuPojo tuPojo;
    public  DotGraphBuilder dotGraphBuilder=new DotGraphBuilder();

    public MAIN() {
        String Path="data3.txt";
        readMytxt readmytxt = new readMytxt(Path);
        tuPojo = new TuPojo();
        tuPojo.buildGraphFromString(readmytxt.s);
    }

    public void showDirectedGraph() {

        //1.����dot�ļ�

        String dotFormat=new String();

        for (int i = 0; i <tuPojo.wordIndexMap.size(); i++) {
            for (int j = 0; j < tuPojo.wordIndexMap.size(); j++) {
                if(tuPojo.adjMatrix[i][j]>0) {
                    String node1 = String.valueOf(i);
                    String node2 = String.valueOf(j);
                    String edge= String.valueOf(tuPojo.adjMatrix[i][j]);

                    dotGraphBuilder.addNode(node1,tuPojo.wordIndex2Map.get(i));
                    dotGraphBuilder.addNode(node2,tuPojo.wordIndex2Map.get(j));
                    dotGraphBuilder.addEdge(node1,node2,edge,"darkgreen");
                }
            }
        }
        dotFormat=dotGraphBuilder.build();
        String fileName = "Dotgraph";


        //2.���л�ͼ
        Graphviz gv= new Graphviz();
        gv.addln(gv.start_graph());
        gv.add(dotFormat);
        gv.addln(gv.end_graph());
        // pngΪ�����ʽ�����ɸ�Ϊpdf��gif��jpg��
        String type = "png";
        // gv.increaseDpi();
        gv.decreaseDpi();
        gv.decreaseDpi();
        File out = new File(fileName+"."+ type);
        byte[] graph = gv.getGraph(gv.getDotSource(), type);

        //3.����ͼ��
        gv.writeGraphToFile(graph, out );
    }

    public List<String> queryBridgeWords(String word1, String word2) {
        FindBridegwords findBridegwords = new FindBridegwords();
        //Ѱ���ŽӴ�

        List<String> BrigeWords = findBridegwords.find(tuPojo,word1,word2);
        return  BrigeWords;

    }

    public String generateNewText(String inputtext) {
        InsertBridegWords insertBridegWords = new InsertBridegWords(tuPojo,inputtext);
        String s = insertBridegWords.GenerateBybridgewords();
        return s;
    }


    //���������
    public static void main(String[] args) {
        //1.��ʼ���������ļ�����������ͼ
        MAIN main = new MAIN();
        System.out.println("�Ѿ������ı�������������ͼ");
        while (true) {
            System.out.println("��������ѡ��Ĺ���\t1: չʾ����ͼ\t2:��ѯ�ŽӴ�\t3:����bridge word�������ı�");
            Scanner scanner = new Scanner(System.in);
            String next = scanner.next();
            if (next.equals("quit")) {
                System.out.println("�������˳�");
                break;
            }
            switch (next) {
                case "1":
                    main.showDirectedGraph();
                    break;
                case "2":
                    Scanner scanner1 = new Scanner(System.in);
                    System.out.println("����������ҵ��ŽӴ�");
                    String word1 = scanner1.next();
                    String word2 = scanner1.next();
                    StringBuilder result= new StringBuilder();
                    List<String> BrigeWords= main.queryBridgeWords(word1,word2);
                    //�����������
                    if (BrigeWords.size() == 0) {
                        result.append("No bridge words from").append(" \"")
                                .append(word1).append("\" ").append("to \"")
                                .append(word2).append("\"!");
                        System.out.println(result.toString());
                    }else if (BrigeWords.size()==1 && BrigeWords.get(0)!="False"){
                        result.append("The bridge words from").append(" \"")
                                .append(word1).append("\" ").append("to \"")
                                .append(word2).append("\" ").append("is:").append(BrigeWords.get(0));
                        System.out.println(result.toString());
                    }else if (BrigeWords.size()==1 && BrigeWords.get(0)=="False"){
                        result.append("No").append(" \"")
                                .append(word1).append("\" ").append("or \"")
                                .append(word2).append("\" ").append("in the graph!");
                        System.out.println(result.toString());
                    }else if (BrigeWords.size()>1){
                        result.append("The bridge words from").append(" \"").append(word1).append("\" ").append("to \"")
                                .append(word2).append("\" ").append("are:")
                                .append(String.join(",",BrigeWords));
                        System.out.println(result.toString());
                    }
                    break;
                case "3":
                    Scanner scanner2 = new Scanner(System.in);
                    System.out.println("������һ���ı�");
                    String inputtext = scanner2.nextLine();
                    System.out.println(inputtext);
                    String newtext = main.generateNewText(inputtext);
                    System.out.println(newtext);
                    break;
                default:
                    System.out.println("������������������");
                    break;
            }
        }
    }
}
