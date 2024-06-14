import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class calcShortestPathTest {
  private String executeMainWithInput(String input) {
    InputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
    System.setIn(in);

    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    Main.main(new String[]{});

    return outContent.toString();
  }

  @Test
  public void testCase5WithCorrectInput() {
    String input = "5\nwill gain\n"; 
    String output = executeMainWithInput(input);

    assertTrue(output.contains("请输入两个英文单词，以空格分隔："));
    assertTrue(output.contains("will->gain，最短距离是：1"));
  }
  @Test
  public void testCase5WithOneInput() {
    String input = "5\nonlyone\n";
    String output = executeMainWithInput(input);

    assertTrue(output.contains("请输入两个英文单词，以空格分隔："));
    assertTrue(output.contains("输入格式错误，请输入两个英文单词！"));
  }
  @Test
  public void testCase5WithThreeInput() {
    String input = "5\nhappy new not\n";
    String output = executeMainWithInput(input);

    assertTrue(output.contains("请输入两个英文单词，以空格分隔："));
    assertTrue(output.contains("输入格式错误，请输入两个英文单词！"));
  }
  @Test
  public void testCase5WithValidInput() {
    String input = "5\nseek + to\n";
    String output = executeMainWithInput(input);

    assertTrue(output.contains("请输入两个英文单词，以空格分隔："));
    assertTrue(output.contains("输入格式错误，请输入两个英文单词！"));
  }
}
