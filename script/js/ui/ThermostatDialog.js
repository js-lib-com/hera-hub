$package("js.ui");

$include("js.hera.hub.Service");

js.ui.ThermostatDialog = function(ownerDoc, node) {
	this.$super(ownerDoc, node);

	this._panorama = ownerDoc.getByClass(js.ui.DevicesLayout);

	this._stateView = this.getByCss(".state");
	this._temperatureView = this.getByCss(".value .temperature");
	this._setpointView = this.getByCss(".value .setpoint");

	this.on(this, {
		"&close-dialog": this._onCloseDialog,
		"&temperature-up": this._onTemperatureUp,
		"&temperature-down": this._onTemperatureDown
	});
};

js.ui.ThermostatDialog.prototype = {
	open: function(deviceName, callback) {
		this._deviceName = deviceName;
		this._callback = callback;
		this._panorama.lock();

		Service.invokeDeviceAction(this._deviceName, "getState", [], function(state) {
			this._temperatureView.setValue(state.temperature);
			this._setpoint = state.setpoint;
			this._setpointView.setValue(state.setpoint);
			this._stateView.addCssClass("running", state.running);
		}, this);

		this.show();
	},

	_onCloseDialog: function(ev) {
		this.hide();
		this._panorama.unlock();
		this._callback();
	},

	_onTemperatureUp: function(ev) {
		this._setpoint += 0.5;
		this._setSetpoint();
	},

	_onTemperatureDown: function(ev) {
		this._setpoint -= 0.5;
		this._setSetpoint();
	},

	_setSetpoint: function() {
		Service.invokeDeviceAction(this._deviceName, "setSetpoint", [this._setpoint], function(setpoint) {
			this._setpoint = setpoint;
			this._setpointView.setValue(setpoint);
		}, this);
	},

	toString: function() {
		return "js.ui.ThermostatDialog";
	}
};
$extends(js.ui.ThermostatDialog, js.dom.Element);
