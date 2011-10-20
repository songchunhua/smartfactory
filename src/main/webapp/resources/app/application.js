Ext.Loader.setConfig({
	enabled: true,
	paths: {
		'Ext.ux': 'js/ux'
	}
});

modules = [];

Ext.onReady(function() {
	Ext.application({
		name: 'SmartFactory',
		autoCreateViewport: false,
	
		controllers: [
			'CommonController'
		].concat(modules),
	
		launch: function() {
			// Login 등등.. 여기서
			// ...
			Ext.apply(SmartFactory, Ext.create('SmartFactory.App'));

			Ext.create('SmartFactory.view.Viewport').show();
		}
	});
});




// Ext.override(Ext.form.Field, {
//     initEvents: function () {
//         this.el.on(Ext.isIE ? "keydown" : "keypress", this.fireKey, this);
//         this.el.on("focus", this.onFocus, this);
//         this.el.on("blur", this.onBlur, this);
//         this.el.on("change", this.markDirty, this);
//         // reference to original value for reset
//         this.originalValue = this.getValue();
//     },
//     markDirty: function () {
//         if (this.isDirty() && this.originalValue != this.getValue()) {
//             if (!this.dirtyIcon) {
//                 var elp = this.el.parent('.x-form-element');
//                 this.dirtyIcon = elp.createChild({
//                     cls: 'x-grid3-dirty-cell'
//                 });
//                 this.dirtyIcon.position("absolute",0,0,0);
//                 this.dirtyIcon.setSize(10, 10);
//             }
//             this.alignDirtyIcon();
//             this.dirtyIcon.show();
//             this.on('resize', this.alignDirtyIcon, this);
//         } else {
//             if (this.dirtyIcon) {
//                 this.dirtyIcon.hide();
//             }
//         }
//     },
//     alignDirtyIcon: function () {
//         this.dirtyIcon.alignTo(this.el, 'tl', [0, 0]);
//     }
// });
