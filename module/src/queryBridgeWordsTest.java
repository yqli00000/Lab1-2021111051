import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test; // 使用JUnit 5

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

class queryBridgeWordsTest {
//  @Test
//  public void testCase1ThenCase3WithValidInput() {
//    // 模拟输入：3， 然后两个单词 "hello world"
//    String input = "3\nseek new\n";
//    InputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
//    System.setIn(in);
//
//    // 捕获系统输出
//    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
//    System.setOut(new PrintStream(outContent));
//
//    // 调用主方法
//    Main.main(new String[]{});
//
//    // 检查系统输出
//    String output = outContent.toString();
//    assertTrue(output.contains("请输入两个英文单词，以空格分隔："));
//    assertTrue(output.contains("The bridge words from hello to world are: in, out"));
//  }
// 封装要测试的方法
//  private static String executeQueryBridgeWords(String input) {
//    InputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
//    System.setIn(in);
//
//    // 捕获系统输出
//    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
//    System.setOut(new PrintStream(outContent));
//
//    // 调用主方法
//    Main.main(new String[]{});
//
//    // 返回系统输出
//    return outContent.toString();
//  }
//
//  @Test
//  public void testCase1ThenCase3WithValidInput() {
//    // 模拟输入：3， 然后两个单词 "seek new"
//    String input = "3\nseek new\n";
//
//    // 执行方法并获取系统输出
//    String output = executeQueryBridgeWords(input);
//
//    // 检查系统输出
//    assertTrue(output.contains("请输入两个英文单词，以空格分隔："));
//    assertTrue(output.contains("The bridge words from seek to new are: bridgeWord1, bridgeWord2"));
//  }
private void executeMainWithInput(String input) {
  InputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
  System.setIn(in);

  Main.main(new String[]{});
}

  private String captureOutput(Runnable runnable) {
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    runnable.run();
    return outContent.toString();
  }

  @Test
  public void testCase1ThenCase3WithValidInput() {
    // 第一次输入：1，初始化图形
//    String input1 = "1";
//    executeMainWithInput(input1);

    // 第二次输入：3，然后输入两个单词 "seek new"
    String input2 = "3\nseek new";
//    String wordInput = "seek new";

    // 捕获并检查输出
    String output = captureOutput(() -> executeMainWithInput(input2));
//    String output2 = captureOutput(() -> executeMainWithInput(wordInput));

    // 检查系统输出
    assertTrue(output.contains("请输入两个英文单词，以空格分隔："));
    assertTrue(output.contains("The bridge words from seek to new are: bridgeWord1, bridgeWord2"));
  }
//    private String captureOutput(String input) {
//      ByteArrayOutputStream outContent = new ByteArrayOutputStream();
//      System.setOut(new PrintStream(outContent));
//      InputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
//      System.setIn(in);
//
//      // 调用主方法
//      Main.main(new String[]{});
//
//      // 返回系统输出
//      return outContent.toString();
//    }
//
//    @Test
//    public void testCase1ThenCase3WithValidInput() {
//      // 第一次输入：1，初始化图形
//      String input1 = "1";
//      captureOutput(input1);
//
//      // 第二次输入：3，然后输入两个单词 "seek new"
//      String input2 = "3\nseek new";
//      String output2 = captureOutput(input2);
//
//      // 检查系统输出
////      assertTrue(output1.contains("Enter the first word:"));
//      assertTrue(output2.contains("请输入两个英文单词，以空格分隔："));
//      assertTrue(output2.contains("The bridge words from seek to new are: bridgeWord1, bridgeWord2"));
//    }
}
