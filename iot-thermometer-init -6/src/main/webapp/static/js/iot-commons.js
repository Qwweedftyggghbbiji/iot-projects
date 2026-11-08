function showMsg(msg,lvl){
	  let id=Date.now();
	  let msgHtml="";
      $("#msgPanel").append("<div class='popupalert alert alert-"+lvl+"' role='alert' id='"+id+"' onclick=\"$('#"+id+"').fadeOut()\">"+msg+"</div>").fadeIn();
      setTimeout(function(){
         $("#"+id).fadeOut();
      },3000);
    }
$(function(){
  $(".popupalert").click(function(){
    $(this).fadeOut();
  });
});