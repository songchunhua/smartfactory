Ext.define('CMN.store.SearchStore', {
	extend : 'Ext.data.Store',

	storeId : 'cmn.search_store',

	autoLoad : false,

	model : 'CMN.model.Search',
	
	pageSize: 10,

	proxy : {
		type : 'ajax',
		url : 'module/CMN/data/searches.json',
		reader : {
			type : 'json'
		}
	}
});