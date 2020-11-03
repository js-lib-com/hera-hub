$package("js.hera.dev");

/**
 * BinaryLight class.
 * 
 * @author Iulian Rotaru
 * 
 * @constructor Construct an instance of BinaryLight class.
 * @param String deviceName deviceName.
 */
js.hera.dev.BinaryLight = function(deviceName) {
	this._deviceName = deviceName;
};

js.hera.dev.BinaryLight.prototype = {
	turnON : function() {
		js.hera.hub.Service.invokeDeviceAction(this._deviceName, "turnON", [], this._callback, this);
	},

	turnOFF : function() {
		js.hera.hub.Service.invokeDeviceAction(this._deviceName, "turnOFF", [], this._callback, this);
	},

	getState : function(callback, scope) {
		js.hera.hub.Service.invokeDeviceAction(this._deviceName, "getState", [], callback, scope);
	},

	_callback : function() {
	},

	/**
	 * Class string representation.
	 * 
	 * @return this class string representation.
	 */
	toString : function() {
		return "js.hera.dev.BinaryLight";
	}
};
$extends(js.hera.dev.BinaryLight, Object);
