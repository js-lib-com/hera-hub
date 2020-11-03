$package("js.ui");

$include("js.hera.hub.Service");

js.ui.PowerMeterView = function(ownerDoc, node) {
	this.$super(ownerDoc, node);
	this._energyView = this.getByCss(".value .energy");
	this._powerView = this.getByCss(".value .power");
};

js.ui.PowerMeterView.prototype = {
	setDevice : function(device) {
		Service.getPowerMeterValue(function(value) {
			this._energyView.setValue(value.energy);
			this._powerView.setValue(value.power);
		}, this);
	},

	toString : function() {
		return "js.ui.PowerMeterView";
	}
};
$extends(js.ui.PowerMeterView, js.dom.Element);
