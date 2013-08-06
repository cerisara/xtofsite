function twitterSidebar(){
  jQuery("#tweetscerisara").tweet({
    avatar_size: 32,
    count: 5,
    query: "cerisara",
    loading_text: "searching twitter..."
  });  
  jQuery("#tweetsnlp").tweet({
    avatar_size: 32,
    count: 5,
    query: "nlp",
    loading_text: "searching twitter..."
  });  
  jQuery("#tweetsgo").tweet({
    avatar_size: 32,
    count: 5,
    query: "igo",
    loading_text: "searching twitter..."
  });  
}

jQuery(document).ready(function(){
  twitterSidebar();
});
