$package("js.hera");

$include("js.hera.hub.Service");

/**
 * SliderTest class.
 * 
 * @author Iulian Rotaru
 * @since 1.0
 * 
 * @constructor Construct an instance of SliderTest class.
 */
js.hera.SliderTest = function() {
	this.$super();

	this._sliderControl = this.getByCssClass("slider");
	this._sliderControl.on("input", this._onSliderChange, this);
};

js.hera.SliderTest.prototype = {
	_onSliderChange : function(ev) {
		console.log(this._sliderControl.getValue());
		js.hera.hub.Service.invokeDeviceAction("dimmer", "setValue", [ this._sliderControl.getValue() ], function() {
		}, this);
	},

	/**
	 * Class string representation.
	 * 
	 * @return this class string representation.
	 */
	toString : function() {
		return "js.hera.SliderTest";
	}
};
$extends(js.hera.SliderTest, js.ua.Page);
