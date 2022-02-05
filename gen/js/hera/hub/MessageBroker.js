$package("js.hera.hub");

/**
 * Message broker.
 */
js.hera.hub.MessageBroker = {
	/**
	 * Bind stream.
	 *
	 * @param js.tiny.container.net.EventStream stream,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return void
	 * @assert callback is a {@link Function} and scope is an {@link Object}, if they are defined.
	 * @note since method return type is void, callback, and hence scope too, is optional.
	 */
	 bindStream: function(stream) {
		$assert(typeof stream !== "undefined", "js.hera.hub.MessageBroker#bindStream", "Stream argument is undefined.");

		var __callback__ = arguments[1];
		$assert(typeof __callback__ === "undefined" || js.lang.Types.isFunction(__callback__), "js.hera.hub.MessageBroker#bindStream", "Callback is not a function.");
		var __scope__ = arguments[2];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "js.hera.hub.MessageBroker#bindStream", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("js.hera.hub.MessageBroker", "bindStream");
		rmi.setParameters(stream);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Unbind stream.
	 *
	 * @param js.tiny.container.net.EventStream stream,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return void
	 * @assert callback is a {@link Function} and scope is an {@link Object}, if they are defined.
	 * @note since method return type is void, callback, and hence scope too, is optional.
	 */
	 unbindStream: function(stream) {
		$assert(typeof stream !== "undefined", "js.hera.hub.MessageBroker#unbindStream", "Stream argument is undefined.");

		var __callback__ = arguments[1];
		$assert(typeof __callback__ === "undefined" || js.lang.Types.isFunction(__callback__), "js.hera.hub.MessageBroker#unbindStream", "Callback is not a function.");
		var __scope__ = arguments[2];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "js.hera.hub.MessageBroker#unbindStream", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("js.hera.hub.MessageBroker", "unbindStream");
		rmi.setParameters(stream);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Publish.
	 *
	 * @param js.hera.hub.Message message,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return void
	 * @assert callback is a {@link Function} and scope is an {@link Object}, if they are defined.
	 * @note since method return type is void, callback, and hence scope too, is optional.
	 */
	 publish: function(message) {
		$assert(typeof message !== "undefined", "js.hera.hub.MessageBroker#publish", "Message argument is undefined.");

		var __callback__ = arguments[1];
		$assert(typeof __callback__ === "undefined" || js.lang.Types.isFunction(__callback__), "js.hera.hub.MessageBroker#publish", "Callback is not a function.");
		var __scope__ = arguments[2];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "js.hera.hub.MessageBroker#publish", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("js.hera.hub.MessageBroker", "publish");
		rmi.setParameters(message);
		rmi.exec(__callback__, __scope__);
	}
};

if(typeof MessageBroker === 'undefined') {
	MessageBroker = js.hera.hub.MessageBroker;
}
