$package("js.ui");

$include("js.hera.hub.Service");

js.ui.ColorLedDialog = function(ownerDoc, node) {
	this.$super(ownerDoc, node);

	this._panorama = ownerDoc.getByClass(js.ui.DevicesLayout);
	this._titleView = this.getByCssClass("title");
	this._switchView = this.getByCssClass("switch");
	this._colorPreview = this.getByCssClass("body");

	this._redSlider = this.getByName("red");
	this._redSlider.on("input", this._onSliderDrag, this);
	this._redSlider.on("change", this._onSliderChange, this);

	this._greenSlider = this.getByName("green");
	this._greenSlider.on("input", this._onSliderDrag, this);
	this._greenSlider.on("change", this._onSliderChange, this);

	this._blueSlider = this.getByName("blue");
	this._blueSlider.on("input", this._onSliderDrag, this);
	this._blueSlider.on("change", this._onSliderChange, this);

	this.on(this, {
		"&switch": this._onSwitchClick,
		"&close-dialog": this._onCloseDialog
	});
};

js.ui.ColorLedDialog.prototype = {
	open: function(device, callback, scope) {
		this._deviceName = device.name;
		this._callback = callback;
		this._scope = scope;

		Service.invokeDeviceAction(this._deviceName, "getState", [], function(state) {
			this._switchView.addCssClass("active", state.active);
			
			this._redSlider.setValue(state.red);
			this._greenSlider.setValue(state.green);
			this._blueSlider.setValue(state.blue);

			this._onSliderDrag();

			this._panorama.lock();
			this._titleView.setValue(device.display);
			this.show();
		}, this);
	},

	_onSliderDrag: function(ev) {
		var red = parseInt(255 * this._redSlider.getValue() / 100);
		var green = parseInt(255 * this._greenSlider.getValue() / 100);
		var blue = parseInt(255 * this._blueSlider.getValue() / 100);

		var color = $format("#%02X%02X%02X", red, green, blue);
		this._colorPreview.style.set("background-color", color);
	},

	_onSliderChange: function(ev) {
		Service.invokeDeviceAction(this._deviceName, "setColor", [this._getColor()], function() {
		}, this);
	},

	_onSwitchClick: function(ev) {
		var action = this._switchView.hasCssClass("active") ? "turnOFF" : "turnON";
		Service.invokeDeviceAction(this._deviceName, action, [], function(state) {
			this._switchView.addCssClass("active", state.active);
		}, this);
	},

	_onCloseDialog: function(ev) {
		this.hide();
		this._panorama.unlock();
		this._callback.call(this._scope, this._getColor());
	},

	_getColor: function() {
		var red = parseInt(255 * this._redSlider.getValue() / 100);
		var green = parseInt(255 * this._greenSlider.getValue() / 100);
		var blue = parseInt(255 * this._blueSlider.getValue() / 100);
		return (red << 16) + (green << 8) + blue;
	},

	toString: function() {
		return "js.ui.ColorLedDialog";
	}
};
$extends(js.ui.ColorLedDialog, js.dom.Element);
