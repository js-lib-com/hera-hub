WinMain.on("load", function() {
	var device = WinMain.doc.getByCssClass("light-dimmer");
	device.setDevice({
		name : "dimmer",
		deviceClass : "js.hera.dev.LightDimmer",
		state : true,
		icon : "@image/icon/lights",
		display: "Light Dimmer"
	});
});