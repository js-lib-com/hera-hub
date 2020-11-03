WinMain.on("load", function() {
	var dialog = WinMain.doc.getByCss(".color-led.dialog");
	dialog.open({
		name : "color-led",
		deviceClass : "js.hera.dev.ColorLED",
		state : true,
		icon : "@image/icon/lights",
		display: "Color LEDr"
	});
});