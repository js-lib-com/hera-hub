$package("js.ui");

js.ui.ZoneView = function(ownerDoc, node) {
	this.$super(ownerDoc, node);
};

js.ui.ZoneView.prototype = {
	setObject : function(zone) {

	},

	toString : function() {
		return "js.ui.ZoneView";
	}
};
$extends(js.ui.ZoneView, js.dom.Element);
