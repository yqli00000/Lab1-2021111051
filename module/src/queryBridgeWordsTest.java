import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class queryBridgeWordsTest {
  private String executeMainWithInput(String input) {
    InputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
    System.setIn(in);

    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    Main.main(new String[]{});

    return outContent.toString();
  }

  @Test
  public void testCase3With1() {
    String input = "3\nnot exist\n";
    String output = executeMainWithInput(input);

    assertTrue(output.contains("请输入两个英文单词，以空格分隔："));
    assertTrue(output.contains("No word1 or word2 in the graph!"));
  }
  @Test
  public void testCase3With2_3() {
    String input = "3\nseek new\n";
    String output = executeMainWithInput(input);

    assertTrue(output.contains("请输入两个英文单词，以空格分隔："));
    assertTrue(output.contains("The bridge words from seek to new are: in, out"));
  }
  @Test
  public void testCase3With4_6() {
    String input = "3\nwill true\n";
    String output = executeMainWithInput(input);

    assertTrue(output.contains("请输入两个英文单词，以空格分隔："));
    assertTrue(output.contains("The bridge words from will to true are: come"));
  }
  @Test
  public void testCase3With5() {
    String input = "3\nseek to\n";
    String output = executeMainWithInput(input);

    assertTrue(output.contains("请输入两个英文单词，以空格分隔："));
    assertTrue(output.contains("No bridge words from seek to to!"));
  }
  @Test
  public void testCase3With7() {
    String input = "3\nnew and\n";
    String output = executeMainWithInput(input);

    assertTrue(output.contains("请输入两个英文单词，以空格分隔："));
    assertTrue(output.contains("The bridge words from new to and are: life"));
  }
}
