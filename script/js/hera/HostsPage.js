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
js.hera.HostsPage = function () {
	this.$super();
	Service.getHosts(this._onHosts, this);

	this._doCreate = Service.createHost;
	this._doUpdate = Service.updateHost;
	this._doRemove = Service.deleteHost;

	this.getByName("subscribe").on("click", this._onSubscribe, this);
};

js.hera.HostsPage.prototype = {
	_onHosts: function (hosts) {
		this._tableView.setObject(hosts);
	},

	_isRemoveable: function (host) {
		if (host.devicesCount > 0) {
			js.ua.System.alert("Host %s has %d devices and cannot be removed.", host.display, host.devicesCount);
			return false;
		}
		return true;
	},

	_onSubscribe: function () {
		Service.subscribeHosts(function (hosts) {
			js.ua.System.alert("Done");
			this._onHosts(hosts);
		}, this);
	},

	/**
	 * Class string representation.
	 * 
	 * @return this class string representation.
	 */
	toString: function () {
		return "js.hera.HostsPage";
	}
};
$extends(js.hera.HostsPage, js.hera.TablePage);
