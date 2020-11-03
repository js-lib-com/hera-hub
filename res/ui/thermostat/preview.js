WinMain.on("load", function() {
	var device = WinMain.doc.getByCssClass("thermostat");
	device.setDevice({
		name : "thermostat",
		deviceClass : "js.hera.dev.ThermostatSensor",
		state : true,
		icon : "@image/icon/lights",
		display: "Parent Bedroom"
	});
});