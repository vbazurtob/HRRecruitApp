$( document ).ready(function(){
	
	try{
		$(".dropdown-trigger").dropdown();
	}catch(e){
		console.log('ERR ' + e);
	}

});