$package("js.ui");

$include("js.hera.hub.Service");

js.ui.Page = function() {
	this.$super();

	this._devicesLayout = this.getByClass(js.ui.DevicesLayout);
	Service.getSystemDescriptor(this._onDescriptorLoaded, this);
	
	var rmi = new js.net.RMI();
	rmi.setMethod("js.net.EventStreamManager", "subscribe");
	rmi.setParameters({});
	rmi.exec(this._onSubscribe, this);	
};

js.ui.Page.prototype = {
	_onDescriptorLoaded : function(descriptor) {
		function item(items, itemId) {
			for (var i = 0; i < items.length; ++i) {
				if (items[i].id === itemId) {
					return items[i];
				}
			}
		}

		// adapt system descriptor to zones layout
		var zones = descriptor.zones;
		zones.forEach(function(zone) {
			zone.devices = [];
		});
		descriptor.deviceDescriptors.forEach(function(deviceDescriptor) {
			deviceDescriptor.icon = $format("/icons/%s.png", item(descriptor.deviceCategories, deviceDescriptor.categoryId).icon);
			item(zones, deviceDescriptor.zoneId).devices.push(deviceDescriptor);
		});

		this._devicesLayout.setZones(zones);
	},

	_onSubscribe : function(sessionID) {
		this._eventStream = new EventSource(sessionID + ".event");
		// j(s)-lib event stream does not use event field so need to listen to 'message' event
		this._eventStream.addEventListener("message", this._onStateEvent.bind(this), false);
	},

	_onStateEvent : function(ev) {
		if (!ev.data) {
			// for keep alive packets event data is empty string
			return;
		}
		var event = JSON.parse(ev.data);
		var device = WinMain.doc.getByName(event.deviceName);
		device.updateState(event.value);
		console.log(device);
	},

	/**
	 * Class string representation.
	 * 
	 * @return this class string representation.
	 */
	toString : function() {
		return "js.ui.Page";
	}
};
$extends(js.ui.Page, js.ua.Page);
