function showMsg(msg,lvl){
	  let id=Date.now();
	  let msgHtml="";
      $("#msgPanel").append("<div class='alert alert-"+lvl+"' role='alert' id='"+id+"'>"+msg+"</div>").fadeIn();
      setTimeout(function(){
         $("#"+id).fadeOut();
      },4000);
    }