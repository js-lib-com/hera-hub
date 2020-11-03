$package("js.ui");

js.ui.Panorama = function(ownerDoc, node) {
	this.$super(ownerDoc, node);

	this._lock = false;

	this._ownerDoc.on("mousedown", this._onMouseDown, this);
	this._ownerDoc.on("touchstart", this._onMouseDown, this);

	this._left = 30;
	this._render();
};

js.ui.Panorama.prototype = {
	lock : function() {
		this._lock = true;
	},

	unlock : function() {
		this._lock = false;
	},

	_render : function() {
		this.style.setLeft(this._left);
		// request for display drawing should be called for every frame
		window.requestAnimationFrame(this._render.bind(this));
	},

	_onMouseDown : function(ev) {
		if(this._lock) {
			return;
		}
		ev.prevent();

		this._startPageX = ev.pageX;
		this._startPageY = ev.pageY;
		this._startLeft = parseInt(this.style.get('left'));

		this._ownerDoc.on("mousemove", this._onMouseMove, this);
		this._ownerDoc.on("touchmove", this._onMouseMove, this);

		this._ownerDoc.on("mouseup", this._onMouseUp, this);
		this._ownerDoc.on("touchend", this._onMouseUp, this);
	},

	_onMouseMove : function(ev) {
		var deltaPageX = ev.pageX - this._startPageX;
		this._left = this._startLeft + deltaPageX;
	},

	_onMouseUp : function(ev) {
		this._ownerDoc.un('mousemove', this._onMouseMove);
		this._ownerDoc.un('touchmove', this._onMouseMove);

		this._ownerDoc.un('mouseup', this._onMouseUp);
		this._ownerDoc.un('touchend', this._onMouseUp);

		if (this._left > 0) {
			this._left = 30;
		}

		// minimum left position is determined heuristically
		// it is not very clear why panorama width, that is, this.style.getWidth() is changing while dragging
		var minLeft = WinMain.getWidth() - this.style.getWidth() - 30;
		if (minLeft < 0 && this._left < minLeft) {
			this._left = minLeft;
		}
	},

	toString : function() {
		return "js.ui.Panorama";
	}
};
$extends(js.ui.Panorama, js.dom.Element);
