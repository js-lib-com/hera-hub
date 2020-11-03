$package("js.ui");

$include("js.hera.hub.Service");

js.ui.SetpointThermostatView = function(ownerDoc, node) {
	this.$super(ownerDoc, node);

	this._logoPanel = this.getByCss(".panel.logo");
	this._valuesPanel = this.getByCss(".panel.values");

	this._temperatureView = this._valuesPanel.getByCss(".value .temperature");
	this._humidityView = this._valuesPanel.getByCss(".value .humidity");

	this._setPointDialog = ownerDoc.getByCss(".thermostat.dialog");

	this.on("click", this._onClick, this);
};

js.ui.SetpointThermostatView.prototype = {
	setDevice : function(device) {
		this._device = device;
		this._logoPanel.setObject(device);
		this._timer = js.util.Timer(2000, this._onTimer, this);
	},

	_onTimer : function() {
		this._getTemperature(function(temperature) {
			if (temperature !== null) {
				this.removeCssClass("disabled");

				this._timer.stop();
				this._logoPanel.hide();
				this._valuesPanel.show();

				this._temperatureView.setValue(temperature);
			}
			else {
				this.addCssClass("disabled");
			}
		}, this);
	},

	_getTemperature : function(callback, scope) {
		Service.invokeDeviceAction(this._device.name, "getTemperature", [], callback, scope);
	},

	_onClick : function(ev) {
		this._setPointDialog.open(this._device.name);
	},

	toString : function() {
		return "js.ui.SetpointThermostatView";
	}
};
$extends(js.ui.SetpointThermostatView, js.dom.Element);
