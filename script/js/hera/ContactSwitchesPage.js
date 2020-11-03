$package("js.hera");

/**
 * ContactSwitchesPage class.
 * 
 * @author Iulian Rotaru
 * @since 1.0
 * 
 * @constructor Construct an instance of ContactSwitchesPage class.
 */
js.hera.ContactSwitchesPage = function() {
	this.$super();

	this._instances = [];
	
	this._indices = {};

	this._contactSwitchesListView = this.getByName("contact-switches-list-view");
	this._contactSwitchesListView.on("click", this._onClick, this);

	js.hera.hub.Service.getDevicesByCategoryName("doors_windows", this._onDevicesLoaded, this);
};

js.hera.ContactSwitchesPage.prototype = {
	_onDevicesLoaded : function(devices) {
		for (var i = 0; i < devices.length; ++i) {
			devices[i].index = i.toString();

			var instance = new js.hera.dev.ContactSwitch(devices[i].name);
			instance.onState = function(state) {
				this.ledView.addCssClass("on", state);
			}
			this._instances.push(instance);
			this._indices[devices[i].name] = i;
		}

		this._contactSwitchesListView.setObject(devices);

		this._instances.forEach(function(instance, index) {
			instance.ledView = this._contactSwitchesListView.getByIndex(index).getByName("led");
			instance.isOpened(instance.onState, instance);
		}, this);

		this._eventStream = new EventSource("message.event");
		this._eventStream.addEventListener("DeviceState", this._onStateEvent.bind(this), false);
	},

	_onStateEvent : function(ev) {
		if (!ev.data) {
			// for keep alive packets event data is empty string
			return;
		}
		var event = JSON.parse(ev.data);
		var instance = this._instances[this._indices[event.deviceName]];
		instance.ledView.addCssClass("on", event.value);
	},

	_getLedView : function(index) {
		var contactSwitchView = this._contactSwitchesListView.getByIndex(index);
		return contactSwitchView.getByName("led");
	},

	/**
	 * Class string representation.
	 * 
	 * @return this class string representation.
	 */
	toString : function() {
		return "js.hera.ContactSwitchesPage";
	}
};
$extends(js.hera.ContactSwitchesPage, js.ua.Page);
