/**
 *
 */
package ro.isdc.wro.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import junit.framework.Assert;

import org.junit.Test;
import org.mockito.Mockito;

/**
 * Test {@link WroUtil} class.
 * @author Alex Objelean
 */
public class TestWroUtil {
  @Test(expected=IllegalArgumentException.class)
  public void cannotComputeEmptyLocation() {
    WroUtil.getPathInfoFromLocation("");
  }
  @Test
  public void computePathFromSomeLocation() {
    final String result = WroUtil.getPathInfoFromLocation("location");
    Assert.assertEquals("", result);
  }
  @Test
  public void computePathFromNestedLocation() {
    final String result = WroUtil.getPathInfoFromLocation("/a/b/c/d");
    Assert.assertEquals("/b/c/d", result);
  }

  @Test
  public void computeServletPathFromLocation() {
    final String result = WroUtil.getServletPathFromLocation("/a/b/c/d");
    Assert.assertEquals("/a", result);
  }

  /**
   * Test for several mangled header examples based on
   * {@link http://developer.yahoo.com/blogs/ydn/posts/2010/12/pushing-beyond-gzipping/}
   * blog post.
   */
  @Test
  public void testGzipSupport() throws Exception {
    HttpServletRequest request = mockRequestHeader("", "");
    Assert.assertFalse(WroUtil.isGzipSupported(request));

    request = mockRequestHeader("Accept-Encoding", "");
    Assert.assertFalse(WroUtil.isGzipSupported(request));

    request = mockRequestHeader("Accept-Encoding", "gzip, deflate");
    Assert.assertTrue(WroUtil.isGzipSupported(request));

    request = mockRequestHeader("Accept-Encoding", "XYZ");
    Assert.assertFalse(WroUtil.isGzipSupported(request));

    request = mockRequestHeader("Accept-EncodXng", "XXXXXXXXXXXXX");
    Assert.assertTrue(WroUtil.isGzipSupported(request));

    request = mockRequestHeader("X-cept-Encoding", "gzip,deflate");
    Assert.assertTrue(WroUtil.isGzipSupported(request));

    request = mockRequestHeader("XXXXXXXXXXXXXXX", "XXXXXXXXXXXXX");
    Assert.assertTrue(WroUtil.isGzipSupported(request));

    request = mockRequestHeader("XXXXXXXXXXXXXXXX", "gzip, deflate");
    Assert.assertFalse(WroUtil.isGzipSupported(request));

    request = mockRequestHeader("---------------", "-------------");
    Assert.assertTrue(WroUtil.isGzipSupported(request));

    request = mockRequestHeader("~~~~~~~~~~~~~~~", "~~~~~~~~~~~~~");
    Assert.assertTrue(WroUtil.isGzipSupported(request));

    request = mockRequestHeader("Accept-Encoding", "gzip,deflate,sdch");
    Assert.assertTrue(WroUtil.isGzipSupported(request));
  }

  /**
   * @param request
   * @param headerName
   * @param headerValue
   */
  private HttpServletRequest mockRequestHeader(final String headerName, final String headerValue) {
    final HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    final Enumeration<String> enumeration = Collections.enumeration(Arrays.asList(headerName));
    Mockito.when(request.getHeaderNames()).thenReturn(enumeration);
    Mockito.when(request.getHeader(headerName)).thenReturn(headerValue);
    return request;
  }

  @Test
  public void testToJsMultilineString() {
    Assert.assertEquals("[\"\\n\"].join(\"\\n\")", WroUtil.toJSMultiLineString(""));
    Assert.assertEquals("[\"alert1\\n\"].join(\"\\n\")", WroUtil.toJSMultiLineString("alert1"));
    Assert.assertEquals("[\"\",\"alert1\",\"alert2\"].join(\"\\n\")", WroUtil.toJSMultiLineString("\nalert1\nalert2"));
  }


}
