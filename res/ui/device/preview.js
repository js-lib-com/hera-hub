WinMain.on("load", function() {
	var device = WinMain.doc.getByCssClass("device");
	device.setDevice({
		name : "binary-light",
		deviceClass : "js.hera.dev.BinaryLight",
		state : true,
		icon : "@image/icon/lights",
		display: "Parent Bedroom"
	});
});