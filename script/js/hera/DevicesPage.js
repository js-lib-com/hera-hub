$package("js.hera");

$include("js.hera.hub.Service");

/**
 * DevicesPage class.
 * 
 * @author Iulian Rotaru
 * @since 1.0
 * 
 * @constructor Construct an instance of DevicesPage class.
 */
js.hera.DevicesPage = function() {
	this.$super();
	Service.getDevices(this._onDevices, this);

	this._doRemove = Service.deleteDevice;
	this._doCreate = Service.createDevice;
	this._doUpdate = Service.updateDevice;
};

js.hera.DevicesPage.prototype = {
	_onDevices : function(devices) {
		this._tableView.setObject(devices);
	},

	/**
	 * Class string representation.
	 * 
	 * @return this class string representation.
	 */
	toString : function() {
		return "js.hera.DevicesPage";
	}
};
$extends(js.hera.DevicesPage, js.hera.TablePage);
