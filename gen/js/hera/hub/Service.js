$package("js.hera.hub");

/**
 * Service.
 */
js.hera.hub.Service = {
	/**
	 * Get system descriptor.
	 *
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return js.hera.hub.model.SystemDescriptor
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 getSystemDescriptor: function() {
		var __callback__ = arguments[0];
		$assert(js.lang.Types.isFunction(__callback__), "js.hera.hub.Service#getSystemDescriptor", "Callback is not a function.");
		var __scope__ = arguments[1];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "js.hera.hub.Service#getSystemDescriptor", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("js.hera.hub.Service", "getSystemDescriptor");
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Invoke device action.
	 *
	 * @param java.lang.String deviceName,
	 * @param java.lang.String actionName,
	 * @param java.lang.Object[] args,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return java.lang.Object
	 * @throws java.lang.Exception
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 invokeDeviceAction: function(deviceName, actionName, args) {
		$assert(typeof deviceName !== "undefined", "js.hera.hub.Service#invokeDeviceAction", "Device name argument is undefined.");
		$assert(deviceName === null || js.lang.Types.isString(deviceName), "js.hera.hub.Service#invokeDeviceAction", "Device name argument is not a string.");
		$assert(typeof actionName !== "undefined", "js.hera.hub.Service#invokeDeviceAction", "Action name argument is undefined.");
		$assert(actionName === null || js.lang.Types.isString(actionName), "js.hera.hub.Service#invokeDeviceAction", "Action name argument is not a string.");
		$assert(typeof args !== "undefined", "js.hera.hub.Service#invokeDeviceAction", "Args argument is undefined.");
		$assert(args === null || js.lang.Types.isArray(args), "js.hera.hub.Service#invokeDeviceAction", "Args argument is not an array.");

		var __callback__ = arguments[3];
		$assert(js.lang.Types.isFunction(__callback__), "js.hera.hub.Service#invokeDeviceAction", "Callback is not a function.");
		var __scope__ = arguments[4];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "js.hera.hub.Service#invokeDeviceAction", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("js.hera.hub.Service", "invokeDeviceAction");
		rmi.setParameters(deviceName, actionName, args);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Invoke.
	 *
	 * @param java.lang.String[] args,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return java.lang.Object
	 * @throws java.lang.Exception
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 invoke: function(args) {
		$assert(typeof args !== "undefined", "js.hera.hub.Service#invoke", "Args argument is undefined.");
		$assert(args === null || js.lang.Types.isArray(args), "js.hera.hub.Service#invoke", "Args argument is not an array.");

		var __callback__ = arguments[1];
		$assert(js.lang.Types.isFunction(__callback__), "js.hera.hub.Service#invoke", "Callback is not a function.");
		var __scope__ = arguments[2];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "js.hera.hub.Service#invoke", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("js.hera.hub.Service", "invoke");
		rmi.setParameters(args);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Get devices by zone.
	 *
	 * @param int zoneId,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return java.util.List<js.hera.hub.model.DeviceDescriptor>
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 getDevicesByZone: function(zoneId) {
		$assert(typeof zoneId !== "undefined", "js.hera.hub.Service#getDevicesByZone", "Zone id argument is undefined.");
		$assert(js.lang.Types.isNumber(zoneId), "js.hera.hub.Service#getDevicesByZone", "Zone id argument is not a number.");

		var __callback__ = arguments[1];
		$assert(js.lang.Types.isFunction(__callback__), "js.hera.hub.Service#getDevicesByZone", "Callback is not a function.");
		var __scope__ = arguments[2];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "js.hera.hub.Service#getDevicesByZone", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("js.hera.hub.Service", "getDevicesByZone");
		rmi.setParameters(zoneId);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Get devices by category.
	 *
	 * @param int categoryId,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return java.util.List<js.hera.hub.model.DeviceDescriptor>
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 getDevicesByCategory: function(categoryId) {
		$assert(typeof categoryId !== "undefined", "js.hera.hub.Service#getDevicesByCategory", "Category id argument is undefined.");
		$assert(js.lang.Types.isNumber(categoryId), "js.hera.hub.Service#getDevicesByCategory", "Category id argument is not a number.");

		var __callback__ = arguments[1];
		$assert(js.lang.Types.isFunction(__callback__), "js.hera.hub.Service#getDevicesByCategory", "Callback is not a function.");
		var __scope__ = arguments[2];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "js.hera.hub.Service#getDevicesByCategory", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("js.hera.hub.Service", "getDevicesByCategory");
		rmi.setParameters(categoryId);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Create zone.
	 *
	 * @param js.hera.hub.model.Zone zone,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return js.hera.hub.model.Zone
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 createZone: function(zone) {
		$assert(typeof zone !== "undefined", "js.hera.hub.Service#createZone", "Zone argument is undefined.");

		var __callback__ = arguments[1];
		$assert(js.lang.Types.isFunction(__callback__), "js.hera.hub.Service#createZone", "Callback is not a function.");
		var __scope__ = arguments[2];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "js.hera.hub.Service#createZone", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("js.hera.hub.Service", "createZone");
		rmi.setParameters(zone);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Create category.
	 *
	 * @param js.hera.hub.model.DeviceCategory category,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return js.hera.hub.model.DeviceCategory
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 createCategory: function(category) {
		$assert(typeof category !== "undefined", "js.hera.hub.Service#createCategory", "Category argument is undefined.");

		var __callback__ = arguments[1];
		$assert(js.lang.Types.isFunction(__callback__), "js.hera.hub.Service#createCategory", "Callback is not a function.");
		var __scope__ = arguments[2];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "js.hera.hub.Service#createCategory", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("js.hera.hub.Service", "createCategory");
		rmi.setParameters(category);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Read zone.
	 *
	 * @param int zoneId,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return js.hera.hub.model.Zone
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 readZone: function(zoneId) {
		$assert(typeof zoneId !== "undefined", "js.hera.hub.Service#readZone", "Zone id argument is undefined.");
		$assert(js.lang.Types.isNumber(zoneId), "js.hera.hub.Service#readZone", "Zone id argument is not a number.");

		var __callback__ = arguments[1];
		$assert(js.lang.Types.isFunction(__callback__), "js.hera.hub.Service#readZone", "Callback is not a function.");
		var __scope__ = arguments[2];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "js.hera.hub.Service#readZone", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("js.hera.hub.Service", "readZone");
		rmi.setParameters(zoneId);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Update zone.
	 *
	 * @param js.hera.hub.model.Zone zone,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return js.hera.hub.model.Zone
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 updateZone: function(zone) {
		$assert(typeof zone !== "undefined", "js.hera.hub.Service#updateZone", "Zone argument is undefined.");

		var __callback__ = arguments[1];
		$assert(js.lang.Types.isFunction(__callback__), "js.hera.hub.Service#updateZone", "Callback is not a function.");
		var __scope__ = arguments[2];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "js.hera.hub.Service#updateZone", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("js.hera.hub.Service", "updateZone");
		rmi.setParameters(zone);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Create host.
	 *
	 * @param js.hera.hub.model.Host host,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return js.hera.hub.model.Host
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 createHost: function(host) {
		$assert(typeof host !== "undefined", "js.hera.hub.Service#createHost", "Host argument is undefined.");

		var __callback__ = arguments[1];
		$assert(js.lang.Types.isFunction(__callback__), "js.hera.hub.Service#createHost", "Callback is not a function.");
		var __scope__ = arguments[2];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "js.hera.hub.Service#createHost", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("js.hera.hub.Service", "createHost");
		rmi.setParameters(host);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Update host.
	 *
	 * @param js.hera.hub.model.Host host,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return js.hera.hub.model.Host
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 updateHost: function(host) {
		$assert(typeof host !== "undefined", "js.hera.hub.Service#updateHost", "Host argument is undefined.");

		var __callback__ = arguments[1];
		$assert(js.lang.Types.isFunction(__callback__), "js.hera.hub.Service#updateHost", "Callback is not a function.");
		var __scope__ = arguments[2];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "js.hera.hub.Service#updateHost", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("js.hera.hub.Service", "updateHost");
		rmi.setParameters(host);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Delete host.
	 *
	 * @param int hostId,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return void
	 * @assert callback is a {@link Function} and scope is an {@link Object}, if they are defined.
	 * @note since method return type is void, callback, and hence scope too, is optional.
	 */
	 deleteHost: function(hostId) {
		$assert(typeof hostId !== "undefined", "js.hera.hub.Service#deleteHost", "Host id argument is undefined.");
		$assert(js.lang.Types.isNumber(hostId), "js.hera.hub.Service#deleteHost", "Host id argument is not a number.");

		var __callback__ = arguments[1];
		$assert(typeof __callback__ === "undefined" || js.lang.Types.isFunction(__callback__), "js.hera.hub.Service#deleteHost", "Callback is not a function.");
		var __scope__ = arguments[2];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "js.hera.hub.Service#deleteHost", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("js.hera.hub.Service", "deleteHost");
		rmi.setParameters(hostId);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Subscribe hosts.
	 *
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return java.util.List<js.hera.hub.model.Host>
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 subscribeHosts: function() {
		var __callback__ = arguments[0];
		$assert(js.lang.Types.isFunction(__callback__), "js.hera.hub.Service#subscribeHosts", "Callback is not a function.");
		var __scope__ = arguments[1];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "js.hera.hub.Service#subscribeHosts", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("js.hera.hub.Service", "subscribeHosts");
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Update category.
	 *
	 * @param js.hera.hub.model.DeviceCategory category,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return js.hera.hub.model.DeviceCategory
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 updateCategory: function(category) {
		$assert(typeof category !== "undefined", "js.hera.hub.Service#updateCategory", "Category argument is undefined.");

		var __callback__ = arguments[1];
		$assert(js.lang.Types.isFunction(__callback__), "js.hera.hub.Service#updateCategory", "Callback is not a function.");
		var __scope__ = arguments[2];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "js.hera.hub.Service#updateCategory", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("js.hera.hub.Service", "updateCategory");
		rmi.setParameters(category);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Delete zone.
	 *
	 * @param int zoneId,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return void
	 * @assert callback is a {@link Function} and scope is an {@link Object}, if they are defined.
	 * @note since method return type is void, callback, and hence scope too, is optional.
	 */
	 deleteZone: function(zoneId) {
		$assert(typeof zoneId !== "undefined", "js.hera.hub.Service#deleteZone", "Zone id argument is undefined.");
		$assert(js.lang.Types.isNumber(zoneId), "js.hera.hub.Service#deleteZone", "Zone id argument is not a number.");

		var __callback__ = arguments[1];
		$assert(typeof __callback__ === "undefined" || js.lang.Types.isFunction(__callback__), "js.hera.hub.Service#deleteZone", "Callback is not a function.");
		var __scope__ = arguments[2];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "js.hera.hub.Service#deleteZone", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("js.hera.hub.Service", "deleteZone");
		rmi.setParameters(zoneId);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Create device.
	 *
	 * @param js.hera.hub.model.DeviceDescriptor device,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return js.hera.hub.model.DeviceDTO
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 createDevice: function(device) {
		$assert(typeof device !== "undefined", "js.hera.hub.Service#createDevice", "Device argument is undefined.");

		var __callback__ = arguments[1];
		$assert(js.lang.Types.isFunction(__callback__), "js.hera.hub.Service#createDevice", "Callback is not a function.");
		var __scope__ = arguments[2];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "js.hera.hub.Service#createDevice", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("js.hera.hub.Service", "createDevice");
		rmi.setParameters(device);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Read device.
	 *
	 * @param int deviceId,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return js.hera.hub.model.DeviceDescriptor
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 readDevice: function(deviceId) {
		$assert(typeof deviceId !== "undefined", "js.hera.hub.Service#readDevice", "Device id argument is undefined.");
		$assert(js.lang.Types.isNumber(deviceId), "js.hera.hub.Service#readDevice", "Device id argument is not a number.");

		var __callback__ = arguments[1];
		$assert(js.lang.Types.isFunction(__callback__), "js.hera.hub.Service#readDevice", "Callback is not a function.");
		var __scope__ = arguments[2];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "js.hera.hub.Service#readDevice", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("js.hera.hub.Service", "readDevice");
		rmi.setParameters(deviceId);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Update device.
	 *
	 * @param js.hera.hub.model.DeviceDescriptor device,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return js.hera.hub.model.DeviceDTO
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 updateDevice: function(device) {
		$assert(typeof device !== "undefined", "js.hera.hub.Service#updateDevice", "Device argument is undefined.");

		var __callback__ = arguments[1];
		$assert(js.lang.Types.isFunction(__callback__), "js.hera.hub.Service#updateDevice", "Callback is not a function.");
		var __scope__ = arguments[2];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "js.hera.hub.Service#updateDevice", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("js.hera.hub.Service", "updateDevice");
		rmi.setParameters(device);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Delete device.
	 *
	 * @param int deviceId,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return void
	 * @assert callback is a {@link Function} and scope is an {@link Object}, if they are defined.
	 * @note since method return type is void, callback, and hence scope too, is optional.
	 */
	 deleteDevice: function(deviceId) {
		$assert(typeof deviceId !== "undefined", "js.hera.hub.Service#deleteDevice", "Device id argument is undefined.");
		$assert(js.lang.Types.isNumber(deviceId), "js.hera.hub.Service#deleteDevice", "Device id argument is not a number.");

		var __callback__ = arguments[1];
		$assert(typeof __callback__ === "undefined" || js.lang.Types.isFunction(__callback__), "js.hera.hub.Service#deleteDevice", "Callback is not a function.");
		var __scope__ = arguments[2];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "js.hera.hub.Service#deleteDevice", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("js.hera.hub.Service", "deleteDevice");
		rmi.setParameters(deviceId);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Get zones.
	 *
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return java.util.List<js.hera.hub.model.Zone>
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 getZones: function() {
		var __callback__ = arguments[0];
		$assert(js.lang.Types.isFunction(__callback__), "js.hera.hub.Service#getZones", "Callback is not a function.");
		var __scope__ = arguments[1];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "js.hera.hub.Service#getZones", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("js.hera.hub.Service", "getZones");
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Get categories.
	 *
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return java.util.List<js.hera.hub.model.DeviceCategory>
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 getCategories: function() {
		var __callback__ = arguments[0];
		$assert(js.lang.Types.isFunction(__callback__), "js.hera.hub.Service#getCategories", "Callback is not a function.");
		var __scope__ = arguments[1];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "js.hera.hub.Service#getCategories", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("js.hera.hub.Service", "getCategories");
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Get devices.
	 *
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return java.util.List<js.hera.hub.model.DeviceDTO>
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 getDevices: function() {
		var __callback__ = arguments[0];
		$assert(js.lang.Types.isFunction(__callback__), "js.hera.hub.Service#getDevices", "Callback is not a function.");
		var __scope__ = arguments[1];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "js.hera.hub.Service#getDevices", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("js.hera.hub.Service", "getDevices");
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Get zone items.
	 *
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return java.util.List<js.hera.hub.model.SelectItem>
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 getZoneItems: function() {
		var __callback__ = arguments[0];
		$assert(js.lang.Types.isFunction(__callback__), "js.hera.hub.Service#getZoneItems", "Callback is not a function.");
		var __scope__ = arguments[1];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "js.hera.hub.Service#getZoneItems", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("js.hera.hub.Service", "getZoneItems");
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Get category items.
	 *
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return java.util.List<js.hera.hub.model.SelectItem>
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 getCategoryItems: function() {
		var __callback__ = arguments[0];
		$assert(js.lang.Types.isFunction(__callback__), "js.hera.hub.Service#getCategoryItems", "Callback is not a function.");
		var __scope__ = arguments[1];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "js.hera.hub.Service#getCategoryItems", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("js.hera.hub.Service", "getCategoryItems");
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Get hostname items.
	 *
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return java.util.List<js.hera.hub.model.SelectItem>
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 getHostnameItems: function() {
		var __callback__ = arguments[0];
		$assert(js.lang.Types.isFunction(__callback__), "js.hera.hub.Service#getHostnameItems", "Callback is not a function.");
		var __scope__ = arguments[1];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "js.hera.hub.Service#getHostnameItems", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("js.hera.hub.Service", "getHostnameItems");
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Get device classes.
	 *
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return java.util.List<java.lang.Class<? extends js.hera.dev.Device>>
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 getDeviceClasses: function() {
		var __callback__ = arguments[0];
		$assert(js.lang.Types.isFunction(__callback__), "js.hera.hub.Service#getDeviceClasses", "Callback is not a function.");
		var __scope__ = arguments[1];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "js.hera.hub.Service#getDeviceClasses", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("js.hera.hub.Service", "getDeviceClasses");
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Get devices by category name.
	 *
	 * @param java.lang.String categoryName,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return java.util.List<js.hera.hub.model.DeviceDescriptor>
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 getDevicesByCategoryName: function(categoryName) {
		$assert(typeof categoryName !== "undefined", "js.hera.hub.Service#getDevicesByCategoryName", "Category name argument is undefined.");
		$assert(categoryName === null || js.lang.Types.isString(categoryName), "js.hera.hub.Service#getDevicesByCategoryName", "Category name argument is not a string.");

		var __callback__ = arguments[1];
		$assert(js.lang.Types.isFunction(__callback__), "js.hera.hub.Service#getDevicesByCategoryName", "Callback is not a function.");
		var __scope__ = arguments[2];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "js.hera.hub.Service#getDevicesByCategoryName", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("js.hera.hub.Service", "getDevicesByCategoryName");
		rmi.setParameters(categoryName);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Delete category.
	 *
	 * @param int categoryId,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return void
	 * @assert callback is a {@link Function} and scope is an {@link Object}, if they are defined.
	 * @note since method return type is void, callback, and hence scope too, is optional.
	 */
	 deleteCategory: function(categoryId) {
		$assert(typeof categoryId !== "undefined", "js.hera.hub.Service#deleteCategory", "Category id argument is undefined.");
		$assert(js.lang.Types.isNumber(categoryId), "js.hera.hub.Service#deleteCategory", "Category id argument is not a number.");

		var __callback__ = arguments[1];
		$assert(typeof __callback__ === "undefined" || js.lang.Types.isFunction(__callback__), "js.hera.hub.Service#deleteCategory", "Callback is not a function.");
		var __scope__ = arguments[2];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "js.hera.hub.Service#deleteCategory", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("js.hera.hub.Service", "deleteCategory");
		rmi.setParameters(categoryId);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Get hosts.
	 *
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return java.util.List<js.hera.hub.model.Host>
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 getHosts: function() {
		var __callback__ = arguments[0];
		$assert(js.lang.Types.isFunction(__callback__), "js.hera.hub.Service#getHosts", "Callback is not a function.");
		var __scope__ = arguments[1];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "js.hera.hub.Service#getHosts", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("js.hera.hub.Service", "getHosts");
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Get icons.
	 *
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return java.util.List<js.hera.hub.model.Icon>
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 getIcons: function() {
		var __callback__ = arguments[0];
		$assert(js.lang.Types.isFunction(__callback__), "js.hera.hub.Service#getIcons", "Callback is not a function.");
		var __scope__ = arguments[1];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "js.hera.hub.Service#getIcons", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("js.hera.hub.Service", "getIcons");
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Upload icon.
	 *
	 * @param js.tiny.container.http.form.UploadedFile icon,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return void
	 * @throws java.io.IOException
	 * @assert callback is a {@link Function} and scope is an {@link Object}, if they are defined.
	 * @note since method return type is void, callback, and hence scope too, is optional.
	 */
	 uploadIcon: function(icon) {
		$assert(typeof icon !== "undefined", "js.hera.hub.Service#uploadIcon", "Icon argument is undefined.");

		var __callback__ = arguments[1];
		$assert(typeof __callback__ === "undefined" || js.lang.Types.isFunction(__callback__), "js.hera.hub.Service#uploadIcon", "Callback is not a function.");
		var __scope__ = arguments[2];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "js.hera.hub.Service#uploadIcon", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("js.hera.hub.Service", "uploadIcon");
		rmi.setParameters(icon);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Get icon names.
	 *
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return java.util.List<java.lang.String>
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 getIconNames: function() {
		var __callback__ = arguments[0];
		$assert(js.lang.Types.isFunction(__callback__), "js.hera.hub.Service#getIconNames", "Callback is not a function.");
		var __scope__ = arguments[1];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "js.hera.hub.Service#getIconNames", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("js.hera.hub.Service", "getIconNames");
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Update icon name.
	 *
	 * @param java.lang.String name,
	 * @param java.lang.String newName,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return void
	 * @assert callback is a {@link Function} and scope is an {@link Object}, if they are defined.
	 * @note since method return type is void, callback, and hence scope too, is optional.
	 */
	 updateIconName: function(name, newName) {
		$assert(typeof name !== "undefined", "js.hera.hub.Service#updateIconName", "Name argument is undefined.");
		$assert(name === null || js.lang.Types.isString(name), "js.hera.hub.Service#updateIconName", "Name argument is not a string.");
		$assert(typeof newName !== "undefined", "js.hera.hub.Service#updateIconName", "New name argument is undefined.");
		$assert(newName === null || js.lang.Types.isString(newName), "js.hera.hub.Service#updateIconName", "New name argument is not a string.");

		var __callback__ = arguments[2];
		$assert(typeof __callback__ === "undefined" || js.lang.Types.isFunction(__callback__), "js.hera.hub.Service#updateIconName", "Callback is not a function.");
		var __scope__ = arguments[3];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "js.hera.hub.Service#updateIconName", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("js.hera.hub.Service", "updateIconName");
		rmi.setParameters(name, newName);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Is icon used.
	 *
	 * @param java.lang.String iconName,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return boolean
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 isIconUsed: function(iconName) {
		$assert(typeof iconName !== "undefined", "js.hera.hub.Service#isIconUsed", "Icon name argument is undefined.");
		$assert(iconName === null || js.lang.Types.isString(iconName), "js.hera.hub.Service#isIconUsed", "Icon name argument is not a string.");

		var __callback__ = arguments[1];
		$assert(js.lang.Types.isFunction(__callback__), "js.hera.hub.Service#isIconUsed", "Callback is not a function.");
		var __scope__ = arguments[2];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "js.hera.hub.Service#isIconUsed", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("js.hera.hub.Service", "isIconUsed");
		rmi.setParameters(iconName);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Remove icon.
	 *
	 * @param java.lang.String iconName,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return void
	 * @assert callback is a {@link Function} and scope is an {@link Object}, if they are defined.
	 * @note since method return type is void, callback, and hence scope too, is optional.
	 */
	 removeIcon: function(iconName) {
		$assert(typeof iconName !== "undefined", "js.hera.hub.Service#removeIcon", "Icon name argument is undefined.");
		$assert(iconName === null || js.lang.Types.isString(iconName), "js.hera.hub.Service#removeIcon", "Icon name argument is not a string.");

		var __callback__ = arguments[1];
		$assert(typeof __callback__ === "undefined" || js.lang.Types.isFunction(__callback__), "js.hera.hub.Service#removeIcon", "Callback is not a function.");
		var __scope__ = arguments[2];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "js.hera.hub.Service#removeIcon", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("js.hera.hub.Service", "removeIcon");
		rmi.setParameters(iconName);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Get power meter value.
	 *
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return js.hera.hub.model.PowerMeterValue
	 * @throws java.lang.NumberFormatException
	 * @throws java.io.IOException
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 getPowerMeterValue: function() {
		var __callback__ = arguments[0];
		$assert(js.lang.Types.isFunction(__callback__), "js.hera.hub.Service#getPowerMeterValue", "Callback is not a function.");
		var __scope__ = arguments[1];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "js.hera.hub.Service#getPowerMeterValue", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("js.hera.hub.Service", "getPowerMeterValue");
		rmi.exec(__callback__, __scope__);
	}
};

if(typeof Service === 'undefined') {
	Service = js.hera.hub.Service;
}
