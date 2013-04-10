<aside>
  <section>
    <h1 class="post_header_gradient theme_font">Latest Posts</h1>
    <ul>
      % for post in bf.config.blog.iter_posts_published(5):
      <li><a href="${post.path}">${post.title}</a></li>
      % endfor
    </ul>
  </section>
  <section>
    <%
      if hasattr(bf.template_context, 'category'):
        detcat = bf.template_context.category
      else:
        detcat = 'cerisara'
      endif
    %>
    <h1 class="post_header_gradient theme_font">From Twitter "${detcat}"</h1>
    <div id="on_twitter">
      <div id="tweets${detcat}"></div>
      <a href="http://search.twitter.com/search?q=${detcat}" style="float: right">See more tweets</a>
    </div>
  </section>
</aside>
