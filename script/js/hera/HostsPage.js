$package("js.hera");

$include("js.hera.hub.Service");

/**
 * HostsPage class.
 * 
 * @author Iulian Rotaru
 * @since 1.0
 * 
 * @constructor Construct an instance of HostsPage class.
 */
js.hera.HostsPage = function() {
	this.$super();

	this._tableView = this.getByClass(js.widget.TableView);
	this._tableView.on("selection-update", this._onSelectionUpdate, this);

	Service.getHosts(this._onHosts, this);
};

js.hera.HostsPage.prototype = {
	_onHosts : function(hosts) {
		this._tableView.setObject(hosts);
	},

	_onSelectionUpdate : function(selectedItems) {
	},

	/**
	 * Class string representation.
	 * 
	 * @return this class string representation.
	 */
	toString : function() {
		return "js.hera.HostsPage";
	}
};
$extends(js.hera.HostsPage, js.ua.Page);
