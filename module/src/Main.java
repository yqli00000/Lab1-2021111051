import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * 这是主函数类.
 */
public class Main {
  private static Type graph;
  private static SecureRandom random = new SecureRandom();
  /**
   * 这是主函数类.
   */

  public static void main(String[] args) {

    try {
      while (true) {
        System.out.println("--------------------------------------------------");
        System.out.println("--             1. 读入文本并生成有向图              --");
        System.out.println("--             2. 展示有向图(可选)                 --");
        System.out.println("--             3. 查询桥接词                      --");
        System.out.println("--             4. 生成新文本                      --");
        System.out.println("--             5. 计算两个单词之间的最短路径         --");
        System.out.println("--             6.  最短路径-可选功能               --");
        System.out.println("--             7. 随机游走                        --");
        System.out.println("--             8. 退出程序                        --");
        System.out.println("--------------------------------------------------");
        Scanner scan = new Scanner(new InputStreamReader(System.in, StandardCharsets.UTF_8));

        System.out.println("请输入所选择的序号：");
        if (!scan.hasNextInt()) {
          System.out.println("输入无效，请输入一个整数。");
          continue;
        }
        int n = scan.nextInt();
        scan.nextLine();
        switch (n) {
          case 1:
            final long startTime = System.nanoTime();
            File file = new File("text.txt");
            Scanner scanner = new Scanner(file, StandardCharsets.UTF_8.name());

            StringBuilder processedText = new StringBuilder();
            Map<String, Integer> wordCount = new HashMap<>();

            String prevWord = null;

            while (scanner.hasNextLine()) {
              String line = scanner.nextLine();
              String[] words = line.split("[^a-zA-Z]"); // 使用正则表达式分割单词

              for (String word : words) {
                if (!word.isEmpty()) {
                  String normalizedWord = word.toLowerCase();
                  processedText.append(normalizedWord).append(" "); // 转换为小写并添加空格

                  if (prevWord != null) {
                    String edge = prevWord + "-" + normalizedWord;
                    wordCount.put(edge, wordCount.getOrDefault(edge, 0) + 1);
                  }

                  prevWord = normalizedWord;
                }
              }
            }

            System.out.println("the text:");
            System.out.println(processedText.toString().trim()); // 去除末尾多余的空格并输出结果

            scanner.close();
            graph = new Type();

            // 添加顶点和边
            for (Map.Entry<String, Integer> entry : wordCount.entrySet()) {
              String[] words = entry.getKey().split("-");
              graph.addEdge(words[0], words[1], entry.getValue());
            }

            // 显示邻接表
            graph.displayAdjacencyList();
            // 记录结束时间
            long endTime = System.nanoTime();
            // 计算运行时间
            long duration = endTime - startTime;
            System.out.println("执行时间: " + (duration / 1_000_000.0) + " 毫秒");
            break;
          case 2:
            if (graph == null) {
              System.out.println("请先执行 case 1 来创建图形！");
              break;
            }
            long startTime2 = System.nanoTime();
            saveGraphToFile(graph);
            String dotFilePath = "graph.dot";
            String outputImagePath = "output.png";

            // 调用Graphviz生成图形
            showDirectedGraph(dotFilePath, outputImagePath);
            // 记录结束时间
            long endTime2 = System.nanoTime();
            // 计算运行时间
            long duration2 = endTime2 - startTime2;
            System.out.println("执行时间: " + (duration2 / 1_000_000.0) + " 毫秒");
            break;
          case 3:
            File file1 = new File("text.txt");
            Scanner scanner1 = new Scanner(file1, StandardCharsets.UTF_8.name());

            StringBuilder processedText1 = new StringBuilder();
            Map<String, Integer> wordCount1 = new HashMap<>();

            String prevWord1 = null;

            while (scanner1.hasNextLine()) {
              String line = scanner1.nextLine();
              String[] words = line.split("[^a-zA-Z]"); // 使用正则表达式分割单词

              for (String word : words) {
                if (!word.isEmpty()) {
                  String normalizedWord = word.toLowerCase();
                  processedText1.append(normalizedWord).append(" "); // 转换为小写并添加空格

                  if (prevWord1 != null) {
                    String edge = prevWord1 + "-" + normalizedWord;
                    wordCount1.put(edge, wordCount1.getOrDefault(edge, 0) + 1);
                  }

                  prevWord1 = normalizedWord;
                }
              }
            }
            scanner1.close();
            graph = new Type();

            // 添加顶点和边
            for (Map.Entry<String, Integer> entry : wordCount1.entrySet()) {
              String[] words = entry.getKey().split("-");
              graph.addEdge(words[0], words[1], entry.getValue());
            }
            System.out.println("请输入两个英文单词，以空格分隔：");
            String wordInput = scan.nextLine();
            String[] words = wordInput.split(" ");
            if (words.length != 2) {
              System.out.println("输入格式错误，请输入两个英文单词！");
              return;
            }
            String word1 = words[0].toLowerCase();
            String word2 = words[1].toLowerCase();
            String bridgeWords = queryBridgeWords(word1, word2);
            if (bridgeWords == null) {
              System.out.println("No word1 or word2 in the graph!");
            } else if (bridgeWords.length() == 0) {
              System.out.println("No bridge words from " + word1 + " to " + word2 + "!");
            } else {
              System.out.print("The bridge words from " + word1 + " to " + word2 + " are: ");
              System.out.println(bridgeWords);
            }
            return;
          case 4:
            if (graph == null) {
              System.out.println("请先执行 case 1 来创建图形！");
              break;
            }
            System.out.println("请输入一行新的文本：");
            Scanner newText = new Scanner(
                new InputStreamReader(System.in, StandardCharsets.UTF_8));
            String inputText = newText.nextLine();
            long startTime4 = System.nanoTime();
            String outputText = generateNewText(inputText);
            // 记录结束时间
            long endTime4 = System.nanoTime();
            // 计算运行时间
            long duration4 = endTime4 - startTime4;
            System.out.println("添加桥接词后的文本为：");
            System.out.println(outputText);
            System.out.println("执行时间: " + (duration4 / 1_000_000.0) + " 毫秒");
            break;
          case 5:
            if (graph == null) {
              System.out.println("请先执行 case 1 来创建图形！");
              break;
            }
            System.out.println("请输入两个英文单词，以空格分隔：");
            //Scanner scanInput2 = new Scanner(System.in);
            Scanner scanInput2 = new Scanner(
                new InputStreamReader(System.in, StandardCharsets.UTF_8));
            String wordInput2 = scanInput2.nextLine();
            String[] words2 = wordInput2.split(" ");
            if (words2.length != 2) {
              System.out.println("输入格式错误，请输入两个英文单词！");
              break;
            }
            String wwWord1 = words2[0].toLowerCase();
            String wwWord2 = words2[1].toLowerCase();
            long startTime5 = System.nanoTime();
            String outcome = calcShortestPath(wwWord1, wwWord2);
            // 记录结束时间
            long endTime5 = System.nanoTime();
            // 计算运行时间
            long duration5 = endTime5 - startTime5;

            if (outcome == null) {
              System.out.println("有不属于该图的结点！");
            } else if (outcome.equals("")) {
              System.out.println("这两个单词不可达！");
            } else {
              System.out.println(outcome);
            }
            System.out.println("执行时间: " + (duration5 / 1_000_000.0) + " 毫秒");
            break;
          case 6:
            if (graph == null) {
              System.out.println("请先执行 case 1 来创建图形！");
              break;
            }
            System.out.println("请输入一个单词：");
            Scanner scanInput3 = new Scanner(
                new InputStreamReader(System.in, StandardCharsets.UTF_8));
            String word3 = scanInput3.nextLine();

            String inputWord1 = word3.toLowerCase();
            String inputWord2 = "#";
            long startTime6 = System.nanoTime();
            calcShortestPath(inputWord1, inputWord2);
            // 记录结束时间
            long endTime6 = System.nanoTime();
            // 计算运行时间
            long duration6 = endTime6 - startTime6;
            System.out.println("执行时间: " + (duration6 / 1_000_000.0) + " 毫秒");
            break;
          case 7:
            if (graph == null) {
              System.out.println("请先执行 case 1 来创建图形！");
              break;
            }
            long startTime7 = System.nanoTime();
            String walkOutput = randomWalk();
            String outputFilePath = "random_walk_output.txt";
            try (OutputStreamWriter writer = new OutputStreamWriter(
                new FileOutputStream(outputFilePath, true), StandardCharsets.UTF_8)) {
              writer.append(walkOutput);
              //                 writer.append(System.lineSeparator()); // 添加换行符
              System.out.println("Random walk output saved to " + outputFilePath);
            } catch (IOException e) {
              e.printStackTrace();
              System.out.println("Failed to save random walk output to file.");
            }
            // 记录结束时间
            long endTime7 = System.nanoTime();
            // 计算运行时间
            long duration7 = endTime7 - startTime7;
            System.out.println("执行时间: " + (duration7 / 1_000_000.0) + " 毫秒");
            break;
          case 8:
            return;
          default:
            break;
        }
      }
    } catch (FileNotFoundException e) {
      System.out.println("File not found.");
      e.printStackTrace();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }


  }
  /**
   * 这是保存图像到文件.
   */

  public static void saveGraphToFile(Type graph) {
    try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(
        new FileOutputStream("graph.dot"), StandardCharsets.UTF_8))) {
      // 写入DOT文件头部信息
      writer.println("digraph G {");

      // 写入边信息
      for (Map.Entry<String, List<Type.Edge>> entry : graph.getAdjacencyList().entrySet()) {
        for (Type.Edge neighbor : entry.getValue()) {
          writer.println("  " + entry.getKey() + " -> "
              + neighbor.destination + " [label=\"" + neighbor.weight + "\"];");
        }
      }

      // 写入DOT文件尾部信息
      writer.println("}");

      System.out.println("Graph saved to graph.dot");
    } catch (IOException e) {
      System.out.println("Failed to save graph to file.");
      e.printStackTrace();
    }
  }
  /**
   * 这是查询桥接词函数.
   */

  public static String  queryBridgeWords(String word1, String word2) {
    List<String> bridgeWords = new ArrayList<>();
    if (!graph.getAdjacencyList().containsKey(word1)
        || !graph.getAdjacencyList().containsKey(word2)) {
      return null;
    }

    for (Type.Edge edgeFromWord1 : graph.getAdjacencyList().get(word1)) {
      String bridgeWord = edgeFromWord1.destination;
      if (graph.getAdjacencyList().containsKey(bridgeWord)) {
        for (Type.Edge edgeToWord2 : graph.getAdjacencyList().get(bridgeWord)) {
          if (edgeToWord2.destination.equals(word2)) {
            bridgeWords.add(bridgeWord);
          }
        }
      }
    }
    String bbridgeWords = String.join(", ", bridgeWords);
    return bbridgeWords;
  }
  /**
   * 这是生成有向图.
   */

  public static void showDirectedGraph(String dotFilePath, String outputImagePath) {

    try {
      //            // 构建Graphviz命令
      //      String[] cmd = {"dot", "-Tpng", dotFilePath, "-o", outputImagePath};

      // 执行命令
      //      ProcessBuilder pb = new ProcessBuilder(cmd);
      ProcessBuilder pb = new ProcessBuilder("dot", "-Tpng", dotFilePath, "-o", outputImagePath);
      pb.redirectErrorStream(true);
      Process process = pb.start();

      // 等待命令执行完成
      process.waitFor();
      try (BufferedReader reader = new BufferedReader(
          new InputStreamReader(process.getInputStream(), "UTF-8"))) {
        String line;
        while ((line = reader.readLine()) != null) {
          System.out.println(line);
        }
      } catch (IOException e) {
        // 处理异常
      }
      // 输出命令执行结果
      //BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
      //      BufferedReader reader = new BufferedReader(
      //          new InputStreamReader(process.getInputStream(), "UTF-8"));
      //      String line;
      //      while ((line = reader.readLine()) != null) {
      //        System.out.println(line);
      //      }

      // 检查命令执行是否出错
      int exitValue = process.exitValue();
      if (exitValue != 0) {
        // 命令执行出错
        System.err.println("Graphviz命令执行失败，error code: " + exitValue);
      } else {
        // 命令执行成功
        //System.out.println("Graphviz命令执行成功！");
      }
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
  }
  /**
   * 这是生成新文本.
   */

  public static String generateNewText(String inputText) {

    StringBuilder processedText2 = new StringBuilder();

    String prevWord2 = null;
    String[] words2 = inputText.split("[^a-zA-Z]"); // 使用正则表达式分割单词

    for (String word : words2) {
      if (!word.isEmpty()) {
        String normalizedWord = word.toLowerCase();
        //                System.out.println(prevWord2);
        //                System.out.println(normalizedWord);
        if (prevWord2 != null) {
          String bridgeWords = queryBridgeWords(prevWord2, normalizedWord);
          if (bridgeWords == null) {
            processedText2.append(normalizedWord).append(" "); // 转换为小写并添加空格
            prevWord2 = normalizedWord;
            continue;
          } else {
            String[] strArray = bridgeWords.split(", ");
            //                        System.out.println(strArray);
            // 将数组转换为 List<String>
            List<String> listBridgeWords = Arrays.asList(strArray);
            //                        listBridgeWords.removeIf(String::isEmpty);
            //                        System.out.println(listBridgeWords);
            if (listBridgeWords.size() == 1) {
              String bridgeWord = listBridgeWords.get(0);
              if (!bridgeWord.isEmpty()) {
                processedText2.append(listBridgeWords.get(0)).append(" ");
                System.out.println(listBridgeWords.get(0));
              }
            } else if (listBridgeWords.size() > 1) {

              //  Random random = new Random();
              int randomIndex = random.nextInt(listBridgeWords.size());
              // 生成一个随机索引，范围是 [0, list.size())

              // 使用随机索引从List中获取随机元素
              String randomElement = listBridgeWords.get(randomIndex);

              System.out.println("随机选择的元素是：" + randomElement);
              processedText2.append(randomElement).append(" ");
            } else if (listBridgeWords.isEmpty()) {
              processedText2.append(normalizedWord).append(" "); // 转换为小写并添加空格
              prevWord2 = normalizedWord;
              continue;
            }
          }
          //String edge = prevWord2 + "-" + normalizedWord;
        }
        processedText2.append(normalizedWord).append(" "); // 转换为小写并添加空格
        prevWord2 = normalizedWord;
      }
    }
    //        System.out.println(processedText2);
    return processedText2.toString();
  }
  /**
   * 这是计算最短路径.
   */

  public static String calcShortestPath(String word1, String word2) {
    String s = "";
    if (word2.equals("#")) {
      if (!graph.getAdjacencyList().containsKey(word1)) {
        return null;
      }
    } else {
      if (!graph.getAdjacencyList().containsKey(word1)
          || !graph.getAdjacencyList().containsKey(word2)) {
        return null;
      }
    }
    int mmaxValue = 100000;
    int numVertex = graph.getVertexCount();
    //        System.out.println(vertex);
    //int numEdge = graph.getEdgeCount();
    //        System.out.println("Vertex count: " + graph.getVertexCount());
    //        System.out.println("Edge count: " + graph.getEdgeCount());
    //映射
    Map<String, Integer> stringMapping = new HashMap<>();
    Map<Integer, String> reverseStringMapping = new HashMap<>();
    int counter = 0;
    for (String point : graph.getAdjacencyList().keySet()) {
      stringMapping.put(point, counter);
      reverseStringMapping.put(counter, point);
      counter++;
    }
    // 输出映射结果
    //        System.out.println("String 映射结果:");
    //        for (Map.Entry<String, Integer> entry : stringMapping.entrySet()) {
    //            System.out.println(entry.getKey() + " -> " + entry.getValue());
    //        }
    int[][] matrix = new int[numVertex][numVertex];
    //初始化邻接矩阵
    for (int i = 0; i < numVertex; i++) {
      for (int j = 0; j < numVertex; j++) {
        matrix[i][j] = mmaxValue;
      }
    }
    Map<String, List<Type.Edge>> adjacencyList = graph.getAdjacencyList();
    for (Map.Entry<String, List<Type.Edge>> entry : adjacencyList.entrySet()) {
      int m = stringMapping.get(entry.getKey()); //标号
      for (Type.Edge edge : entry.getValue()) {
        int n = stringMapping.get(edge.destination);
        matrix[m][n] = edge.weight;
      }
    }
    //        for (int i = 0; i < numVertex ; i++) {
    //            for (int j = 0; j < numVertex ; j++) {
    //                System.out.print(matrix[i][j]+"\t");
    //            }
    //            System.out.println();
    //        }
    int labelWord1 = stringMapping.get(word1);

    //最短路径长度
    int[] shortest = new int[matrix.length];
    //判断该点的最短路径是否求出
    int[] visited = new int[matrix.length];
    //存储输出路径
    String[] path = new String[matrix.length];

    //初始化输出路径
    for (int i = 0; i < matrix.length; i++) {
      String word = reverseStringMapping.get(i);
      path[i] = word1 + "->" + word;
    }

    //初始化源节点
    shortest[labelWord1] = 0;
    visited[labelWord1] = 1;

    for (int i = 1; i < matrix.length; i++) {
      int min = Integer.MAX_VALUE;
      int index = -1;

      for (int j = 0; j < matrix.length; j++) {
        //已经求出最短路径的节点不需要再加入计算并判断加入节点后是否存在更短路径
        if (visited[j] == 0 && matrix[labelWord1][j] < min) {
          min = matrix[labelWord1][j];
          index = j;
        }
      }

      //更新最短路径
      shortest[index] = min;
      visited[index] = 1;

      //更新从index跳到其它节点的较短路径
      for (int m = 0; m < matrix.length; m++) {
        String word = reverseStringMapping.get(m);
        if (visited[m] == 0 && matrix[labelWord1][index] + matrix[index][m]
            < matrix[labelWord1][m]) {
          matrix[labelWord1][m] = matrix[labelWord1][index] + matrix[index][m];
          path[m] = path[index] + "->" + word;
        }
      }

    }
    if (word2.equals("#")) {
      //打印最短路径
      //int count = 1;
      for (int i = 0; i < matrix.length; i++) {
        if (i != labelWord1) {
          String word = reverseStringMapping.get(i);
          if (shortest[i] == mmaxValue) {
            System.out.println(word1 + "到" + word + "不可达");
          } else {
            System.out.println(word1 + "到" + word + "的最短路径为：" + path[i] + "，最短距离是：" + shortest[i]);
            String[] strArray = path[i].split("->");
            //                        System.out.println(strArray);
            // 将数组转换为 List<String>
            List<String> shortestPath = Arrays.asList(strArray);
            drawing(shortestPath, word, word1);
            //count++;
          }
        }
      }
    } else {
      int labelWord2 = stringMapping.get(word2);
      if (shortest[labelWord2] == mmaxValue) {
        s = "";
      } else {
        s = path[labelWord2] + "，最短距离是：" + shortest[labelWord2];
      }
      String[] strArray = path[labelWord2].split("->");
      //                        System.out.println(strArray);
      // 将数组转换为 List<String>
      List<String> shortestPath = Arrays.asList(strArray);
      drawing(shortestPath, word2, word1);
    }

    return s;
  }
  /**
   * 这是画最短路径图.
   */

  public static void drawing(List<String> shortestPath, String destinationD, String word) {
    try {
      String dotFilePath = "graph.dot";
      final String outputImagePath =
          String.format("./output/output_shortPath_%s_%s.png", word, destinationD);
      // 读取原始 DOT 文件内容
      File file = new File(dotFilePath);
      //BufferedReader reader = new BufferedReader(new FileReader(file));
      BufferedReader reader = new BufferedReader(
          new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
      StringBuilder dotContent = new StringBuilder();
      String line;
      while ((line = reader.readLine()) != null) {
        dotContent.append(line).append("\n");
      }
      reader.close();

      // 标记最短路径的边为红色
      for (int i = 0; i < shortestPath.size() - 1; i++) {
        String source = shortestPath.get(i);
        String destination = shortestPath.get(i + 1);
        int weight = graph.getWeight(source, destination);
        if (weight == -1) {
          return;
        }
        String edge = source + " -> " + destination + " [label=\"" + weight + "\"];";
        String edge2 = source + " -> " + destination + " [label=\"" + weight + "\"]";
        //                System.out.println(edge);
        // 替换 DOT 文件中的边定义，添加 color=red 属性
        dotContent.replace(dotContent.indexOf(edge), dotContent.indexOf(edge) + edge.length(),
            edge2 + " [color=red];");
        //dotContent = dotContent.replaceAll(Pattern.quote(edge), edge + " [color=red];");
        //System.out.println(dotContent.toString());
      }

      // 将修改后的 DOT 内容写入临时文件
      File tempDotFile = new File("temp.dot");
      FileOutputStream fileOutputStream = new FileOutputStream(tempDotFile);
      try (OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
          fileOutputStream, StandardCharsets.UTF_8)) {
        outputStreamWriter.write(dotContent.toString());
        //writer.write(dotContent.toString());
        //writer.close();
      } catch (IOException e) {
        // 处理异常
      }

      // 调用 Graphviz 生成图片
      showDirectedGraph("temp.dot", outputImagePath);

      // 删除临时 DOT 文件
      // tempDotFile.delete();
      if (!tempDotFile.delete()) {
        System.err.println("无法删除临时文件：" + tempDotFile.getAbsolutePath());
        // 可以添加进一步的处理逻辑，比如记录日志或者尝试其他操作
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  /**
   * 这是随机游走.
   */

  public static String randomWalk() throws IOException {
    Thread thread = new Thread();
    thread.start();
    StringBuilder walkOutput = new StringBuilder(); // 用于存储遍历的节点
    //    Random random = new Random();
    SecureRandom random = new SecureRandom();
    if (graph == null || graph.getAdjacencyList().isEmpty()) {
      return "Graph为空";
    }

    // 随机选择一个起始节点
    List<String> vertices = new ArrayList<>(graph.getAdjacencyList().keySet());
    String currentNode = vertices.get(random.nextInt(vertices.size()));

    while (true) {
      walkOutput.append(currentNode).append(" -> "); // 记录经过的节点

      List<Type.Edge> edges = graph.getAdjacencyList().get(currentNode);
      if (edges == null || edges.isEmpty()) {
        int length = walkOutput.length();
        if (length >= 4) {
          walkOutput.setLength(length - 4); // 删除末尾两个字符
          walkOutput.append("\n");
        }
        break;
      }

      // 随机选择一个出边
      Type.Edge randomEdge = edges.get(random.nextInt(edges.size()));
      String nextNode = randomEdge.destination;

      // 检查是否出现重复的边
      if (walkOutput.toString().split(Pattern.quote(currentNode + " -> " + nextNode)).length == 2) {
        int length = walkOutput.length();
        if (length >= 4) {
          walkOutput.setLength(length - 4); // 删除末尾两个字符
          walkOutput.append("\n");
        }
        break;
      }
      currentNode = nextNode;

      // 检查是否有键盘输入
      if (System.in.available() > 0) {
        //Scanner scanner = new Scanner(
        //    new InputStreamReader(System.in, StandardCharsets.UTF_8));
        //String input = scanner.nextLine();
        // 输出当前已经遍历的路径
        System.out.println("遍历已停止. 当前路径：" + walkOutput.toString());
        break;
      }
      // 可用于测试用户暂停
      try {
        Thread.sleep(1000); // 休眠 1 秒
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    return walkOutput.toString();
  }
}

