$package("js.ui");

$include("js.hera.hub.Service");

js.ui.ColorLedTile = function(ownerDoc, node) {
	this.$super(ownerDoc, node);

	this._logoPanel = this.getByCss(".panel.logo");
	this._valuesPanel = this.getByCss(".panel.values");

	this._color = 0;
	this._colorPreview = this.getByCss(".color-preview");

	this._dialog = ownerDoc.getByCss(".color-led.dialog");
	this.on("click", this._onClick, this);
};

js.ui.ColorLedTile.prototype = {
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

				// value is the color in numeric format as integer
				this._setColorPreview(value);
			}
			else {
				this.addCssClass("disabled");
			}
		}, this);
	},

	_onClick : function(ev) {
		this._dialog.open(this._device, function(value) {
			this._setColorPreview(value);
		}, this);
	},

	_getValue : function(callback, scope) {
		Service.invokeDeviceAction(this._device.name, "getColor", [], callback, scope);
	},

	_setColorPreview : function(value) {
		// save color in its numeric value
		this._color = value;
		
		var red = (value & 0x00FF0000) >> 16;
		var green = (value & 0x0000FF00) >> 8;
		var blue = (value & 0x000000FF);

		color = $format("#%02X%02X%02X", red, green, blue);
		this._colorPreview.style.set("background-color", color);
	},

	toString : function() {
		return "js.ui.ColorLedTile";
	}
};
$extends(js.ui.ColorLedTile, js.dom.Element);
