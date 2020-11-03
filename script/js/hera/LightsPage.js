$package("js.hera");

/**
 * LightsPage class.
 * 
 * @author Iulian Rotaru
 * @since 1.0
 * 
 * @constructor Construct an instance of LightsPage class.
 */
js.hera.LightsPage = function() {
	this.$super();

	this._instances = [];
	
	this._indices = {};

	this._lightsListView = this.getByName("lights-list-view");
	this._lightsListView.on("click", this._onClick, this);

	js.hera.hub.Service.getDevicesByCategoryName("lights", this._onDevicesLoaded, this);
};

js.hera.LightsPage.prototype = {
	_onDevicesLoaded : function(devices) {
		this._devices = devices;

		for (var i = 0; i < devices.length; ++i) {
			devices[i].index = i.toString();

			var instance = new js.hera.dev.BinaryLight(devices[i].name);
			instance.onState = function(state) {
				this.ledView.addCssClass("on", state);
			}
			this._instances.push(instance);
			this._indices[devices[i].name] = i;
		}

		this._lightsListView.setObject(devices);

		this._instances.forEach(function(instance, index) {
			instance.ledView = this._lightsListView.getByIndex(index).getByName("led");
			instance.getState(instance.onState, instance);
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

	_onClick : function(ev) {
		if (ev.target.getTag() === "button") {
			var index = Number(ev.target.getAttr("data-index"));
			var instance = this._instances[index];
			instance.getState(function(state) {
				if (state) {
					instance.turnOFF();
				}
				else {
					instance.turnON();
				}
			}, this);
		}
	},

	_getLedView : function(index) {
		var lightView = this._lightsListView.getByIndex(index);
		return lightView.getByName("led");
	},

	/**
	 * Class string representation.
	 * 
	 * @return this class string representation.
	 */
	toString : function() {
		return "js.hera.LightsPage";
	}
};
$extends(js.hera.LightsPage, js.ua.Page);
