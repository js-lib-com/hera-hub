Service = {
	 getSystemDescriptor: function() {
		var __callback__ = arguments[0];
		var __scope__ = arguments[1] || window;
		var url = "js/hera/hub/Service/getSystemDescriptor.rmi";
		var parameters = [];

		this.fetch(url, parameters, __callback__, __scope__);
	},

	 invokeDeviceAction: function(deviceName, actionName, args) {
		var __callback__ = arguments[3];
		var __scope__ = arguments[4] || window;
		var url = "js/hera/hub/Service/invokeDeviceAction.rmi";
		var parameters = [deviceName, actionName, args];

		this.fetch(url, parameters, __callback__, __scope__);
	},

	 invoke: function(args) {
		var __callback__ = arguments[1];
		var __scope__ = arguments[2] || window;
		var url = "js/hera/hub/Service/invoke.rmi";
		var parameters = [args];

		this.fetch(url, parameters, __callback__, __scope__);
	},

	 getDevicesByZone: function(zoneId) {
		var __callback__ = arguments[1];
		var __scope__ = arguments[2] || window;
		var url = "js/hera/hub/Service/getDevicesByZone.rmi";
		var parameters = [zoneId];

		this.fetch(url, parameters, __callback__, __scope__);
	},

	 getDevicesByCategory: function(categoryId) {
		var __callback__ = arguments[1];
		var __scope__ = arguments[2] || window;
		var url = "js/hera/hub/Service/getDevicesByCategory.rmi";
		var parameters = [categoryId];

		this.fetch(url, parameters, __callback__, __scope__);
	},

	 createZone: function(zone) {
		var __callback__ = arguments[1];
		var __scope__ = arguments[2] || window;
		var url = "js/hera/hub/Service/createZone.rmi";
		var parameters = [zone];

		this.fetch(url, parameters, __callback__, __scope__);
	},

	 createCategory: function(category) {
		var __callback__ = arguments[1];
		var __scope__ = arguments[2] || window;
		var url = "js/hera/hub/Service/createCategory.rmi";
		var parameters = [category];

		this.fetch(url, parameters, __callback__, __scope__);
	},

	 readZone: function(zoneId) {
		var __callback__ = arguments[1];
		var __scope__ = arguments[2] || window;
		var url = "js/hera/hub/Service/readZone.rmi";
		var parameters = [zoneId];

		this.fetch(url, parameters, __callback__, __scope__);
	},

	 updateZone: function(zone) {
		var __callback__ = arguments[1];
		var __scope__ = arguments[2] || window;
		var url = "js/hera/hub/Service/updateZone.rmi";
		var parameters = [zone];

		this.fetch(url, parameters, __callback__, __scope__);
	},

	 createHost: function(host) {
		var __callback__ = arguments[1];
		var __scope__ = arguments[2] || window;
		var url = "js/hera/hub/Service/createHost.rmi";
		var parameters = [host];

		this.fetch(url, parameters, __callback__, __scope__);
	},

	 updateHost: function(host) {
		var __callback__ = arguments[1];
		var __scope__ = arguments[2] || window;
		var url = "js/hera/hub/Service/updateHost.rmi";
		var parameters = [host];

		this.fetch(url, parameters, __callback__, __scope__);
	},

	 deleteHost: function(hostId) {
		var __callback__ = arguments[1];
		var __scope__ = arguments[2] || window;
		var url = "js/hera/hub/Service/deleteHost.rmi";
		var parameters = [hostId];

		this.fetch(url, parameters, __callback__, __scope__);
	},

	 subscribeHosts: function() {
		var __callback__ = arguments[0];
		var __scope__ = arguments[1] || window;
		var url = "js/hera/hub/Service/subscribeHosts.rmi";
		var parameters = [];

		this.fetch(url, parameters, __callback__, __scope__);
	},

	 updateCategory: function(category) {
		var __callback__ = arguments[1];
		var __scope__ = arguments[2] || window;
		var url = "js/hera/hub/Service/updateCategory.rmi";
		var parameters = [category];

		this.fetch(url, parameters, __callback__, __scope__);
	},

	 deleteZone: function(zoneId) {
		var __callback__ = arguments[1];
		var __scope__ = arguments[2] || window;
		var url = "js/hera/hub/Service/deleteZone.rmi";
		var parameters = [zoneId];

		this.fetch(url, parameters, __callback__, __scope__);
	},

	 createDevice: function(device) {
		var __callback__ = arguments[1];
		var __scope__ = arguments[2] || window;
		var url = "js/hera/hub/Service/createDevice.rmi";
		var parameters = [device];

		this.fetch(url, parameters, __callback__, __scope__);
	},

	 readDevice: function(deviceId) {
		var __callback__ = arguments[1];
		var __scope__ = arguments[2] || window;
		var url = "js/hera/hub/Service/readDevice.rmi";
		var parameters = [deviceId];

		this.fetch(url, parameters, __callback__, __scope__);
	},

	 updateDevice: function(device) {
		var __callback__ = arguments[1];
		var __scope__ = arguments[2] || window;
		var url = "js/hera/hub/Service/updateDevice.rmi";
		var parameters = [device];

		this.fetch(url, parameters, __callback__, __scope__);
	},

	 deleteDevice: function(deviceId) {
		var __callback__ = arguments[1];
		var __scope__ = arguments[2] || window;
		var url = "js/hera/hub/Service/deleteDevice.rmi";
		var parameters = [deviceId];

		this.fetch(url, parameters, __callback__, __scope__);
	},

	 getZones: function() {
		var __callback__ = arguments[0];
		var __scope__ = arguments[1] || window;
		var url = "js/hera/hub/Service/getZones.rmi";
		var parameters = [];

		this.fetch(url, parameters, __callback__, __scope__);
	},

	 getCategories: function() {
		var __callback__ = arguments[0];
		var __scope__ = arguments[1] || window;
		var url = "js/hera/hub/Service/getCategories.rmi";
		var parameters = [];

		this.fetch(url, parameters, __callback__, __scope__);
	},

	 getDevices: function() {
		var __callback__ = arguments[0];
		var __scope__ = arguments[1] || window;
		var url = "js/hera/hub/Service/getDevices.rmi";
		var parameters = [];

		this.fetch(url, parameters, __callback__, __scope__);
	},

	 getZoneItems: function() {
		var __callback__ = arguments[0];
		var __scope__ = arguments[1] || window;
		var url = "js/hera/hub/Service/getZoneItems.rmi";
		var parameters = [];

		this.fetch(url, parameters, __callback__, __scope__);
	},

	 getCategoryItems: function() {
		var __callback__ = arguments[0];
		var __scope__ = arguments[1] || window;
		var url = "js/hera/hub/Service/getCategoryItems.rmi";
		var parameters = [];

		this.fetch(url, parameters, __callback__, __scope__);
	},

	 getHostnameItems: function() {
		var __callback__ = arguments[0];
		var __scope__ = arguments[1] || window;
		var url = "js/hera/hub/Service/getHostnameItems.rmi";
		var parameters = [];

		this.fetch(url, parameters, __callback__, __scope__);
	},

	 getDeviceClasses: function() {
		var __callback__ = arguments[0];
		var __scope__ = arguments[1] || window;
		var url = "js/hera/hub/Service/getDeviceClasses.rmi";
		var parameters = [];

		this.fetch(url, parameters, __callback__, __scope__);
	},

	 getDevicesByCategoryName: function(categoryName) {
		var __callback__ = arguments[1];
		var __scope__ = arguments[2] || window;
		var url = "js/hera/hub/Service/getDevicesByCategoryName.rmi";
		var parameters = [categoryName];

		this.fetch(url, parameters, __callback__, __scope__);
	},

	 deleteCategory: function(categoryId) {
		var __callback__ = arguments[1];
		var __scope__ = arguments[2] || window;
		var url = "js/hera/hub/Service/deleteCategory.rmi";
		var parameters = [categoryId];

		this.fetch(url, parameters, __callback__, __scope__);
	},

	 getHosts: function() {
		var __callback__ = arguments[0];
		var __scope__ = arguments[1] || window;
		var url = "js/hera/hub/Service/getHosts.rmi";
		var parameters = [];

		this.fetch(url, parameters, __callback__, __scope__);
	},

	 getIcons: function() {
		var __callback__ = arguments[0];
		var __scope__ = arguments[1] || window;
		var url = "js/hera/hub/Service/getIcons.rmi";
		var parameters = [];

		this.fetch(url, parameters, __callback__, __scope__);
	},

	 uploadIcon: function(icon) {
		var __callback__ = arguments[1];
		var __scope__ = arguments[2] || window;
		var url = "js/hera/hub/Service/uploadIcon.rmi";
		var parameters = [icon];

		this.fetch(url, parameters, __callback__, __scope__);
	},

	 getIconNames: function() {
		var __callback__ = arguments[0];
		var __scope__ = arguments[1] || window;
		var url = "js/hera/hub/Service/getIconNames.rmi";
		var parameters = [];

		this.fetch(url, parameters, __callback__, __scope__);
	},

	 updateIconName: function(name, newName) {
		var __callback__ = arguments[2];
		var __scope__ = arguments[3] || window;
		var url = "js/hera/hub/Service/updateIconName.rmi";
		var parameters = [name, newName];

		this.fetch(url, parameters, __callback__, __scope__);
	},

	 isIconUsed: function(iconName) {
		var __callback__ = arguments[1];
		var __scope__ = arguments[2] || window;
		var url = "js/hera/hub/Service/isIconUsed.rmi";
		var parameters = [iconName];

		this.fetch(url, parameters, __callback__, __scope__);
	},

	 removeIcon: function(iconName) {
		var __callback__ = arguments[1];
		var __scope__ = arguments[2] || window;
		var url = "js/hera/hub/Service/removeIcon.rmi";
		var parameters = [iconName];

		this.fetch(url, parameters, __callback__, __scope__);
	},

	 getPowerMeterValue: function() {
		var __callback__ = arguments[0];
		var __scope__ = arguments[1] || window;
		var url = "js/hera/hub/Service/getPowerMeterValue.rmi";
		var parameters = [];

		this.fetch(url, parameters, __callback__, __scope__);
	},

	fetch: function(url, parameters, callback, scope) {
		if(this.contextBaseUrl) {
			url = this.contextBaseUrl + url;
		}
		
		var responsePromise = fetch(url, {
			method: 'POST',
			headers: { 'Content-Type': 'application/json' },
			body: JSON.stringify(parameters)
		});

		var responseOK = true;
		var jsonPromise = responsePromise.then(response => { 
			if(!response.ok) {
				responseOK = false;
			}
			if(response.headers.get("Content-Type")) { 
				return response.json(); 
			}
		});

		jsonPromise.then(json => {
			if(!responseOK) {
				if(this.errorHandler) {
					this.errorHandler(json);
				}
				console.error(json);
				return;
			}
			if (callback) {
				callback.call(scope, json);
			}
		});
	}
};
