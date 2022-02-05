package js.hera.hub.unit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import js.hera.hub.Service;
import js.hera.hub.model.SystemDescriptor;

/**
 * Unit tests for application controller.
 * 
 * @author Iulian Rotaru
 */
@Ignore
public class ServiceUnitTest
{
  //private static Application context;

  /** Service instance under test. */
  private Service service;

  @BeforeClass
  public static void beforeClass() throws Exception
  {
    //context = TestContext.start(ServiceUnitTest.class.getResourceAsStream("/test-config.xml"));
  }

  @Before
  public void beforeTest()
  {
    //service = context.getInstance(Service.class);
  }

  @Test
  public void getSystemDescriptor()
  {
    SystemDescriptor system = service.getSystemDescriptor();
    assertThat(system, notNullValue());
  }
}
