$package("js.ui");

$include("js.hera.hub.Service");

js.ui.ThermostatDialog = function(ownerDoc, node) {
	this.$super(ownerDoc, node);

	this._panorama = ownerDoc.getByClass(js.ui.DevicesLayout);
	
	this._temperatureView = this.getByCss(".value .temperature");
	this._setpointView = this.getByCss(".value .setpoint");

	this.on(this, {
		"&close-dialog" : this._onCloseDialog,
		"&temperature-up" : this._onTemperatureUp,
		"&temperature-down" : this._onTemperatureDown
	});
};

js.ui.ThermostatDialog.prototype = {
	open : function(deviceName, callback) {
		this._deviceName = deviceName;
		this._callback = callback;
		this._panorama.lock();

		this._updateTemperature();
		this._updateSetpoint();

		this.show();
	},

	_onCloseDialog : function(ev) {
		this.hide();
		this._panorama.unlock();
		this._callback();
	},

	_onTemperatureUp : function(ev) {
		this._setpoint += 0.5;
		this._setSetpoint();
	},

	_onTemperatureDown : function(ev) {
		this._setpoint -= 0.5;
		this._setSetpoint();
	},

	_updateTemperature : function() {
		Service.invokeDeviceAction(this._deviceName, "getTemperature", [], function(temperature) {
			this._temperatureView.setValue(temperature);
		}, this);
	},

	_updateSetpoint : function() {
		Service.invokeDeviceAction(this._deviceName, "getSetpoint", [], function(setpoint) {
			this._setpoint = setpoint;
			this._setpointView.setValue(setpoint);
		}, this);
	},

	_setSetpoint : function() {
		Service.invokeDeviceAction(this._deviceName, "setSetpoint", [ this._setpoint ], function() {
			this._updateSetpoint();
		}, this);
	},

	toString : function() {
		return "js.ui.ThermostatDialog";
	}
};
$extends(js.ui.ThermostatDialog, js.dom.Element);
