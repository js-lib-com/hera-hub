$package("js.hera");

$include("js.hera.hub.Service");

/**
 * ZonesPage class.
 * 
 * @author Iulian Rotaru
 * @since 1.0
 * 
 * @constructor Construct an instance of ZonesPage class.
 */
js.hera.ZonesPage = function() {
	this.$super();
	Service.getZones(this._onZones, this);

	this._doRemove = Service.deleteZone;
	this._doCreate = Service.createZone;
	this._doUpdate = Service.updateZone;
};

js.hera.ZonesPage.prototype = {
	_onZones : function(zones) {
		this._tableView.setObject(zones);
	},

	_isRemoveable : function(zone) {
		if (zone.devicesCount > 0) {
			js.ua.System.alert("Zone %s has %d devices and cannot be removed.", zone.display, zone.devicesCount);
			return false;
		}
		return true;
	},

	/**
	 * Class string representation.
	 * 
	 * @return this class string representation.
	 */
	toString : function() {
		return "js.hera.ZonesPage";
	}
};
$extends(js.hera.ZonesPage, js.hera.TablePage);
