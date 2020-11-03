package js.hera.hub.impl;

import js.hera.HostAdvertise;

/**
 * Event handler for device advertisement.
 * 
 * @author Iulian Rotaru
 */
public interface HostAdvertiseListener
{
  void onDeviceAdvertise(HostAdvertise advertise);
}