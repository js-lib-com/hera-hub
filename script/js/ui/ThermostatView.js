$package("js.ui");

$include("js.hera.hub.Service");

js.ui.ThermostatView = function (ownerDoc, node) {
	this.$super(ownerDoc, node);

	this._logoPanel = this.getByCss(".panel.logo");
	this._valuesPanel = this.getByCss(".panel.values");

	this._temperatureView = this._valuesPanel.getByCss(".value .temperature");
	this._setPointDialog = ownerDoc.getByCss(".thermostat.dialog");

	this.on("click", this._onClick, this);
};

js.ui.ThermostatView.prototype = {
	setDevice: function (device) {
		this._device = device;
		this._logoPanel.setObject(device);
		this._timer = js.util.Timer(2000, this._getState, this);
	},

	_getState: function () {
		Service.invokeDeviceAction(this._device.name, "getState", [], this._onStateLoaded, this);
	},

	_onStateLoaded: function (state) {
		if (state == null) {
			this.addCssClass("disabled");
			return;
		}
		this.removeCssClass("disabled");

		this._logoPanel.hide();
		this._valuesPanel.show();

		this._temperatureView.setValue(state.temperature);
		this.addCssClass("active", state.running);
	},

	_onClick: function (ev) {
		this._setPointDialog.open(this._device.name, this._onDialogClose.bind(this));
	},

	_onDialogClose: function () {
		Service.invokeDeviceAction(this._device.name, "update", [], this._onStateLoaded, this);
	},

	toString: function () {
		return "js.ui.ThermostatView";
	}
};
$extends(js.ui.ThermostatView, js.dom.Element);
