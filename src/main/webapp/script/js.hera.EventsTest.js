$package("js.hera");

/**
 * EventsTest class.
 * 
 * @author Iulian Rotaru
 * 
 * @constructor Construct an instance of EventsTest class.
 */
js.hera.EventsTest = function() {
	this.$super();

	/**
	 * HEAR device events stream.
	 * 
	 * @type EventSource
	 */
	this._eventStream = null;

	this._binaryLight = null;

	this._lightsSelect = this.getByName("lights-select");
	this._lightsSelect.on("change", this._onLightsSelected, this);

	/**
	 * Binary light state.
	 * 
	 * @type Boolean
	 */
	this._state = false;

	this.getByTag("button").on("click", this._onButton, this);

	this._ledView = this.getByName("led");

	var rmi = new js.net.RMI();
	rmi.setMethod("js.net.EventStreamManager", "subscribe");
	rmi.setParameters({});
	rmi.exec(this._onSubscribe, this);

	// js.net.EventStreamManager.subscribe(null, this._onSubscribe, this);

	WinMain.on("unload", this._onUnload, this);
};

js.hera.EventsTest.prototype = {
	_onSubscribe : function(sessionID) {
		this._eventStream = new EventSource(sessionID + ".event");
		// j(s)-lib event stream does not use event field so need to listen to 'message' event
		this._eventStream.addEventListener("message", this._onStateEvent.bind(this), false);
	},

	_onBinaryLightState : function(state) {
		this._state = state;
		this._update();
	},

	_onButton : function(ev) {
		this._state = !this._state;
		if (this._state) {
			this._binaryLight.turnON();
		}
		else {
			this._binaryLight.turnOFF();
		}
		this._update();
	},

	_onStateEvent : function(ev) {
		if (!ev.data) {
			// for keep alive packets event data is empty string
			return;
		}

		var event = JSON.parse(ev.data);
		this._state = event.value;
		this._update();
	},

	_onLightsSelected : function(ev) {
		this._binaryLight = new js.hera.dev.BinaryLight(this._lightsSelect.getValue());
		this._binaryLight.getState(this._onBinaryLightState, this);
	},

	_update : function() {
		if (this._state) {
			this._ledView.setSrc("/hera/media/asset_led-red-on.png");
		}
		else {
			this._ledView.setSrc("/hera/media/asset_led-red-off.png");
		}
	},

	_onUnload : function(ev) {
		$debug("Close event stream.");
		this._eventStream.close();
	},

	/**
	 * Class string representation.
	 * 
	 * @return this class string representation.
	 */
	toString : function() {
		return "js.hera.EventsTest";
	}
};
$extends(js.hera.EventsTest, js.ua.Page);
