WinMain.on("load", function() {
	var dialog = WinMain.doc.getByCss(".light-dimmer.dialog");
	dialog.open({
		name : "dimmer",
		deviceClass : "js.hera.dev.LightDimmer",
		state : true,
		icon : "@image/icon/lights",
		display: "Light Dimmer"
	});
});