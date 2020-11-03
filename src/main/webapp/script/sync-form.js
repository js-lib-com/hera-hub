$package("js.widget");

/**
 * SyncForm class.
 * 
 * @author Iulian Rotaru
 * @since 1.0
 * 
 * @constructor Construct an instance of SyncForm class.
 * @param js.dom.Document ownerDoc element owner document,
 * @param Node node native {@link Node} instance.
 * @assert assertions imposed by {@link js.dom.Element#Element(js.dom.Document, Node)}.
 */
js.widget.SyncForm = function(ownerDoc, node) {
	this.$super(ownerDoc, node);

	this.click("[type='submit'],.submit", this._onSubmit);
	this.click("[type='reset'],.reset", this._onReset);

	var it = this.findByCss("[data-load]").it();
	while(it.hasNext()) {
		it.next().toString();
	}
};

js.widget.SyncForm.prototype = {
	click : function(selector, listener) {
		var target = this.getByCss(selector);
		if (target !== null) {
			target.on("click", listener, this);
		}
	},

	_onSubmit : function(ev) {
		ev.prevent();
		if (this.isValid()) {
			this.submit();
		}
	},

	_onReset : function(ev) {
		ev.prevent();
		this.reset();
	},

	/**
	 * Class string representation.
	 * 
	 * @return this class string representation.
	 */
	toString : function() {
		return "js.widget.SyncForm";
	}
};
$extends(js.widget.SyncForm, js.dom.Form);
$preload(js.widget.SyncForm);
