Service = {};

Service.invokeDeviceAction = function (deviceName, method, [], callback, scope) {
	callback.call(scope, 0x004080C0);
}

WinMain.on("load", function() {
	var device = WinMain.doc.getByCssClass("color-led");
	device.setDevice({
		name : "color-led",
		deviceClass : "js.hera.dev.ColorLED",
		state : true,
		icon : "@image/icon/lights",
		display: "Color LED"
	});
});