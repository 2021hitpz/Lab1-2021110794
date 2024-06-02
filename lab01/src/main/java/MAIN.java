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

        //1.构建dot文件

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


        //2.进行绘图
        Graphviz gv= new Graphviz();
        gv.addln(gv.start_graph());
        gv.add(dotFormat);
        gv.addln(gv.end_graph());
        // png为输出格式，还可改为pdf，gif，jpg等
        String type = "png";
        // gv.increaseDpi();
        gv.decreaseDpi();
        gv.decreaseDpi();
        File out = new File(fileName+"."+ type);
        byte[] graph = gv.getGraph(gv.getDotSource(), type);

        //3.保存图像
        gv.writeGraphToFile(graph, out );
    }

    public List<String> queryBridgeWords(String word1, String word2) {
        FindBridegwords findBridegwords = new FindBridegwords();
        //寻找桥接词

        List<String> BrigeWords = findBridegwords.find(tuPojo,word1,word2);
        return  BrigeWords;

    }

    public String generateNewText(String inputtext) {
        InsertBridegWords insertBridegWords = new InsertBridegWords(tuPojo,inputtext);
        String s = insertBridegWords.GenerateBybridgewords();
        return s;
    }


    //主函数入口
    public static void main(String[] args) {
        //1.初始化，读入文件并生成有向图
        MAIN main = new MAIN();
        System.out.println("已经读入文本并生成了有向图");
        while (true) {
            System.out.println("请输入想选择的功能\t1: 展示有向图\t2:查询桥接词\t3:根据bridge word生成新文本");
            Scanner scanner = new Scanner(System.in);
            String next = scanner.next();
            if (next.equals("quit")) {
                System.out.println("程序已退出");
                break;
            }
            switch (next) {
                case "1":
                    main.showDirectedGraph();
                    break;
                case "2":
                    Scanner scanner1 = new Scanner(System.in);
                    System.out.println("请输入向查找的桥接词");
                    String word1 = scanner1.next();
                    String word2 = scanner1.next();
                    StringBuilder result= new StringBuilder();
                    List<String> BrigeWords= main.queryBridgeWords(word1,word2);
                    //处理输出函数
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
                    System.out.println("请输入一段文本");
                    String inputtext = scanner2.nextLine();
                    System.out.println(inputtext);
                    String newtext = main.generateNewText(inputtext);
                    System.out.println(newtext);
                    break;
                default:
                    System.out.println("输入有误，请重新输入");
                    break;
            }
        }
    }
}
