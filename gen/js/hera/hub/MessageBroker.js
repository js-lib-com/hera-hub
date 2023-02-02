MessageBroker = {
	 bindStream: function(stream) {
		var __callback__ = arguments[1];
		var __scope__ = arguments[2] || window;
		var url = "js/hera/hub/MessageBroker/bindStream.rmi";
		var parameters = [stream];

		this.fetch(url, parameters, __callback__, __scope__);
	},

	 unbindStream: function(stream) {
		var __callback__ = arguments[1];
		var __scope__ = arguments[2] || window;
		var url = "js/hera/hub/MessageBroker/unbindStream.rmi";
		var parameters = [stream];

		this.fetch(url, parameters, __callback__, __scope__);
	},

	 publish: function(message) {
		var __callback__ = arguments[1];
		var __scope__ = arguments[2] || window;
		var url = "js/hera/hub/MessageBroker/publish.rmi";
		var parameters = [message];

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
