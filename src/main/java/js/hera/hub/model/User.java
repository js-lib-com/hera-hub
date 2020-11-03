package js.hera.hub.model;

/**
 * Immutable application user.
 * 
 * @author Iulian Rotaru
 * @since 1.0
 */
public class User
{
  /** User database ID. */
  private int id;

  /** User full name. */
  private String name;

  /** Account used to authenticate user on application. */
  private String account;

  /** Account password. */
  private String password;

  /**
   * Get user database ID.
   * 
   * @return user database ID.
   * @see #id
   */
  public int getId()
  {
    return id;
  }

  /**
   * Get user name.
   * 
   * @return user name.
   * @see #name
   */
  public String getName()
  {
    return name;
  }

  /**
   * Get user account name.
   * 
   * @return user account name.
   * @see #account
   */
  public String getAccount()
  {
    return account;
  }

  /**
   * Get user password.
   * 
   * @return user password.
   * @see #password
   */
  public String getPassword()
  {
    return password;
  }
}
