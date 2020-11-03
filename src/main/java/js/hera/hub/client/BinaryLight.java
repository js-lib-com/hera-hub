package js.hera.hub.client;

import js.hera.dev.Device;
import js.lang.Callback;

/**
 * Switch the light source on or off. Binary light has two inputs, wall push switch and action from controller via
 * network and a single output, actuator to control the light bulb. Binary light device has internal state that is
 * updated by both inputs; on any event from input internal state is toggled and actuator state updated. Controller can
 * retrieve binary light state, see {@link #getState(Callback)}. Also there are actions to force binary light state in
 * desired state: on or off.
 * 
 * <pre>
 *     WS
 * ----/ -------+     +----+             APP - Mobile App for Controller
 *              +-----+    |             WS  - Wall Push Switch 
 *                    | BL +---(X)       BL  - Binary Light Device Firmware
 *              +-----+    |             X   - Light Bulb 
 * +-----+      |     +----+
 * |     |      |
 * | APP +------+
 * |     |
 * +-----+
 * </pre>
 * 
 * @author Iulian Rotaru
 */
public interface BinaryLight extends Device
{
  /**
   * Turn light bulb on. If light bulb is already <code>on</code> this action does nothing.
   */
  void turnON();

  /**
   * Force light bulb off. If light bulb is already <code>off</code> this action does nothing.
   */
  void turnOFF();

  /**
   * Toggle current light bulb state.
   */
  void toggle();

  /**
   * Get light bulb state. Light bulb state is determined by both wall switch and controller.
   */
  void getState(Callback<Boolean> callback);
}
