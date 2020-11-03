WinMain.on("load", function() {
	var device = WinMain.doc.getByCssClass("dht-sensor");
	device.setDevice({
		name : "dht",
		deviceClass : "js.hera.dev.DHTSensor",
		state : true,
		icon : "@image/icon/lights",
		display: "Parent Bedroom"
	});
});