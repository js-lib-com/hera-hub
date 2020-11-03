$package("js.ui");

js.ui.DevicesLayout = function(ownerDoc, node) {
	this.$super(ownerDoc, node);

	this._deviceTemplate = this._ownerDoc.getByCss(".template .device");
};

js.ui.DevicesLayout.prototype = {
	_TEMPLATES : {
		"js.hera.dev.Actuator" : "js.ui.DeviceView",
		"js.hera.dev.BinaryLight" : "js.ui.DeviceView",
		"js.hera.dev.ContactSwitch" : "js.ui.DeviceView",
		"js.hera.dev.RadioSwitch" : "js.ui.DeviceView",
		"js.hera.dev.LightDimmer" : "js.ui.LightDimmerTile",
		"js.hera.dev.ColorLED" : "js.ui.ColorLedTile",
		"js.hera.dev.Thermostat" : "js.ui.ThermostatView",
		"js.hera.dev.ThermostatSensor" : "js.ui.TemperatureSensorView",
		"js.hera.dev.DHTSensor" : "js.ui.DHTSensorView",
		"js.hera.dev.PowerMeter" : "js.ui.PowerMeterView"
	},

	setZones : function(zones) {
		this.removeChildren();
		zones.forEach(function(zone) {
			this._createZoneView(zone);
		}, this);
	},

	_createZoneView : function(zone) {
		var zoneView = this._ownerDoc.createElement("div");
		zoneView.addCssClass("zone");

		var titleView = this._ownerDoc.createElement("h1");
		titleView.addCssClass("title");
		titleView.setText(zone.display);
		zoneView.addChild(titleView);

		var devicesListView = this._ownerDoc.createElement("div");
		zoneView.addChild(devicesListView);

		zone.devices.forEach(function(device) {
			var template = this._ownerDoc.getByCss(".template .device[data-class='%s']", this._TEMPLATES[device.deviceClass]);
			if(template == null) {
				return;
			}
			console.log(template)
			var deviceView = template.clone(true);
			deviceView.setDevice(device);
			devicesListView.addChild(deviceView);
		}, this);
		this.addChild(zoneView);
	},

	toString : function() {
		return "js.ui.DevicesLayout";
	}
};
$extends(js.ui.DevicesLayout, js.ui.Panorama);
