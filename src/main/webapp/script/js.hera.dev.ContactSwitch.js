$package("js.hera.dev");

/**
 * ContactSwitch class.
 * 
 * @author Iulian Rotaru
 * 
 * @constructor Construct an instance of ContactSwitch class.
 * @param String deviceName deviceName.
 */
js.hera.dev.ContactSwitch = function(deviceName) {
	this._deviceName = deviceName;
};

js.hera.dev.ContactSwitch.prototype = {
	turnON : function() {
		js.hera.hub.Service.invokeDeviceAction(this._deviceName, "turnON", [], this._callback, this);
	},

	turnOFF : function() {
		js.hera.hub.Service.invokeDeviceAction(this._deviceName, "turnOFF", [], this._callback, this);
	},

	getState : function(callback, scope) {
		js.hera.hub.Service.invokeDeviceAction(this._deviceName, "getState", [], callback, scope);
	},

	isOpened : function(callback, scope) {
		js.hera.hub.Service.invokeDeviceAction(this._deviceName, "isOpened", [], callback, scope);
	},

	_callback : function() {
	},

	/**
	 * Class string representation.
	 * 
	 * @return this class string representation.
	 */
	toString : function() {
		return "js.hera.dev.ContactSwitch";
	}
};
$extends(js.hera.dev.ContactSwitch, Object);
