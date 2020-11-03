$package("js.net");

/**
 * Event stream manager.
 */
js.net.EventStreamManager = {
	/**
	 * Subscribe.
	 *
	 * @param java.lang.Object config,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return void
	 * @assert callback is a {@link Function} and scope is an {@link Object}, if they are defined.
	 * @note since method return type is void, callback, and hence scope too, is optional.
	 */
	 subscribe: function(config) {
		$assert(typeof config !== "undefined", "js.net.EventStreamManager#subscribe", "Config argument is undefined.");

		var __callback__ = arguments[1];
		$assert(typeof __callback__ === "undefined" || js.lang.Types.isFunction(__callback__), "js.net.EventStreamManager#subscribe", "Callback is not a function.");
		var __scope__ = arguments[2];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "js.net.EventStreamManager#subscribe", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("js.net.EventStreamManager", "subscribe");
		rmi.setParameters(config);
		rmi.exec(__callback__, __scope__);
	}
};
