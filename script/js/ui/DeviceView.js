$package("js.ui");

$include("js.hera.hub.Service");

js.ui.DeviceView = function(ownerDoc, node) {
	this.$super(ownerDoc, node);
};

js.ui.DeviceView.prototype = {
	setDevice : function(device) {
		this._device = device;
		this.setObject(device);

		switch (device.deviceClass) {
		case "js.hera.dev.BinaryLight":
		case "js.hera.dev.Actuator":
			this._getState(function(state) {
				if (state === null) {
					this.addCssClass("disabled")
				}
				else {
					this.removeCssClass("disabled")
					this.addCssClass("active", state);
				}
			}, this);
			this.on("click", this._onClick, this);
			break;

		default:
			break;
		}
	},

	updateState : function(state) {
		if (state === null) {
			this.addCssClass("disabled")
		}
		else {
			this.removeCssClass("disabled")
			this.addCssClass("active", state);
		}
		this.removeCssClass("pending");
	},

	_onClick : function(ev) {
		navigator.vibrate(100);
		this.addCssClass("pending");
		this._getState(function(state) {
			if (state) {
				this._turnOFF();
				// this.removeCssClass("active");
			}
			else {
				this._turnON();
				// this.addCssClass("active");
			}
		}, this);
	},

	_getState : function(callback, scope) {
		Service.invokeDeviceAction(this._device.name, "getState", [], callback, scope);
	},

	_turnON : function() {
		Service.invokeDeviceAction(this._device.name, "turnON", [], this._callback, this);
	},

	_turnOFF : function() {
		Service.invokeDeviceAction(this._device.name, "turnOFF", [], this._callback, this);
	},

	_callback : function() {
	},

	toString : function() {
		return "js.ui.DeviceView";
	}
};
$extends(js.ui.DeviceView, js.dom.Element);
