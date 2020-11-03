package js.hera.hub.unit;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import js.hera.hub.Service;
import js.hera.hub.model.SystemDescriptor;
import js.tiny.container.core.AppContext;
import js.tiny.container.unit.TestContext;

/**
 * Unit tests for application controller.
 * 
 * @author Iulian Rotaru
 */
@Ignore
public class ServiceUnitTest
{
  private static AppContext context;

  /** Service instance under test. */
  private Service service;

  @BeforeClass
  public static void beforeClass() throws Exception
  {
    context = TestContext.start(ServiceUnitTest.class.getResourceAsStream("/test-config.xml"));
  }

  @Before
  public void beforeTest()
  {
    service = context.getInstance(Service.class);
  }

  @Test
  public void getSystemDescriptor()
  {
    SystemDescriptor system = service.getSystemDescriptor();
    assertThat(system, notNullValue());
  }
}
