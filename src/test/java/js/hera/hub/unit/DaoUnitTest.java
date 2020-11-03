package js.hera.hub.unit;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import js.hera.hub.dao.DaoImpl;
import js.hera.hub.model.User;
import js.hera.hub.model.Zone;
import js.json.Json;
import js.tiny.container.core.AppContext;
import js.util.Classes;
import js.util.Files;

/**
 * Unit tests for DAO implementation.
 * 
 * @author Iulian Rotaru
 */
@RunWith(MockitoJUnitRunner.class)
public class DaoUnitTest
{
  @Mock
  private AppContext context;

  /** DAO interface under test. */
  private DaoImpl dao;

  @Before
  public void beforeTest() throws Exception
  {
    Files.copy(file("user.src"), file("user.store"));
    Files.copy(file("zone.src"), file("zone.store"));

    when(context.getAppFile(anyString())).thenAnswer(new Answer<File>()
    {
      @Override
      public File answer(InvocationOnMock invocation) throws Throwable
      {
        return file(invocation.getArgument(0));
      }
    });

    when(context.getInstance(Json.class)).thenReturn(Classes.loadService(Json.class));

    dao = new DaoImpl(context);
    dao.postConstruct();
  }

  @Test
  public void getUser()
  {
    User user = dao.getUserById(1);
    assertThat(user, notNullValue());
    assertThat(user.getId(), equalTo(1));
    assertThat(user.getName(), equalTo("Iulian Rotaru"));
    assertThat(user.getAccount(), equalTo("developer"));
    assertThat(user.getPassword(), equalTo("q1w2e3r4"));
  }

  @Test
  public void createZone()
  {
    Zone zone = new Zone();
    zone.setName("bedroom");
    zone.setDisplay("Small Bedroom");
    dao.createZone(zone);
  }

  @Test
  public void getZones()
  {
    List<Zone> zones = dao.getZones();
    assertThat(zones, notNullValue());
    assertThat(zones.size(), equalTo(1));

    Zone zone = zones.get(0);
    assertThat(zone.getId(), equalTo(1));
    assertThat(zone.getName(), equalTo("parent_bedroom"));
    assertThat(zone.getDisplay(), equalTo("Parent Bedroom"));
  }

  private static File file(Object fileName)
  {
    return new File("fixture/store/" + fileName);
  }
}
