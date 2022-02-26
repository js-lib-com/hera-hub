$package("js.ui");

$include("js.hera.hub.Service");

js.ui.LightDimmerDialog = function(ownerDoc, node) {
	this.$super(ownerDoc, node);

	this._panorama = ownerDoc.getByClass(js.ui.DevicesLayout);
	this._titleView = this.getByCssClass("title");
	this._switchView = this.getByCssClass("switch");
	this._brightnessValue = this.getByCssClass("brightness");

	this._sliderControl = this.getByCssClass("slider");
	this._sliderControl.on("input", this._onSliderDrag, this);
	this._sliderControl.on("change", this._onSliderChange, this);

	this.on(this, {
		"&switch": this._onSwitchClick,
		"&close-dialog": this._onCloseDialog
	});
};

js.ui.LightDimmerDialog.prototype = {
	open: function(device, callback, scope) {
		this._deviceName = device.name;
		this._callback = callback;
		this._scope = scope;

		Service.invokeDeviceAction(this._deviceName, "getState", [], function(state) {
			this._switchView.addCssClass("active", state.active);

			this._sliderControl.setValue(parseInt(100 * state.value / 255));
			this._onSliderDrag();

			this._panorama.lock();
			this._titleView.setValue(device.display);
			this.show();
		}, this);
	},

	_onSliderDrag: function(ev) {
		this._brightnessValue.setValue(this._sliderControl.getValue());
	},

	_onSliderChange: function(ev) {
		var value = parseInt(255 * this._sliderControl.getValue() / 100);
		Service.invokeDeviceAction(this._deviceName, "setValue", [value], function() {
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
		this._callback.call(this._scope, this._sliderControl.getValue());
	},

	toString: function() {
		return "js.ui.LightDimmerDialog";
	}
};
$extends(js.ui.LightDimmerDialog, js.dom.Element);
