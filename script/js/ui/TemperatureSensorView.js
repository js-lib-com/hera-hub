$package("js.ui");

$include("js.hera.hub.Service");

js.ui.TemperatureSensorView = function(ownerDoc, node) {
	this.$super(ownerDoc, node);
	
	this._logoPanel = this.getByCss(".panel.logo");
	this._valuesPanel = this.getByCss(".panel.values");
	
	this._temperatureView = this._valuesPanel.getByCss(".value .temperature");
};

js.ui.TemperatureSensorView.prototype = {
	setDevice : function(device) {
		this._device = device;
		this._logoPanel.setObject(device);
		this._timer = js.util.Timer(2000, this._onTimer, this);
	},

	_onTimer : function() {
		this._getValue(function(value) {
			if (value) {
				this.removeCssClass("disabled");
				
				this._timer.stop();
				this._logoPanel.hide();
				this._valuesPanel.show();
				
				this._temperatureView.setValue(value);
			}
			else {
				this.addCssClass("disabled");
			}
		}, this);
	},

	_getValue : function(callback, scope) {
		Service.invokeDeviceAction(this._device.name, "getValue", [], callback, scope);
	},

	toString : function() {
		return "js.ui.TemperatureSensorView";
	}
};
$extends(js.ui.TemperatureSensorView, js.dom.Element);
