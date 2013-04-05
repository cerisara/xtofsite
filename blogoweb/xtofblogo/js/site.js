function twitterSidebar(){
  jQuery("#tweets").tweet({
    avatar_size: 32,
    count: 5,
    query: "cerisara",
    loading_text: "searching twitter..."
  });  
}

jQuery(document).ready(function(){
  twitterSidebar();
});
