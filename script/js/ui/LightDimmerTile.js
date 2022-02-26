$package("js.ui");

$include("js.hera.hub.Service");

js.ui.LightDimmerTile = function(ownerDoc, node) {
	this.$super(ownerDoc, node);
	
	this._logoPanel = this.getByCss(".panel.logo");
	this._valuesPanel = this.getByCss(".panel.values");

	this._valueView = this.getByCss(".brightness .value");
	this._dialog = ownerDoc.getByCss(".light-dimmer.dialog");
	this.on("click", this._onClick, this);
};

js.ui.LightDimmerTile.prototype = {
	setDevice : function(device) {
		this._device = device;
		this.setObject(device);
		this._timer = js.util.Timer(2000, this._onTimer, this);
	},

	_onTimer : function() {
		this._getValue(function(value) {
			if (value !== null) {
				this.removeCssClass("disabled");

				this._timer.stop();
				this._logoPanel.hide();
				this._valuesPanel.show();

				this._valueView.setValue(parseInt(100 * value / 255));
			}
			else {
				this.addCssClass("disabled");
			}
		}, this);
	},

	_onClick : function(ev) {
		this._dialog.open(this._device, function(value) {
			this._valueView.setValue(value);
		}, this);
	},

	_getValue : function(callback, scope) {
		Service.invokeDeviceAction(this._device.name, "getValue", [], callback, scope);
	},

	toString : function() {
		return "js.ui.LightDimmerTile";
	}
};
$extends(js.ui.LightDimmerTile, js.dom.Element);
