$package("js.ui");

$include("js.hera.hub.Service");

js.ui.DHTSensorView = function(ownerDoc, node) {
	this.$super(ownerDoc, node);
	
	this._logoPanel = this.getByCss(".panel.logo");
	this._valuesPanel = this.getByCss(".panel.values");
	
	this._temperatureView = this._valuesPanel.getByCss(".value .temperature");
	this._humidityView = this._valuesPanel.getByCss(".value .humidity");
};

js.ui.DHTSensorView.prototype = {
	setDevice : function(device) {
		this._device = device;
		this._logoPanel.setObject(device);
		this._timer = js.util.Timer(2000, this._onTimer, this);
	},

	_onTimer : function() {
		this._getValue(function(value) {
			if (value) {
				if(!value.temperature) {
					return;
				}
				if(!value.humidity) {
					return;
				}
				this.removeCssClass("disabled");
				
				this._timer.stop();
				this._logoPanel.hide();
				this._valuesPanel.show();
				
				this._temperatureView.setValue(value.temperature);
				this._humidityView.setValue(value.humidity);
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
		return "js.ui.DHTSensorView";
	}
};
$extends(js.ui.DHTSensorView, js.dom.Element);
