WinMain.on("load", function() {
	var device = WinMain.doc.getByCssClass("temperature-sensor");
	device.setDevice({
		name : "thermostat-sensor",
		deviceClass : "js.hera.dev.TemperatureSensor",
		state : true,
		icon : "@image/icon/central-heating",
		display: "Thermostat Sensor"
	});
});