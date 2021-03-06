/**
 * Copyright Alex Objelean
 */
package ro.isdc.wro.extensions.processor.support.linter;

import org.junit.Test;


/**
 * @author Alex Objelean
 */
public class TestJsHint {
  private final JsHint jsHint = new JsHint();

  @Test
  public void testSetNullOptions()
    throws Exception {
    jsHint.setOptions(null);
    jsHint.validate("");
  }


  @Test
  public void testWithNoOptions()
    throws Exception {
    jsHint.validate("");
  }


  @Test
  public void testWithSeveralOptions()
    throws Exception {
    jsHint.setOptions("1,2");
    jsHint.validate("");
  }


  @Test(expected = LinterException.class)
  public void testInvalidScript()
    throws Exception {
    jsHint.validate("al ert(1)");
  }


  @Test
  public void testValidScript()
    throws Exception {
    jsHint.validate("$(function(){})(jQuery);");
  }


  @Test(expected = LinterException.class)
  public void testWithUndefOption()
    throws Exception {
    jsHint.setOptions("undef");
    jsHint.validate("$(function(){})(jQuery);");
  }


  @Test(expected = LinterException.class)
  public void testEqeqOption()
    throws Exception {
    jsHint.setOptions("eqeqeq");
    jsHint.validate("var j = 1;var i = j == 0 ? j + 1 : j - 1;");
  }

  @Test(expected = LinterException.class)
  public void testEqeqOption2()
    throws Exception {
    jsHint.setOptions("eqeqeq");
    jsHint.validate("if (text == 0) {win.location.href = link; }");
  }
}
